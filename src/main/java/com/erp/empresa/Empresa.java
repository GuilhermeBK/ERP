package com.erp.empresa;

import com.erp.pagamento.Maquina;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cnpj;
    private String limiteFaturamento;
    private String cpf;

    @ManyToMany
    private List<Maquina> maquinas;

    @CollectionTable
    @OneToMany(orphanRemoval = true)
    private List<ChavePix> chavePix;

}
