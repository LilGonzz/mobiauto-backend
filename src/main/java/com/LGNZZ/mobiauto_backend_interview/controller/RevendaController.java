package com.LGNZZ.mobiauto_backend_interview.controller;

import com.LGNZZ.mobiauto_backend_interview.api.dto.RevendaApi;
import com.LGNZZ.mobiauto_backend_interview.api.mapper.RevendaMapper;
import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.service.RevendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/revenda")
public class RevendaController {

    @Autowired
    private RevendaService revendaService;
    @Autowired
    private RevendaMapper revendaMapper;

    @GetMapping
    public ResponseEntity<List<RevendaApi>> obterTodasRevendas() {
        List<Revenda> revendas = revendaService.findAll();
        List<RevendaApi> revendaApis = revendas.stream().map(revenda -> revendaMapper.revendaToApi(revenda)).toList();
        return ResponseEntity.ok(revendaApis);
    }

    @GetMapping("/{idRevenda}")
    public ResponseEntity<RevendaApi> obterRevendaPorId(@PathVariable("idRevenda") Long idRevenda) {
        Revenda revenda = revendaService.obterPorId(idRevenda);
        RevendaApi revendaApi = revendaMapper.revendaToApi(revenda);
        return ResponseEntity.ok(revendaApi);
    }

}
