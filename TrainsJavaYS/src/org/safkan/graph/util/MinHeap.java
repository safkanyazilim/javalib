package org.safkan.graph.util;

import java.util.ArrayList;
import java.util.List;

public class MinHeap<T> {

    private List<HeapElement<T>> heap;

    public MinHeap() {
        this.heap = new ArrayList<HeapElement<T>>();
    }

    private int parent(int i) {
        return i >> 1;
    }

    private int left(int i) {
        return i << 1;
    }

    private int right(int i) {
        return (i << 1) | 1;
    }

    private HeapElement<T> element(int i) {
        return heap.get(i-1);
    }

    private double a(int i) {
        return element(i).getPriority();
    }

    private void exchange(int i, int j) {
        HeapElement<T> swap;
        swap = heap.get(i-1);
        heap.set(i - 1, heap.get(j - 1));
        heap.set(j-1,swap);
    }

    public void minHeapify(int i) {

        int l = left(i);
        int r = right(i);
        int smallest;

        if (l <= heap.size() && a(l) < a(i)) {
            smallest = l;
        } else {
            smallest = i;
        }

        if (r <= heap.size() && a(r) < a(smallest)) {
            smallest = r;
        }

        if (smallest != i) {
            exchange(i, smallest);
            minHeapify(smallest);
        }
    }

    private HeapElement<T> item(int i) {
        return element(i);
    }

    public boolean isEmpty() {
        return heap.size() < 1;
    }

    public HeapElement<T> heapExtractMin() {
        if (heap.isEmpty()) {
            return null;
        }

        HeapElement<T> min = item(1);
        heap.set(0,element(heap.size()));
        heap.remove(heap.size()-1);
        minHeapify(1);

        return min;
    }

    public void decreasePriorityToValue(int i, double priority) {
        if (priority > a(i)) {
            return;
        }

        element(i).setPriority(priority);

        while (i > 1 && a(parent(i)) > a(i) ) {
            exchange(i, parent(i));
            i = parent(i);
        }
    }

    public void minHeapInsertObjectWithPriority(T item, double priority) {
        HeapElement<T> element = new HeapElement<T>(item, priority);

        heap.add(element);
        decreasePriorityToValue(heap.size(), priority);

    }

}