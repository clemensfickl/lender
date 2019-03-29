package at.kohlenrutsche.lender.service.mapper;

import at.kohlenrutsche.lender.domain.*;
import at.kohlenrutsche.lender.service.dto.ItemCostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ItemCost and its DTO ItemCostDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface ItemCostMapper extends EntityMapper<ItemCostDTO, ItemCost> {

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    ItemCostDTO toDto(ItemCost itemCost);

    @Mapping(source = "itemId", target = "item")
    ItemCost toEntity(ItemCostDTO itemCostDTO);

    default ItemCost fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemCost itemCost = new ItemCost();
        itemCost.setId(id);
        return itemCost;
    }
}
