package org.example;

import org.example.dao.ConsDao;
import org.example.dao.ConsDaoImpl;
import org.example.dao.CustomFilter;
import org.example.entity.Consumer;

import java.sql.SQLException;
import java.util.List;

public class JdbcMain {
    public static void main(String[] args) throws SQLException {
        ConsDao consDao = ConsDaoImpl.getInstance();

        Consumer consumer = new Consumer();
        consumer.setName("Max");
        consumer.setEmail("max111@yandex.ru");
        Consumer con = consDao.save(consumer);
//        consDao.update(consumer);
        System.out.println(con);

        List<Consumer> list = consDao.findAllConsumer(new CustomFilter(20, 0, null, "ru"));
        System.out.println(list);
    }
}
