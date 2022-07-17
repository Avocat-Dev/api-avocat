package br.com.avocat.util;

public class ConstantesUtil {

	private ConstantesUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static final String AMB_LOCAL_HOST = "http://localhost:";
	
	public static final String PATH_PROCESSO_V1 = "/api/v1/processos";	
	public static final String PATH_ADMINISTRATIVO_V1 = "/api/v1/administrativos";	
	public static final String PATH_USUARIO_V1 = "/api/v1/usuarios";
	public static final String PATH_AUTH_V1 = "/api/v1/auth";	
}
