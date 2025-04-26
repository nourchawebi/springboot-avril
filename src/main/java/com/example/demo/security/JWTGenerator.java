package  com.example.demo.security;

import com.example.demo.entities.UserEntity;
import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import java.nio.file.attribute.UserPrincipal;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTGenerator {
	@Autowired
	UserRepository userRepository;
	private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
		// Extract roles
		// Déclare une variable 'authorities' de type Collection qui peut contenir des objets de type 'GrantedAuthority'
// Le type '?' signifie qu'on accepte n'importe quel type qui étend 'GrantedAuthority' (par exemple 'SimpleGrantedAuthority').
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

// 'authentication.getAuthorities()' appelle la méthode getAuthorities() de l'objet 'authentication',
// qui est un objet représentant l'utilisateur actuellement authentifié dans l'application.
// Cette méthode retourne une liste d'objets 'GrantedAuthority', qui représentent les rôles et permissions
// associés à cet utilisateur. Par exemple, si l'utilisateur a un rôle "ROLE_ADMIN", cela sera inclus ici.

		List<String> roles = authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		// Cast to your custom user class

		UserEntity user =userRepository.findByUsername(username).get();// direct cast
		String token = Jwts.builder()
				.setSubject(username)
				.claim("user", Map.of(
						"id", user.getId(),
						"username", user.getUsername(),
						"email", user.getEmail()
				))
				.claim("roles", roles)
				.setIssuedAt( new Date())
				.setExpiration(expireDate)
				.signWith(key,SignatureAlgorithm.HS512)
				.compact();
		System.out.println("New token :");
		System.out.println(token);
		return token;
	}
	public String getUsernameFromJWT(String token){
		// Utilisation de parserBuilder pour créer un constructeur de parser JWT
// Cela nous permet de personnaliser la configuration pour analyser le token JWT existant.
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
		}
	}

}
