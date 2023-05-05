package org.example.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Consumer")
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Consumer name cannot be blank")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Consumer email cannot be blank")
    @Email(message = "Invalid email")
    @Column(name = "email")
    private String email;

    public Consumer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Consumer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
