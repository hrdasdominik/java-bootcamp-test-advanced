package main.java.com.javabootcamptestadvance.services;

import main.java.com.javabootcamptestadvance.interfaces.LibraryUtils;
import main.java.com.javabootcamptestadvance.models.*;
import main.java.com.javabootcamptestadvance.repository.RepositoryBase;
import main.java.com.javabootcamptestadvance.utils.LocalizationUtil;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class LibraryManagerService<T extends Item> {
    private final RepositoryBase<T> repository;

    public LibraryManagerService(RepositoryBase<T> repository) {
        this.repository = repository;
    }

    public void start() {
        if (!repository.checkIsConnected()) {
            repository.connect();
        }
    }

    public void stop() {
        if (repository.checkIsConnected()) {
            repository.closeConnection();
        }
    }

    public void printItems() {
        Comparator<T> sortByCriteria = Comparator.comparing(Item::getId);
        List<T> items = LibraryUtils.sortItems(repository.getAllItems(), sortByCriteria);
        items.forEach(Item::displayItemDetails);
    }

    public List<T> getAllItems() {
        return repository.getAllItems();
    }

    public void addItems(ItemContainer<T> itemContainer) {
        processItems(itemContainer, repository::addItem);
    }

    public void updateItems(ItemContainer<T> itemContainer) {
        processItems(itemContainer, repository::updateItem);
    }

    public void removeItems(ItemContainer<T> itemContainer) {
        processItems(itemContainer, repository::deleteItem);
    }

    private void processItems(ItemContainer<T> itemContainer, Consumer<T> itemProcessor) {
        List<Thread> threads = new ArrayList<>();

        for (T item : itemContainer.getItems().values()) {
            Thread thread = new Thread(() -> itemProcessor.accept(item));
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void searchItemsByFilter(Map<String, String> filterCriteria) {
        List<T> items = repository.getItemsByFilter(filterCriteria);
        if (items.isEmpty()) {
            System.out.println(LocalizationUtil.getErrorString("error.list.item.not_found") + ".filter "
                    + filterCriteria.keySet().stream().findFirst().orElse(null)
                    + ": " + filterCriteria.values().stream().findFirst().orElse(null));
            return;
        }
        Comparator<T> sortByCriteria = Comparator.comparing(Item::getTitle);
        items = LibraryUtils.sortItems(items, sortByCriteria);
        items.forEach(Item::displayItemDetails);
    }

    public T getItem(int itemId) {
        Map<String, String> filter = new HashMap<>();
        filter.put("id", Integer.toString(itemId));
        List<T> items = repository.getItemsByFilter(filter);
        return items.stream().findFirst().orElse(null);
    }

    public boolean checkIsEmpty() {
        List<T> items = repository.getAllItems();
        return items.isEmpty();
    }
}
