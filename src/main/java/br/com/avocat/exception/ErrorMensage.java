package br.com.avocat.exception;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorMensage {
	/**
	 * UUID generated to identify exception launched in log. 
	 */
	private UUID uuid = UUID.randomUUID();
	private int statusCode;
	private LocalDateTime data;
	private String mensagem;
	private String descricao;
}
