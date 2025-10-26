package io.eddie.demo.domain.orders.infrastructure.adapter.in;

import io.eddie.demo.common.model.web.BaseResponse;
import io.eddie.demo.domain.orders.application.service.OrderApplication;
import io.eddie.demo.domain.orders.domain.model.Orders;
import io.eddie.demo.domain.orders.infrastructure.model.dto.OrderDescription;
import io.eddie.demo.domain.orders.infrastructure.model.vo.CreateOrderRequest;
import io.eddie.demo.domain.orders.infrastructure.mapper.OrderMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderApplication orderService;
    @PostMapping
    public BaseResponse<OrderDescription> create(
            @AuthenticationPrincipal(expression = "accountCode") String accountCode,
            @RequestBody @Valid CreateOrderRequest request
    ) {

        Orders order = orderService.createOrder(accountCode, request);

        return new BaseResponse<>(
                OrderMapper.toOrderDescription(order),
                "성공적으로 주문되었습니다."
        );

    }

    @GetMapping("/{orderCode}")
    public BaseResponse<OrderDescription> getDescription(
            @AuthenticationPrincipal(expression = "accountCode") String accountCode,
            @PathVariable String orderCode
    ) {

        Orders order = orderService.getOrder(accountCode, orderCode);

        return new BaseResponse<>(
                OrderMapper.toOrderDescription(order),
                "주문이 성공적으로 조회되었습니다."
        );

    }

}
