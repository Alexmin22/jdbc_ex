package org.example.dao;

import org.example.entity.Consumer;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.example.config.Comfigure.*;

@Repository
public class ConsDaoImpl implements ConsDao {


    @Override
    public List<Consumer> findAllConsumer() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql;
        sql = "SELECT id, email, name FROM Consumer";
        ResultSet rs = statement.executeQuery(sql);

        // Extract data from result set
        List<Consumer> consumers = new ArrayList<>();
        while (rs.next()) {
            //Retrieve by column name
            int id = rs.getInt("id");
            String email = rs.getString("email");
            String name = rs.getString("name");

            //Fill the object with data
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setEmail(email);
            consumer.setName(name);

            //Add the object to the list
            consumers.add(consumer);
        }
        // Clean-up environment
        closeResultSet(rs);
        closeStatement(statement);
        closeConnection(connection);
        return consumers;
    }

    @Override
    public void save(Consumer consumer) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql;
        sql = "INSERT INTO Consumer (email, name) VALUES ('" + consumer.getEmail() + "','" + consumer.getName() + "')";

        statement.executeUpdate(sql);
        closeStatement(statement);
        closeConnection(connection);
    }

    @Override
    public Consumer findConsumerById(int id) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT id, email, name FROM Consumer WHERE id = " + id;
        ResultSet rs = statement.executeQuery(sql);

        // Extract data from result set
        Consumer consumer = new Consumer();
        while (rs.next()) {
            String email = rs.getString("email");
            String name = rs.getString("name");

            consumer.setId(id);
            consumer.setEmail(email);
            consumer.setName(name);
        }

        closeResultSet(rs);
        closeStatement(statement);
        closeConnection(connection);
        return consumer;
    }

    @Override
    public void deleteById(int id) throws SQLException {

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql = "DELETE FROM Consumer WHERE id = " + id;
        statement.executeUpdate(sql);

        closeStatement(statement);
        closeConnection(connection);
    }

    @Override
    public void update(Consumer consumer) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql = "UPDATE Consumer SET email = '" + consumer.getEmail() +
                "', name = '" + consumer.getName() + "' WHERE id = " + consumer.getId();
        statement.executeUpdate(sql);
        closeStatement(statement);
        closeConnection(connection);
    }
}
