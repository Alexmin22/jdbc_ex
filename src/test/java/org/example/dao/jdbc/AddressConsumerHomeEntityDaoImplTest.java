package org.example.dao.jdbc;

import org.example.entity.AddressConsumerHome;
import org.example.util.ConnectionManagerTest;
import org.example.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddressConsumerHomeEntityDaoImplTest {

    private static final AddressConsumerHomeEntityDaoImpl address = new AddressConsumerHomeEntityDaoImpl();

    @BeforeEach
    public void dataLoader() {
        TestDataImporter.importData();
    }

    @Test
    void findAllTest() {
        try {
            List<AddressConsumerHome> list = address.findAll(ConnectionManagerTest.buildConnection());

            assertAll(
                    () -> assertEquals(2, list.size()),
                    () -> assertEquals(new AddressConsumerHome(1L, "Krasnodar", "Krasnaya, 1"),
                            list.get(0)),
                    () -> assertEquals(new AddressConsumerHome(2L, "Rostov", "Donskaya, 1"),
                            list.get(1))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findByIDTest() {
        try {
            AddressConsumerHome addressConsumerHome = address.findById(1L, ConnectionManagerTest.buildConnection());

            assertThat(addressConsumerHome)
                    .isEqualTo(new AddressConsumerHome(1L, "Krasnodar", "Krasnaya, 1"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveTest() {
        AddressConsumerHome addressConsumerHome = new AddressConsumerHome(3L, "Voronezh", "Glavnaya, 10");

        try {
            AddressConsumerHome address2 = address.save(addressConsumerHome, ConnectionManagerTest.buildConnection());

            assertThat(address2).isEqualTo(addressConsumerHome);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateTest() {
        try {
            AddressConsumerHome addressConsumerHome = address.findById(1L, ConnectionManagerTest.buildConnection());
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteByIDTest() {
        try {
            AddressConsumerHome addressConsumerHome = address.findById(1L, ConnectionManagerTest.buildConnection());
            assertEquals(addressConsumerHome, new AddressConsumerHome(1L, "Krasnodar", "Krasnaya, 1"));

            address.deleteById(1L, ConnectionManagerTest.buildConnection());

            assertThrows(RuntimeException.class, () -> address.findById(1L, ConnectionManagerTest.buildConnection()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
