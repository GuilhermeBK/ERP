package com.erp.pedido.rest;

import com.erp.base.rest.QueryParams;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class QueryParamsPedido extends QueryParams {

    private String clientName;
    private String id;
    private String vendedorName;
    private String valor;
    private String custo;
    private String lucro;
    private String dataVenda;
    private String dataInicial;
    private String dataFinal;
    @Override
    public Sort getSorting() {
        return null;
        //TODO
    }
}
