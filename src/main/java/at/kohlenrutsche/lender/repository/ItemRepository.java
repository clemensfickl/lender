package at.kohlenrutsche.lender.repository;

import at.kohlenrutsche.lender.domain.Item;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Item entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select item from Item item where item.owner.login = ?#{principal.username}")
    List<Item> findByOwnerIsCurrentUser();

}
