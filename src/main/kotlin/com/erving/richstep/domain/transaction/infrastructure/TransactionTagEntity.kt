package com.erving.richstep.domain.transaction.infrastructure

import com.erving.richstep.domain.tag.infrastructure.TagEntity
import com.erving.richstep.domain.transaction.domain.TransactionTag
import jakarta.persistence.*

@Entity
@Table(name = "transaction_tag")
class TransactionTagEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    val transaction: TransactionEntity,

    @ManyToOne
    @JoinColumn(name = "tag_id")
    val tag: TagEntity
) {

    fun toModel(): TransactionTag {
        return TransactionTag(
            id = this.id,
            transactionId = this.transaction.id,
            tag = this.tag.toModel()
        )
    }

    companion object {
        fun from(transactionTag: TransactionTag, transactionEntity: TransactionEntity): TransactionTagEntity {
            return TransactionTagEntity(
                id = transactionTag.id,
                transaction = transactionEntity,
                tag = TagEntity.fromModel(transactionTag.tag)
            )
        }
    }
}