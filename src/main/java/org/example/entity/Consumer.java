package org.example.entity;

import java.util.Objects;
import java.util.Set;

public class Consumer {

    private long id;

    private String name;

    private String email;

    Role role;

    Company company;

    AddressConsumerHome address;

    Set<Subscribe> subscribes;


    public Consumer() {
    }

    public Consumer(long id, String name, String email, Role role, Company company, AddressConsumerHome address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.company = company;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public AddressConsumerHome getAddress() {
        return address;
    }

    public void setAddress(AddressConsumerHome address) {
        this.address = address;
    }

    public Set<Subscribe> getSubscriberConsSet() {
        return subscribes;
    }

    public void setSubscriberConsSet(Set<Subscribe> subscriberConsSet) {
        this.subscribes = subscriberConsSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumer consumer = (Consumer) o;
        return Objects.equals(name, consumer.name) && Objects.equals(email, consumer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }

    @Override
    public String toString() {
        return "Consumer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", company=" + company +
                ", address=" + address +
                '}';
    }
}
