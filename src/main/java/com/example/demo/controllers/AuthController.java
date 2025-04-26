package com.example.demo.controllers;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginDto;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleName;
import com.example.demo.entities.UserEntity;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepo;
import com.example.demo.security.JWTGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepo userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator
                          ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }



    @PostConstruct
    public void createDefaultAdminAccount() {
        if (!userRepository.existsByUsername("admin")) {
            UserEntity adminUser = new UserEntity();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@bridge.com");
            adminUser.setPassword(passwordEncoder.encode("admin")); // You can change the default password
            adminUser.setFirstName("Admin");
            adminUser.setLastName("Bridge");
            adminUser.setAddress("tunis");

            Role adminRole = new Role();
            adminRole.setRolename(RoleName.ADMIN);
            adminRole.setUser(adminUser);


            adminUser.setRole(adminRole);

            userRepository.save(adminUser);
        }
    }



    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            // 1. Essayer d'authentifier l'utilisateur avec son nom d'utilisateur et son mot de passe
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),  // Nom d'utilisateur récupéré du corps de la requête
                            loginDto.getPassword()   // Mot de passe récupéré du corps de la requête
                    )
            );

            // 2. Si l'authentification réussit, la session est associée à l'utilisateur authentifié
            // L'authentification réussie est enregistrée dans le contexte de sécurité
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. Récupérer les détails de l'utilisateur authentifié
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 4. Générer un token JWT pour l'utilisateur authentifié
            String token = jwtGenerator.generateToken(authentication);

            // 5. Chercher l'utilisateur dans la base de données en utilisant le nom d'utilisateur
            UserEntity user= userRepository.findByUsername(userDetails.getUsername());

            // 6. Vérifier si l'utilisateur existe dans la base de données


                // 7. Créer une réponse contenant le token et l'utilisateur
                AuthResponseDTO authResponseDTO = new AuthResponseDTO(token, user);

                // 8. Retourner une réponse HTTP avec le token et les informations de l'utilisateur
                return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);


        } catch (AuthenticationException e) {
            // 10. Si l'authentification échoue (nom d'utilisateur ou mot de passe incorrect)
            // Retourner une réponse HTTP indiquant que l'authentification a échoué
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }







}
