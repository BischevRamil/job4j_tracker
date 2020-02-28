package ru.job4j.tracker;

/**
 * Класс реализующий патерн Одиночка(Singleton) 3. static final field. Eager loading.
 * @author Bischev Ramil
 * @version 1.0
 * @since 2019-08-17
 */

public class TrackerSingleThree {

    private static final TrackerSingleThree INSTANCE = new TrackerSingleThree();

    private TrackerSingleThree() {
    }

    public static TrackerSingleThree getInstance() {
        return INSTANCE;
    }

    ITracker tracker = new Tracker();

    public static void main(String[] args) {
        TrackerSingleThree tracker = TrackerSingleThree.getInstance();
    }
}