package com.erp.pedido.service;

import com.erp.empresa.dal.EmpresaRepository;
import com.erp.exception.Check;
import com.erp.pagamento.Pagamento;
import com.erp.pedido.Pedido;
import com.erp.pedido.dal.PedidoRepository;
import com.erp.pedido.dto.PedidoDto;
import com.erp.users.Client;
import com.erp.users.ClientRepository;
import com.erp.users.User;
import com.erp.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final EmpresaRepository empresaRepository;


    public Pedido create(PedidoDto dto) {
        Check.nonNull(dto.getTotal(), "Total do pedido nao pode ser vazio");
        Check.nonNull(dto.getClientId(), "Cliente nao pode ser vazio");
        Check.nonNull(dto.getProduto(), "Nao pode criar um pedido sem produto");
        Check.nonNull(dto.getPagamento(), "Nao pode criar um pedido sem pagamento");
        Check.nonNull(dto.getVendedorId(), "Nao pode criar um pedido sem vendedor");

        final User vendedor = userRepository.findById(dto.getVendedorId()).orElseThrow(() -> new RuntimeException("Codigo vendedor nao existe"));
        final Client client = clientRepository.findById(dto.getClientId()).orElseThrow(() -> new RuntimeException("Codigo cliente nao existe"));

        final Pedido pedido = dto.to(client, vendedor);

        ofNullable(dto.getMeiId())
                .map(empresaRepository::ById)
                .ifPresent(pedido::setMei);


        checkPagamentoDoPedido(pedido);

        log.info("Salvando pedido: {}, vendedor: {}, total: {}", pedido.getId(), dto.getVendedorId(), dto.getTotal());
        return pedidoRepository.save(pedido);
    }

    private static void checkPagamentoDoPedido(Pedido pedido) {
        if (Objects.isNull(pedido.getMei())) {

            final Set<Pagamento> pagamentosOnline = pedido.getPagamento().stream()
                    .filter(ped -> ped.getTipoPagamento().equals(Pagamento.tipoPagamento.PIX) || ped.getTipoPagamento().equals(Pagamento.tipoPagamento.TED)
                            || ped.getTipoPagamento().equals(Pagamento.tipoPagamento.CARTAO_CREDITO) || ped.getTipoPagamento().equals(Pagamento.tipoPagamento.CARTAO_DEBITO))
                    .collect(Collectors.toSet());

            Check.notEmpty(pagamentosOnline, "Pagamentos online NAO podem ser pagos sem uma conta de empresa referenciada");
        }
    }

    public Pedido update(PedidoDto pedidoDto, String id) {
        final Pedido pedido = pedidoRepository.findById(Long.valueOf(id)).orElseThrow(() -> new RuntimeException("Codigo pedido nao existe"));

        ofNullable(pedidoDto.getPagamento())
                .ifPresent(pedido::setPagamento);
        ofNullable(pedidoDto.getMeiId())
                .map(empresaRepository::ById)
                .ifPresent(pedido::setMei);
        ofNullable(pedidoDto.getMarketingUuid())
                .ifPresent(pedido::setMarketingUuid);

        checkPagamentoDoPedido(pedido);
        return pedidoRepository.save(pedido);
    }
}
