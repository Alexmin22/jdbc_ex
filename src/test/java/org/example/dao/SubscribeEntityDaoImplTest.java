package org.example.dao;

import org.example.entity.Company;
import org.example.entity.Subscribe;
import org.example.util.ConnectionManagerTest;
import org.example.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubscribeEntityDaoImplTest {

    private static final SubscribeEntityDaoImpl subscribeEntityDao = new SubscribeEntityDaoImpl();
    @BeforeEach
    public void dataLoader() {
        TestDataImporter.importData();
    }
    @Test
    void findAllTest() {
        try {
            List<Subscribe> list = subscribeEntityDao.findAll(ConnectionManagerTest.buildConnection());

            assertAll(
                    () -> assertEquals(5, list.size()),
                    () -> assertEquals(new Subscribe(1L, "Ok.ru"), list.get(1)),
                    () -> assertEquals(new Subscribe(2L, "jet brains"), list.get(0)),
                    () -> assertEquals(new Subscribe(3L, "vk"), list.get(3)),
                    () -> assertEquals(new Subscribe(4L, "yandex"), list.get(4)),
                    () -> assertEquals(new Subscribe(5L, "tricolor"), list.get(2))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findByIDTest() {
        try {
            Subscribe subscribe = subscribeEntityDao.findById(1L, ConnectionManagerTest.buildConnection());

            assertThat(subscribe)
                    .isEqualTo(new Subscribe(1L, "Ok.ru"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveTest() {
        Subscribe subscribe = new Subscribe(10L, "amazon-prime");

        Subscribe subscribe1 = subscribeEntityDao.save(subscribe, ConnectionManagerTest.buildConnection());

        assertThat(subscribe1).isEqualTo(subscribe);
    }

    @Test
    void updateTest() {
        try {
            Subscribe subscribe = subscribeEntityDao.findById(1L, ConnectionManagerTest.buildConnection());
            subscribe.setName("Romashka plus");
            Subscribe subscribe1 =
                    subscribeEntityDao.update(subscribe, ConnectionManagerTest.buildConnection());

            assertAll(
                    () -> assertEquals(subscribe1.getId(), 1L),
                    () -> assertEquals(subscribe1.getName(), "Romashka plus"),
                    () -> assertEquals(subscribe1, subscribe)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
