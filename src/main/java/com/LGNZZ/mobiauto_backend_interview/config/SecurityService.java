package com.LGNZZ.mobiauto_backend_interview.config;

import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import com.nimbusds.jwt.JWT;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SecurityService {

    public Boolean hasPermission(String... permission){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> permissionList = Arrays.asList(permission);
        if(authentication instanceof JwtAuthenticationToken){
            JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;
            Map<String, Object> t = jwtAuthToken.getTokenAttributes();
            List<String> permissions = (List<String>) t.get("roles");
            for(String perm : permissions){
                if(permissionList.contains(perm)){
                    return true;
                }
            }
        }
        return false;
    }

    public Usuario getUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null;
    }
}
