package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

public interface ITracker {
    Item add(Item item);
    boolean replace(String id, Item item);
    boolean delete(String id);
    List findAll();
    ArrayList findByName(String key);
    Item findById(String id);
}
