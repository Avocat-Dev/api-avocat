package br.com.avocat.web.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto implements Serializable {	
	private static final long serialVersionUID = 1L;
	
    private String username;
    private String password;           
}
