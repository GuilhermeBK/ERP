package com.erp.empresa.rest;

import com.erp.empresa.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/empresa")
@RequiredArgsConstructor
@Slf4j
public class EmpresaController {

    private EmpresaService service;


    //TODO
//    public getTotalFaturado() {
//
//    }
//
//    public getTotalFaturadoPorEmpresa() {
//
//    }

}
