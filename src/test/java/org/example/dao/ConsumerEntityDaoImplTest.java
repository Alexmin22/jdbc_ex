package org.example.dao;

import org.example.entity.AddressConsumerHome;
import org.example.entity.Company;
import org.example.entity.Consumer;
import org.example.entity.Role;
import org.example.util.ConnectionManagerTest;
import org.example.util.TestDataImporter;
import org.example.utils.ConnectionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ConsumerEntityDaoImplTest {
    private static final ConsumerEntityDaoImpl cons = new ConsumerEntityDaoImpl();

    @BeforeEach
    public void dataLoader() {
        TestDataImporter.importData();
    }
    @Test
    void findAllTest() {
        try {
            List<Consumer> list = cons.findAll(ConnectionManagerTest.buildConnection());

            assertAll(
                    () -> assertEquals(2, list.size()),
                    () -> assertEquals(new Consumer(7L, "Ivan", "vanya@vk.com", Role.USER,
                                    new Company(2L, "RostselMash"),
                                    new AddressConsumerHome(2L, "Rostov", "Donskaya, 1")),
                                    list.get(0)),
                    () -> assertEquals(new Consumer(1L, "Viktor", "vitya@ya.ru", Role.ADMIN,
                            new Company(1L, "Magnit"),
                            new AddressConsumerHome(1L, "Krasnodar", "Krasnaya, 1")), list.get(1))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findByIDTest() {
        Consumer consumer = cons.findById(2L, ConnectionManagerTest.buildConnection());

        assertThat(consumer).isEqualTo(new Consumer(2L, "Ivan", "vanya@vk.com", Role.USER,
                new Company(2L, "RostselMash"),
                new AddressConsumerHome(2L, "Rostov", "Donskaya, 1")));
    }

    @Test
    void saveTest() {
        Consumer consumer = new Consumer(10L, "Petr", "petr@vk.com", Role.USER,
                new Company(2L, "RostselMash"),
                new AddressConsumerHome(3L, "Perm", "ul. Lenina, 1"));

        Consumer consumer1  = cons.save(consumer, ConnectionManagerTest.buildConnection());

        assertThat(consumer).isEqualTo(consumer1);
    }
}
