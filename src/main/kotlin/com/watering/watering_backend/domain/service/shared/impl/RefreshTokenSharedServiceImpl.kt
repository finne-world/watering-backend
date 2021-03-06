package com.watering.watering_backend.domain.service.shared.impl

import arrow.core.Option
import com.watering.watering_backend.config.property.AuthenticationProperties
import com.watering.watering_backend.domain.constant.Error
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.exception.application.ApplicationException
import com.watering.watering_backend.domain.exception.application.ResourceNotFoundException
import com.watering.watering_backend.domain.repository.RefreshTokenRepository
import com.watering.watering_backend.domain.repository.UserRepository
import com.watering.watering_backend.domain.service.shared.RefreshTokenSharedService
import com.watering.watering_backend.lib.extension.getOrThrow
import com.watering.watering_backend.lib.extension.get
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

//TODO: よく考えたら shared されてなくね？サービスクラスの処理を区分けする意味で shared service 作るのはアンチパターンなのかな。 shared の存在する意味としてはアンチパターンな気がするけど。
@Service
class RefreshTokenSharedServiceImpl(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authenticationProperties: AuthenticationProperties
): RefreshTokenSharedService {
    override fun getByTokenUUID(tokenUUID: UUID): Option<RefreshTokenEntity> {
        return refreshTokenRepository.getByUUID(tokenUUID)
    }

    override fun registerRefreshToken(userDetails: UserDetails): RefreshTokenEntity {
        val user: UserEntity = userRepository.getByUsername(userDetails.username)
                                             .toEither {
                                                 ResourceNotFoundException(
                                                     httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
                                                     error = Error.UNKNOWN_ERROR,
                                                     errorDescription = "User resource not found. username=${userDetails.username}",
                                                     errorMessage = ApplicationException.UNKNOWN_MESSAGE
                                                 )
                                             }
                                             .getOrThrow()

        return refreshTokenRepository.create(
            user.id,
            LocalDateTime.now().plusSeconds(authenticationProperties.refreshToken.exp)
        ).getOrThrow()
    }

    override fun exchangeRefreshToken(userId: Long): RefreshTokenEntity {
        val userEntity: UserEntity = userRepository.getById(userId).get()

        return exchangeRefreshToken(refreshTokenRepository.getByUserId(userEntity.id).get())
    }

    override fun exchangeRefreshToken(refreshToken: RefreshTokenEntity): RefreshTokenEntity {
        refreshTokenRepository.updateToken(refreshToken.id, UUID.randomUUID())
        return refreshTokenRepository.getById(refreshToken.id).get()
    }

    override fun existsByUserId(userId: Long): Boolean {
        return refreshTokenRepository.existsByUserId(userId)
    }

    override fun deleteByUserId(userId: Long): Boolean {
        return refreshTokenRepository.deleteByUserId(userId)
    }
}
