package org.safkan.graph.util;

public class HeapElement<T> {

    private T item;
    private double priority;

    public HeapElement(T item, double priority) {
        this.item = item;
        this.priority = priority;
    }

    public T getItem() {
        return this.item;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }
}