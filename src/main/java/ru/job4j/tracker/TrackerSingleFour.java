package ru.job4j.tracker;

/**
 * Класс реализующий патерн Одиночка(Singleton) 4. private static final class. Lazy loading.
 * @author Bischev Ramil
 * @version 1.0
 * @since 2019-08-17
 */

public class TrackerSingleFour {
    private TrackerSingleFour() {
    }

    public static TrackerSingleFour getInstance() {
        return Holder.INSTANCE;
    }

    ITracker tracker = new Tracker();

    private static final class Holder {
        private static final TrackerSingleFour INSTANCE = new TrackerSingleFour();
    }

    public static void main(String[] args) {
        TrackerSingleFour tracker = TrackerSingleFour.getInstance();
    }
}
