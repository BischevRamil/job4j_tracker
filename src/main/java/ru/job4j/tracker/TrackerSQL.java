package ru.job4j.tracker;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

/**
 * @author Bischev Ramil
 * @since 2020-02-23
 * Имплементация интерфейса Tracker для хранения заявок в базе данных
 */
public class TrackerSQL implements ITracker, AutoCloseable {
    private Connection connection;
    private static final Random RN = new Random();

    public TrackerSQL() {
        this.init();
    }

    public TrackerSQL(Connection connection) {
        this.connection = connection;
    }

    public boolean init() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
            Statement st = connection.createStatement();
            st.execute(String.format("create table if not exists items (id serial primary key, name varchar(100) not null, description varchar(100) not null, time bigint not null);"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return this.connection != null;
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }

    /**
     * Добавление заявки в таблицу.
     * @param item заявка
     * @return заявка
     */
    @Override
    public long add(Item item) {
        long id = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLItems.INSERT.query)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDesc());
            statement.setLong(3, item.getTime());
            ResultSet rs = statement.executeQuery();
            rs.next();
            id = rs.getLong("id");
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return id;
    }

    /**
     * Замена заявки по id
     * @param id id
     * @param item заявка
     * @return true если есть такой id.
     */
    @Override
    public boolean replace(long id, Item item) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLItems.UPDATE.query)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDesc());
            statement.setLong(3, id);
            result = statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Удаление заявки из таблицы по ID
     * @param id ID
     * @return true если есть такой id.
     */
    @Override
    public boolean delete(long id) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLItems.DELETE.query)) {
            statement.setLong(1, id);
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Выдернуть все записи таблицы
     * @return записи
     */
    @Override
    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLItems.GETALL.query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getString("name"), rs.getString("description"));
                item.setId(rs.getLong("id"));
                item.setTime(rs.getLong("time"));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Найти заявки по имени
     * @param key имя
     * @return лист заявок с искомым именем
     */
    @Override
    public ArrayList<Item> findByName(String key) {
        ArrayList<Item> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLItems.GETFROMNAME.query)) {
            statement.setString(1, key);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getString("name"), rs.getString("description"));
                item.setId(rs.getLong("id"));
                item.setTime(rs.getLong("time"));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Item findById(long id) {
        Item item = null;
        try (PreparedStatement statement = connection.prepareStatement(SQLItems.GETFROMID.query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                item = new Item(rs.getString("name"), rs.getString("description"));
                item.setId(rs.getLong("id"));
                item.setTime(rs.getLong("time"));
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * SQL queries for items table.
     */
    enum SQLItems {
        GETALL("SELECT * FROM items;"),
        GETFROMNAME("SELECT * FROM items WHERE name=?;"),
        GETFROMID("SELECT * FROM items WHERE id=?;"),
        INSERT("INSERT INTO items (name, description, time) VALUES (?, ?, ?) RETURNING id;"),
        DELETE("DELETE FROM items WHERE id = ? RETURNING id;"),
        UPDATE("UPDATE items SET name = ?, description = ? WHERE id = ?;");

        String query;

        SQLItems(String query) {
            this.query = query;
        }
    }
}
