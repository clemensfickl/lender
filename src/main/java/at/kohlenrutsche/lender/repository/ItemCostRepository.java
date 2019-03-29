package at.kohlenrutsche.lender.repository;

import at.kohlenrutsche.lender.domain.ItemCost;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemCost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemCostRepository extends JpaRepository<ItemCost, Long> {

}
