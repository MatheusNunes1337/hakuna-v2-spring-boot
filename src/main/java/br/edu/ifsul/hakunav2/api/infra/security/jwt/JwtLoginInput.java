package br.edu.ifsul.hakunav2.api.infra.security.jwt;

import lombok.Data;

@Data
public class JwtLoginInput {
    private String username;
    private String password;
}