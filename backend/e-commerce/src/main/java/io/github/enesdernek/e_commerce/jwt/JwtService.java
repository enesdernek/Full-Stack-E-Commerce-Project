package io.github.enesdernek.e_commerce.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	private static final String SECRET_KEY = "9gy8GnWZW6WGeoy4BmHYieNZjqH57afZMqsjLyNsEqY=";

	public String generateToken(UserDetails userDetails) {

		Map<String, Object> claimsMap = new HashMap<>();
		claimsMap.put("role", "USER");

		return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date()).addClaims(claimsMap)
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 240))
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();

	}

	public Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public Object getClaimsByKey(String token,String key) {
		Claims claims = getClaims(token);
		return claims.get(key);
	}

	public Claims getClaims(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
		return claims;
	}

	public <T> T exportToken(String token, Function<Claims, T> claimsFunction) {
		Claims claims = getClaims(token);

		return claimsFunction.apply(claims);
	}

	public String getUsernameByToken(String token) {
		return exportToken(token, Claims::getSubject);
	}

	public boolean isTokenExpired(String token) {
		Date expiredDate = exportToken(token, Claims::getExpiration);
		return new Date().after(expiredDate);
	}

}
