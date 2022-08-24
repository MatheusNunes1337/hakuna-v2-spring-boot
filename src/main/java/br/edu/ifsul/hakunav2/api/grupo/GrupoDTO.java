package br.edu.ifsul.hakunav2.api.grupo;

import lombok.Data;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Data
public class GrupoDTO {
    private Long id;
    private String nome;
    private String assunto;

    public static GrupoDTO create(Grupo g){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(g, GrupoDTO.class);
    }

    public static Grupo create(GrupoDTO g){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(g, Grupo.class);
    }
}
