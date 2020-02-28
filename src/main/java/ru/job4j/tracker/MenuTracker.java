package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MenuTracker {
    /**
     * @param хранит ссылку на объект .
     */
    private Input input;
    /**
     * @param хранит ссылку на объект .
     */
    private ITracker tracker;
    /**
     * @param хранит ссылку на массив типа UserAction.
     */
    private List<UserAction> actions = new ArrayList<>();
    private final Consumer<String> output;

    /**
     * Конструктор.
     *
     * @param input   объект типа Input
     * @param tracker объект типа Tracker
     */
    public MenuTracker(Input input, ITracker tracker, Consumer<String> output) {
        this.input = input;
        this.tracker = tracker;
        this.output = output;
    }

    /**
     * Метод для получения массива меню.
     *
     * @return длину массива
     */
    public int getActionsLength() {
        return this.actions.size();
    }

    /**
     * Метод заполняет массив.
     */
    public void fillActions() {
        this.actions.add(new AddItem(0, "Добавить заявку"));
        this.actions.add(new FindAllItem(1, "Показать все заявки"));
        this.actions.add(new UpdateItem(2, "Редактирвать заявку"));
        this.actions.add(new DeleteItem(3, "Удалить заявку"));
        this.actions.add(new FindByIdItem(4, "Найти по ID"));
        this.actions.add(new FindByNameItem(5, "Найти по имени"));
    }

    /**
     * Метод в зависимости от указанного ключа, выполняет соотвествующие действие.
     *
     * @param key ключ операции
     */
    public void select(int key) {
            this.actions.get(key).execute(this.input, this.tracker);
    }

    /**
     * Метод выводит на экран меню.
     */
    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    public class AddItem extends BaseAction {

        public AddItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            output.accept("------------ Добавление новой заявки --------------");
            String name = input.ask("Введите имя заявки :");
            String desc = input.ask("Введите описание заявки :");
            Item item = new Item(name, desc);
            tracker.add(item);
            output.accept("------------ Новая заявка с ID : " + item.getId());
            output.accept("------------ Новая заявка с именем : " + item.getName());
            output.accept("------------ Новая заявка с описанием : " + item.getDesc());
        }
    }

    public class FindAllItem extends BaseAction {

        public FindAllItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            output.accept("-----------------Показать все заявки:---------------------");
            List<Item> items = tracker.findAll();
            for (Item item:items) {
                output.accept(item.getId() + " " + item.getName() + " " + item.getDesc());
            }
        }
    }

    public class UpdateItem extends BaseAction {
        public UpdateItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            output.accept("-------------------Редактирование заявки------------------------------");
            String id = input.ask("Введите ID заявки :");
            String name = input.ask("Введите новое имя заявки :");
            String desc = input.ask("Введите новое описание заявки :");
            Item item = new Item(name, desc);
            boolean rsl = tracker.replace(id, item);
            if (rsl) {
                output.accept("------------ Новая заявка с ID : " + id + "-----------");
            } else {
                output.accept("------------ Заявка с таким ID не найдена--------------");
            }
        }
    }

    public class DeleteItem extends BaseAction {
        public DeleteItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            output.accept("---------------------Удаление заявки------------------------------------");
            String id = input.ask("Введите ID заявки которую нужно удалить :");
            boolean rsl = tracker.delete(id);
            if (rsl) {
                output.accept("-----------------Заявка с ID :" + id + "удалена--------------------");
            } else {
                output.accept("------------ Заявка с таким ID не найдена--------------");
            }
        }
    }

    public class FindByIdItem extends BaseAction {
        public FindByIdItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            output.accept("---------------------Поиск заявки по ID------------------------------------");
            String id = input.ask("Введите ID заявки, которую нужно найти");
            Item item = tracker.findById(id);
            if (item != null) {
                output.accept(item.getId() + " " + item.getName() + " " + item.getDesc());
            } else {
                output.accept("------------ Заявка с таким ID не найдена--------------");
            }
        }
    }

    public class FindByNameItem extends BaseAction {
        public FindByNameItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            output.accept("---------------------Поиск заявок по имени-----------------------------------");
            String name = input.ask("Введите имя заявки: ");
            ArrayList<Item> items = tracker.findByName(name);
            if (items != null) {
                for (Item item : items) {
                    output.accept(item.getId() + " " + item.getName() + " " + item.getDesc());
                }
            } else {
                output.accept("------------ Заявок с таким именем не найдено--------------");
            }
        }
    }
}
