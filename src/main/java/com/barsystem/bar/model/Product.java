package com.barsystem.bar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String subtype;

    @NotNull
    @Column(nullable = false)
    private String brand;

    @NotNull
    @Column(nullable = false)
    private double price;

    @NotNull
    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    private ProductType type;

}
