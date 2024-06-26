package com.LGNZZ.mobiauto_backend_interview.api.mapper;

import com.LGNZZ.mobiauto_backend_interview.api.dto.RevendaApi;
import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RevendaMapper {

    @Mapping(source = "id", target = "idRevenda")
    RevendaApi revendaToApi(Revenda revenda);
}
