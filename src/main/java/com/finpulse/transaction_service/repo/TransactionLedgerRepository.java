package com.finpulse.transaction_service.repo;

import com.finpulse.transaction_service.model.TransactionLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionLedgerRepository extends JpaRepository<TransactionLedger, Long> {
    List<TransactionLedger> findByTransaction_TransactionId(Long transactionId);
}
