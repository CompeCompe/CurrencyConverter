package ru.eremenko.demo.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "currencyFrom")
    private String currencyFrom;
    @Column(name = "currencyTo")
    private String currencyTo;
    @Column(name = "amount")
    private Double amount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

}
