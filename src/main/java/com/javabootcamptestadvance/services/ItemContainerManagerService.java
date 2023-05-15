package main.java.com.javabootcamptestadvance.services;

import main.java.com.javabootcamptestadvance.models.Item;
import main.java.com.javabootcamptestadvance.models.ItemContainer;
import main.java.com.javabootcamptestadvance.utils.LocalizationUtil;

public class ItemContainerManagerService<T extends Item> {
    private final ItemContainer<T> itemContainer;

    public ItemContainerManagerService(ItemContainer<T> itemContainer) {
        this.itemContainer = itemContainer;
    }

    public ItemContainer<T> getItemContainer() {
        return itemContainer;
    }

    public void printItems() {
        if (checkIsItemContainerEmpty()) return;
        itemContainer.getItems().values().forEach(Item::displayItemDetails);
    }

    public void printItemsOnlyIdTitleAuthor() {
        if (checkIsItemContainerEmpty()) return;
        itemContainer.getItems().values().forEach(item -> System.out.println(item.toString()));
    }

    public T getItem(int itemId) {
        return itemContainer.getItems().get(itemId);
    }

    public void addItem(T item) {
        itemContainer.getItems().put(item.getId(), item);
    }

    public void removeItem(T item) {
        itemContainer.getItems().remove(item.getId());
    }

    public void updateItem(T item) {
        itemContainer.getItems().replace(item.getId(), item);
    }

    public void clearContainer() {
        itemContainer.getItems().clear();
    }

    public boolean checkIsEmpty() {
        return itemContainer.getItems().isEmpty();
    }

    public boolean checkIsItemNotNull(Item item) {
        return item != null;
    }

    private boolean checkIsItemContainerEmpty() {
        if (checkIsEmpty()) {
            System.out.println(LocalizationUtil.getErrorString("error.list.empty"));
            return true;
        }
        return false;
    }
}
