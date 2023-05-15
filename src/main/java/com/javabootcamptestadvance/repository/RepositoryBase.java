package main.java.com.javabootcamptestadvance.repository;

import main.java.com.javabootcamptestadvance.models.Item;

import java.util.List;
import java.util.Map;

public interface RepositoryBase<T extends Item> {
    void connect();
    void closeConnection();
    boolean checkIsConnected();
    void addItem(T item);
    void updateItem(T item);
    void deleteItem(T item);
    List<T> getAllItems();
    List<T> getItemsByFilter(Map<String, String> filters);
}

