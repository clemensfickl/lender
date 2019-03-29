package at.kohlenrutsche.lender.repository;

import at.kohlenrutsche.lender.domain.Lending;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Lending entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LendingRepository extends JpaRepository<Lending, Long> {

    @Query("select lending from Lending lending where lending.borrower.login = ?#{principal.username}")
    List<Lending> findByBorrowerIsCurrentUser();

}
