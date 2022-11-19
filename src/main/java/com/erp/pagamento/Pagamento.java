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
    public enum TipoPagamento {
        CARTAO_CREDITO,
        CARTAO_DEBITO,
        PIX,
        TED,
        DINHEIRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double valor;

    private Double desconto;

    private Double acrescimo;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    @ManyToOne
    private Pedido pedido;
}
