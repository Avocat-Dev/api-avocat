package br.com.avocat.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorMensage {
	/**
	 * UUID generated to identify exception launched in log. 
	 */
	private UUID uuid;
	private int statusCode;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime data;

	private String erro;
	private List<String> erros = new ArrayList<>();

	public static ErrorMensage newInstance() {
		ErrorMensage errorMensage = new ErrorMensage();
		errorMensage.setUuid(UUID.randomUUID());
		errorMensage.setData(LocalDateTime.now());
		return errorMensage;
	}
}
