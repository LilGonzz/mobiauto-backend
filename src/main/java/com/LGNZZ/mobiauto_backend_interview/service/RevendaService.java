package com.LGNZZ.mobiauto_backend_interview.service;

import br.com.caelum.stella.validation.CNPJValidator;
import com.LGNZZ.mobiauto_backend_interview.api.dto.revenda.CriacaoAteracaoRevendaApi;
import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.entity.RevendaUsuario;
import com.LGNZZ.mobiauto_backend_interview.repository.RevendaRepository;
import com.LGNZZ.mobiauto_backend_interview.repository.RevendaUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RevendaService {

    @Autowired
    private RevendaRepository revendaRepository;

    CNPJValidator cnpjValidator = new CNPJValidator();

    @Autowired
    private RevendaUsuarioRepository revendaUsuarioRepository;

    public List<Revenda> findAll() {
        return revendaRepository.findAll();
    }

    public Revenda obterPorId(Long id) {
        return revendaRepository.findById(id).orElseThrow(() -> new NotFoundException("Revenda com id: "+ id +" nao encontrada"));
    }

    public void alteraRevenda(Revenda revenda, CriacaoAteracaoRevendaApi api) {
        if(!revenda.getCnpj().equals(api.cnpj()))
            checkRequisitos(api);
        else
            checkCnpj(api.cnpj());

        revenda.setNomeSocial(api.nomeSocial());
        revenda.setCnpj(api.cnpj());
        revendaRepository.save(revenda);

    }
    public Revenda criarNovaRevenda(CriacaoAteracaoRevendaApi revendaApi) {
        checkRequisitos(revendaApi);
        Revenda revenda = new Revenda(revendaApi.cnpj(), revendaApi.nomeSocial());
        return revendaRepository.save(revenda);
    }
    public void softDeleteRevenda(Revenda revenda){
        List<RevendaUsuario> revendaUsuarios = softDeleteRevendaUsuarioPorRevenda(revenda);
        revenda.setRevendaUsuarios(revendaUsuarios);
        revenda.setActive(false);
        revenda.setUpdatedAt(LocalDateTime.now());

        revendaRepository.save(revenda);
    }

    private List<RevendaUsuario> softDeleteRevendaUsuarioPorRevenda(Revenda revenda){
        Set<RevendaUsuario> revendasUsuario = revendaUsuarioRepository.findAllByRevenda(revenda);
        revendasUsuario.forEach(revendaUsuario -> {
            revendaUsuario.setActive(false);
            revendaUsuario.setUpdatedAt(LocalDateTime.now());
        });

        return revendaUsuarioRepository.saveAll(revendasUsuario);
    }
    private void checkRequisitos(CriacaoAteracaoRevendaApi revendaApi) {
        checkCnpj(revendaApi.cnpj());
        Optional<Revenda> revenda = revendaRepository.findByCnpj(revendaApi.cnpj());
        if (revenda.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private void checkCnpj(String cnpj){
        try{
            cnpjValidator.assertValid(cnpj);
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
