package com.marcinsz1993.eventmanagementkotlin.service

import com.marcinsz1993.eventmanagementkotlin.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

@Service
class JwtService(
    val SECRET_KEY: String = "d87382f9b30cb375d74a6d337867cd32dbfc3bbd4062fee7fabd8"
) {
    fun extractUsername(token: String?): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun <T> extractClaim(token: String?, resolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return resolver.apply(claims)
    }

    fun isValid(token: String?, user: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == user.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String?): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }
    private fun extractAllClaims(token: String?): Claims {
        return Jwts
            .parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }
    fun generateToken(user: User): String? {
        return Jwts
            .builder()
            .subject(user.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
            .signWith(getSigningKey())
            .compact()
    }
    private fun getSigningKey(): SecretKey? {
        val keyBytes = Decoders.BASE64URL.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}