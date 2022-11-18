package com.erp.pedido.rest;

import com.erp.pedido.Pedido;
import com.erp.pedido.dto.PedidoDto;
import com.erp.pedido.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping("/create")
    public Pedido create(PedidoDto pedido) {
       return pedidoService.create(pedido);
    }

    @PutMapping("/{id}")
    public Pedido update(PedidoDto pedido, @PathVariable String id) {
        return pedidoService.update(pedido, id);
    }
}
