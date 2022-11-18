package com.erp.pedido;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Marketing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private String titulo;
    private String notes;
}
