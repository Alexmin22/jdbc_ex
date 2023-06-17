package org.example.entity;

import java.util.Objects;

public class AddressConsumerHome {
    long id;
    String city;
    String street;
    Consumer consumer;

    public AddressConsumerHome(long id, String city, String street) {
        this.id = id;
        this.city = city;
        this.street = street;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressConsumerHome that = (AddressConsumerHome) o;
        return id == that.id && Objects.equals(city, that.city) && Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street);
    }

    @Override
    public String toString() {
        return "AddressConsumerHome{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
