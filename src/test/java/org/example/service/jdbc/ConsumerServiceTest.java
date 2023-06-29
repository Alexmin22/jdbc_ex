package org.example.service.jdbc;

import org.example.dao.jdbc.CustomFilter;
import org.example.entity.AddressConsumerHome;
import org.example.entity.Company;
import org.example.entity.Consumer;
import org.example.entity.Role;
import org.example.util.jdbc.ConnectionManagerTest;
import org.example.util.jdbc.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ConsumerServiceTest {

    private static final ConsumerService cons = new ConsumerService();

    @BeforeEach
    public void dataLoader() {
        TestDataImporter.importData();
    }

    @Test
    void getAllTest() {
        List<Consumer> list = cons.getAll(ConnectionManagerTest.buildConnection());
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
    }

    @Test
    void getByIDTest() {
        Consumer consumer = cons.getById(2L, ConnectionManagerTest.buildConnection());

        assertThat(consumer).isEqualTo(new Consumer(2L, "Ivan", "vanya@vk.com", Role.USER,
                new Company(2L, "RostselMash"),
                new AddressConsumerHome(2L, "Rostov", "Donskaya, 1")));
    }

    @Test
    public void getAllWithFilterTest() throws Exception {

        List<Consumer> list = cons.getAllWithFilter(new CustomFilter
                (20, 0, null, "ru", null), ConnectionManagerTest.buildConnection());
        List<Consumer> list2 = cons.getAllWithFilter(new CustomFilter
                (20, 0, "Viktor", null, Role.ADMIN), ConnectionManagerTest.buildConnection());
        List<Consumer> list3 = cons.getAllWithFilter(new CustomFilter
                (20, 0, "an", "vanya@vk.com", Role.USER), ConnectionManagerTest.buildConnection());
        List<Consumer> list4 = cons.getAllWithFilter(new CustomFilter
                (20, 0, "Iva", "nya@vk.co", null), ConnectionManagerTest.buildConnection());
        List<Consumer> list5 = cons.getAllWithFilter(new CustomFilter
                (20, 0, "Sema", null, null), ConnectionManagerTest.buildConnection());
        List<Consumer> list6 = cons.getAllWithFilter(new CustomFilter
                (20, 0, null, "ya@", null), ConnectionManagerTest.buildConnection());
        List<Consumer> list7 = cons.getAllWithFilter(new CustomFilter
                (20, 0, null, null, Role.USER), ConnectionManagerTest.buildConnection());
        List<Consumer> list8 = cons.getAllWithFilter(new CustomFilter
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
    void addTest() {

        Company company = new Company(2L, "RostselMash");
        AddressConsumerHome addressConsumerHome = new AddressConsumerHome(1L, "Krasnodar", "Krasnaya, 1");
        Consumer consumer = new Consumer(10L, "Petr", "petr@vk.com", Role.USER,
                company,
                addressConsumerHome);

        Consumer consumer1 = cons.add(consumer, ConnectionManagerTest.buildConnection());

        assertThat(consumer).isEqualTo(consumer1);
    }

    @Test
    void deleteByIDTest() {
        cons.deleteById(1L, ConnectionManagerTest.buildConnection());

        assertThrows(RuntimeException.class, () -> cons.getById(1L, ConnectionManagerTest.buildConnection()));
    }
}
