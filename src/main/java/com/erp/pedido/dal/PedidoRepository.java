package com.erp.pedido.dal;

import com.erp.pedido.Pedido;
import com.erp.pedido.rest.QueryParamsPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {


    @Query("FROM Pedido p WHERE (" +
            "( :id IS NULL OR p.id = :id ) AND " +
            "( :vendedorName IS NULL OR p.vendedor.name = :vendedorName ) AND " +
            "( :valor IS NULL OR p.total = :valor ) AND " +
            "( :lucro IS NULL OR p.lucro = :lucro ) AND " +
            "( :custo IS NULL OR p.custo = :custo ) AND " +
            "( :dataVenda IS NULL OR p.creationTimestamp = :dataVenda ) AND " +
            "( :clientName IS NULL OR p.client.name LIKE concat('%', :clientName, '%')) AND " +
            "( :clientNameExact IS NULL OR p.client.name = :clientNameExact )) ")
    Page<Pedido> findByFilters(@Param("id") String id, @Param("clientNameExact") String clientNameExact, @Param("clientName") String clientName,
                               @Param("vendedorName")  String vendedorName, @Param("custo")  String custo, @Param("valor")  String valor, @Param("lucro")  String lucro, @Param("dataVenda")  String dataVenda, Sort sort);

    Pedido findVendasTotal();

    Pedido findVendasTotalByParams(QueryParamsPedido params);
}
