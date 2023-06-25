package org.example.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address_consumer_home")
@Builder
public class Company implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String street;
    @Transient
    private Consumer consumer;

    public AddressConsumerHome(Long id, String city, String street) {
        this.id = id;
        this.city = city;
        this.street = street;
    }
}
