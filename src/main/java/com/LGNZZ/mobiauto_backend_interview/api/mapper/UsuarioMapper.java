package com.LGNZZ.mobiauto_backend_interview.api.mapper;

import com.LGNZZ.mobiauto_backend_interview.api.dto.RevendaApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.usuario.SoftDeleteResponseApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.usuario.UsuarioApi;
import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "id", target = "idUsuario")
    UsuarioApi toUsuarioApi(Usuario usuario);

    SoftDeleteResponseApi toSoftDeleteResponseApi(String tipoDadoAlterado, List<Long> ids);
}
