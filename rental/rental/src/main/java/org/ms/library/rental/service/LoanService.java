package org.ms.library.rental.service;

import feign.FeignException;
import org.ms.library.rental.dto.*;
import org.ms.library.rental.entities.Loan;
import org.ms.library.rental.entities.LoanItem;
import org.ms.library.rental.entities.LoanStatus;
import org.ms.library.rental.feign.CatalogFeign;
import org.ms.library.rental.feign.ClientFeign;
import org.ms.library.rental.feign.UserFeign;
import org.ms.library.rental.repository.LoanItemRepository;
import org.ms.library.rental.repository.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final LoanItemRepository loanItemRepository;
    @Autowired
    private CatalogFeign catalogFeign;
    @Autowired
    private ClientFeign clientFeign;
    @Autowired
    private UserFeign userFeign;
    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    public LoanService(LoanRepository loanRepository, LoanItemRepository loanItemRepository) {
        this.loanRepository = loanRepository;
        this.loanItemRepository = loanItemRepository;
    }

    @Transactional
    public LoanDTO orderNewLoan(LoanDTO loan) throws Exception {

        try {
            Loan loanEntity = new Loan();
            ResponseEntity<ClientDTO> clientFeignById = clientFeign.findById(loan.getClientId());

            loan.setLoanDate(LocalDateTime.now());

            if (clientFeignById.getBody().getId() == null) {

                logger.error("No client found with id {}", loan.getClientId());
                throw new NoSuchElementException("Client not found");

            }

            copyDTOToEntity(loan, loanEntity);
            Map<Long, BookCategoriesDTO> bookDetailsMap = new HashMap<>();


            Set<BookCategoriesDTO> body = catalogFeign.findBooksByIds(loan.getItems().stream().map(x -> x.getBookId()).collect(Collectors.toSet())).getBody();
            for (BookCategoriesDTO book : body) {
                bookDetailsMap.put(book.getId(), book);
            }

            for (LoanItemDTO loanItemDTO : loan.getItems()) {

                catalogFeign.changeStockQuantity(loanItemDTO.getBookId(), loanItemDTO.getQuantity(), "DECREASE");

            }
            loanRepository.save(loanEntity);
            return new LoanDTO(loanEntity, bookDetailsMap);
        } catch (FeignException ex) {

            logger.error("There was a error while making a feign request. Status:{}\nMessage: {}", ex.status(), ex.getMessage());
            throw new RuntimeException("There was a error while accessing the catalog service\nPlease try it again!");
        }

    }


    @Transactional(readOnly = true)
    public RentalsBookCategoriesClientDTO getLoanInfoByClientId(Long clientId, Authentication authentication) {

        try {
            logger.info("Trying to fetch loan from client with id {}...", clientId);
            ClientDTO clientFoundByID = clientFeign.findById(clientId).getBody();
            if (clientFoundByID == null) {
                logger.error("No client found with id {}", clientId);
                throw new NoSuchElementException("Client not found");
            }

            if (!isUserAdminOrOwner(clientFoundByID, authentication)) {
                logger.error("User is not admin or owner of loan");
                return null;
            }

            List<Loan> loanByClientId = loanRepository.findLoansByClientId(clientFoundByID.getId()).orElseThrow(NoSuchElementException::new);

            Set<Long> allBookIds = loanByClientId.stream().flatMap(loan -> loan.getItems().stream()).map(LoanItem::getBookId).collect(Collectors.toSet());

            Map<Long, BookCategoriesDTO> bookDTOMap = new HashMap<>();
            if (!allBookIds.isEmpty()) {
                Set<BookCategoriesDTO> body = catalogFeign.findBooksByIds(allBookIds).getBody();
                if (body != null) {
                    for (BookCategoriesDTO book : body) {
                        bookDTOMap.put(book.getId(), book);
                    }
                }
            }

            logger.info("Found loans for client with id {}", clientId);
            return new RentalsBookCategoriesClientDTO(clientFoundByID.getName(), clientFoundByID.getLastName(), clientFoundByID.getCpf(), clientFoundByID.getPhone(), loanByClientId, bookDTOMap);
        } catch (FeignException ex) {

            logger.error("There was a feign exception. Status: {}\nMessage: {}", ex.status(), ex.getMessage());
            throw new RuntimeException("There was a error while accessing the catalog service", ex);


        }

        }


    @Transactional
    public void returnBooks(UUID loanId, Long clientId) throws Exception {


        try {
            Loan loan = loanRepository.findLoanById(loanId).orElseThrow(() -> {


                logger.error("Loan with id {} not found", loanId);
                return new NoSuchElementException("Loan not found");

            });


            if (loan.getClientId().equals(clientId)) {

                if (loan.getStatus().equals(LoanStatus.RETURNED)) {

                    logger.error("Loan with id {} has already been returned", loanId);
                    throw new Exception("Loan already returned");

                }

                loan.setReturnDate(LocalDateTime.now());
                loan.setStatus(LoanStatus.RETURNED);
                Loan updatedEntity = loanRepository.save(loan);

                for (LoanItem rentedItem : updatedEntity.getItems()) {


                    catalogFeign.changeStockQuantity(rentedItem.getBookId(), rentedItem.getQuantity(), "INCREASE");


                }

                logger.info("All rented items returned successfully");

            } else {

                logger.error("No loan found belonging to this client");
                throw new BadCredentialsException("Something went wrong with the returned loan");

            }
        } catch (FeignException ex) {


            logger.error("There was a error while making a feign request. Status: {}\nMessage: {}", ex.status(), ex.getMessage());
            throw new RuntimeException("There was a error while accessing the catalog service", ex);

        }


    }

    private boolean isUserAdminOrOwner(ClientDTO client, Authentication authentication) {
        if (authentication == null) {
            return false;
        }

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        if (jwtAuthenticationToken.getTokenAttributes().get("roles").toString().contains("ROLE_ADMIN")) {
            return true;
        }

        if (client.getUser_id() == null) {
            return false;
        }

        UserDTO user = userFeign.findUserById(client.getUser_id().toString()).getBody();
        if (user == null) {
            return false;
        }

        return jwtAuthenticationToken.getTokenAttributes().get("username").equals(user.getUsername());
    }


    private void copyDTOToEntity(LoanDTO dto, Loan entity) {

        entity.setClientId(dto.getClientId());
        entity.setLoanDate(dto.getLoanDate());

        if (dto.getDueDate() == null || dto.getDueDate().toString().isBlank()) {

            entity.setDueDate(dto.getLoanDate().plusDays(30L));

        } else {

            entity.setDueDate(dto.getDueDate());

        }


        entity.setStatus(LoanStatus.ACTIVE);
        Set<LoanItem> loanItemSet = new HashSet<>();

        for (LoanItemDTO item : dto.getItems()) {

            LoanItem loanItem = new LoanItem();
            loanItem.setBookId(item.getBookId());
            loanItem.setPrice(catalogFeign.findOneBook(item.getBookId()).getBody().getPrice());
            loanItem.setQuantity(item.getQuantity());
            loanItemSet.add(loanItem);
        }

        entity.setTotal(loanItemSet.stream().mapToDouble(x -> x.getPrice()).sum());
        entity.getItems().addAll(loanItemSet);


    }


}
