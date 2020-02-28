package ru.job4j.tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TrackerTest {
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        long created = System.currentTimeMillis();
        Item item = new Item("test1", "testDescription");
        tracker.add(item);
        Item result = tracker.findById(item.getId());
        assertThat(result.getName(), is(item.getName()));
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription");
        // Добавляем заявку в трекер. Теперь в объект проинициализирован id.
        tracker.add(previous);
        // Создаем новую заявку.
        Item next = new Item("test2", "testDescription2");
        // Проставляем старый id из previous, который был сгенерирован выше.
        next.setId(previous.getId());
        // Обновляем заявку в трекере.
        tracker.replace(previous.getId(), next);
        // Проверяем, что заявка с таким id имеет новые имя test2.
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }

    @Test
    public void whenDeleteIdThenReturnTrue() {
        Tracker tracker = new Tracker();
        Item first = new Item("test1", "Desc1");
        tracker.add(first);
        Item second = new Item("test2", "Desc2");
        tracker.add(second);
        Item third = new Item("test3", "Desc3");
        tracker.add(third);
        Boolean result = tracker.delete(second.getId());
        assertThat(result, is(true));
    }

    @Test
    public void whenNotDeleteIdThenReturnFalse() {
        Tracker tracker = new Tracker();
        Item first = new Item("test1", "Desc1");
        tracker.add(first);
        Item second = new Item("test2", "Desc2");
        tracker.add(second);
        Item third = new Item("test3", "Desc3");
        tracker.add(third);
        Boolean result = tracker.delete("string");
        assertThat(result, is(false));
    }

    @Test
    public void findAllTest() {
        Tracker tracker = new Tracker();
        Item first = new Item("test1", "Desc1");
        tracker.add(first);
        Item second = new Item("test2", "Desc2");
        tracker.add(second);
        Item third = new Item("test3", "Desc3");
        tracker.add(third);
        List<Item> items = Arrays.asList(first, second, third);
        List<Item> result = tracker.findAll();
        assertThat(items, is(result));
    }

    @Test
    public void findByNameTest() {
        Tracker tracker = new Tracker();
        Item first = new Item("test1", "Desc1");
        tracker.add(first);
        Item second = new Item("test1", "Desc2");
        tracker.add(second);
        Item third = new Item("test3", "Desc3");
        tracker.add(third);
        List<Item> items = Arrays.asList(first, second);
        List<Item> result = tracker.findByName("test1");
        assertThat(items, is(result));
    }
}
