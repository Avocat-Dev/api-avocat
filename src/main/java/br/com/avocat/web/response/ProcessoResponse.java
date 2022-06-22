package br.com.avocat.web.response;

import br.com.avocat.persistence.model.Unidade;
import br.com.avocat.persistence.model.processo.Area;
import br.com.avocat.persistence.model.processo.Processo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ProcessoResponse {

    private String numeroProcesso;
    private String partePrincipal;
    private String parteContraria;
    private String observacaoInterna;
    private String observacaoEncerramento;
    private String observacaoCliente;
    private String observacaoFinanceiro;
    private String detalheObjeto;
    private LocalDate dataEntrada;
    private LocalDate dataDistribuicao;

    private Area area;
    private Unidade unidade;

    public ProcessoResponse(Processo processo) {
        this.numeroProcesso = processo.getNumeroProcesso();
        this.partePrincipal = processo.getPartePrincipal();
        this.parteContraria = processo.getParteContraria();
        this.observacaoInterna = processo.getObservacaoInterna();
        this.observacaoEncerramento = processo.getObservacaoEncerramento();
        this.observacaoCliente = processo.getObservacaoCliente();
        this.observacaoFinanceiro = processo.getObservacaoFinanceiro();
        this.detalheObjeto = processo.getDetalheObjeto();
        this.dataEntrada = processo.getDataEntrada();
        this.dataDistribuicao = processo.getDataDistribuicao();
        this.area = processo.getArea();
        this.unidade = processo.getUnidade();
    }
}
