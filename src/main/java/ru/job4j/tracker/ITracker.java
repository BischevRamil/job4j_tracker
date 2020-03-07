package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

public interface ITracker {
    long add(Item item);
    boolean replace(long id, Item item);
    boolean delete(long id);
    List findAll();
    ArrayList findByName(String key);
    Item findById(long id);
}
