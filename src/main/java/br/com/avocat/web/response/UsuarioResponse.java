package br.com.avocat.web.response;

import br.com.avocat.persistence.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioResponse {

	private Long id;
    private String username;
	
    public UsuarioResponse(Usuario usuario) {
		this.id = usuario.getId();
		this.username = usuario.getUsername();
	}    
}
