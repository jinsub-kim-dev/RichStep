package com.erving.richstep.domain.tag.infrastructure

import com.erving.richstep.domain.tag.domain.Tag
import com.erving.richstep.domain.user.infrastructure.UserEntity
import jakarta.persistence.*

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