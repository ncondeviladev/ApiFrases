package apifrases;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityRoleTest {

    @Autowired
    private MockMvc mockMvc;

    // 1. Acceso PÚBLICO (Sin Login) -> Debe fallar (401)
    @Test
    void testPublicAccessDenied() throws Exception {
        mockMvc.perform(get("/api/v1/frases"))
                .andExpect(status().isUnauthorized());
    }

    // 2. Acceso STANDARD (User) -> Puede leer (200), pero NO borrar (403)
    @Test
    @WithMockUser(username = "user", roles = { "STANDARD" }) // Simulamos ser un usuario normal
    void testStandardUserCanRead() throws Exception {
        mockMvc.perform(get("/api/v1/frases"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = { "STANDARD" })
    void testStandardUserCannotDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/frases/1"))
                .andExpect(status().isForbidden()); // 403 Forbidden
    }

    // 3. Acceso ADMIN -> Puede leer (200) y borrar (200/404)
    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testAdminCanRead() throws Exception {
        mockMvc.perform(get("/api/v1/frases"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testAdminCanDelete() throws Exception {
        // Esperamos 200 OK, o 404 si el ID no existe, pero NUNCA 403 Forbidden
        mockMvc.perform(delete("/api/v1/frases/999")) // ID inventado
                // Al intentar borrar un ID inexistente, el servicio lanza RuntimeException.
                // Nuestro GlobalExceptionHandler captura RuntimeException y devuelve 400 Bad
                // Request.
                // Esto demuestra que la petición LLEGÓ al controlador (pasó la seguridad).
                .andExpect(status().isBadRequest());
    }
}
