package org.ms.library.rental.service;

import org.ms.library.rental.dto.*;
import org.ms.library.rental.entities.Loan;
import org.ms.library.rental.entities.LoanItem;
import org.ms.library.rental.entities.LoanStatus;
import org.ms.library.rental.feign.CatalogFeign;
import org.ms.library.rental.feign.ClientFeign;
import org.ms.library.rental.feign.UserFeign;
import org.ms.library.rental.repository.LoanItemRepository;
import org.ms.library.rental.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoanService {
    @Autowired
    private CatalogFeign catalogFeign;

    @Autowired
    private ClientFeign clientFeign;

    @Autowired
    private UserFeign userFeign;

    private final LoanRepository loanRepository;
    private final LoanItemRepository loanItemRepository;

    public LoanService(LoanRepository loanRepository, LoanItemRepository loanItemRepository) {
        this.loanRepository = loanRepository;
        this.loanItemRepository = loanItemRepository;
    }

    @Transactional
    public LoanDTO orderNewLoan(LoanDTO loan) {

        Loan loanEntity = new Loan();
        ResponseEntity<ClientDTO> clientFeignById = clientFeign.findById(loan.getClientId());

        if (clientFeignById.getBody().getId() == null) {

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

    }


    @Transactional(readOnly = true)
    public RentalsBookCategoriesClientDTO getLoanInfoByClientId(Long clientId, Authentication authentication) {

        ClientDTO clientFoundByID = clientFeign.findById(clientId).getBody();
        if (clientFoundByID == null) {
            throw new NoSuchElementException("Client not found");
        }

        if (!isUserAdminOrOwner(clientFoundByID, authentication)) {
            return null;
        }

        List<Loan> loanByClientId = loanRepository.findLoansByClientId(clientFoundByID.getId()).orElseThrow(NoSuchElementException::new);

        Set<Long> allBookIds = loanByClientId.stream()
                .flatMap(loan -> loan.getItems().stream())
                .map(LoanItem::getBookId)
                .collect(Collectors.toSet());

        Map<Long, BookCategoriesDTO> bookDTOMap = new HashMap<>();
        if (!allBookIds.isEmpty()) {
            Set<BookCategoriesDTO> body = catalogFeign.findBooksByIds(allBookIds).getBody();
            if (body != null) {
                for (BookCategoriesDTO book : body) {
                    bookDTOMap.put(book.getId(), book);
                }
            }
        }

        return new RentalsBookCategoriesClientDTO(clientFoundByID.getName(), clientFoundByID.getLastName(), clientFoundByID.getCpf(), clientFoundByID.getPhone(), loanByClientId, bookDTOMap);
    }


    @Transactional
    public void returnBooks (UUID loanId, Long clientId) throws Exception {



        Loan loan = loanRepository.findLoanById(loanId).orElseThrow(() -> new NoSuchElementException("Rental not found"));


        if (loan.getClientId().equals(clientId)) {


            loan.setReturnDate(LocalDateTime.now());
            loan.setStatus(LoanStatus.RETURNED);
            Loan updatedEntity = loanRepository.save(loan);

            for (LoanItem rentedItem : updatedEntity.getItems()) {


                catalogFeign.changeStockQuantity(rentedItem.getBookId(), rentedItem.getQuantity(), "INCREASE");


            }

        } else {


            throw new Exception("A error occurred, please check the data and try it again!");

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
