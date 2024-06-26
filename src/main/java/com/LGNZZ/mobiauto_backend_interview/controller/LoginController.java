package com.LGNZZ.mobiauto_backend_interview.controller;

import com.LGNZZ.mobiauto_backend_interview.api.dto.usuario.LoginApiRequest;
import com.LGNZZ.mobiauto_backend_interview.api.dto.usuario.LoginApiResponse;
import com.LGNZZ.mobiauto_backend_interview.api.mapper.UsuarioMapper;
import com.LGNZZ.mobiauto_backend_interview.config.SecurityService;
import com.LGNZZ.mobiauto_backend_interview.entity.Enums.RoleEnum;
import com.LGNZZ.mobiauto_backend_interview.entity.RevendaUsuario;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.LGNZZ.mobiauto_backend_interview.service.RevendaUsuarioService;
import com.LGNZZ.mobiauto_backend_interview.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import java.util.List;


@RestController
@RequestMapping("api/v1/login")
public class LoginController {
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RevendaUsuarioService revendaUsuarioService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    public ResponseEntity<LoginApiResponse> login(@RequestBody LoginApiRequest loginRequest){
        Usuario user = usuarioService.getUsuarioByEmail(loginRequest.email()).orElseThrow(() -> new UsernameNotFoundException("usuário não encontrado na base de dados!"));

        if(!user.confereLogin(loginRequest.senha(), bCryptPasswordEncoder)){
            throw new BadCredentialsException("Usuário ou senha incorretos");
        }

        List<RevendaUsuario> revendaUser = revendaUsuarioService.obterRevendasPorUsuario(user);
        var scope = revendaUser.stream()
                .map(revenda ->{
                    if(revenda.getRole().equals(RoleEnum.ADMINISTRADOR))
                        return "ROLE_" + revenda.getRole().name();
                    return "ROLE_" + revenda.getRole().name()+ "_" + revenda.getId();
                }).toList();

        Long expiresIn = 600L;
        var claims = JwtClaimsSet.builder()
                .issuer("mobiauto-backend")
                .subject(loginRequest.email())
                .issuedAt(Instant.now().plusSeconds(600L))
                .claim("roles", scope)
                .build();

        String jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginApiResponse(jwt, expiresIn));
    }
}
