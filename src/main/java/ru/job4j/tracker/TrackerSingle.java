package ru.job4j.tracker;

/**
 * Класс реализующий патерн Одиночка(Singleton) 1. enum. Eager loading.
 * @author Bischev Ramil
 * @version 1.0
 * @since 2019-08-17
 */
public enum TrackerSingle {
    INSTANCE;

    ITracker tracker = new Tracker();

}
