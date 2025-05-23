package com.erving.richstep.domain.user.infrastructure

import com.erving.richstep.domain.user.domain.User
import com.erving.richstep.domain.user.service.port.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun save(user: User): User {
        var userEntity = UserEntity.fromModel(user)
        userEntity = this.userJpaRepository.save(userEntity)
        return userEntity.toModel()
    }

    override fun findById(id: Long): User? {
        val userEntity = this.userJpaRepository.findByIdOrNull(id)
        return userEntity?.toModel()
    }

    override fun findByEmail(email: String): User? {
        val userEntity = this.userJpaRepository.findByEmail(email)
        return userEntity?.toModel()
    }
}