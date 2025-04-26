package com.example.demo.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
// L'annotation @Component permet à Spring de reconnaître cette classe comme un bean et de l'ajouter au contexte d'application.
// Cela signifie qu'elle sera gérée par le conteneur Spring et pourra être injectée dans d'autres classes si nécessaire.
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    // L'annotation @Override signifie que cette méthode redéfinit (implémente) une méthode de l'interface AuthenticationEntryPoint.
    // La méthode commence() est appelée lorsque l'authentification échoue ou qu'un utilisateur tente d'accéder à une ressource protégée sans être authentifié.
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Cette méthode est déclenchée quand une exception d'authentification se produit.
        // Elle permet de gérer la réponse HTTP envoyée au client lorsque l'authentification échoue.

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        // Cette ligne renvoie une réponse HTTP avec un code d'erreur 401 (non autorisé).
        // Le message d'erreur est récupéré depuis l'exception d'authentification (authException).
        // Cela permet d'informer le client que l'authentification a échoué.
        // Le code SC_UNAUTHORIZED correspond à 401 et indique que l'utilisateur n'est pas autorisé à accéder à la ressource.
    }
}
