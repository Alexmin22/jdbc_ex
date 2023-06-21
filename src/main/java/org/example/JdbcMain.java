package org.example;

import org.example.dao.*;
import org.example.entity.*;
import org.example.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JdbcMain {
    public static void main(String[] args) throws SQLException {

        ConsumerEntityDaoImpl consDao = ConsumerEntityDaoImpl.getInstance();
        AddressConsumerHomeEntityDaoImpl addressDao = AddressConsumerHomeEntityDaoImpl.getInstance();
        CompanyEntityDaoImpl companyEntityDao = CompanyEntityDaoImpl.getInstance();
        SubscribeEntityDaoImpl subjectDao = SubscribeEntityDaoImpl.getInstance();

        AddressConsumerHome address = new AddressConsumerHome(1L, "Maykop222", "Lenina, 34");

        Company company = new Company(1L, "Amazon222");

        Subscribe subscribe = new Subscribe(1L, "vk222");
        Subscribe subscribe2 = new Subscribe(1L, "yandex222");
        Subscribe subscribe3 = new Subscribe(1L, "youtube222");

        AddressConsumerHome addr = addressDao.save(address, ConnectionManager.get());
        Company com = companyEntityDao.save(company, ConnectionManager.get());
        Subscribe sub = subjectDao.save(subscribe, ConnectionManager.get());
        Subscribe sub2 = subjectDao.save(subscribe2, ConnectionManager.get());
        Subscribe sub3 = subjectDao.save(subscribe3, ConnectionManager.get());

        Consumer consumer = new Consumer();
        consumer.setName("Filipp22");
        consumer.setEmail("feel22@rambler.ru");
//        consumer.setRole(1);
//        consumer.setAddress(address);
//        consumer.setCompany(company);
        consumer.setSubscriberConsSet(Set.of(subscribe, subscribe2, subscribe3));
        Consumer con = consDao.save(consumer, ConnectionManager.get());





//        con.setName("Vasya");
//        con.setRole(Role.USER);
//        con.setEmail("vasya@vk.com");
//        consDao.update(con);
//        System.out.println(con);
//
//        List<Consumer> list = consDao.findAllWithFilter(new CustomFilter(20, 0, null, "ru", Role.USER));
//        System.out.println(list);
    }
}
