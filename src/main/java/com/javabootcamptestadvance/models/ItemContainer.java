package main.java.com.javabootcamptestadvance.models;

import main.java.com.javabootcamptestadvance.models.Item;

import java.util.HashMap;
import java.util.Map;

public class ItemContainer<T extends Item> {
    private final Map<Integer, T> items;

    public ItemContainer() {
        items = new HashMap<>();
    }

    public Map<Integer, T> getItems() {
        return items;
    }
}
