package io.eddie.demo.domain.carts.application.service;

import io.eddie.demo.domain.carts.application.port.in.CartCrudUseCase;
import io.eddie.demo.domain.carts.application.port.out.CartItemPersistencePort;
import io.eddie.demo.domain.carts.application.port.out.CartPersistencePort;
import io.eddie.demo.domain.carts.domain.exception.CouldNotDecreaseCartItemQuantity;
import io.eddie.demo.domain.carts.domain.exception.CouldNotFindCartException;
import io.eddie.demo.domain.carts.domain.exception.CouldNotFindCartItemException;
import io.eddie.demo.domain.carts.domain.model.Cart;
import io.eddie.demo.domain.carts.domain.model.CartItem;
import io.eddie.demo.domain.carts.infrastructure.model.web.CreateCartItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartApplication implements CartCrudUseCase {

    private final CartPersistencePort cartPersistencePort;
    private final CartItemPersistencePort cartItemPersistencePort;

    @Override
    @Transactional
    public Cart save(String accountCode) {
        Cart cart = new Cart(accountCode);
        return cartPersistencePort.Save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public Cart getByAccountCode(String accountCode) {
        Optional<Cart> byAccountCode = cartPersistencePort.findByAccountCode(accountCode);
        if(byAccountCode.isEmpty()) {
            throw new CouldNotFindCartException("장바구니를 찾을 수 없습니다");
        }
        return byAccountCode.get();
    }

    @Override
    @Transactional(readOnly = true)
    public CartItem getItem(String accountCode, String cartItemCode) {
        return cartItemPersistencePort.findOwnCartItem(accountCode, cartItemCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 장바구니 항목을 찾을 수 없습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> getItemsByCodes(List<String> cartItemCodes) {
        return cartItemPersistencePort.findAllByCodesIn(cartItemCodes);
    }

    @Override
    @Transactional
    public CartItem appendItem(String accountCode, CreateCartItemRequest request) {
        Cart byAccountCode = getByAccountCode(accountCode);
        CartItem cartItem = new CartItem(byAccountCode,
                null,
                request.productCode(),
                request.productName(),
                request.productPrice(),
                request.quantity());

        return cartItemPersistencePort.save(cartItem);
    }

    @Override
    @Transactional
    public CartItem increaseQuantity(String accountCode, String cartItemCode) {
        CartItem cartItem = getItem(accountCode, cartItemCode);
        cartItem.increaseQuantity();

        return  cartItemPersistencePort.save(cartItem);
    }

    @Override
    @Transactional
    public CartItem decreaseQuantity(String accountCode, String cartItemCode) {
        CartItem cartItem = getItem(accountCode, cartItemCode);
        if(cartItem.canDecrease()){
            cartItem.decreaseQuantity();
        }else{
            throw new CouldNotDecreaseCartItemQuantity("상품의 수량을 줄일 수 없습니다");
        }

        return  cartItemPersistencePort.save(cartItem);
    }



    @Override
    @Transactional
    public void deleteItem(String accountCode, String cartItemCode) {
        CartItem targetCartItem = getItem(accountCode, cartItemCode);
        targetCartItem.delete();
        cartItemPersistencePort.save(targetCartItem);
    }

    @Override
    @Transactional
    public void deleteItemsByCodes(List<String> cartItemCodes) {
        List<CartItem> items = getItemsByCodes(cartItemCodes);
        for (CartItem item : items) {
            item.delete();
            cartItemPersistencePort.save(item);
        }
    }
}
