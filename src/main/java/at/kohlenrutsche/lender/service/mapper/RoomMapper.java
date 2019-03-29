package at.kohlenrutsche.lender.service.mapper;

import at.kohlenrutsche.lender.domain.*;
import at.kohlenrutsche.lender.service.dto.RoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Room and its DTO RoomDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoomMapper extends EntityMapper<RoomDTO, Room> {



    default Room fromId(Long id) {
        if (id == null) {
            return null;
        }
        Room room = new Room();
        room.setId(id);
        return room;
    }
}
