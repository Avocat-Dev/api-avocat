package br.com.avocat.service.processo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.processo.Processo;
import br.com.avocat.persistence.repository.ContratoRepository;
import br.com.avocat.persistence.repository.UnidadeRepository;
import br.com.avocat.persistence.repository.processo.AreaRepository;
import br.com.avocat.persistence.repository.processo.ProcessoRepository;
import br.com.avocat.web.response.ProcessoResponse;

@Service
public class ProcessoService {

	@Autowired
	private ProcessoRepository processoRepository;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private UnidadeRepository unidadeRepository;

	@Autowired
	private ContratoRepository contratoRepository;

	public Optional<ProcessoResponse> save(Processo processo) {

		try {
			var unidade = unidadeRepository.findById(processo.getUnidadeId());
			var contrato = contratoRepository.findById(processo.getContratoId());
			var area = areaRepository.findById(processo.getAreaId());
			
			processo.setUnidade(unidade.get());
			processo.setContrato(contrato.get());
			processo.setArea(area.get());

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
