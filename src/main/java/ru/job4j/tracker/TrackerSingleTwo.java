package ru.job4j.tracker;

/**
 * Класс реализующий патерн Одиночка(Singleton) 2. static field. Lazy loading.
 * @author Bischev Ramil
 * @version 1.0
 * @since 2019-08-17
 */

public class TrackerSingleTwo {
    private static TrackerSingleTwo  instance;

    private TrackerSingleTwo() {

    }

    public static TrackerSingleTwo getInstance() {
        if (instance == null) {
            instance = new TrackerSingleTwo();
        }
        return instance;
    }

    ITracker tracker = new Tracker();
}
