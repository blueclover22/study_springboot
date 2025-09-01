package com.study.springboot.common.security.jwt.provider;

import com.study.springboot.common.security.jwt.constants.SecurityConstants;
import com.study.springboot.domain.CustomUser;
import com.study.springboot.domain.Member;
import com.study.springboot.prop.ShopProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final ShopProperties properties;

    public long getUserNo(String header) throws Exception {
        String token = header.substring(7);

        String secretKey = properties.getSecretKey();
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

        Jwt<JwsHeader, Claims> parsedToken = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token);

        // "uno" claim에서 userNo 추출
        String userNoStr = (String) parsedToken.getPayload().get("uno");
        long userNo = Long.parseLong(userNoStr);

        return userNo;
    }

    public String createToken(long userNo, String userId, List<String> roles) {

        byte[] signingKey = getSigningKey();
        SecretKey key = Keys.hmacShaKeyFor(signingKey);

        String token = Jwts.builder()
            .header()
            .add("typ", SecurityConstants.TOKEN_TYPE)
            .and()
            .expiration(new Date(System.currentTimeMillis() + 864000000))
            .claim("uno", "" + userNo)
            .claim("uid", userId)
            .claim("rol", roles)
            .signWith(key, Jwts.SIG.HS512)
            .compact();

        return token;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {

        if (isNotEmpty(tokenHeader)) {
            try {
                String token = tokenHeader.substring(7); // "Bearer " 제거

                String secretKey = properties.getSecretKey();
                SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

                // JWT 토큰 파싱
                Jwt<JwsHeader, Claims> parsedToken = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

                Claims claims = parsedToken.getPayload();

                // JWT에서 사용자 정보 추출
                String userNo = (String) claims.get("uno");
                String userId = (String) claims.get("uid");

                List<SimpleGrantedAuthority> authorities = ((List<?>) claims.get("rol"))
                    .stream()
                    .map(authority -> new SimpleGrantedAuthority((String) authority))
                    .collect(Collectors.toList());

                if (isNotEmpty(userId)) {
                    Member member = new Member();
                    member.setUserNo(Long.parseLong(userNo));
                    member.setUserId(userId);
                    member.setUserPw("");

                    UserDetails userDetails = new CustomUser(member, authorities);

                    return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                }
            } catch (ExpiredJwtException e) {
                log.warn("Expired JWT token", e);
            } catch (UnsupportedJwtException e) {
                log.warn("Unsupported JWT token", e);
            } catch (MalformedJwtException e) {
                log.warn("Malformed JWT token", e);
            } catch (IllegalArgumentException e) {
                log.warn("JWT claims string is empty.", e);
            }
        }

        return null;
    }

    private byte[] getSigningKey() {
        String secretKey = properties.getSecretKey();
        return Decoders.BASE64.decode(secretKey);  // Base64 디코딩
    }

    private boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);  // 로직 수정
    }

    private boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public boolean validateToken(String jwtToken) {
        try {
            String secretKey = properties.getSecretKey();
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

            Jwt<JwsHeader, Claims> claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwtToken);

            return !claims.getPayload().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token", e);
            return false;
        } catch (JwtException e) {
            log.error("Invalid JWT token", e);
            return false;
        } catch (NullPointerException e) {
            log.error("JWT token is null", e);
            return false;
        } catch (Exception e) {
            log.error("JWT token is invalid", e);
            return false;
        }
    }

}
