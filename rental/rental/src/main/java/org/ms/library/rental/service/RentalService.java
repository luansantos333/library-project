package org.ms.library.rental.service;

import org.ms.library.rental.dto.*;
import org.ms.library.rental.entities.Rental;
import org.ms.library.rental.entities.RentalItem;
import org.ms.library.rental.entities.RentalStatus;
import org.ms.library.rental.feign.CatalogFeign;
import org.ms.library.rental.feign.ClientFeign;
import org.ms.library.rental.repository.RentalItemRepository;
import org.ms.library.rental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RentalService {
    @Autowired
    private CatalogFeign catalogFeign;

    @Autowired
    private ClientFeign clientFeign;
    private final RentalRepository rentalRepository;
    private final RentalItemRepository rentalItemRepository;

    public RentalService(RentalRepository rentalRepository, RentalItemRepository rentalItemRepository) {
        this.rentalRepository = rentalRepository;
        this.rentalItemRepository = rentalItemRepository;
    }

    @Transactional
    public RentalDTO orderNewRental(RentalDTO rental) {

        Rental rentalEntity = new Rental();
        ResponseEntity<ClientDTO> clientFeignById = clientFeign.findById(rental.getClientId());

        if (clientFeignById.getBody().getId() == null) {

            throw new NoSuchElementException("Client not found");

        }

        copyDTOToEntity(rental, rentalEntity);
        Map<Long, BookCategoriesDTO> bookDetailsMap = new HashMap<>();

        Set<BookCategoriesDTO> body = catalogFeign.findBooksByIds(rental.getItems().stream().map(x -> x.getBookId()).collect(Collectors.toSet())).getBody();
        for (BookCategoriesDTO book : body) {
            bookDetailsMap.put(book.getId(), book);
        }

        for (RentalItemDTO rentalItemDTO : rental.getItems()) {



            catalogFeign.changeStockQuantity(rentalItemDTO.getBookId(), rentalItemDTO.getQuantity(), "DECREASE");

        }
        rentalRepository.save(rentalEntity);
        return new RentalDTO(rentalEntity, bookDetailsMap);

    }


    @Transactional(readOnly = true)
    public RentalsBookCategoriesClientDTO getRentalInfoByClientId(Long clientId) {

        ClientDTO clientFoundByID = clientFeign.findById(clientId).getBody();

        if (clientFoundByID != null) {

            List<Rental> rentalsByClientId = rentalRepository.findRentalsByClientId(clientFoundByID.getId()).orElseThrow(NoSuchElementException::new);

            Map<Long, BookCategoriesDTO> bookDTOMap = new HashMap<>();

            for (Rental rental : rentalsByClientId) {

                Set<BookCategoriesDTO> body = catalogFeign.findBooksByIds(rental.getItems().stream().map(x -> x.getBookId()).collect(Collectors.toSet())).getBody();

                for (BookCategoriesDTO book : body) {

                    bookDTOMap.put(book.getId(), book);

                }

            }

            return new RentalsBookCategoriesClientDTO(clientFoundByID.getName(), clientFoundByID.getLastName(), clientFoundByID.getCpf(), clientFoundByID.getPhone(), rentalsByClientId, bookDTOMap);


        }

        return null;

    }


    @Transactional
    public void returnBooks (UUID rentalId, Long clientId) throws Exception {



        Rental rental = rentalRepository.findRentalById(rentalId).orElseThrow(() -> new NoSuchElementException("Rental not found"));


        if (rental.getClientId().equals(clientId)) {


            rental.setReturnDate(LocalDateTime.now());
            rental.setStatus(RentalStatus.RETURNED);
            Rental updatedEntity = rentalRepository.save(rental);

            for (RentalItem rentedItem : updatedEntity.getItems()) {


                catalogFeign.changeStockQuantity(rentedItem.getBookId(), rentedItem.getQuantity(), "INCREASE");


            }

        } else {


            throw new Exception("A error occurred, please check the data and try it again!");

        }


    }



    private void copyDTOToEntity(RentalDTO dto, Rental entity) {

        entity.setClientId(dto.getClientId());
        entity.setRentalDate(dto.getRentalDate());

        if (dto.getDueDate() == null || dto.getDueDate().toString().isBlank()) {

            entity.setDueDate(dto.getRentalDate().plusDays(30L));

        } else {

            entity.setDueDate(dto.getDueDate());

        }




        entity.setStatus(RentalStatus.ACTIVE);
        Set<RentalItem> rentalItemSet = new HashSet<>();

        for (RentalItemDTO item : dto.getItems()) {

            RentalItem rentalItem = new RentalItem();
            rentalItem.setBookId(item.getBookId());
            rentalItem.setPrice(catalogFeign.findOneBook(item.getBookId()).getBody().getPrice());
            rentalItem.setQuantity(item.getQuantity());
            rentalItemSet.add(rentalItem);
        }

        entity.setTotal(rentalItemSet.stream().mapToDouble(x -> x.getPrice()).sum());
        entity.getItems().addAll(rentalItemSet);


    }


}
