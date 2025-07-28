package com.studioAlanGodoy.partiturasEServicos.business.dtos.ClientDTO;


import lombok.*;

import java.util.List;

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
