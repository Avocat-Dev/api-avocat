package br.com.avocat.web.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest implements Serializable {	
	private static final long serialVersionUID = 1L;
	
    private String username;
    private String password;           
}
