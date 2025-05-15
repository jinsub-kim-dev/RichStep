package com.erving.richstep.tag.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TagJpaRepository : JpaRepository<TagEntity, Long> {

    @Query("""
        SELECT t FROM TagEntity t
        WHERE t.user.id = :userId
        AND t.name IN :names
    """)
    fun findAllByUserIdAndNameIn(
        @Param("userId") userId: Long,
        @Param("tagNames") tagNames: List<String>
    ): List<TagEntity>
}