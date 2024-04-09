package tn.esprit.spring.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import tn.esprit.spring.entities.User;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashSet;
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

    public  String getEmailFromToken(String token) {
        // Décoder le jeton pour récupérer les claims
        Claims claims = Jwts.parser()
                .setSigningKey(getSignInKey()) // Remplacez par votre clé secrète
                .parseClaimsJws(token)
                .getBody();

        // Récupérer l'email depuis les claims
        return claims.getSubject();
    }


    public boolean isLoggedInAndJwtValid(String jwtToken) {
        if (isTokenBlacklisted(jwtToken)) {
            return false; // Le token est invalide s'il est en liste noire
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .parseClaimsJws(jwtToken)
                    .getBody();
            Date expirationDate = claims.getExpiration();
            return expirationDate.after(new Date()); // Vérifie si le token n'est pas expiré
        } catch (SignatureException e) {
            return false; // Le token est invalide pour une raison de signature
        } catch (Exception e) {
            return false; // Le token est invalide pour une autre raison
        }
    }

    private Set<String> blacklistedTokens = new HashSet<>();

    public void blacklistToken(String jwtToken) {
        blacklistedTokens.add(jwtToken);
    }

    public boolean isTokenBlacklisted(String jwtToken) {
        return blacklistedTokens.contains(jwtToken);
    }
    //token maghir role
   /* public String generateToken(
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
    }*/

//toke heda yemchi jawoui bahi
    /*
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // Extract roles from UserDetails
        String roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Add roles to extraClaims
        extraClaims.put("roles", roles);
// Add user ID to extraClaims


        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }*/

    //netfalsef
    private Key key;
    @PostConstruct
    public void init() {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        this.key = keySpec;

    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // Extract roles from UserDetails
        String roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Add roles to extraClaims
        extraClaims.put("roles", roles);

        // Cast UserDetails to User
        User user = (User) userDetails;

        // Add user ID and email to extraClaims
        extraClaims.put("userId", user.getId());
        extraClaims.put("email", user.getUsername());
        // user fisrt name
        extraClaims.put("lastName",user.getLastName());
        extraClaims.put("firstName",user.getFirstName());

     /*   String csrfToken = UUID.randomUUID().toString();

// Ajouter la valeur CSRF dans le JWT
        extraClaims.put("csrfToken", csrfToken);
*/
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }

   /* public boolean verifyCsrfToken(String csrfTokenFromRequest, String jwtToken) {
        try {
            // Extraire le csrfToken du JWT en utilisant la même clé pour signer et vérifier le JWT
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            String csrfTokenInJwt = claims.get("csrfToken", String.class);

            // Comparer les deux tokens
            return csrfTokenFromRequest.equals(csrfTokenInJwt);
        } catch (JwtException e) {
            // Gérer l'exception si le JWT est invalide (signature incorrecte, expiré, etc.)
            return false;
        }
    } */




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
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .parseClaimsJws(token)
                .getBody();
    }

  /*  @Value("${jwt.secret}")
    private String secretKey;*/

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        // byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}