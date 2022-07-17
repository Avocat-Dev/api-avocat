package br.com.avocat.service.processo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.Unidade;
import br.com.avocat.util.ObjetoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.processo.Processo;
import br.com.avocat.persistence.model.processo.ValorCausa;
import br.com.avocat.persistence.repository.ContratoRepository;
import br.com.avocat.persistence.repository.UnidadeRepository;
import br.com.avocat.persistence.repository.processo.AreaRepository;
import br.com.avocat.persistence.repository.processo.ComarcaRepository;
import br.com.avocat.persistence.repository.processo.FaseProcessualRepository;
import br.com.avocat.persistence.repository.processo.ForoRepository;
import br.com.avocat.persistence.repository.processo.PapelRepository;
import br.com.avocat.persistence.repository.processo.ProcessoRepository;
import br.com.avocat.persistence.repository.processo.RitoRepository;
import br.com.avocat.persistence.repository.processo.TipoAcaoRepository;
import br.com.avocat.persistence.repository.processo.TipoValorRepository;
import br.com.avocat.persistence.repository.processo.ValorCausaRepository;
import br.com.avocat.persistence.repository.processo.VaraRepository;
import br.com.avocat.web.response.ProcessoResponse;
import br.com.avocat.web.response.ValorCausaResponse;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ValorCausaRepository valorCausaRepository;

    @Autowired
    private TipoValorRepository tipoValorRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private TipoAcaoRepository tipoAcaoRepository;

    @Autowired
    private FaseProcessualRepository faseRepository;

    @Autowired
    private RitoRepository ritoRepository;

    @Autowired
    private ComarcaRepository comarcaRepository;

    @Autowired
    private ForoRepository foroRepository;

    @Autowired
    private VaraRepository varaRepository;

    @Autowired
    private PapelRepository papelRepository;

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    @Transactional
    public Optional<ProcessoResponse> save(Processo processo) {
        validarProcesso(processo);

        var unidade = unidadeRepository.findById(processo.getUnidadeId());
        var contrato = contratoRepository.findById(processo.getContratoId());

        var area = areaRepository.findById(processo.getAreaId());
        var tipoAcao = tipoAcaoRepository.findById(processo.getTipoAcaoId());
        var fase = faseRepository.findById(processo.getFaseId());
        var rito = ritoRepository.findById(processo.getRitoId());
        var comarca = comarcaRepository.findById(processo.getComcarcaId());
        var foro = foroRepository.findById(processo.getForoId());
        var vara = varaRepository.findById(processo.getVaraId());
        var partePrincipal = papelRepository.findById(processo.getPartePrincipalId());
        var parteContraria = papelRepository.findById(processo.getParteContrariaId());

        if(area.isPresent() && tipoAcao.isPresent() && fase.isPresent() &&
           rito.isPresent() && comarca.isPresent() && foro.isPresent() &&
           vara.isPresent() && partePrincipal.isPresent() && parteContraria.isPresent() &&
           unidade.isPresent() && contrato.isPresent()) {

            processo.setUnidade(unidade.get());
            processo.setContrato(contrato.get());
            processo.setArea(area.get());
            processo.setTipoAcao(tipoAcao.get());
            processo.setFaseProcessual(fase.get());
            processo.setRito(rito.get());
            processo.setComarca(comarca.get());
            processo.setForo(foro.get());
            processo.setVara(vara.get());
            processo.setPapelPartePrincipal(partePrincipal.get());
            processo.setPapelParteContraria(parteContraria.get());

            var result = processoRepository.save(processo);

            return Optional.of(new ProcessoResponse(result));

        } else {
            throw new AvocatException("Ocorreu algom erro ao tantar salvar o Processo " + processo.getNumeroProcesso());
        }
    }

    @Transactional
    public Optional<ValorCausaResponse> salvarValorCausa(ValorCausa data) {

        var processo = processoRepository.findById(data.getProcessoId());
        var tipoValor = tipoValorRepository.findById(data.getTipoValorId());

        data.setProcesso(processo.get());
        data.setTipoValor(tipoValor.get());

        var result = valorCausaRepository.save(data);

        return Optional.of(new ValorCausaResponse(result));
    }

    private void validarProcesso(Processo processo) {

        ObjetoUtil.verifica(processo.getUnidadeId()).orElseThrow(() ->
                new AvocatException("Processo deve ter uma unidade.")
        );

        ObjetoUtil.verifica(processo.getContratoId()).orElseThrow(() ->
                new AvocatException("Processo deve ter um contrato.")
        );

        ObjetoUtil.verifica(processo.getNumeroProcesso()).orElseThrow(() ->
                new AvocatException("Número do processo não deve ser nulo ou vazio.")
        );

        ObjetoUtil.verifica(processo.getAreaId()).orElseThrow(() ->
                new AvocatException("Processo deve ter uma area.")
        );

        ObjetoUtil.verifica(processo.getTipoAcaoId()).orElseThrow(() ->
                new AvocatException("Processo deve ter uma acção.")
        );

        ObjetoUtil.verifica(processo.getFaseId()).orElseThrow(() ->
                new AvocatException("Processo de ter uma fase processual.")
        );

        ObjetoUtil.verifica(processo.getRitoId()).orElseThrow(() ->
                new AvocatException("Processo deve ter um rito.")
        );

        ObjetoUtil.verifica(processo.getForoId()).orElseThrow(() ->
                new AvocatException("Processo deve ter um foro.")
        );

        ObjetoUtil.verifica(processo.getVaraId()).orElseThrow(() ->
                new AvocatException("Processo deve ter uma vara.")
        );

        ObjetoUtil.verifica(processo.getPartePrincipal()).orElseThrow(() ->
                new AvocatException("Processo deve ter uma parte principal.")
        );

        ObjetoUtil.verifica(processo.getParteContraria()).orElseThrow(() ->
                new AvocatException("Processo deve ter uma parte contraria.")
        );
    }
}
