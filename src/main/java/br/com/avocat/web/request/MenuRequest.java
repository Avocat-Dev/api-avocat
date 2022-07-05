package br.com.avocat.web.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MenuRequest implements Serializable {	
	private static final long serialVersionUID = 1L;

	private long roleId;
	private List<Integer> menuIds = new ArrayList<>();
}
