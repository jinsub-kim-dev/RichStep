package com.erving.richstep.domain.transaction.infrastructure

import com.erving.richstep.domain.transaction.domain.Transaction
import com.erving.richstep.domain.transaction.domain.TransactionType
import com.erving.richstep.domain.user.infrastructure.UserEntity
import jakarta.persistence.*
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

    @OneToMany(mappedBy = "transaction", cascade = [CascadeType.ALL], orphanRemoval = true)
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
                    TransactionTagEntity.from(it, transactionEntity)
                }
            )

            return transactionEntity
        }
    }
}