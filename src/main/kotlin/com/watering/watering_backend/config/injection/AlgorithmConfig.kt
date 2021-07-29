package com.watering.watering_backend.config.injection

import com.amazonaws.util.Base64
import com.auth0.jwt.algorithms.Algorithm
import com.watering.watering_backend.config.property.AWSProperties
import com.watering.watering_backend.lib.AWSParameterStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPublicKeySpec
import java.security.spec.X509EncodedKeySpec

@Configuration
class AlgorithmConfig(
    private val parameterStore: AWSParameterStore,
    private val awsProperties: AWSProperties
) {
    companion object {
        val KEY_FACTORY: KeyFactory = KeyFactory.getInstance("RSA")
    }
    @Bean
    fun getAlgorithm(): Algorithm {
        val public = getPublicKey()
        val private = getPrivateKey()

        return Algorithm.RSA256(public, private)
    }

    private fun getPublicKey(): RSAPublicKey {
        val x509EncodedKeySpec = X509EncodedKeySpec(
            this.parameterStore.getParameter(
                this.awsProperties.parameterStore.jwt.publicKey
            ).let { Base64.decode(it) }
        )

        return KEY_FACTORY.generatePublic(x509EncodedKeySpec) as RSAPublicKey
    }

    private fun getPrivateKey(): RSAPrivateKey {
        val pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(
            this.parameterStore.getParameter(
                this.awsProperties.parameterStore.jwt.privateKey
            ).let { Base64.decode(it) }
        )

        return KEY_FACTORY.generatePrivate(pkcs8EncodedKeySpec) as RSAPrivateKey
    }
}
