package com.erp.pedido.rest;

import com.erp.pedido.Pedido;
import com.erp.pedido.dto.PedidoDto;
import com.erp.pedido.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController("/api/pedido")
@Slf4j
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public Page<Pedido> getPedidos(QueryParamsPedido params) {
        return pedidoService.getPedidos(params);
    }

    @PostMapping("/create")
    public Pedido create(@RequestBody PedidoDto pedido) {
       return pedidoService.create(pedido);
    }

    @PutMapping("/{id}")
    public Pedido update(@RequestBody PedidoDto pedido, @PathVariable String id) {
        return pedidoService.update(pedido, id);
    }

    @GetMapping("/{id}")
    public Pedido getPedido(@PathVariable String id) {
        return pedidoService.getPedidoById(id);
    }

    @GetMapping("/total-vendas")
    public Pedido getTotalVendas(QueryParamsPedido params) {
        return pedidoService.getTotalVendas(params);
    }

    @PutMapping("/status/{status}")
    public Pedido getTotalVendas(@PathVariable String status) {
        return pedidoService.changeStatus(status);
    }
}
