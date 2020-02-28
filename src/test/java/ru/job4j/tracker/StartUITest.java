package ru.job4j.tracker;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class StartUITest {
    // поле содержит дефолтный вывод в консоль.
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final PrintStream stdout = new PrintStream(out);
    private final Consumer<String> output = new Consumer<String>() {
        @Override
        public void accept(String s) {
            stdout.println(s);
        }
    };
    private final String menu = new StringBuilder()
                                .append("0 : Добавить заявку\n")
                                .append("1 : Показать все заявки\n")
                                .append("2 : Редактирвать заявку\n")
                                .append("3 : Удалить заявку\n")
                                .append("4 : Найти по ID\n")
                                .append("5 : Найти по имени\n")
                                .toString();

    @Before
    public void loadOutput() {
        System.out.println("execute before method");
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
        System.out.println("execute after method");
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();     // создаём Tracker
        Input input = new StubInput(new String[]{"0", "test name", "desc", "y"});   //создаём StubInput с последовательностью действий
        new StartUI(input, tracker, output).init();     //   создаём StartUI и вызываем метод init()
        assertThat(tracker.findByName("test name"), is(tracker.findByName("test name"))); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        Tracker tracker = new Tracker();                // создаём Tracker
        Item item = tracker.add(new Item("test name", "desc"));             //Напрямую добавляем заявку
        Input input = new StubInput(new String[]{"2", item.getId(), "test replace", "заменили заявку", "y"});    //создаём StubInput с последовательностью действий(производим замену заявки)
        new StartUI(input, tracker, output).init();         // создаём StartUI и вызываем метод init()
        assertThat(tracker.findById(item.getId()).getName(), is("test replace"));        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
    }

    @Test
    public void whenDeleteThenTrackerNotHasValue() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("test name", "desc"));
        Input input = new StubInput(new String[]{"3", item.getId(), "y"});
        new StartUI(input, tracker, output).init();
        assertThat(tracker.delete(item.getId()), is(false));
    }

    @Test
    public void whenFindAllThenTrackerShowAll() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("test name1", "desc1"));
        Item item2 = tracker.add(new Item("test name2", "desc2"));
        Item item3 = tracker.add(new Item("test name3", "desc3"));
        Input input = new StubInput(new String[]{"1", "y"});
        new StartUI(input, tracker, output).init();
        assertThat(
                new String(out.toByteArray()),
                Is.is(
                        new StringBuilder()
                                .append(menu)
                                .append("-----------------Показать все заявки:---------------------\n")
                                .append(item1.getId() + " test name1 desc1\n")
                                .append(item2.getId() + " test name2 desc2\n")
                                .append(item3.getId() + " test name3 desc3\n")
                                .toString()
                )
        );
    }

    @Test
    public void whenFindByIDThenTrackerItem() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("test name1", "desc1"));
        Item item2 = tracker.add(new Item("test name2", "desc2"));
        Item item3 = tracker.add(new Item("test name3", "desc3"));
        Input input = new StubInput(new String[]{"4", item1.getId(), "y"});
        new StartUI(input, tracker, output).init();
        assertThat(
                new String(out.toByteArray()),
                Is.is(
                        new StringBuilder()
                                .append(menu)
                                .append("---------------------Поиск заявки по ID------------------------------------\n")
                                .append(item1.getId() + " test name1 desc1\n")
                                .toString()
                )
        );
    }

    @Test
    public void whenFindByNameThenTrackerItem() {
        Tracker tracker = new Tracker();
        Item item1 = tracker.add(new Item("test name1", "desc1"));
        Item item2 = tracker.add(new Item("test name3", "desc2"));
        Item item3 = tracker.add(new Item("test name3", "desc3"));
        Input input = new StubInput(new String[]{"5", "test name3", "y"});
        new StartUI(input, tracker, output).init();
        assertThat(
                new String(out.toByteArray()),
                Is.is(
                        new StringBuilder()
                                .append(menu)
                                .append("---------------------Поиск заявок по имени-----------------------------------\n")
                                .append(item2.getId() + " test name3 desc2\n")
                                .append(item3.getId() + " test name3 desc3\n")
                                .toString()
                )
        );
    }
}
