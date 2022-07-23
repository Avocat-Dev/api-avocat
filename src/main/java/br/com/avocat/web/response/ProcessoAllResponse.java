package br.com.avocat.web.response;

import br.com.avocat.persistence.model.processo.Processo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ProcessoAllResponse {

    private String numeroProcesso;
    private String partePrincipal;
    private String parteContraria;
    private LocalDate dataEntrada;
    private LocalDate dataDistribuicao;

    private String area;
    private String unidade;

    public ProcessoAllResponse(Processo processo) {
        this.numeroProcesso = processo.getNumeroProcesso();
        this.partePrincipal = processo.getPartePrincipal();
        this.parteContraria = processo.getParteContraria();
        this.dataEntrada = processo.getDataEntrada();
        this.dataDistribuicao = processo.getDataDistribuicao();
        this.area = processo.getArea().getDescricao();
        this.unidade = processo.getUnidade().getNomeUnidade();
    }
}
