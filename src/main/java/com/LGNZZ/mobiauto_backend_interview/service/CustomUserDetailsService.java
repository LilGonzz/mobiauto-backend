package com.LGNZZ.mobiauto_backend_interview.service;

import com.LGNZZ.mobiauto_backend_interview.config.auth.CustomGrantedAuthority;
import com.LGNZZ.mobiauto_backend_interview.config.auth.CustomUserDetails;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.LGNZZ.mobiauto_backend_interview.repository.UsuarioRepository;
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
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("usuário não encontrado"));

        Set<CustomGrantedAuthority> authorities = usuario.getRolesPorRevenda().stream()
                .map(usuarioRole -> new CustomGrantedAuthority(usuarioRole.getRole(), usuarioRole.getRevenda().getId())).collect(Collectors.toSet());

        return new CustomUserDetails(usuario.getEmail(), usuario.getSenha(), authorities);

    }
}
