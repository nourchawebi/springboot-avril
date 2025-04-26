package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator tokenGenerator;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
// Cette méthode est appelée pour filtrer la requête HTTP entrante. Elle permet de vérifier si un jeton JWT est présent dans la requête et, si oui, de l'utiliser pour authentifier l'utilisateur.
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Extraire le JWT de la requête HTTP en appelant la méthode getJWTFromRequest()
        String token = getJWTFromRequest(request);

        // Vérifie si le jeton existe et si celui-ci est valide en utilisant la méthode validateToken() du tokenGenerator.
        // Si les conditions sont remplies, on continue avec l'authentification de l'utilisateur.
        if(StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {

            // Extraire le nom d'utilisateur du jeton JWT (le "subject" du token).
            String username = tokenGenerator.getUsernameFromJWT(token);

            // Charger les détails de l'utilisateur à partir de la base de données (ou du service utilisateur).
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Créer un token d'authentification basé sur les détails de l'utilisateur et les autorisations (rôles) associés à l'utilisateur.
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());

            // Définir les détails de la requête dans le token d'authentification (cela permet de garder des informations sur la session de l'utilisateur).
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Mettre l'authentification dans le contexte de sécurité de Spring, ce qui indique à Spring que l'utilisateur est authentifié.
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // Laisser la chaîne de filtres continuer son processus (si le token est valide ou non).
        filterChain.doFilter(request, response);
    }

    // Méthode pour extraire le JWT depuis l'en-tête "Authorization" de la requête HTTP.
    private String getJWTFromRequest(HttpServletRequest request) {
        // Récupérer l'en-tête "Authorization" de la requête.
        String bearerToken = request.getHeader("Authorization");

        // Si l'en-tête contient un jeton et qu'il commence par "Bearer ", on extrait le jeton sans le préfixe "Bearer ".
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());  // Extraire le jeton JWT.
        }

        // Si le jeton n'est pas présent ou n'est pas valide, retourner null.
        return null;
    }

}
