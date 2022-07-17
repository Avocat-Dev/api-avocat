package br.com.avocat.exception;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(AvocatException.class)
	public ResponseEntity<ErrorMensage> avocatException(AvocatException ex) {
		ErrorMensage error = new ErrorMensage();

		UUID uuid = gerarUUID(ex, error);

		error.setUuid(uuid);
		error.setStatusCode(HttpStatus.BAD_REQUEST.value());
		error.setData(LocalDateTime.now());
		error.setMensagem(ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMensage> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMensage error = new ErrorMensage();

		UUID uuid = gerarUUID(ex, error);

		error.setUuid(uuid);
		error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setData(LocalDateTime.now());
		error.setMensagem(ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ErrorMensage> globalExceptionHandler(RecursoNaoEncontradoException ex, WebRequest request) {
		ErrorMensage error = new ErrorMensage();

		UUID uuid = gerarUUID(ex, error);

		error.setUuid(uuid);
		error.setStatusCode(HttpStatus.NOT_FOUND.value());
		error.setData(LocalDateTime.now());
		error.setMensagem(ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	private UUID gerarUUID(Exception ex, ErrorMensage error) {
		var uuid = error.getUuid();

		LOGGER.warn(uuid, ex);
		return uuid;
	}
}
