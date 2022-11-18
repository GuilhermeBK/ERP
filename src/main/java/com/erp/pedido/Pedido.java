package com.erp.pedido;

import com.erp.empresa.Empresa;
import com.erp.pagamento.Pagamento;
import com.erp.produto.Produto;
import com.erp.users.Client;
import com.erp.users.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String total;
    private String quantidade;
    private Timestamp lastUpdateTimestamp;
    private Timestamp creationTimestamp;

    private UUID marketingUuid;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User vendedor;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany
    private List<Produto> produto;

    @ManyToMany
    private List<Pagamento> pagamento;

    @ManyToOne
    private Empresa mei;

}
