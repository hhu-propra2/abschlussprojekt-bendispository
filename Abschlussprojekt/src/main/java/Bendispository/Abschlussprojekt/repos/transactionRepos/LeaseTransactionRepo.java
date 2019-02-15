package Bendispository.Abschlussprojekt.repos.transactionRepos;

import Bendispository.Abschlussprojekt.model.transactionModels.LeaseTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LeaseTransactionRepo extends CrudRepository<LeaseTransaction, Long> {
    List<LeaseTransaction> findAll();

    Optional<LeaseTransaction> findByRequestId(Long id);
}