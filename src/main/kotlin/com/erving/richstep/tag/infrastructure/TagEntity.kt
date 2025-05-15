package com.erving.richstep.tag.infrastructure

import com.erving.richstep.tag.domain.Tag
import com.erving.richstep.user.infrastructure.UserEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tag")
class TagEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(targetEntity = UserEntity::class)
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    @Column(name = "name")
    val name: String
) {

    fun toModel(): Tag {
        return Tag(
            id = this.id,
            user = this.user.toModel(),
            name = this.name
        )
    }

    companion object {
        fun fromModel(tag: Tag): TagEntity {
            return TagEntity(
                id = tag.id,
                user = UserEntity.fromModel(tag.user),
                name = tag.name
            )
        }
    }
}