package br.edu.ifsul.hakunav2;

import br.edu.ifsul.hakunav2.api.grupo.Grupo;
import br.edu.ifsul.hakunav2.api.grupo.GrupoDTO;
import br.edu.ifsul.hakunav2.api.grupo.GrupoService;
import br.edu.ifsul.hakunav2.api.infra.exception.ObjectNotFoundException;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

@SpringBootTest
class HakunaV2ApplicationTests {

    @Autowired
    private GrupoService service;

    @Test
    public void getGrupos() {
        List<GrupoDTO> grupos = service.getGrupos();
        assertEquals(2, grupos.size());
    }

    @Test
    public void getGrupoById() {
        GrupoDTO grupo = service.getGrupoById(1L);
        assertNotNull(grupo);
        TestCase.assertEquals("Café", grupo.getNome());
        TestCase.assertEquals("assunto 01", grupo.getAssunto());
    }

    @Test
    public void getGruposByNome() {
        List<GrupoDTO> grupos = service.getGruposByNome("Café");
        assertNotNull(grupos);
        assertEquals(1, grupos.size());
    }

    @Test
    public void insert() {

        Grupo grupo = new Grupo();
        grupo.setNome("Café 03");
        grupo.setAssunto("assunto 03");

        GrupoDTO g = service.insert(grupo);
        assertNotNull(g);

        Long id = g.getId();
        assertNotNull(id);
        g = service.getGrupoById(id);
        assertNotNull(g);

        TestCase.assertEquals("Café 03", g.getNome());
        TestCase.assertEquals("assunto 03", g.getAssunto());

        service.delete(id);
        try {
            service.getGrupoById(id);
            fail("O grupo não foi excluído");
        } catch (ObjectNotFoundException e) {
            // OK
        }
    }
    @Test
    public void update() {
        GrupoDTO gDTO = service.getGrupoById(1L);
        String nome = gDTO.getNome();
        gDTO.setNome("Café modificado");
        Grupo g = GrupoDTO.create(gDTO);

        gDTO = service.update(g, g.getId());
        assertNotNull(gDTO);
        TestCase.assertEquals("Café modificado", gDTO.getNome());

        g.setNome(nome);
        gDTO = service.update(g, g.getId());
        assertNotNull(gDTO);
    }

    @Test
    public void delete() {
        Grupo grupo = new Grupo();
        grupo.setNome("Café Teste");
        grupo.setAssunto("Assunto Teste");

        GrupoDTO g = service.insert(grupo);
        assertNotNull(g);

        Long id = g.getId();
        assertNotNull(id);
        g = service.getGrupoById(id);
        assertNotNull(g);

        service.delete(id);
        try {
            service.getGrupoById(id);
            fail("O grupo não foi excluído");
        } catch (ObjectNotFoundException e) {
            // OK
        }
    }

}
