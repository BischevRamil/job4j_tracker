package ru.job4j.tracker;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TrackerSQLTest {

    public Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void createItem() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item item = new Item("name", "desc");
            item.setId(Tracker.generateId());
            item.setTime(147L);
            tracker.add(item);
            assertThat(tracker.findByName("name").size(), is(1));
        }
    }

    @Test
    public void whenAddNewItemThenTableHasSameItem() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item item = new Item("test1", "testDescription");
            item.setId(Tracker.generateId());
            item.setTime(123L);
            tracker.add(item);
            Item result = tracker.findById(item.getId());
            assertThat(result.getName(), is(item.getName()));
        }
    }

    @Test
    public void whenReplaceNameThenReturnNewName() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item previous = new Item("test1", "testDescription");
            previous.setId(Tracker.generateId());
            previous.setTime(124L);
            tracker.add(previous);
            Item next = new Item("test2", "testDescription2");
            next.setId(Tracker.generateId());
            previous.setTime(154L);
            next.setId(previous.getId());
            tracker.replace(previous.getId(), next);
            assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
        }
    }

    @Test
    public void whenDeleteIdThenReturnTrue() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item first = new Item("test1", "Desc1");
            first.setId(Tracker.generateId());
            first.setTime(154L);
            tracker.add(first);
            Item second = new Item("test2", "Desc2");
            second.setId(Tracker.generateId());
            second.setTime(147L);
            tracker.add(second);
            Item third = new Item("test3", "Desc3");
            third.setId(Tracker.generateId());
            third.setTime(157L);
            tracker.add(third);
            Boolean result = tracker.delete(second.getId());
            assertThat(result, is(true));
        }
    }

    @Test
    public void whenNotDeleteIdThenReturnFalse() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item first = new Item("test1", "Desc1");
            first.setId(Tracker.generateId());
            first.setTime(154L);
            tracker.add(first);
            Item second = new Item("test2", "Desc2");
            second.setId(Tracker.generateId());
            second.setTime(147L);
            tracker.add(second);
            Item third = new Item("test3", "Desc3");
            third.setId(Tracker.generateId());
            third.setTime(157L);
            tracker.add(third);
            Boolean result = tracker.delete("string");
            assertThat(result, is(false));
        }
    }

    @Test
    public void findAllTest() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item first = new Item("test1", "Desc1");
            first.setId(Tracker.generateId());
            first.setTime(154L);
            tracker.add(first);
            Item second = new Item("test2", "Desc2");
            second.setId(Tracker.generateId());
            second.setTime(147L);
            tracker.add(second);
            Item third = new Item("test3", "Desc3");
            third.setId(Tracker.generateId());
            third.setTime(157L);
            tracker.add(third);
            List<Item> items = Arrays.asList(first, second, third);
            List<Item> result = tracker.findAll();
            assertThat(items, is(result));
        }
    }

    @Test
    public void findByNameTest() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item first = new Item("test1", "Desc1");
            first.setId(Tracker.generateId());
            first.setTime(154L);
            tracker.add(first);
            Item second = new Item("test1", "Desc2");
            second.setId(Tracker.generateId());
            second.setTime(147L);
            tracker.add(second);
            Item third = new Item("test3", "Desc3");
            third.setId(Tracker.generateId());
            third.setTime(157L);
            tracker.add(third);
            List<Item> items = Arrays.asList(first, second);
            List<Item> result = tracker.findByName("test1");
            assertThat(items, is(result));
        }
    }
}
