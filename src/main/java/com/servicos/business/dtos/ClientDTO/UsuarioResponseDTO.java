package com.servicos.business.dtos.ClientDTO;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseDTO {

    private String email;
    private String senha;
    private Role role;
}
