package tn.esprit.spring.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "BL1V1g4BMp7mPipXG2CR05lSHZZiNcOJLJaEFv4p381Z9JC/e/o/VvY5Q0XVY/R5ZyeM72EF8kO6ayoX+YA2Ip/WCy4JN+NRTMu+UqUafDu4w6QNin72CkcZOLTlYaZRDeEINFLjdmvXjufr0apRwTMmEXyIktqvZLdA+HnAcjfgbd+kqAV2oimXq29fPeB4gERCT/F7UcPAS1cQxjj+VGF+UlsvCipnhfi1M6MvWhbp1+f9RUNA7XDqi+dwj706L2iggwROZYayBzRu6qQhCabGZEavgF+Cf/PDl4GT2OQ/l9wQ3CwOy45LoE7alX0FYmNvnE9ldO8fqZfBe/KmwTBE1xTUDe+/uuJXRMqKmXg=";
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims =extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Set<String> blacklistedTokens = new HashSet<>();

    public void blacklistToken(String jwtToken) {
        blacklistedTokens.add(jwtToken);
    }

    public boolean isTokenBlacklisted(String jwtToken) {
        return blacklistedTokens.contains(jwtToken);
    }
    public String generateToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean isTokenValid(String token ,UserDetails userDetails) {

        if (isTokenBlacklisted(token)) {
            return false; // Le jeton est invalide s'il est dans la liste noire
        }

        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);}



    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
