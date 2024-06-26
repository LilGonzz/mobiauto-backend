package com.LGNZZ.mobiauto_backend_interview.service;

import com.LGNZZ.mobiauto_backend_interview.entity.logs.AtendimentoLog;
import com.LGNZZ.mobiauto_backend_interview.repository.AtendimentoLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AtendimentoLogService {

    @Autowired
    private AtendimentoLogRepository atendimentoLogRepository;


    public List<AtendimentoLog> obterLogsAtendimento(Long idAtendimento){
        return atendimentoLogRepository.findAllByIdAtendimento(idAtendimento).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long salvarLog(AtendimentoLog log){
        return atendimentoLogRepository.save(log).getId();
    }

}
