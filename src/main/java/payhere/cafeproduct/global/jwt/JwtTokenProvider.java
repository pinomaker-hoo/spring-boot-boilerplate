package payhere.cafeproduct.global.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import payhere.cafeproduct.global.dto.TokenDto;
import payhere.cafeproduct.global.enums.UserRole;
import payhere.cafeproduct.global.utils.EncryptionUtils;

import java.security.PrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;
    private final EncryptionUtils encryptionUtils;

    @Value("${jwt.accessTokenValidityInSeconds}")
    private String accessTokenValidationTime;

    @Value("${jwt.refreshTokenValidityInSeconds}")
    private String refreshTokenValidationTime;

    public TokenDto issueToken(Long userId, UserRole role) throws Exception {
        final String accessToken = generateToken(userId, role, Long.valueOf(accessTokenValidationTime));
        final String refreshToken = generateToken(userId, role, Long.valueOf(refreshTokenValidationTime));

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenDto reissueToken(Long userId, UserRole role, String refreshToken) throws Exception {
        final String accessToken = generateToken(userId, role, Long.valueOf(accessTokenValidationTime));

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String generateToken(Long userId, UserRole role, Long tokenValidationTime) throws Exception {
        final Map<String, Object> claims = createClaims(userId, role);
        final PrivateKey privateKey = jwtConfig.getPrivateKey();

        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidationTime * 1000))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    private Map<String, Object> createClaims(Long userId, UserRole role) {
        Map<String, Object> claims = new HashMap<>();
        String encodedId = encryptionUtils.encrypt(String.valueOf(userId));
        String encodedRole = encryptionUtils.encrypt(String.valueOf(role));

        claims.put("id", encodedId);
        claims.put("role", encodedRole);

        return claims;
    }
}
