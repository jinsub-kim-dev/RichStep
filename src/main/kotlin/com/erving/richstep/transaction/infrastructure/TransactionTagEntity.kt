package com.erving.richstep.transaction.infrastructure

import com.erving.richstep.tag.infrastructure.TagEntity
import com.erving.richstep.transaction.domain.TransactionTag
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

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