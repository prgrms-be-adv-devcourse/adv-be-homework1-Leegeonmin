package io.eddie.demo.domain.orders.infrastructure.model.entity;

import io.eddie.demo.common.model.persistence.BaseEntity;
import io.eddie.demo.domain.orders.domain.vo.OrderState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdersEntity extends BaseEntity {
    @Setter
    private String accountCode;

    @Setter
    @Enumerated(EnumType.STRING)
    private OrderState orderState = OrderState.CREATED;

    @OneToMany(mappedBy = "order")
    @Setter
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    @Builder
    public OrdersEntity(String accountCode) {
        this.accountCode = accountCode;
    }
}
