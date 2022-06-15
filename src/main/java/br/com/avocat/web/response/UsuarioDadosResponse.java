package br.com.avocat.web.response;

import br.com.avocat.persistence.model.Usuario;
import br.com.avocat.persistence.model.UsuarioDados;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioDadosResponse {

	private Long id;
	private String nome;
	private String email;
	private String celular;
	private Usuario usuario;
	private Long usuarioId;
	
	public UsuarioDadosResponse(UsuarioDados usuarioDados) {		
		this.id = usuarioDados.getId();
		this.nome = usuarioDados.getNome();
		this.email = usuarioDados.getEmail();
		this.celular = usuarioDados.getCelular();
		this.usuario = usuarioDados.getUsuario();
		this.usuarioId = usuarioDados.getUsuarioId();
	}
}
