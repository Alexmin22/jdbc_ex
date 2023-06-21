package org.example.entity;

import java.util.List;
import java.util.Objects;

public class Company {
    long id;
    private String name;
    private List<Consumer> consumerSet;

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

    public List<Consumer> getConsumerSet() {
        return consumerSet;
    }

    public void setConsumerSet(List<Consumer> consumerSet) {
        this.consumerSet = consumerSet;
    }

    public void addInCompany(Consumer consumer) {
        consumerSet.add(consumer);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Company() {
    }

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id && Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
