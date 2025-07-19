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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        clientFeign.findById(rental.getClientId());
        copyDTOToEntity(rental, rentalEntity);
        Map<Long, BookDTO> bookDetailsMap = new HashMap<>();

        Set<BookDTO> body = catalogFeign.findBooksByIds(rental.getItems().stream().map(x -> x.getBookId()).collect(Collectors.toSet())).getBody();

        for (BookDTO book : body) {

            bookDetailsMap.put(book.getId(), book);


        }

        rentalRepository.save(rentalEntity);
        return new RentalDTO(rentalEntity, bookDetailsMap);

    }


    @Transactional(readOnly = true)
    public RentalsBookClientDTO getRentalInfoByClientId(Long clientId) {

        ClientDTO clientFoundByID = clientFeign.findById(clientId).getBody();

        if (clientFoundByID != null) {

            List<Rental> rentalsByClientId = rentalRepository.findRentalsByClientId(clientFoundByID.getId()).orElseThrow(NoSuchElementException::new);

            Map<Long, BookDTO> bookDTOMap = new HashMap<>();

            for (Rental rental : rentalsByClientId) {

                Set<BookDTO> body = catalogFeign.findBooksByIds(rental.getItems().stream().map(x -> x.getBookId()).collect(Collectors.toSet())).getBody();

                for (BookDTO book : body) {

                    bookDTOMap.put(book.getId(), book);

                }

            }

            return new RentalsBookClientDTO(clientFoundByID.getName(), clientFoundByID.getLastName(), clientFoundByID.getCpf(), clientFoundByID.getPhone(), rentalsByClientId,
                   bookDTOMap);


        }

        return null;

    }


    private void copyDTOToEntity(RentalDTO dto, Rental entity) {

        entity.setClientId(dto.getClientId());
        entity.setRentalDate(dto.getRentalDate());
        entity.setDueDate(dto.getDueDate());
        entity.setStatus(RentalStatus.ACTIVE);
        Set<RentalItem> rentalItemSet = new HashSet<>();

        for (RentalItemDTO item : dto.getItems()) {

            RentalItem rentalItem = new RentalItem();
            rentalItem.setBookId(item.getBookId());
            rentalItemSet.add(rentalItem);
        }

        entity.getItems().addAll(rentalItemSet);


    }


}
