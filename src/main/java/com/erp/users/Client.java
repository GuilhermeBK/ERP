package com.erp.users;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String address;
    private String celular;
    private String email;
    private String cpf;

    @Type(type = "text")
    @Column(columnDefinition = "text")
    private String notes;

    private String dataNascimento;
    private String lastUpdateTimestamp;
    private String creationTimestamp;
    private String lastUpdate; //o que foi alterado por utlimo

}
