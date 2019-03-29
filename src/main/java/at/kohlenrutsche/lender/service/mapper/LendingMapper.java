package at.kohlenrutsche.lender.service.mapper;

import at.kohlenrutsche.lender.domain.*;
import at.kohlenrutsche.lender.service.dto.LendingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lending and its DTO LendingDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ItemMapper.class})
public interface LendingMapper extends EntityMapper<LendingDTO, Lending> {

    @Mapping(source = "borrower.id", target = "borrowerId")
    @Mapping(source = "borrower.login", target = "borrowerLogin")
    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.name", target = "itemName")
    LendingDTO toDto(Lending lending);

    @Mapping(source = "borrowerId", target = "borrower")
    @Mapping(source = "itemId", target = "item")
    Lending toEntity(LendingDTO lendingDTO);

    default Lending fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lending lending = new Lending();
        lending.setId(id);
        return lending;
    }
}
