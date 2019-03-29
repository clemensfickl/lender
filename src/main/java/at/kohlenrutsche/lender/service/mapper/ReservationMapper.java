package at.kohlenrutsche.lender.service.mapper;

import at.kohlenrutsche.lender.domain.*;
import at.kohlenrutsche.lender.service.dto.ReservationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reservation and its DTO ReservationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ItemMapper.class})
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {

    @Mapping(source = "borrower.id", target = "borrowerId")
    @Mapping(source = "borrower.login", target = "borrowerLogin")
    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    ReservationDTO toDto(Reservation reservation);

    @Mapping(source = "borrowerId", target = "borrower")
    @Mapping(source = "itemId", target = "item")
    Reservation toEntity(ReservationDTO reservationDTO);

    default Reservation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setId(id);
        return reservation;
    }
}
