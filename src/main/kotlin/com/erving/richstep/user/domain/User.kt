package com.erving.richstep.user.domain

import com.erving.richstep.tag.domain.Tag
import com.erving.richstep.tag.domain.TagLoader
import java.time.LocalDateTime

data class User(
    var id: Long? = null,
    val email: String,
    val createdAt: LocalDateTime? = null,
    val modifiedAt: LocalDateTime? = null,
) {

    fun getOrCreateTags(tagNames: List<String>, tagLoader: TagLoader): List<Tag> {
        return tagLoader.load(this, tagNames)
    }


    companion object {
        fun from(userCreate: UserCreate): User {
            return User(
                email = userCreate.email
            )
        }
    }
}