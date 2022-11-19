package com.erp.pedido.service;

import com.erp.empresa.dal.EmpresaRepository;
import com.erp.exception.BusinessException;
import com.erp.exception.Check;
import com.erp.pagamento.Pagamento;
import com.erp.pedido.Pedido;
import com.erp.pedido.dal.PedidoRepository;
import com.erp.pedido.dto.PedidoDto;
import com.erp.pedido.rest.QueryParamsPedido;
import com.erp.users.Client;
import com.erp.users.ClientRepository;
import com.erp.users.User;
import com.erp.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
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

        log.info("vendedor: {}, total: {}", dto.getVendedorId(), dto.getTotal());
        return pedidoRepository.save(pedido);
    }

    private static void checkPagamentoDoPedido(Pedido pedido) {
        if (!Objects.equals(pedido.getStatus(), Pedido.Status.CONCLUIDO)) {
            return;
        }

        if (Objects.isNull(pedido.getMei())) {

            final Set<Pagamento> pagamentosOnline = pedido.getPagamento().stream()
                    .filter(ped -> ped.getTipoPagamento().equals(Pagamento.TipoPagamento.PIX) || ped.getTipoPagamento().equals(Pagamento.TipoPagamento.TED)
                            || ped.getTipoPagamento().equals(Pagamento.TipoPagamento.CARTAO_CREDITO) || ped.getTipoPagamento().equals(Pagamento.TipoPagamento.CARTAO_DEBITO))
                    .collect(Collectors.toSet());

            Check.notEmpty(pagamentosOnline, "Pagamentos online NAO podem ser pagos sem uma conta de empresa referenciada");
        }
        final double totalPagamento = pedido.getPagamento().stream().map(Pagamento::getValor).mapToDouble(Double::doubleValue).sum();
        final double totalPedido = Double.parseDouble(pedido.getTotal());

        if (Double.compare(totalPagamento, totalPedido) != 0) {
            throw new BusinessException("Voce nao pode concluir um pedido se o valor dos pagamentos nao for igual ao valor do pedido");
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

    public Pedido getPedidoById(String id) {
        return pedidoRepository.findById(Long.valueOf(id)).orElseThrow(() -> new RuntimeException(String.format("Pedido de ID %s nao existe", id)));
    }

    public Page<Pedido> getPedidos(QueryParamsPedido params) {
        return pedidoRepository.findByFilters(params.getId(), params.getClientName(), params.getClientName(), params.getVendedorName(), params.getCusto(), params.getValor(), params.getLucro(), params.getDataVenda(), params.getSorting());
    }

    public Pedido getTotalVendas(QueryParamsPedido params) {
        //TODO
        return Strings.isBlank(params.getDataFinal()) || Strings.isBlank(params.getDataInicial()) ? pedidoRepository.findVendasTotal() : pedidoRepository.findVendasTotalByParams(params);
    }

    //TODO
    public Pedido changeStatus(String status) {
        return null;
    }
}
