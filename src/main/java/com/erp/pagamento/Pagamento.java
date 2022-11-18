package com.erp.pagamento;

import com.erp.pedido.Pedido;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {

    @Getter
    public enum tipoPagamento {
        CARTAO_CREDITO,
        CARTAO_DEBITO,
        PIX,
        TED,
        DINHEIRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double valor;
    private tipoPagamento tipoPagamento;

    @ManyToOne
    private Pedido pedido;
}
