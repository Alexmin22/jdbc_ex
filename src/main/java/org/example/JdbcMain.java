package org.example;

import org.example.dao.*;
import org.example.entity.*;

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

        AddressConsumerHome address = new AddressConsumerHome(1L, "Maykop", "Lenina, 34");

        Company company = new Company(1L, "Amazon");

        Subscribe subscribe = new Subscribe(1L, "vk");
        Subscribe subscribe2 = new Subscribe(1L, "yandex");
        Subscribe subscribe3 = new Subscribe(1L, "youtube");

        AddressConsumerHome addr = addressDao.save(address);
        Company com = companyEntityDao.save(company);
        Subscribe sub = subjectDao.save(subscribe);
        Subscribe sub2 = subjectDao.save(subscribe2);
        Subscribe sub3 = subjectDao.save(subscribe3);

        Consumer consumer = new Consumer();
        consumer.setName("Filipp");
        consumer.setEmail("feel@rambler.ru");
        consumer.setRole(Role.ADMIN);
        consumer.setAddress(address);
        consumer.setCompany(company);
        consumer.setSubscriberConsSet(Set.of(subscribe, subscribe2, subscribe3));
        Consumer con = consDao.save(consumer);





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
