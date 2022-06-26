package br.com.avocat.service.processo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.processo.Processo;
import br.com.avocat.persistence.model.processo.TipoAcao;
import br.com.avocat.persistence.repository.ContratoRepository;
import br.com.avocat.persistence.repository.UnidadeRepository;
import br.com.avocat.persistence.repository.processo.AreaRepository;
import br.com.avocat.persistence.repository.processo.ComarcaRepository;
import br.com.avocat.persistence.repository.processo.FaseProcessualRepository;
import br.com.avocat.persistence.repository.processo.ForoRepository;
import br.com.avocat.persistence.repository.processo.ProcessoRepository;
import br.com.avocat.persistence.repository.processo.RitoRepository;
import br.com.avocat.persistence.repository.processo.TipoAcaoRepository;
import br.com.avocat.persistence.repository.processo.VaraRepository;
import br.com.avocat.web.response.ProcessoResponse;

@Service
public class ProcessoService {

	@Autowired
	private ProcessoRepository processoRepository;

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
	private UnidadeRepository unidadeRepository;

	@Autowired
	private ContratoRepository contratoRepository;

	public Optional<ProcessoResponse> save(Processo processo) {

		try {
			var unidade = unidadeRepository.findById(processo.getUnidadeId());
			var contrato = contratoRepository.findById(processo.getContratoId());
			
			var area = areaRepository.findById(processo.getAreaId());
			var tipoAcao = tipoAcaoRepository.findById(processo.getTipoAcaoId());
			var fase = faseRepository.findById(processo.getFaseId());
			var rito = ritoRepository.findById(processo.getRitoId());
			var comarca = comarcaRepository.findById(processo.getComcarcaId());
			var foro = foroRepository.findById(processo.getForoId());
			var vara = varaRepository.findById(processo.getVaraId());
			
			processo.setUnidade(unidade.get());
			processo.setContrato(contrato.get());
			processo.setArea(area.get());
			processo.setTipoAcao(tipoAcao.get());
			processo.setFaseProcessual(fase.get());
			processo.setRito(rito.get());
			processo.setComarca(comarca.get());
			processo.setForo(foro.get());
			processo.setVara(vara.get());

			var result = processoRepository.save(processo);

			if (result != null)
				return Optional.of(new ProcessoResponse(result));
			else
				return Optional.empty();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<ProcessoResponse> get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<List<ProcessoResponse>> all() {
		// TODO Auto-generated method stub
		return null;
	}

}
