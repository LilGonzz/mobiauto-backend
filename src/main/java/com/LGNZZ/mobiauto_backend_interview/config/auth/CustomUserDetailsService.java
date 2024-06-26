package com.LGNZZ.mobiauto_backend_interview.config.auth;

import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.LGNZZ.mobiauto_backend_interview.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getUsuarioByEmail(email).orElseThrow(() -> new UsernameNotFoundException("usuário não encontrado na base de dados!"));

        Set<CustomGrantedAuthority> authorities = usuario.getRolesPorRevenda().stream()
                .map(usuarioRole -> new CustomGrantedAuthority(usuarioRole.getRole(), usuarioRole.getRevenda().getId())).collect(Collectors.toSet());

        return new CustomUserDetails(usuario.getEmail(), usuario.getSenha(), authorities);
    }
}
