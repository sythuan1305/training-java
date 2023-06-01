package com.beetech.trainningJava.jwt;

import java.util.Date;

import com.beetech.trainningJava.entity.RefreshTokenEntity;
import com.beetech.trainningJava.exception.HttpError;
import com.beetech.trainningJava.repository.RefreshTokenRepository;
import com.beetech.trainningJava.utils.Utils;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Class này dùng để tạo token và kiểm tra token
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JwtTokenProvider {
    private final String JWT_SECRET_KEY = "7we4rfbhka9";
    private final long JWT_EXPIRATION = 15 * 60 * 1000;
    private final RefreshTokenRepository refreshTokenRepository;

    public String generateToken(String username) {
        var now = new Date();
        var expireDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public String generateSpecificToken(String s, long l) {
        var now = new Date();
        var expireDate = new Date(now.getTime() + l);
        return Jwts.builder()
                .setSubject(s)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();

        }
    }

    public boolean isValidToken(String authToken) throws HttpError {
        try {
            Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            throw new HttpError("ExpiredJwtException", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            throw new HttpError("UnsupportedJwtException", HttpStatus.UNAUTHORIZED);
        } catch (MalformedJwtException e) {
            throw new HttpError("MalformedJwtException", HttpStatus.UNAUTHORIZED);
        } catch (SignatureException e) {
            throw new HttpError("SignatureException", HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException e) {
            throw new HttpError("IllegalArgumentException", HttpStatus.UNAUTHORIZED);
        }
    }

    public long getExpiredTime() {
        return JWT_EXPIRATION;
    }


    public String createRefreshToken(String username, String accessToken) {
        String refreshToken = Utils.Base64Image.randomString(10);

        RefreshTokenEntity rf = refreshTokenRepository.findByUsername(username);

        if (rf == null) {
            rf = new RefreshTokenEntity(username, refreshToken, accessToken);
        } else {
            rf.setRefreshToken(refreshToken);
            rf.setAccessToken(accessToken);
        }

        return refreshTokenRepository.saveAndFlush(rf).getRefreshToken();
    }

    public boolean isValidToCreateNewAccessToken(String username, String refreshToken, String accessToken) {
        RefreshTokenEntity rfTokenInDB = refreshTokenRepository.findByUsername(username);

        System.out.println("refreshToken: " + refreshToken + " accessToken: " + accessToken);
        System.out.println("rfTokenInDB: " + rfTokenInDB.getRefreshToken() + " " + rfTokenInDB.getAccessToken());
        return (rfTokenInDB != null
                && rfTokenInDB.getRefreshToken().equals(refreshToken)
                && rfTokenInDB.getAccessToken().equals(accessToken));
    }
}
