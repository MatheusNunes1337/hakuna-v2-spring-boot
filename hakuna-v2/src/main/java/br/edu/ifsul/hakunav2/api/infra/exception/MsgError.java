package br.edu.ifsul.hakunav2.api.infra.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MsgError {
    public String error;
}
