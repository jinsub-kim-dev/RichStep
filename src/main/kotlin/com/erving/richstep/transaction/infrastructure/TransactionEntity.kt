package com.erving.richstep.transaction.infrastructure

import com.erving.richstep.transaction.domain.Transaction
import com.erving.richstep.transaction.domain.TransactionType
import com.erving.richstep.user.infrastructure.UserEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transaction")
class TransactionEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(targetEntity = UserEntity::class)
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    @Column(name = "date")
    val date: LocalDateTime,

    @Column(name = "amount")
    val amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    val type: TransactionType,

    @Column(name = "description")
    val description: String? = null,

    @JoinColumn(name = "transaction_id")
    val transactionTags: MutableList<TransactionTagEntity> = mutableListOf()
) {

    fun toModel(): Transaction {
        return Transaction(
            id = this.id,
            user = this.user.toModel(),
            date = this.date,
            amount = this.amount,
            type = this.type,
            description = this.description,
            transactionTags = this.transactionTags
                .map { it.toModel() }
                .toMutableList()
        )
    }

    companion object {
        fun fromModel(transaction: Transaction): TransactionEntity {
            val transactionEntity = TransactionEntity(
                id = transaction.id,
                user = UserEntity.fromModel(transaction.user),
                date = transaction.date,
                amount = transaction.amount,
                type = transaction.type,
                description = transaction.description,
                transactionTags = mutableListOf()
            )

            transactionEntity.transactionTags.addAll(
                transaction.transactionTags.map {
                    TransactionTagEntity.fromModel(it)
                }
            )

            return transactionEntity
        }
    }
}