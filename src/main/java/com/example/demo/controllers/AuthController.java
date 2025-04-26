package  com.example.demo.controllers;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginDtop;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.entities.UserEntity;
import com.example.demo.entities.UserRole;
import com.example.demo.entities.UserRoleName;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTGenerator;
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


import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
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
            adminUser.setCreationDate(new Date());
            UserRole adminRole = new UserRole();
            adminRole.setUserRoleName(UserRoleName.ADMIN);
            adminRole.setUserEntity(adminUser);

            //---Access
            adminRole.setEventManagement(true);
            adminRole.setEventManagement(true);
            //--------

            adminUser.setUserRole(adminRole);

            userRepository.save(adminUser);
        }
    }



    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDtop loginDto) {
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
            Optional<UserEntity> userOptional = userRepository.findByUsername(userDetails.getUsername());

            // 6. Vérifier si l'utilisateur existe dans la base de données
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();  // Récupérer l'utilisateur trouvé dans la base de données

                // 7. Créer une réponse contenant le token et l'utilisateur
                AuthResponseDTO authResponseDTO = new AuthResponseDTO(token, user);

                // 8. Retourner une réponse HTTP avec le token et les informations de l'utilisateur
                return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
            } else {
                // 9. Si l'utilisateur n'est pas trouvé dans la base de données, retourner une erreur
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

        } catch (AuthenticationException e) {
            // 10. Si l'authentification échoue (nom d'utilisateur ou mot de passe incorrect)
            // Retourner une réponse HTTP indiquant que l'authentification a échoué
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return ResponseEntity.badRequest().body("Username is taken!");
        }

        // Check if the email is already registered
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already registered!");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());

        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Set creationDate to the current date
        user.setCreationDate(new Date());



        UserRole defaultRole = new UserRole();
        defaultRole.setUserRoleName(UserRoleName.USER);
        defaultRole.setUserEntity(user);

        defaultRole.setUserManagement(true);
        defaultRole.setEventManagement(false);
        user.setUserRole(defaultRole);


        userRepository.save(user);




        return ResponseEntity.ok(user);
    }


    @DeleteMapping("delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        // Recherche de l'utilisateur dans la base de données
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            // L'utilisateur existe, le supprimer de la base de données
            userRepository.delete(userOptional.get());
            return ResponseEntity.ok("User deleted successfully");
        } else {
            // L'utilisateur n'existe pas dans la base de données
            return ResponseEntity.notFound().build();
        }
    }


}
