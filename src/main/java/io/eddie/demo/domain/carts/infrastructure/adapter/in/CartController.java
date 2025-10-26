package io.eddie.demo.domain.carts.infrastructure.adapter.in;

import io.eddie.demo.common.model.web.BaseResponse;
import io.eddie.demo.domain.carts.application.port.in.CartCrudUseCase;
import io.eddie.demo.domain.carts.infrastructure.mapper.CartMapper;
import io.eddie.demo.domain.carts.infrastructure.model.dto.CartDescription;
import io.eddie.demo.domain.carts.infrastructure.model.dto.CartItemDescription;
import io.eddie.demo.domain.carts.domain.model.Cart;
import io.eddie.demo.domain.carts.domain.model.CartItem;
import io.eddie.demo.domain.carts.infrastructure.model.web.CreateCartItemRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartCrudUseCase cartService;

    @GetMapping
    public BaseResponse<CartDescription> getDescription(
            @AuthenticationPrincipal(expression = "accountCode") String accountCode
    ) {
        return new BaseResponse<>(
                CartMapper.toCartDescription(cartService.
                        getByAccountCode(accountCode)),
                "장바구니를 성공적으로 조회하였습니다"
        );
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<CartItemDescription> appendItem(
            @AuthenticationPrincipal(expression = "accountCode") String accountCode,
            @RequestBody @Valid CreateCartItemRequest request
    ) {
        CartItem cartItem = cartService.appendItem(accountCode, request);
        return new BaseResponse<>(
                CartMapper.toCartItemDescription(cartItem),
                "장바구니에 항목이 성공적으로 추가되었습니다."
        );
    }

    @DeleteMapping("/items/{itemCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BaseResponse<Void> deleteItem(
            @AuthenticationPrincipal(expression = "accountCode") String accountCode,
            @PathVariable String itemCode
    ) {
        cartService.deleteItem(accountCode, itemCode);
        return new BaseResponse<>(
                null,
                "장바구니에 항목이 성공적으로 삭제되었습니다."
        );
    }

    @PatchMapping("/items/{itemCode}/increase")
    public BaseResponse<CartItemDescription> increaseQuantity(
            @AuthenticationPrincipal(expression = "accountCode") String accountCode,
            @PathVariable String itemCode
    ) {
        CartItem cartItem = cartService.increaseQuantity(accountCode, itemCode);
        return new BaseResponse<>(
                CartMapper.toCartItemDescription(cartItem),
                "장바구니 항목 갯수가 성공적으로 증가하였습니다."
        );
    }

    @PatchMapping("/items/{itemCode}/decrease")
    public BaseResponse<CartItemDescription> decreaseQuantity(
            @AuthenticationPrincipal(expression = "accountCode") String accountCode,
            @PathVariable String itemCode
    ) {
        CartItem cartItem = cartService.decreaseQuantity(accountCode, itemCode);
        return new BaseResponse<>(
                CartMapper.toCartItemDescription(cartItem),
                "장바구니 항목 갯수가 성공적으로 감소되었습니다."
        );
    }


}
