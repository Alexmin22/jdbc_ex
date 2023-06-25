package org.example.dao.jdbc;

import org.example.entity.AddressConsumerHome;
import org.example.entity.Consumer;
import org.example.entity.Role;
import org.example.util.ConnectionManagerTest;
import org.example.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

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
            System.out.println(list);

            assertAll(
                    () -> assertEquals(2, list.size()),
                    () -> assertEquals(new Consumer(2L, "Ivan", "vanya@vk.com", Role.USER,
                                    new Company(2L, "RostselMash"),
                                    new AddressConsumerHome(2L, "Rostov", "Donskaya, 1")),
                            list.get(1)),

                    () -> assertEquals(new Consumer(1L, "Viktor", "vitya@ya.ru", Role.ADMIN,
                                    new Company(1L, "Magnit"),
                                    new AddressConsumerHome(1L, "Krasnodar", "Krasnaya, 1")),
                            list.get(0))
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
    public void FindAllWithFilterTest() throws Exception {

        List<Consumer> list = cons.findAllWithFilter(new CustomFilter
                (20, 0, null, "ru", null), ConnectionManagerTest.buildConnection());
        List<Consumer> list2 = cons.findAllWithFilter(new CustomFilter
                (20, 0, "Viktor", null, Role.ADMIN), ConnectionManagerTest.buildConnection());
        List<Consumer> list3 = cons.findAllWithFilter(new CustomFilter
                (20, 0, "an", "vanya@vk.com", Role.USER), ConnectionManagerTest.buildConnection());
        List<Consumer> list4 = cons.findAllWithFilter(new CustomFilter
                (20, 0, "Iva", "nya@vk.co", null), ConnectionManagerTest.buildConnection());
        List<Consumer> list5 = cons.findAllWithFilter(new CustomFilter
                (20, 0, "Sema", null, null), ConnectionManagerTest.buildConnection());
        List<Consumer> list6 = cons.findAllWithFilter(new CustomFilter
                (20, 0, null, "ya@", null), ConnectionManagerTest.buildConnection());
        List<Consumer> list7 = cons.findAllWithFilter(new CustomFilter
                (20, 0, null, null, Role.USER), ConnectionManagerTest.buildConnection());
        List<Consumer> list8 = cons.findAllWithFilter(new CustomFilter
                (20, 0, "Viktor", "vanya@vk.com", Role.USER), ConnectionManagerTest.buildConnection());

        assertAll(
                () -> assertEquals(1, list.size()),
                () -> assertEquals(1, list2.size()),
                () -> assertEquals(1, list3.size()),
                () -> assertEquals(1, list4.size()),
                () -> assertEquals(0, list5.size()),
                () -> assertEquals(2, list6.size()),
                () -> assertEquals(1, list7.size()),
                () -> assertEquals(0, list8.size())
        );
    }

    @Test
    void saveTest() {

        Company company = new Company(2L, "RostselMash");
        AddressConsumerHome addressConsumerHome = new AddressConsumerHome(1L, "Krasnodar", "Krasnaya, 1");
        Consumer consumer = new Consumer(10L, "Petr", "petr@vk.com", Role.USER,
                company,
                addressConsumerHome);

        Consumer consumer1 = cons.save(consumer, ConnectionManagerTest.buildConnection());

        assertThat(consumer).isEqualTo(consumer1);
    }

    @Test
    void deleteByIDTest() {
        try {
            cons.deleteById(1L, ConnectionManagerTest.buildConnection());

            assertThrows(RuntimeException.class, () -> cons.findById(1L, ConnectionManagerTest.buildConnection()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
