package com.finpulse.transaction_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "transaction_ledger",
        indexes = {
                @Index(name = "ix_ledger_transaction", columnList = "transaction_id"),
                @Index(name = "ix_ledger_account", columnList = "account_id")
        }
)
public class TransactionLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ledger_id")
    private Long ledgerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_ledger_transaction"))
    private Transaction transaction;

    @Column(name = "account_id", nullable = false)
    private Long accountId; // reference to account_db (no FK)

    public enum EntryType { DEBIT, CREDIT }
    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type", nullable = false, length = 10)
    private EntryType entryType;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}
