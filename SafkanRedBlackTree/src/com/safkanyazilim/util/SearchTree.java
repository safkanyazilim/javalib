package com.safkanyazilim.util;

import java.util.Set;


public interface SearchTree<E extends Comparable<E>> extends Set<E> {
	public E minimum();
	public E maximum();
	
}
