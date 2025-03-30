package com.app.furniture.entity;

import com.app.furniture.enums.OrderState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double totalPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderedAt;
    @Column(nullable = false, updatable = false)
    private LocalDateTime deliveredAt;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderState state;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
