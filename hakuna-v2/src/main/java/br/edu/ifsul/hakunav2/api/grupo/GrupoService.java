package br.edu.ifsul.hakunav2.api.grupo;

import br.edu.ifsul.hakunav2.api.infra.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GrupoService {
    @Autowired
    private GrupoRepository rep;

    public List<GrupoDTO> getGrupos() {
        return rep.findAll().stream().map(GrupoDTO::create).collect(Collectors.toList());
    }

    public GrupoDTO getGrupoById(Long id) {
        Optional<Grupo> grupo = rep.findById(id);
        return grupo.map(GrupoDTO::create).orElseThrow(() -> new ObjectNotFoundException("Grupo não encontrado"));
    }

    public List<GrupoDTO> getGruposByNome(String nome) {
        return rep.findByNome(nome).stream().map(GrupoDTO::create).collect(Collectors.toList());
    }

    public GrupoDTO insert(Grupo grupo) {
        Assert.isNull(grupo.getId(),"Não foi possível inserir o registro");

        return GrupoDTO.create(rep.save(grupo));
    }

    public GrupoDTO update(Grupo grupo, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca o produto no banco de dados
        Optional<Grupo> optional = rep.findById(id);
        if(optional.isPresent()) {
            Grupo db = optional.get();
            // Copiar as propriedades
            db.setNome(grupo.getNome());
            db.setAssunto(grupo.getAssunto());
            System.out.println("Grupo id " + db.getId());

            // Atualiza o produto
            rep.save(db);

            return GrupoDTO.create(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }


    public void delete(Long id) {
        rep.deleteById(id);
    }
}
