package com.servicos.business.dtos.in;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResquestDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;

}
