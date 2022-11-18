package com.erp.pedido.dto;

import com.erp.empresa.Empresa;
import com.erp.pagamento.Pagamento;
import com.erp.pedido.Pedido;
import com.erp.produto.Produto;
import com.erp.users.Client;
import com.erp.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PedidoDto {

    private UUID marketingUuid;

    private List<Produto> produto;

    private Long clientId;

    private Long vendedorId;

    private String total;

    private Long meiId;

    private List<Pagamento> pagamento;

    public Pedido to(Client client, User user) {
        return Pedido.builder()
                .client(client)
                .vendedor(user)
                .total(total)
                .marketingUuid(marketingUuid)
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .produto(produto)
                .pagamento(pagamento)
                .build();
    }
}
