package org.example.util;

import org.example.dao.*;
import org.example.entity.*;

import java.sql.SQLException;
import java.util.Set;

public class TestDataImporter {

    private static final String CREATE_TABLE_ADDRESS_CONSUMER_HOME_SQL = """
                        create table IF NOT EXISTS address_consumer_home (
                                                               id bigint PRIMARY KEY AUTO_INCREMENT,
                                                               city varchar(55) NOT NULL,
                                                               street varchar(55) NOT NULL
            );
            """;

    private static final String DROP_TABLE_ADDRESS_CONSUMER_HOME_SQL = """
            DROP TABLE IF EXISTS address_consumer_home""";
    private static final String CREATE_TABLE_ROLES_SQL = """
                        create table IF NOT EXISTS roles (
                                                 id int primary key auto_increment,
                                                 roles varchar(25) NOT NULL
            );
            """;

    private static final String DROP_TABLE_ROLES_SQL = """
            DROP TABLE IF EXISTS roles""";
    private static final String DROP_TABLE_SUBSCRIBE_SQL = """
            DROP TABLE IF EXISTS subscribe""";
    private static final String CREATE_TABLE_SUBSCRIBE_SQL = """
            create table IF NOT EXISTS subscribe (
                                                     id bigint primary key auto_increment,
                                                     name varchar(50) not null unique
            );
            """;

    private static final String CREATE_TABLE_CONSUMER_SQL = """
                        create table if not exists consumer (
                                                    id bigint primary key auto_increment,
                                                    name varchar(30) not null,
                                                    email varchar(40) not null unique,
                                                    roles_id int,
                                                    address_id bigint,
                                                    company_id bigint,
                                                    foreign key(address_id) references address_consumer_home(id) on delete cascade,
                                                    foreign key(company_id) references company(id) on delete set null,
                                                    foreign key(roles_id) references roles(id) on delete set null
            );
            """;

    private static final String DROP_TABLE_CONSUMER_SQL = """
            DROP TABLE IF EXISTS consumer""";
    private static final String CREATE_TABLE_COMPANY_SQL = """   
            create table IF NOT EXISTS company (
                                                   id bigint primary key auto_increment,
                                                   name varchar(50) NOT NULL unique
            );
            """;

    private static final String DROP_TABLE_COMPANY_SQL = """
            DROP TABLE IF EXISTS company""";

    private final static EntityDao ADDRESS_DAO = new AddressConsumerHomeEntityDaoImpl();
    private final static EntityDao COMPANY_DAO = new CompanyEntityDaoImpl();
    private final static EntityDao CONSUMER_DAO = new ConsumerEntityDaoImpl();
    private final static EntityDao SUBSCRIBE_DAO = new SubscribeEntityDaoImpl();

    public static void importData() {
        dropTable();
        createTables();
        saveData();
    }

    private static void createTables() {

        try (var connection = ConnectionManagerTest.buildConnection();
             var statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_ADDRESS_CONSUMER_HOME_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (var connection = ConnectionManagerTest.buildConnection();
             var statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_ROLES_SQL);
            statement.execute("""
                    INSERT INTO 
                        roles(roles) 
                    VALUES 
                        ('ADMIN'), ('USER');
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (var connection = ConnectionManagerTest.buildConnection();
             var statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_SUBSCRIBE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try (var connection = ConnectionManagerTest.buildConnection();
             var statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_COMPANY_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (var connection = ConnectionManagerTest.buildConnection();
             var statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_CONSUMER_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveData() {
        AddressConsumerHome address = new AddressConsumerHome(1L, "Krasnodar", "Krasnaya, 1");
        AddressConsumerHome address2 = new AddressConsumerHome(2L, "Rostov", "Donskaya, 1");

        Company magnit = new Company(1L, "Magnit");
        Company rostselMash = new Company(2L, "RostselMash");

        Subscribe ok = new Subscribe(1L, "Ok.ru");
        Subscribe jetBrains = new Subscribe(2L, "jet brains");
        Subscribe vk = new Subscribe(3L, "vk");
        Subscribe yandex = new Subscribe(4L, "yandex");
        Subscribe tricolor = new Subscribe(5L, "tricolor");

        Consumer victor = new Consumer();
        victor.setName("Viktor");
        victor.setEmail("vitya@ya.ru");
        victor.setRole(Role.ADMIN);
        victor.setAddress(address);
        victor.setCompany(magnit);
        victor.setSubscriberConsSet(Set.of(yandex, tricolor));

        Consumer ivan = new Consumer(2L, "Ivan", "vanya@vk.com", Role.USER, rostselMash, address2);

        try {
            ADDRESS_DAO.save(address, ConnectionManagerTest.buildConnection());
            ADDRESS_DAO.save(address2, ConnectionManagerTest.buildConnection());

            COMPANY_DAO.save(magnit, ConnectionManagerTest.buildConnection());
            COMPANY_DAO.save(rostselMash, ConnectionManagerTest.buildConnection());

            SUBSCRIBE_DAO.save(ok, ConnectionManagerTest.buildConnection());
            SUBSCRIBE_DAO.save(jetBrains, ConnectionManagerTest.buildConnection());
            SUBSCRIBE_DAO.save(vk, ConnectionManagerTest.buildConnection());
            SUBSCRIBE_DAO.save(yandex, ConnectionManagerTest.buildConnection());
            SUBSCRIBE_DAO.save(tricolor, ConnectionManagerTest.buildConnection());

            CONSUMER_DAO.save(victor, ConnectionManagerTest.buildConnection());
            CONSUMER_DAO.save(ivan, ConnectionManagerTest.buildConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void dropTable() {
        try (var connection = ConnectionManagerTest.buildConnection();
             var statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_CONSUMER_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (var connection = ConnectionManagerTest.buildConnection();
             var statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_ADDRESS_CONSUMER_HOME_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (var connection = ConnectionManagerTest.buildConnection();
             var statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_ROLES_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (var connection = ConnectionManagerTest.buildConnection();
             var statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_COMPANY_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (var connection = ConnectionManagerTest.buildConnection();
             var statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_SUBSCRIBE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
