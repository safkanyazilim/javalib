package org.safkan.graph.util;

public class PriorityQueue<T> {

    private MinHeap<T> minHeap;

    public PriorityQueue() {
        this.minHeap = new MinHeap<T>();
    }

    public boolean isEmpty() {
        return minHeap.isEmpty();
    }

    public void insertObjectWithPriority(T item, double priority) {
        minHeap.minHeapInsertObjectWithPriority(item, priority);
    }

    public T popLowestPriority() {
    	if (minHeap.isEmpty()) {
    		return null; 
    	} else {
    		return minHeap.heapExtractMin().getItem();
    	}
    }
}