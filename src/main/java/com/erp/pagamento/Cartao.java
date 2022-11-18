package com.erp.pagamento;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

@Entity
@Data
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bandeira;
    private String nome;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private HashMap<Integer, Double> taxaJuros;

    @ManyToMany
    private List<Maquina> maquina;


}
