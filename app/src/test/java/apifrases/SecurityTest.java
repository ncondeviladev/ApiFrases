package apifrases;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testPasswordMatches() {
        // 1. Cargamos el usuario 'admin' de la BD real
        UserDetails admin = userDetailsService.loadUserByUsername("admin");

        System.out.println(">>> USUARIO TEST: " + admin.getUsername());
        System.out.println(">>> PASSWORD TEST: " + admin.getPassword());

        // 2. Comprobamos si la contraseña '1234' coincide con el HASH de la BD
        boolean matches = passwordEncoder.matches("1234", admin.getPassword());

        System.out.println(">>> PASSWORD MATCHES '1234'? " + matches);

        // Assert
        assertTrue(matches, "La contraseña '1234' DEBERÍA coincidir con el hash de la BD para 'admin'");
    }

    @Test
    @WithMockUser
    void testPublicEndpoint() throws Exception {
        // Intento de acceso a un endpoint protegido SIN auth real (simulado por
        // @WithMockUser)
        mockMvc.perform(get("/api/v1/frases")) // Asumiendo que existe
                .andExpect(status().isOk());
    }

    @Test
    void testLoginUnauthorized() throws Exception {
        // Acceso sin credenciales -> 401
        mockMvc.perform(get("/api/v1/frases"))
                .andExpect(status().isUnauthorized());
    }
}
