package com.erp.produto;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String foto;

    @Type(type = "text")
    @Column(columnDefinition = "text")
    private String notes;

    private String preco;
    private String precoMin;
    private String custo;
    private String quantidade;
    private String lastUpdateTimestamp;
    private String creationTimestamp;
}
