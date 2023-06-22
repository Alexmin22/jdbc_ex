//import org.example.dao.*;
//import org.example.entity.*;
//import org.example.util.ConnectionManagerTest;
//import org.example.utils.ConnectionManager;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class GeneralTest {
//
//    private static final String CLEAN = """
//            TRUNCATE TABLE Consumer;
//            TRUNCATE TABLE Company;
//            TRUNCATE TABLE addressconsumerhome;
//            TRUNCATE TABLE Subscribe;
//            """;
//
//    ConsumerEntityDaoImpl consDao = ConsumerEntityDaoImpl.getInstance();
//    AddressConsumerHomeEntityDaoImplTest addressDao = AddressConsumerHomeEntityDaoImplTest.getInstance();
//    CompanyEntityDaoImpl companyEntityDao = CompanyEntityDaoImpl.getInstance();
//    SubscribeEntityDaoImpl subjectDao = SubscribeEntityDaoImpl.getInstance();
//    @Test
//    void calculate() {
//        Assertions.assertEquals(1+1, 2);
//        assertThat(1+1).isEqualTo(2);
//    }
//
//    @Test
//    void createTable() {
//        String sql = """
//                create table IF NOT EXISTS addressConsumerHome (
//                                                                   id bigint PRIMARY KEY AUTO_INCREMENT,
//                                                                   city varchar(55) NOT NULL,
//                                                                   street varchar(55) NOT NULL
//                );
//                """;
//
//        try (var connection = ConnectionManagerTest.buildConnection();
//             var statement = connection.createStatement()) {
//            statement.execute(sql);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void createEntity() throws SQLException {
//
//
//        AddressConsumerHome address = new AddressConsumerHome(1L, "Krasnodar", "Krasnaya, 1");
//        AddressConsumerHome address2 = new AddressConsumerHome(2L, "Rostov", "Donskaya, 1");
//
//        Company company = new Company(1L, "Magnit");
//        Company company2 = new Company(1L, "RostselMash");
//
//        Subscribe subscribe = new Subscribe(1L, "Ok.ru");
//        Subscribe subscribe2 = new Subscribe(2L, "jet brains");
//        Subscribe subscribe3 = new Subscribe(3L, "vk");
//        Subscribe subscribe4 = new Subscribe(4L, "yandex");
//        Subscribe subscribe5 = new Subscribe(5L, "tricolor");
//
//        AddressConsumerHome addr = addressDao.save(address, ConnectionManager.get());
//        AddressConsumerHome addr2 = addressDao.save(address2, ConnectionManager.get());
//        Company com = companyEntityDao.save(company, ConnectionManager.get());
//        Company com2 = companyEntityDao.save(company2, ConnectionManager.get());
//        Subscribe sub = subjectDao.save(subscribe, ConnectionManager.get());
//        Subscribe sub2 = subjectDao.save(subscribe2, ConnectionManager.get());
//        Subscribe sub3 = subjectDao.save(subscribe3, ConnectionManager.get());
//        Subscribe sub4 = subjectDao.save(subscribe4, ConnectionManager.get());
//        Subscribe sub5 = subjectDao.save(subscribe5, ConnectionManager.get());
//
//        Consumer consumer = new Consumer();
//        consumer.setName("Viktor");
//        consumer.setEmail("vitya@ya.ru");
//        consumer.setRole(Role.ADMIN);
//        consumer.setAddress(address);
//        consumer.setCompany(com);
//        consumer.setSubscriberConsSet(Set.of(subscribe, subscribe2));
//
//        Consumer consumer2 = new Consumer(7L, "Ivan", "vanya@vk.com", Role.USER, com2, address2);
//        Consumer con = consDao.save(consumer, ConnectionManager.get());
//        Consumer con2 = consDao.save(consumer2, ConnectionManager.get());
//
//        List<Consumer> consumers = consDao.findAll(ConnectionManager.get());
//
//        assertAll(
//                () -> assertEquals(2, consumers.size()),
//                () -> assertEquals(con, consumers.get(0)),
//                () -> assertEquals(con2, consumers.get(1))
//        );
//    }
//
//    @Test
//    public void testFindAllWithFilter() throws Exception {
//
//        List<Consumer> list = consDao.findAllWithFilter(new CustomFilter
//                (20, 0, null, "ru", null), ConnectionManager.get());
//        List<Consumer> list2 = consDao.findAllWithFilter(new CustomFilter
//                (20, 0, "Viktor", null, Role.ADMIN), ConnectionManager.get());
//        List<Consumer> list3 = consDao.findAllWithFilter(new CustomFilter
//                (20, 0, "an", "vanya@vk.com", Role.USER), ConnectionManager.get());
//        List<Consumer> list4 = consDao.findAllWithFilter(new CustomFilter
//                (20, 0, "Iva", "nya@vk.co", null), ConnectionManager.get());
//        List<Consumer> list5 = consDao.findAllWithFilter(new CustomFilter
//                (20, 0, "Sema", null, null), ConnectionManager.get());
//        List<Consumer> list6 = consDao.findAllWithFilter(new CustomFilter
//                (20, 0, null, "ya@", null), ConnectionManager.get());
//        List<Consumer> list7 = consDao.findAllWithFilter(new CustomFilter
//                (20, 0, null, null, Role.USER), ConnectionManager.get());
//        List<Consumer> list8 = consDao.findAllWithFilter(new CustomFilter
//                (20, 0, "Viktor", "vanya@vk.com", Role.USER), ConnectionManager.get());
//
//        assertAll(
//                () -> assertEquals(1, list.size()),
//                () -> assertEquals(1, list2.size()),
//                () -> assertEquals(1, list3.size()),
//                () -> assertEquals(1, list4.size()),
//                () -> assertEquals(0, list5.size()),
//                () -> assertEquals(2, list6.size()),
//                () -> assertEquals(1, list7.size()),
//                () -> assertEquals(0, list8.size())
//        );
//    }
//
//    @Test
//    public void testAdding() throws SQLException {
//        AddressConsumerHome address = new AddressConsumerHome(3L, "Moscow", "Vozdvizhenka, 4");
//
//        Company company = new Company(3L, "Yandex");
//
//        Subscribe subscribe = new Subscribe(6L, "sub-1");
//        Subscribe subscribe2 = new Subscribe(7L, "sub-2");
//        Subscribe subscribe3 = new Subscribe(8L, "sub-3");
//
//        AddressConsumerHome addr = addressDao.save(address, ConnectionManager.get());
//        Company com = companyEntityDao.save(company, ConnectionManager.get());
//        Subscribe sub = subjectDao.save(subscribe, ConnectionManager.get());
//        Subscribe sub2 = subjectDao.save(subscribe2, ConnectionManager.get());
//        Subscribe sub3 = subjectDao.save(subscribe3, ConnectionManager.get());
//
//        Consumer consumer = new Consumer();
//        consumer.setName("Ruslan");
//        consumer.setEmail("rusik@gmail.com");
//        consumer.setRole(Role.ADMIN);
//        consumer.setAddress(address);
//        consumer.setCompany(company);
//        consumer.setSubscriberConsSet(Set.of(subscribe, subscribe2, subscribe3));
//        Consumer savedCons = consDao.save(consumer, ConnectionManager.get());
//
//        assertAll(
//                () -> assertEquals("Ruslan", savedCons.getName()),
//                () -> assertEquals("rusik@gmail.com", savedCons.getEmail()),
//                () -> assertEquals(Role.ADMIN, savedCons.getRole()),
//                () -> assertEquals(address, savedCons.getAddress()),
//                () -> assertEquals(company, savedCons.getCompany()),
//                () -> assertTrue(savedCons.getSubscriberConsSet().contains(subscribe)
//                        && savedCons.getSubscriberConsSet().contains(subscribe2)
//                && savedCons.getSubscriberConsSet().contains(subscribe3))
//        );
//    }
//
//    @Test
//    public void testFindById() throws SQLException {
//        AddressConsumerHome address = addressDao.findById(3L, ConnectionManager.get());
//        Company company = companyEntityDao.findById(3L, ConnectionManager.get());
//        Consumer consumer = consDao.findById(3L, ConnectionManager.get());
//        Subscribe subscribe = subjectDao.findById(6L, ConnectionManager.get());
//        Subscribe subscribe2 = subjectDao.findById(7L, ConnectionManager.get());
//        Subscribe subscribe3 = subjectDao.findById(8L, ConnectionManager.get());
//
//        Consumer consumer2 = new Consumer();
//        consumer2.setId(3);
//        consumer2.setName("Ruslan");
//        consumer2.setEmail("rusik@gmail.com");
//        consumer2.setRole(Role.ADMIN);
//        consumer2.setAddress(address);
//        consumer2.setCompany(company);
//        consumer2.setSubscriberConsSet(Set.of(subscribe, subscribe2, subscribe3));
//
//        assertThat(address).isEqualTo(new AddressConsumerHome(3L, "Moscow", "Vozdvizhenka, 4"));
//        assertThat(company).isEqualTo(new Company(3L, "Yandex"));
//        assertThat(subscribe).isEqualTo(new Subscribe(6L, "sub-1"));
//        assertThat(subscribe2).isEqualTo(new Subscribe(7L, "sub-2"));
//        assertThat(subscribe3).isEqualTo(new Subscribe(8L, "sub-3"));
//        assertThat(consumer).isEqualTo(consumer2);
//        assertThrows(RuntimeException.class, () -> {
//            consDao.findById(consumer.getId() + 100, ConnectionManager.get());
//        });
//    }
//
//    @Test
//    public void testUpdating() throws SQLException {
//        Consumer consumer = consDao.findById(3L, ConnectionManager.get());
//
//        consumer.setName("Mark");
//        consumer.setEmail("markus@gmail.com");
//
//        Consumer update = consDao.update(consumer, ConnectionManager.get());
//
//        assertAll(
//                () -> assertThat("Mark").isEqualTo(consumer.getName()),
//                () -> assertThat(consumer.getId()).isEqualTo(update.getId()),
//                () -> assertThat("markus@gmail.com").isEqualTo(update.getEmail()),
//                () -> assertThat(consumer.getCompany()).isEqualTo(update.getCompany()),
//                () -> assertThat(consumer.getAddress()).isEqualTo(update.getAddress()),
//                () -> assertThat(consumer.getSubscriberConsSet().size()).isEqualTo(update.getSubscriberConsSet().size())
//        );
//    }
//
////    @Test
////    public void testDeleting() throws DaoException {
////        Consumer consumer = new Consumer();
////        consumer.setName("Peter");
////        consumer.setEmail("peter@yandex.ru");
////        Consumer con = consDao.save(consumer);
////
////        consDao.deleteById(con.getId());
////
////        assertThrows(DaoException.class, () -> {
////            consDao.findConsumerById(con.getId());
////        });
////    }
//
////    @AfterAll
////    @Test
////    static void cleanTable() throws CustomException {
////        try (Connection connection = ConnectionManager.get();
////             PreparedStatement statement = connection.prepareStatement(CLEAN)) {
////            statement.executeUpdate();
////        } catch (SQLException e) {
////            throw new CustomException("Error while cleaning table");
////        }
////    }
//}
