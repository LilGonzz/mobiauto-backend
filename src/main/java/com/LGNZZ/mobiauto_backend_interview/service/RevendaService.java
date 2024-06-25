package com.LGNZZ.mobiauto_backend_interview.service;

import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.repository.RevendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class RevendaService {

    @Autowired
    private RevendaRepository revendaRepository;

    public List<Revenda> findAll() {
        return revendaRepository.findAll();
    }

    public Revenda findById(Long id) {
        return revendaRepository.findById(id).orElseThrow(() -> new NotFoundException("Revenda com id: "+ id +" nao encontrada"));
    }

}
