package ru.job4j.tracker;


import java.util.*;
import java.lang.System;

public class Tracker implements ITracker {
    private final List<Item> items = new ArrayList<>();
    private static final Random RN = new Random();

    public long add(Item item) {
        item.setId(generateId());
        this.items.add(item);
        return item.getId();
    }

    private static long generateId() {
        return System.currentTimeMillis() + RN.nextInt(100);
    }

    public boolean replace(long id, Item item) {
            for (int i = 0; i < items.size(); i++) {
                if (this.items.get(i).getId() == (id)) {
                    item.setId(id);
                    this.items.set(i, item);
                    return true;
                }
            }
        return false;
    }

    public boolean delete(long id) {
        Iterator<Item> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            Item nextItem = itemIterator.next();
            if (nextItem.getId() == id) {
                itemIterator.remove();
                return true;
            }
        }
        return false;
    }

    public List<Item> findAll() {
        return items;
    }

    public ArrayList<Item> findByName(String key) {
        ArrayList<Item> tmp = new ArrayList<>();
        for (Item item:items) {
            if (item.getName().equals(key)) {
                tmp.add(item);
            }
        }
        return tmp;
    }

    public Item findById(long id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
