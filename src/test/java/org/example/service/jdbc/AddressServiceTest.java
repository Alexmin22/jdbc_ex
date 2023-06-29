package org.example.service.jdbc;

import org.example.entity.AddressConsumerHome;
import org.example.util.jdbc.ConnectionManagerTest;
import org.example.util.jdbc.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class AddressServiceTest {

    private static final AddressService address = new AddressService();

    @BeforeEach
    public void dataLoader() {
        TestDataImporter.importData();
    }

    @Test
    void getAllTest() {
        List<AddressConsumerHome> list = address.getAll(ConnectionManagerTest.buildConnection());

        assertAll(
                () -> assertEquals(2, list.size()),
                () -> assertEquals(new AddressConsumerHome(1L, "Krasnodar", "Krasnaya, 1"),
                        list.get(0)),
                () -> assertEquals(new AddressConsumerHome(2L, "Rostov", "Donskaya, 1"),
                        list.get(1))
        );
    }

    @Test
    void findByIDTest() {
        AddressConsumerHome addressConsumerHome = address.getById(1L, ConnectionManagerTest.buildConnection());

        assertThat(addressConsumerHome)
                .isEqualTo(new AddressConsumerHome(1L, "Krasnodar", "Krasnaya, 1"));
    }

    @Test
    void addTest() {
        AddressConsumerHome addressConsumerHome = new AddressConsumerHome(3L, "Voronezh", "Glavnaya, 10");

        AddressConsumerHome address2 = address.add(addressConsumerHome, ConnectionManagerTest.buildConnection());

        assertThat(address2).isEqualTo(addressConsumerHome);
    }

    @Test
    void updateTest() {
        AddressConsumerHome addressConsumerHome = address.getById(1L, ConnectionManagerTest.buildConnection());
        addressConsumerHome.setCity("Stavropol");
        addressConsumerHome.setStreet("ul. Novaya, 5");
        AddressConsumerHome addressConsumerHome2 =
                address.update(addressConsumerHome, ConnectionManagerTest.buildConnection());

        assertAll(
                () -> assertEquals(addressConsumerHome2.getId(), 1L),
                () -> assertEquals(addressConsumerHome2.getCity(), "Stavropol"),
                () -> assertEquals(addressConsumerHome2.getStreet(), "ul. Novaya, 5"),
                () -> assertEquals(addressConsumerHome2, addressConsumerHome)
        );
    }

    @Test
    void deleteByIDTest() {
        AddressConsumerHome addressConsumerHome = address.getById(1L, ConnectionManagerTest.buildConnection());
        assertEquals(addressConsumerHome, new AddressConsumerHome(1L, "Krasnodar", "Krasnaya, 1"));

        address.deleteById(1L, ConnectionManagerTest.buildConnection());

        assertThrows(RuntimeException.class, () -> address.getById(1L, ConnectionManagerTest.buildConnection()));
    }
}
