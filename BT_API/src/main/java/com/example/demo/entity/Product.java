package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer quantity;

    @Column(name = "description")
    private String desc;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}