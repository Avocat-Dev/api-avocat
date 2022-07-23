package br.com.avocat.exception;

import br.com.avocat.util.ConstantesUtil;
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
		var errorMensage = ErrorMensage.newInstance();
		errorMensage.setStatusCode(HttpStatus.BAD_REQUEST.value());
		capturarErros(ex, errorMensage);
		return new ResponseEntity<>(errorMensage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMensage> globalExceptionHandler(Exception ex, WebRequest request) {
		var errorMensage = ErrorMensage.newInstance();
		errorMensage.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		capturarErros(ex, errorMensage);
		return new ResponseEntity<>(errorMensage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ErrorMensage> globalExceptionHandler(RecursoNaoEncontradoException ex, WebRequest request) {
		var errorMensage = ErrorMensage.newInstance();
		errorMensage.setStatusCode(HttpStatus.NOT_FOUND.value());
		capturarErros(ex, errorMensage);
		return new ResponseEntity<>(errorMensage, HttpStatus.NOT_FOUND);
	}

	private void capturarErros(Exception ex, ErrorMensage errorMensage) {

		if(ex.getMessage().contains(ConstantesUtil.SEPARADOR_ERROS)) {
			String[] msgs = ex.getMessage().split(ConstantesUtil.SEPARADOR_ERROS);
			for(String m : msgs)
				errorMensage.getErros().add(m);
		} else {
			errorMensage.setErro(ex.getMessage());
		}
		LOGGER.warn(errorMensage.getUuid(), ex);
	}
}
