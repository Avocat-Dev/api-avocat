package br.com.avocat.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostMenuRequest {
	private Long idRole;
	private Iterable<Long> listaMenus;
}
