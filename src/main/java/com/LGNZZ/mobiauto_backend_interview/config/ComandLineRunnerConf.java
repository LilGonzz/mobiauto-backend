package com.LGNZZ.mobiauto_backend_interview.config;

import com.LGNZZ.mobiauto_backend_interview.entity.Enums.RoleEnum;
import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.entity.RevendaUsuario;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.LGNZZ.mobiauto_backend_interview.repository.RevendaRepository;
import com.LGNZZ.mobiauto_backend_interview.repository.RevendaUsuarioRepository;
import com.LGNZZ.mobiauto_backend_interview.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
public class ComandLineRunnerConf implements CommandLineRunner {
    @Autowired
    RevendaRepository revendaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RevendaUsuarioRepository revendaUsuarioRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Revenda revenda1 = new Revenda("1111111111111", "nome social 1");
        Revenda revenda2 = new Revenda("222222222222", "nome social 2");

        Usuario usuario1 = new Usuario("nome 1", "emailuser1@gmail.com", passwordEncoder.encode("432"));
        Usuario usuario2 = new Usuario("nome 2", "emailuser2@gmail.com", passwordEncoder.encode("234"));

        RevendaUsuario revendaUsuario1 = new RevendaUsuario(usuario1, null, RoleEnum.ADMINISTRADOR);
        RevendaUsuario revendaUsuario2 = new RevendaUsuario(usuario1, revenda1, RoleEnum.PROPRIETARIO);

        RevendaUsuario revendaUsuario3 = new RevendaUsuario(usuario2, revenda2, RoleEnum.ASSISTENTE);

        revendaRepository.saveAll(List.of(revenda1, revenda2));
        usuarioRepository.saveAll(List.of(usuario1, usuario2));
        revendaUsuarioRepository.saveAll(List.of(revendaUsuario1, revendaUsuario2, revendaUsuario3));
    }
}
