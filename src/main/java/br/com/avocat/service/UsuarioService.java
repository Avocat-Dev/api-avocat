package br.com.avocat.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.model.UsuarioDados;
import br.com.avocat.persistence.repository.UnidadeRepository;
import br.com.avocat.persistence.repository.UsuarioDadosRepository;
import br.com.avocat.persistence.repository.UsuarioRepository;
import br.com.avocat.web.response.UsuarioDadosResponse;
import br.com.avocat.web.response.UsuarioResponse;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioDadosRepository usuarioDadosRepository;

	@Autowired
	private UnidadeRepository unidadeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private GrupoService grupoService;
	
	//TODO fazer a criaçao de uma conta Master com algumas configurações iniciais.
	@Transactional
	public Optional<UsuarioResponse> novaConta(Usuario credencial) {

		try {
			credencial.setPassword(passwordEncoder.encode(credencial.getPassword()));

			var result = usuarioRepository.save(credencial);

			if (result != null)
				return Optional.of(new UsuarioResponse(result));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return Optional.empty();
	}

	@Transactional
	public Optional<UsuarioDadosResponse> update(UsuarioDados data) {

		try {
			var usuario = usuarioRepository.findById(data.getUsuarioId());
			var unidade = unidadeRepository.findById(data.getUnidadeId()).get();
			var grupo = grupoService.get(data.getGrupoId());

			UsuarioDados result = new UsuarioDados();
			
			if (data.getId() == null) {
				
				data.setUnidade(unidade);
				data.setUsuario(usuario.get());
				data.setGrupo(grupo.get());

				result = usuarioDadosRepository.save(data);

			} else {

				var resUsuarioDados = usuarioDadosRepository.findById(data.getId());

				resUsuarioDados.get().setNome(data.getNome());
				resUsuarioDados.get().setEmail(data.getEmail());
				resUsuarioDados.get().setCelular(data.getCelular());
				resUsuarioDados.get().setUnidade(unidade);
				resUsuarioDados.get().setGrupo(grupo.get());

				result = usuarioDadosRepository.save(resUsuarioDados.get());
			}

			return Optional.of(new UsuarioDadosResponse(result));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
