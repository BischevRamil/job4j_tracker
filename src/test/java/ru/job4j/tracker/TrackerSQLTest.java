package sql;

import org.junit.Test;
import ru.job4j.tracker.Item;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TrackerSQLTest {
    private TrackerSQL trackersql = new TrackerSQL();

    @Test
    public void checkConnection() {
        assertThat(trackersql.init(), is(true));
    }

    @Test
    public void whenAddNewItemThenTableHasSameItem() {
        Item item = new Item("test1", "testDescription");
        item.setTime(123L);
        trackersql.add(item);
        Item result = trackersql.findById(item.getId());
        assertThat(result.getName(), is(item.getName()));
        trackersql.clearTable("items");

    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        Item previous = new Item("test1", "testDescription");
        previous.setTime(124L);
        trackersql.add(previous);
        Item next = new Item("test2", "testDescription2");
        previous.setTime(154L);
        next.setId(previous.getId());
        trackersql.replace(previous.getId(), next);
        assertThat(trackersql.findById(previous.getId()).getName(), is("test2"));
        trackersql.clearTable("items");
    }

    @Test
    public void whenDeleteIdThenReturnTrue() {
        Item first = new Item("test1", "Desc1");
        first.setTime(154L);
        trackersql.add(first);
        Item second = new Item("test2", "Desc2");
        second.setTime(147L);
        trackersql.add(second);
        Item third = new Item("test3", "Desc3");
        third.setTime(157L);
        trackersql.add(third);
        Boolean result = trackersql.delete(second.getId());
        assertThat(result, is(true));
        trackersql.clearTable("items");
    }

    @Test
    public void whenNotDeleteIdThenReturnFalse() {
        Item first = new Item("test1", "Desc1");
        first.setTime(154L);
        trackersql.add(first);
        Item second = new Item("test2", "Desc2");
        second.setTime(147L);
        trackersql.add(second);
        Item third = new Item("test3", "Desc3");
        third.setTime(157L);
        trackersql.add(third);
        Boolean result = trackersql.delete("string");
        assertThat(result, is(false));
        trackersql.clearTable("items");
    }

    @Test
    public void findAllTest() {
        Item first = new Item("test1", "Desc1");
        first.setTime(154L);
        trackersql.add(first);
        Item second = new Item("test2", "Desc2");
        second.setTime(147L);
        trackersql.add(second);
        Item third = new Item("test3", "Desc3");
        third.setTime(157L);
        trackersql.add(third);
        List<Item> items = Arrays.asList(first, second, third);
        List<Item> result = trackersql.findAll();
        assertThat(items, is(result));
        trackersql.clearTable("items");
    }

    @Test
    public void findByNameTest() {
        Item first = new Item("test1", "Desc1");
        first.setTime(154L);
        trackersql.add(first);
        Item second = new Item("test1", "Desc2");
        second.setTime(147L);
        trackersql.add(second);
        Item third = new Item("test3", "Desc3");
        third.setTime(157L);
        trackersql.add(third);
        List<Item> items = Arrays.asList(first, second);
        List<Item> result = trackersql.findByName("test1");
        assertThat(items, is(result));
        trackersql.clearTable("items");
    }
}
