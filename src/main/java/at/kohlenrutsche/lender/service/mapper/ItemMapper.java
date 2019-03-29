package at.kohlenrutsche.lender.service.mapper;

import at.kohlenrutsche.lender.domain.*;
import at.kohlenrutsche.lender.service.dto.ItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Item and its DTO ItemDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, RoomMapper.class})
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.login", target = "ownerLogin")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.name", target = "locationName")
    ItemDTO toDto(Item item);

    @Mapping(target = "costs", ignore = true)
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "locationId", target = "location")
    Item toEntity(ItemDTO itemDTO);

    default Item fromId(Long id) {
        if (id == null) {
            return null;
        }
        Item item = new Item();
        item.setId(id);
        return item;
    }
}
