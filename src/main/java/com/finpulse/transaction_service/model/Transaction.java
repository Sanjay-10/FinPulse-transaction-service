package com.finpulse.transaction_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "transactions",
        indexes = {
                @Index(name = "ix_txn_from_account", columnList = "from_account_id"),
                @Index(name = "ix_txn_to_account", columnList = "to_account_id"),
                @Index(name = "ix_txn_status", columnList = "status"),
                @Index(name = "ix_txn_type", columnList = "transaction_type")
        }
)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "from_account_id")
    private Long fromAccountId; // from account (no FK)

    @Column(name = "to_account_id")
    private Long toAccountId;   // to account (no FK)

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 10)
    private String currency;

    public enum TransactionType { DEPOSIT, WITHDRAWAL, TRANSFER }
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 30)
    private TransactionType transactionType;

    public enum TransactionStatus { PENDING, COMPLETED, FAILED, REVERSED }
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TransactionStatus status;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionLedger> ledgerEntries;

    @PrePersist
    void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}

