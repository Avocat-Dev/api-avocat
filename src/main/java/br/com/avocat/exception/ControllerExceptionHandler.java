package br.com.avocat.exception;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	static final Logger LOGGER = LogManager.getLogger(ControllerExceptionHandler.class);
	
	@ExceptionHandler(AvocatException.class)
	public ResponseEntity<ErrorMensage> avocatException(AvocatException ex) {
		ErrorMensage msg = new ErrorMensage();
		
		LOGGER.warn(msg.getUuid(), ex);
		
		msg.setStatusCode(HttpStatus.BAD_REQUEST.value());
		msg.setData(LocalDateTime.now());
		msg.setMensagem(ex.getMessage());		

		return new ResponseEntity<ErrorMensage>(msg, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMensage> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMensage msg = new ErrorMensage();

		LOGGER.warn(msg.getUuid(), ex);
		
		msg.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		msg.setData(LocalDateTime.now());
		msg.setMensagem(ex.getMessage());
		msg.setDescricao(request.getDescription(false));

		return new ResponseEntity<ErrorMensage>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ErrorMensage> globalExceptionHandler(RecursoNaoEncontradoException ex, WebRequest request) {
		ErrorMensage msg = new ErrorMensage();
		
		msg.setStatusCode(HttpStatus.NOT_FOUND.value());
		msg.setData(LocalDateTime.now());
		msg.setMensagem(ex.getMessage());
		msg.setDescricao(request.getDescription(false));

		return new ResponseEntity<ErrorMensage>(msg, HttpStatus.NOT_FOUND);
	}
}
