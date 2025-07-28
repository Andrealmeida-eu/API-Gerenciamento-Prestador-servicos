package com.studioAlanGodoy.partiturasEServicos.infastructure.security;


import com.studioAlanGodoy.partiturasEServicos.business.dtos.ClientDTO.UsuarioResponseDTO;
import com.studioAlanGodoy.partiturasEServicos.infastructure.security.Client.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl {

    @Autowired
    private UsuarioClient client;

    public UserDetails carregaDadosUsuario(String email, String token){

        UsuarioResponseDTO usuarioDTO = client.buscaUsuarioPorEmail(email, token);
        return User
                .withUsername(usuarioDTO.getEmail()) // Define o nome de usuário como o e-mail
                .password(usuarioDTO.getSenha())// Define a senha do usuário
                .roles(usuarioDTO.getRole().name()) // Define a senha do usuário
                .build();
    }


}
