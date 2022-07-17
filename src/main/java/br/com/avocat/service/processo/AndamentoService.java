package br.com.avocat.service.processo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.processo.Andamento;
import br.com.avocat.persistence.repository.processo.AndamentoRepository;
import br.com.avocat.persistence.repository.processo.ProcessoRepository;
import br.com.avocat.persistence.repository.processo.TipoAndamentoRepository;
import br.com.avocat.web.response.AndamentoResponse;

@Service
public class AndamentoService {
	
	@Autowired
	private ProcessoRepository processoRepository;

	@Autowired
	private TipoAndamentoRepository tipoAndamentoRepository;
	
	@Autowired
	private AndamentoRepository andamentoRepository;
	
	@Transactional
	public Optional<AndamentoResponse> save(Andamento andamento) {

		try {
			var processo = processoRepository.findById(andamento.getProcessoId());
			var tipoAndamento = tipoAndamentoRepository.findById(andamento.getTipoAndamentoId());

			andamento.setProcesso(processo.get());
			andamento.setTipoAndamento(tipoAndamento.get());
			
			var result = andamentoRepository.save(andamento);

			if (result != null)
				return Optional.of(new AndamentoResponse(result));
			else
				return Optional.empty();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
