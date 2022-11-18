package com.erp.pagamento;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Maquina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToMany(mappedBy = "maquina")
    private List<Cartao> cartao;
}
