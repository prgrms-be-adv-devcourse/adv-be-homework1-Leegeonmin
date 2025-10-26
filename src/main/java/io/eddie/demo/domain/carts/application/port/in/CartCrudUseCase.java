package io.eddie.demo.domain.carts.application.port.in;

import io.eddie.demo.domain.carts.domain.model.Cart;
import io.eddie.demo.domain.carts.domain.model.CartItem;
import io.eddie.demo.domain.carts.infrastructure.model.web.CreateCartItemRequest;

import java.util.List;
import java.util.Optional;

public interface CartCrudUseCase {
    Cart save(String accountCode);

    Cart getByAccountCode(String accountCode);

    CartItem getItem(String accountCode, String cartItemCode);
    List<CartItem> getItemsByCodes(List<String> cartItemCodes);

    CartItem appendItem(String accountCode, CreateCartItemRequest request);

    CartItem increaseQuantity(String accountCode, String cartItemCode);
    CartItem decreaseQuantity(String accountCode, String cartItemCode);

    void deleteItem(String accountCode, String cartItemCode);

    void deleteItemsByCodes(List<String> cartItemCodes);

}
