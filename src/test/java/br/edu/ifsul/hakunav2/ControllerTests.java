package br.edu.ifsul.hakunav2;

import br.edu.ifsul.hakunav2.api.grupo.Grupo;
import br.edu.ifsul.hakunav2.api.grupo.GrupoDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HakunaV2Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTests extends BaseAPITest {
    //Métodos utilitários
    private ResponseEntity<GrupoDTO> getGrupo(String url) {
        return get(url, GrupoDTO.class);
    }

    private ResponseEntity<List<GrupoDTO>> getGrupos(String url) {
        HttpHeaders headers = getHeaders();

        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<GrupoDTO>>() {
                });
    }

    @Test
    public void selectAll() {
        List<GrupoDTO> grupos = getGrupos("/api/v1/grupos").getBody();
        assertNotNull(grupos);
        assertEquals(2, grupos.size());

        grupos = getGrupos("/api/v1/grupos?page=0&size=5").getBody();
        assertNotNull(grupos);
        assertEquals(2, grupos.size());
    }

    @Test
    public void selectByNome() {

        assertEquals(1, getGrupos("/api/v1/grupos/nome/Café").getBody().size());
        assertEquals(1, getGrupos("/api/v1/grupos/nome/Café 02").getBody().size());
    }

    @Test
    public void selectById() {

        assertNotNull(getGrupo("/api/v1/grupos/1"));
        assertNotNull(getGrupo("/api/v1/grupos/2"));

        assertEquals(HttpStatus.NOT_FOUND, getGrupo("/api/v1/grupo/1000").getStatusCode());
    }

    @Test
    public void testInsert() {

        Grupo grupo = new Grupo();
        grupo.setNome("Café 1000");
        grupo.setAssunto("assunto 1000");

        // Insert
        ResponseEntity response = post("/api/v1/grupos", grupo, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        GrupoDTO g = getGrupo(location).getBody();

        assertNotNull(g);
        assertEquals("Café 1000", g.getNome());
        assertEquals("assunto 1000", g.getAssunto());

        // Deletar o objeto
        delete(location, null);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getGrupo(location).getStatusCode());
    }

    @Test
    public void testUpdate() {
        Grupo grupo = new Grupo();
        grupo.setNome("Café 1000");
        grupo.setAssunto("assunto 1000");

        // Insert
        ResponseEntity response = post("/api/v1/grupos", grupo, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        GrupoDTO g = getGrupo(location).getBody();

        assertNotNull(g);
        assertEquals("Café 1000", g.getNome());
        assertEquals("assunto 1000", g.getAssunto());

        //depois altera seu valor
        Grupo gr = GrupoDTO.create(g);
        gr.setAssunto("assunto 03");

        // Update
        response = put("/api/v1/grupos/" + g.getId(), gr, null);
        System.out.println(response);
        assertEquals("assunto 03", gr.getAssunto());

        // Deletar o objeto
        delete(location, null);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getGrupo(location).getStatusCode());

    }

    @Test
    public void testDelete() {
        this.testInsert();
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity response = getGrupo("/api/v1/grupos/1100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
