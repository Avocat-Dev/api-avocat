package br.com.avocat.service.processo;

import br.com.avocat.exception.AvocatException;
import br.com.avocat.persistence.model.processo.Processo;
import br.com.avocat.persistence.model.processo.ValorCausa;
import br.com.avocat.persistence.repository.ContratoRepository;
import br.com.avocat.persistence.repository.UnidadeRepository;
import br.com.avocat.persistence.repository.processo.*;
import br.com.avocat.util.ConstantesUtil;
import br.com.avocat.util.ObjetoUtil;
import br.com.avocat.web.response.ProcessoAllResponse;
import br.com.avocat.web.response.ProcessoResponse;
import br.com.avocat.web.response.ValorCausaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    public Optional<ProcessoResponse> save(Processo processo) throws Exception {
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

        if (area.isPresent() && tipoAcao.isPresent() && fase.isPresent() &&
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
            throw new AvocatException("Ocorreu algom erro ao tantar salvar o processo nro: " + processo.getNumeroProcesso());
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

        var erros = "";
        var separador = ConstantesUtil.SEPARADOR_ERROS;

        if (ObjetoUtil.verifica(processo.getUnidadeId()).isEmpty())
            erros = erros.concat("Processo deve ter uma unidade.").concat(separador);

        if (ObjetoUtil.verifica(processo.getContratoId()).isEmpty())
            erros = erros.concat("Processo deve ter um contrato.").concat(separador);

        if (ObjetoUtil.verifica(processo.getNumeroProcesso()).isEmpty())
            erros = erros.concat("Número do processo não deve ser nulo ou vazio.").concat(separador);

        if (ObjetoUtil.verifica(processo.getAreaId()).isEmpty())
            erros = erros.concat("Processo deve ter uma area.").concat(separador);

        if (ObjetoUtil.verifica(processo.getTipoAcaoId()).isEmpty())
            erros = erros.concat("Processo deve ter uma acção.").concat(separador);

        if (ObjetoUtil.verifica(processo.getFaseId()).isEmpty())
            erros = erros.concat("Processo de ter uma fase processual.").concat(separador);

        if (ObjetoUtil.verifica(processo.getRitoId()).isEmpty())
            erros = erros.concat("Processo deve ter um rito.").concat(separador);

        if (ObjetoUtil.verifica(processo.getForoId()).isEmpty())
            erros = erros.concat("Processo deve ter um foro.").concat(separador);

        if (ObjetoUtil.verifica(processo.getVaraId()).isEmpty())
            erros = erros.concat("Processo deve ter uma vara.").concat(separador);

        if (ObjetoUtil.verifica(processo.getPartePrincipal()).isEmpty())
            erros = erros.concat("Processo deve ter uma parte principal.").concat(separador);

        if (ObjetoUtil.verifica(processo.getParteContraria()).isEmpty())
            erros = erros.concat("Processo deve ter uma parte contraria.").concat(separador);

        if(!erros.isEmpty())
            throw new AvocatException(erros);
    }

    public List<ProcessoAllResponse> all() {
        var result = processoRepository.findAll();
        return result.stream().map(ProcessoAllResponse::new).toList();
    }
}
