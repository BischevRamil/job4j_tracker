package ru.job4j.tracker;


import java.util.*;
import java.lang.System;

public class Tracker implements ITracker {
    private final List<Item> items = new ArrayList<>();
    private static final Random RN = new Random();

    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        return item;
    }

    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt(100));
    }

    public boolean replace(String id, Item item) {
            for (int i = 0; i < items.size(); i++) {
                if (this.items.get(i).getId().equals(id)) {
                    item.setId(id);
                    this.items.set(i, item);
                    return true;
                }
            }
        return false;
    }

    public boolean delete(String id) {
        Iterator<Item> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            Item nextItem = itemIterator.next();
            if (nextItem.getId().equals(id)) {
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

    public Item findById(String id) {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
}
