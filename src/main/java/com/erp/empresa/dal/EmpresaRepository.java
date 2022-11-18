package com.erp.empresa.dal;

import com.erp.empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository  extends JpaRepository<Empresa, Long> {

    Empresa ById(Long id);

}
