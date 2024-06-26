package com.LGNZZ.mobiauto_backend_interview.service;

import com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade.AlteracaoOportunidadeApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade.ClienteApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade.OportunidadeRequestApi;
import com.LGNZZ.mobiauto_backend_interview.api.dto.oportunidade.VeiculoApi;
import com.LGNZZ.mobiauto_backend_interview.entity.Cliente;
import com.LGNZZ.mobiauto_backend_interview.entity.Oportunidade;
import com.LGNZZ.mobiauto_backend_interview.entity.Revenda;
import com.LGNZZ.mobiauto_backend_interview.entity.Veiculo;
import com.LGNZZ.mobiauto_backend_interview.repository.OportunidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OportunidadeService {

    @Autowired
    private OportunidadeRepository oportunidadeRepository;
    @Autowired
    private RevendaService revendaService;

    public List<Oportunidade> obterOportunidadesPorRevenda(@PathVariable Long idRevenda){
        return oportunidadeRepository.findAllByIdRevenda(idRevenda).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Oportunidade obterOportunidadePorId(Long id){
        return oportunidadeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @Transactional
    public Oportunidade criaNovaOportunidade(Long idRevenda, OportunidadeRequestApi api){
        Revenda revenda = revendaService.findById(idRevenda);
        Cliente cliente = new Cliente(api.dadosCliente().nome(), api.dadosCliente().email(), api.dadosCliente().telefone());
        Veiculo veiculo = new Veiculo(api.dadosVeiculo().marca(), api.dadosVeiculo().versao(), api.dadosVeiculo().modelo(), api.dadosVeiculo().anoModelo());
        Oportunidade oportunidade = new Oportunidade(revenda, cliente, veiculo);

        return oportunidadeRepository.save(oportunidade);
    }
    @Transactional
    public void alteraOportunidade(AlteracaoOportunidadeApi api){
        Oportunidade oportunidade = this.obterOportunidadePorId(api.idOportunidade());

        oportunidade.setCliente(this.alteraDadosCliente(oportunidade.getCliente(), api.dadosCliente()));
        oportunidade.setVeiculo(this.alterDadosVeiculo(oportunidade.getVeiculo(), api.dadosVeiculo()));
        oportunidade.setSituacao(api.situacao());
        oportunidade.setDescricaoConclusao(api.descricaoConclusao());

        oportunidadeRepository.save(oportunidade);
    }

    public void deletaOportunidade(Long id){
        oportunidadeRepository.deleteById(id);
    }
    private Cliente alteraDadosCliente(Cliente cliente, ClienteApi api){
        cliente.setEmail(api.email());
        cliente.setTelefone(api.telefone());
        cliente.setName(api.nome());

        return cliente;
    }

    private Veiculo alterDadosVeiculo(Veiculo veiculo, VeiculoApi api){
        veiculo.setMarca(api.marca());
        veiculo.setVersao(api.versao());
        veiculo.setModelo(api.modelo());
        veiculo.setAnoModelo(api.anoModelo());

        return veiculo;
    }
}
