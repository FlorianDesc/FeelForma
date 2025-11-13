package fr.ubordeaux.m1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple counter model with observer support.
 */
public class Counter {
    private int value;
    private final List<CounterListener> listeners = new ArrayList<>();

    public Counter(int initial) { this.value = initial; }

    public int getValue() { return value; }

    public void increment() {
        value++;
        notifyListeners();
    }

    public void addListener(CounterListener l) { listeners.add(l); }
    public void removeListener(CounterListener l) { listeners.remove(l); }

    private void notifyListeners() {
        for (CounterListener l : listeners) l.counterUpdated(this);
    }
}
