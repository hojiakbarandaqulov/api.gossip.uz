package api.giybat.uz.util;

import api.giybat.uz.dto.JwtDTO;
import api.giybat.uz.enums.ProfileRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 96; // 2-day
    private static final String secretKey = "verylongmazgiskjdhskjdhadasdasgfgdfgdfdftrhdgrgefergetdgsfegvergdgsbdzsfbvgdsetbgrFLKWRMFKJERNGVSFUOISNIUVNSDBFIUSHIULFHWAUOIESIUOFIOEJOIGJMKLDFMGghjgjOTFIJBP";
/*
    public static String encode(String profileId, String username, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuedAt(new Date());

        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());

        jwtBuilder.signWith(secretKeySpec);
        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("role", role);
        jwtBuilder.claim("username", username);

        jwtBuilder.expiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.issuer("Interview_questions");
        return jwtBuilder.compact();
    }*/


    public static String encode(Integer profileId, List<ProfileRole> roleList) {
        String strRoles = roleList.stream().map(Enum::name).
                collect(Collectors.joining(","));

        Map<String, String> claims = new HashMap<>();
        claims.put("roles", strRoles);
        return Jwts
                .builder()
                .setSubject(String.valueOf(profileId))
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        Integer id = Integer.valueOf(claims.getSubject());
        String strRoles = (String) claims.get("role");
        List<ProfileRole> roleLis = Arrays.stream(strRoles.split(","))
                .map(ProfileRole::valueOf)
                .toList();
        return  new JwtDTO(id, roleLis);
    }


    private static Key getSignInKey() {
        byte[] keyBytes = Base64.getUrlDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
