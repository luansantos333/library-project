package org.ms.library.rental.service;

import org.ms.library.rental.dto.ClientDTO;
import org.ms.library.rental.dto.RentalBookClientDTO;
import org.ms.library.rental.dto.RentalDTO;
import org.ms.library.rental.dto.RentalItemDTO;
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

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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
        rentalRepository.save(rentalEntity);
        return new RentalDTO(rentalEntity);

    }


    @Transactional(readOnly = true)
    public RentalBookClientDTO getRentalInfoByClientId(Long clientId) {

        ClientDTO clientFoundByID = clientFeign.findById(clientId).getBody();

        if (clientFoundByID != null) {

            List<Rental> rentalsByClientId = rentalRepository.findRentalsByClientId(clientFoundByID.getId()).orElseThrow(NoSuchElementException::new);


            return new RentalBookClientDTO(clientFoundByID.getName(), clientFoundByID.getLastName(),
                    clientFoundByID.getCpf(), clientFoundByID.getPhone(), rentalsByClientId);

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

            rentalItem.setRental(entity);
            rentalItem.setBookId(item.getBookId());
            rentalItemRepository.save(rentalItem);
            rentalItemSet.add(rentalItem);
        }

        entity.getItems().addAll(rentalItemSet);


    }


}
