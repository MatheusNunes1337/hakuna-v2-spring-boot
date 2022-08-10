package br.edu.ifsul.hakunav2.api.grupo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/grupos")
public class GrupoController {
    @Autowired
    private GrupoService service;

    @GetMapping
    public ResponseEntity<List<GrupoDTO>> selectAll() {

        return ResponseEntity.ok(service.getGrupos());
    }

    @GetMapping("{id}")
    public ResponseEntity<GrupoDTO> selectById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getGrupoById(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<GrupoDTO>> selectByName(@PathVariable("nome") String nome) {
        return ResponseEntity.ok(service.getGruposByNome(nome));
    }

    @PostMapping
   // @Secured({"ROLE_ADMIN"})
    public ResponseEntity<String> insert(@RequestBody Grupo grupo) {
        GrupoDTO g = service.insert(grupo);
        URI location = getUri(g.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<GrupoDTO> update(@PathVariable("id") Long id, @RequestBody Grupo grupo) {
        grupo.setId(id);
        GrupoDTO g = service.update(grupo, id);
        return g != null ?
                ResponseEntity.ok(g) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    //utilitário
    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }


}
