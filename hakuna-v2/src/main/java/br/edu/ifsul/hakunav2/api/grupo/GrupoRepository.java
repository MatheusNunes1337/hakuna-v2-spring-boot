package br.edu.ifsul.hakunav2.api.grupo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    List<Grupo> findByNome(String nome);

}
