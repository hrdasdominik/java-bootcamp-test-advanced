package main.java.com.javabootcamptestadvance.services;

import main.java.com.javabootcamptestadvance.interfaces.LibraryUtils;
import main.java.com.javabootcamptestadvance.models.*;
import main.java.com.javabootcamptestadvance.utils.IsbnGenerator;
import main.java.com.javabootcamptestadvance.utils.LocalizationUtil;

import java.util.*;
import java.util.function.Consumer;

public class ApplicationManagerService {
    private boolean isRunning;
    private final Scanner scanner;
    private final ItemContainerManagerService<Item> itemContainerManagerService;
    private final ItemContainerManagerService<Item> deletionContainerManagerService;
    private final ItemContainerManagerService<Item> updateContainerManagerService;
    private final LibraryManagerService<Item> itemLibraryManagerService;

    public ApplicationManagerService(Scanner scanner,
                                     ItemContainerManagerService<Item> itemContainerManagerService,
                                     ItemContainerManagerService<Item> deletionContainerManagerService,
                                     ItemContainerManagerService<Item> updateContainerManagerService,
                                     LibraryManagerService<Item> itemLibraryManagerService) {
        this.isRunning = true;
        this.scanner = scanner;
        this.itemContainerManagerService = itemContainerManagerService;
        this.deletionContainerManagerService = deletionContainerManagerService;
        this.updateContainerManagerService = updateContainerManagerService;
        this.itemLibraryManagerService = itemLibraryManagerService;
    }

    public void run() {
        Locale.setDefault(Locale.forLanguageTag("en"));
        LocalizationUtil.loadResources(Locale.getDefault());

        itemLibraryManagerService.start();
        while (isRunning) {
            printMainMenu();
            isRunning = choiceLogic();
        }
    }

    private void printMainMenu() {
        LocalizationUtil.printMenuBundle();
    }

    private boolean choiceLogic() {
        switch(readUserInputString()) {
            case "1", "1." -> {
                printAllItemsWithDetailsInList();
                return true;
            }
            case "2", "2." -> {
                addItemToList();
                return true;
            }
            case "3", "3." -> {
                searchForItemInList();
                return true;
            }
            case "4", "4." -> {
                updateItemInList();
                return true;
            }
            case "5", "5." -> {
                deleteItemFromList();
                return true;
            }
            case "6", "6." -> {
                uploadItemsFromListToLibraryDatabase();
                return true;
            }
            case "7", "7." -> {
                printAllItemsInLibraryDatabase();
                return true;
            }
            case "8", "8." -> {
                searchLibraryDatabaseForItems();
                return true;
            }
            case "9", "9." -> {
                removeItemFromTheLibraryDatabase();
                return true;
            }
            case "10", "10." -> {
                updateItemInTheLibraryDatabase();
                return true;
            }
            case "11", "11." -> {
                getOldestItemFromTheLibraryDatabase();
                return true;
            }
            case "12", "12." -> {
                getAveragePublicationYearInTheLibraryDatabase();
                return true;
            }
            case "13", "13." -> {
                chooseLanguage();
                return true;
            }
            case "14", "14." -> {
                return exit();
            }
            default -> {
                System.out.println(LocalizationUtil.getErrorString("error.input.invalid"));
                return true;
            }
        }
    }

    private void printAllItemsInList() {
        itemContainerManagerService.printItemsOnlyIdTitleAuthor();
    }

    private void printAllItemsWithDetailsInList() {
        itemContainerManagerService.printItems();
    }

    private void addItemToList() {
        printMessage(LocalizationUtil.getLabelString("label.title"));
        String title = readUserInputString();
        printMessage(LocalizationUtil.getLabelString("label.author"));
        String author = readUserInputString();
        printMessage(LocalizationUtil.getLabelString("label.publication_year"));
        int publicationYear = readUserInputInteger();
        printMessage(LocalizationUtil.getLabelString("label.item_type"));
        Item.Type type = readUserInputItemTypeString();
        Random random = new Random();
        int id = random.nextInt(Integer.MAX_VALUE);
        if (Item.Type.BOOK.equals(type)) {
            printMessage(LocalizationUtil.getLabelString("label.genre"));
            String genre = readUserInputString();
            printMessage(LocalizationUtil.getLabelString("label.pages"));
            int pages = readUserInputInteger();
            String isbn = IsbnGenerator.generateIsbn();
            Book book = new Book(id, title, author, publicationYear, type, genre, pages, isbn);
            itemContainerManagerService.addItem(book);
        } else if (Item.Type.MAGAZINE.equals(type)) {
            printMessage(LocalizationUtil.getLabelString("label.issue_number"));
            int issueNumber = readUserInputInteger();
            printMessage(LocalizationUtil.getLabelString("label.publisher"));
            String publisher = readUserInputString();
            Magazine magazine = new Magazine(id, title, author, publicationYear, type, issueNumber, publisher);
            itemContainerManagerService.addItem(magazine);
        } else if (Item.Type.DVD.equals(type)) {
            printMessage(LocalizationUtil.getLabelString("label.duration_minutes"));
            int durationMinutes = readUserInputInteger();
            printMessage(LocalizationUtil.getLabelString("label.director"));
            String director = readUserInputString();
            printMessage(LocalizationUtil.getLabelString("label.rating"));
            Dvd.Rating rating = readUserInputRatingString();
            Dvd dvd = new Dvd(id, title, author, publicationYear, type, durationMinutes, director, rating);
            itemContainerManagerService.addItem(dvd);
        } else {
            System.out.println(LocalizationUtil.getErrorString("error.input.invalid"));
        }
        System.out.println(LocalizationUtil.getInfoString("info.list.success.added"));
        itemContainerManagerService.clearContainer();
    }

    private Dvd.Rating readUserInputRatingString() {
        while (true) {
            Dvd.Rating rating = Dvd.Rating.fromValue(readUserInputString());
            if (rating != null) {
                return rating;
            } else {
                System.out.println(LocalizationUtil.getErrorString("error.input.invalid"));
                System.out.println(LocalizationUtil.getPromptString("prompt.valid_values"));
                Dvd.Rating.printValues();
            }
        }
    }

    private Item.Type readUserInputItemTypeString() {
        while (true) {
            Item.Type type = Item.Type.fromValue(readUserInputString());
            if (type != null) {
                return type;
            } else {
                System.out.println(LocalizationUtil.getErrorString("error.input.invalid"));
                System.out.println(LocalizationUtil.getPromptString("prompt.valid_values"));
                Item.Type.printValues();
            }
        }
    }

    private void searchForItemInList() {
        if (itemContainerManagerService.checkIsEmpty()) {
            System.out.println(LocalizationUtil.getErrorString("error.list.empty"));
            return;
        }
        printMessage(LocalizationUtil.getPromptString("prompt.enter_id"));
        Item item = itemContainerManagerService.getItem(readUserInputInteger());
        if (item == null) {
            System.out.println(LocalizationUtil.getErrorString("error.list.item.not_found"));
        } else if (Item.Type.BOOK.equals(item.getItemType())) {
            Book book = (Book) item;
            book.displayItemDetails();
        } else if (Item.Type.MAGAZINE.equals(item.getItemType())) {
            Magazine magazine = (Magazine) item;
            magazine.displayItemDetails();
        } else {
            Dvd dvd = (Dvd) item;
            dvd.displayItemDetails();
        }
    }

    private void updateItemInList() {
        if (itemContainerManagerService.checkIsEmpty()) {
            System.out.println(LocalizationUtil.getErrorString("error.list.empty"));
            return;
        }
        itemContainerManagerService.printItemsOnlyIdTitleAuthor();
        printMessage(LocalizationUtil.getPromptString("prompt.enter_id"));
        Item item = itemContainerManagerService.getItem(readUserInputInteger());

        if (item == null) {
            System.out.println(LocalizationUtil.getErrorString("error.list.item.not_found"));
            return;
        }

        switch (item.getItemType()) {
            case BOOK -> updateBook((Book) item, itemContainerManagerService);
            case MAGAZINE -> updateMagazine((Magazine) item, itemContainerManagerService);
            case DVD -> updateDvd((Dvd) item, itemContainerManagerService);
            default -> System.out.println(LocalizationUtil.getErrorString("error.item.invalid_type"));
        }
    }

    public void updateBook(Book book, ItemContainerManagerService itemContainerManagerService) {
        book.displayItemDetails();

        Map<String, Consumer<Book>> bookUpdates = new HashMap<>();

        bookUpdates.put("title", bookItem -> book.setTitle(readUserInputString(LocalizationUtil.getLabelString("label.title"))));
        bookUpdates.put("author", bookItem -> book.setAuthor(readUserInputString(LocalizationUtil.getLabelString("label.author"))));
        bookUpdates.put("publication year", bookItem -> book.setPublicationYear(readUserInputInteger(LocalizationUtil.getLabelString("label.publication_year"))));
        bookUpdates.put("genre", bookItem -> book.setGenre(readUserInputString(LocalizationUtil.getLabelString("label.genre"))));
        bookUpdates.put("pages", bookItem -> book.setPages(readUserInputInteger(LocalizationUtil.getLabelString("label.pages"))));

        updateItemAttributes(book, bookUpdates);
        itemContainerManagerService.updateItem(book);
    }

    public void updateMagazine(Magazine magazine, ItemContainerManagerService itemContainerManagerService) {
        magazine.displayItemDetails();

        Map<String, Consumer<Magazine>> magazineUpdates = new HashMap<>();
        magazineUpdates.put("title", magItem -> magazine.setTitle(readUserInputString(LocalizationUtil.getLabelString("label.title"))));
        magazineUpdates.put("author", magItem -> magazine.setAuthor(readUserInputString(LocalizationUtil.getLabelString("label.author"))));
        magazineUpdates.put("publication year", magItem -> magazine.setPublicationYear(readUserInputInteger(LocalizationUtil.getLabelString("label.publication_year"))));
        magazineUpdates.put("issue number", magItem -> magazine.setIssueNumber(readUserInputInteger(LocalizationUtil.getLabelString("label.issue_number"))));
        magazineUpdates.put("publisher", magItem -> magazine.setPublisher(readUserInputString(LocalizationUtil.getLabelString("label.publisher"))));

        updateItemAttributes(magazine, magazineUpdates);
        itemContainerManagerService.updateItem(magazine);
    }

    public void updateDvd(Dvd dvd, ItemContainerManagerService itemContainerManagerService) {
        dvd.displayItemDetails();

        Map<String, Consumer<Dvd>> dvdUpdates = new HashMap<>();
        dvdUpdates.put("title", dvdItem -> dvd.setTitle(readUserInputString(LocalizationUtil.getLabelString("label.title"))));
        dvdUpdates.put("author", dvdItem -> dvd.setAuthor(readUserInputString(LocalizationUtil.getLabelString("label.author"))));
        dvdUpdates.put("publication year", dvdItem -> dvd.setPublicationYear(readUserInputInteger(LocalizationUtil.getLabelString("label.publication_year"))));
        dvdUpdates.put("duration minutes", dvdItem -> dvd.setDurationMinutes(readUserInputInteger(LocalizationUtil.getLabelString("label.duration_minutes"))));
        dvdUpdates.put("director", dvdItem -> dvd.setDirector(readUserInputString(LocalizationUtil.getLabelString("label.director"))));
        dvdUpdates.put("rating", this::updateDvdRating);

        updateItemAttributes(dvd, dvdUpdates);
        itemContainerManagerService.updateItem(dvd);
    }

    private void updateDvdRating(Dvd dvd) {
        boolean isProperRating = false;
        while (!isProperRating) {
            System.out.println(LocalizationUtil.getLabelString("label.rating"));
            Dvd.Rating rating = Dvd.Rating.fromValue(readUserInputString());
            if (rating != null) {
                dvd.setRating(rating);
                isProperRating = true;
            } else {
                System.out.println(LocalizationUtil.getErrorString("error.input.invalid"));
                System.out.println(LocalizationUtil.getPromptString("prompt.valid_values"));
                Dvd.Rating.printValues();
            }
        }
    }

    private <T> void updateItemAttributes(T item, Map<String, Consumer<T>> updates) {
        boolean wantMoreUpdate;
        do {
            System.out.println(LocalizationUtil.getPromptString("prompt.update_attribute"));
            updates.forEach((key, value) -> System.out.println(key));
            Consumer<T> updateAction = updates.get(readUserInputString());
            if (updateAction != null) {
                updateAction.accept(item);
            } else {
                System.out.println(LocalizationUtil.getErrorString("error.input.invalid"));
            }
            wantMoreUpdate = confirmMoreUpdates(LocalizationUtil.getPromptString("prompt.update_another_attribute"));
        } while (wantMoreUpdate);
    }

    private boolean confirmMoreUpdates(String message) {
        do {
            printMessage(message);
            String input = readUserInputString();
            switch (input) {
                case "n", "no", "ne" -> {
                    return false;
                }
                case "y", "yes", "d", "da" -> {
                    return true;
                }
                default -> System.out.println(LocalizationUtil.getErrorString("error.input.invalid"));
            }
        } while (true);
    }

    private void deleteItemFromList() {
        printAllItemsInList();
        printMessage(LocalizationUtil.getPromptString("prompt.enter_id"));
        Item item = itemContainerManagerService.getItem(readUserInputInteger());
        if (itemContainerManagerService.checkIsItemNotNull(item)) {
            itemContainerManagerService.removeItem(item);
        } else {
            System.out.println(LocalizationUtil.getErrorString("error.list.item.not_found"));
        }
    }

    private void uploadItemsFromListToLibraryDatabase() {
        if (itemContainerManagerService.getItemContainer().getItems().isEmpty()) {
            System.out.println(LocalizationUtil.getErrorString("error.list.empty"));
            return;
        }
        itemLibraryManagerService.addItems(itemContainerManagerService.getItemContainer());
    }

    private void printAllItemsInLibraryDatabase() {
        itemLibraryManagerService.printItems();
    }

    private void searchLibraryDatabaseForItems() {
        LocalizationUtil.printSearchBundle();

        switch (readUserInputString()) {
            case "1", "1." -> {
                printMessage(LocalizationUtil.getLabelString("label.title"));
                String title = readUserInputString();
                Map<String, String> filters = new HashMap<>();
                filters.put("title", title);
                itemLibraryManagerService.searchItemsByFilter(filters);
            }
            case "2", "2." -> {
                printMessage(LocalizationUtil.getLabelString("label.author"));
                String author = readUserInputString();
                Map<String, String> filters = new HashMap<>();
                filters.put("author", author);
                itemLibraryManagerService.searchItemsByFilter(filters);
            }
            case "3", "3." -> {
                printMessage(LocalizationUtil.getLabelString("label.publication_year"));
                String publicationYear = readUserInputString();
                Map<String, String> filters = new HashMap<>();
                filters.put("publication_year", publicationYear);
                itemLibraryManagerService.searchItemsByFilter(filters);
            }
            case "4", "4." -> {
                printMessage(LocalizationUtil.getLabelString("label.item_type"));
                String itemType = Objects.requireNonNull(Item.Type.fromValue(readUserInputString())).getValue();
                Map<String, String> filters = new HashMap<>();
                filters.put("item_type", itemType);
                itemLibraryManagerService.searchItemsByFilter(filters);
            }
            default -> System.out.println(LocalizationUtil.getErrorString("error.input.invalid"));
        }
    }

    private void removeItemFromTheLibraryDatabase() {
        if (itemLibraryManagerService.checkIsEmpty()) {
            System.out.println(LocalizationUtil.getErrorString("error.database.empty"));
            return;
        }

        boolean wantMoreDeletion;
        do {
            printMessage(LocalizationUtil.getPromptString("prompt.enter_id"));
            int itemId = readUserInputInteger();

            Item itemInDeleteContainer = deletionContainerManagerService.getItem(itemId);
            if (!deletionContainerManagerService.checkIsItemNotNull(itemInDeleteContainer)) {
                handleItemForDeletion(itemId);
            } else {
                System.out.println(LocalizationUtil.getErrorString("error.list.item.already_in"));
            }
            wantMoreDeletion = confirmMoreDeletions();
        } while (wantMoreDeletion);

        itemLibraryManagerService.removeItems(deletionContainerManagerService.getItemContainer());
        deletionContainerManagerService.clearContainer();
    }

    private void handleItemForDeletion(int itemId) {
        Item item = itemLibraryManagerService.getItem(itemId);
        if (item != null) {
            deletionContainerManagerService.addItem(item);
        } else {
            System.out.println(LocalizationUtil.getErrorString("error.list.item.not_found"));
        }
        scanner.nextLine(); // For removing trailing \n whitespace on the next scanner read
    }

    private boolean confirmMoreDeletions() {
        while (true) {
            printMessage(LocalizationUtil.getPromptString("prompt.add_another_item_for_delete"));
            switch (readUserInputString()) {
                case "n", "no", "ne" -> {
                    return false;
                }
                case "y", "yes", "d", "da" -> {
                    return true;
                }
                default -> System.out.println(LocalizationUtil.getErrorString("error.input.invalid"));
            }
        }
    }

    private void updateItemInTheLibraryDatabase() {
        if (itemLibraryManagerService.checkIsEmpty()) {
            System.out.println(LocalizationUtil.getErrorString("error.database.empty"));
            return;
        }
        boolean wantMoreUpdate;
        do {
            printMessage(LocalizationUtil.getPromptString("prompt.enter_id"));
            int itemId = readUserInputInteger();

            Item itemInUpdateContainer = updateContainerManagerService.getItem(itemId);
            if (!updateContainerManagerService.checkIsItemNotNull(itemInUpdateContainer)) {
                handleItemForUpdate(itemId);
            } else {
                System.out.println(LocalizationUtil.getErrorString("error.list.item.already_in"));
            }
            wantMoreUpdate = confirmMoreUpdates(LocalizationUtil.getPromptString("prompt.update_another_item"));
        } while (wantMoreUpdate);
        itemLibraryManagerService.updateItems(updateContainerManagerService.getItemContainer());
        updateContainerManagerService.clearContainer();
    }

    private void handleItemForUpdate(int itemId) {
        Item item = itemLibraryManagerService.getItem(itemId);
        if (item == null) {
            System.out.println(LocalizationUtil.getErrorString("error.list.item.not_found"));
            scanner.nextLine(); // For removing trailing \n whitespace on the next scanner read
        } else {
            switch (item.getItemType()) {
                case BOOK -> updateBook((Book) item, updateContainerManagerService);
                case MAGAZINE -> updateMagazine((Magazine) item, updateContainerManagerService);
                case DVD -> updateDvd((Dvd) item, updateContainerManagerService);
                default -> System.out.println(LocalizationUtil.getErrorString("error.item.invalid_type"));
            }
            updateContainerManagerService.addItem(item);
        }
    }

    private void getOldestItemFromTheLibraryDatabase() {
        LibraryUtils.printOldestItem(itemLibraryManagerService.getAllItems());
    }

    private void getAveragePublicationYearInTheLibraryDatabase() {
        LibraryUtils.printAveragePublicationYear(itemLibraryManagerService.getAllItems());
    }

    private boolean exit() {
        System.out.println(LocalizationUtil.getInfoString("info.app.shutting_down"));
        itemLibraryManagerService.stop();
        return false;
    }

    private void chooseLanguage() {
        printMessage("""
                Choose language:
                    en (ENGLISH)
                    hr (CROATIAN)
                Choice:""");

        switch (readUserInputString()) {
            case "en" -> LocalizationUtil.loadResources(Locale.forLanguageTag("en-EN"));
            case "hr" -> LocalizationUtil.loadResources(Locale.forLanguageTag("hr-HR"));
            default -> System.out.println(LocalizationUtil.getErrorString("error.input.invalid"));
        }
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private String readUserInputString() {
        String result = "";
        while (result.isEmpty()) {
            result = scanner.nextLine().trim().replace("\n", "");
            if (result.isEmpty()) {
                System.out.println(LocalizationUtil.getErrorString("error.input.invalid"));
            }
        }
        return result;
    }

    private String readUserInputString(String message) {
        String result = "";
        while (result.isEmpty()) {
            System.out.println(message);
            result = scanner.nextLine().trim().replace("\n", "");
            if (result.isEmpty()) {
                System.out.println(LocalizationUtil.getErrorString("error.input.invalid"));
            }
        }
        return result;
    }

    private int readUserInputInteger() {
        int result = 0;
        while (result <= 0) {
            try {
                result = scanner.nextInt();
                scanner.nextLine(); // For removing trailing \n whitespace on the next scanner read
                if (result <= 0) {
                    System.out.println(LocalizationUtil.getErrorString("error.input.smaller_then_1"));
                }
            } catch (Exception e) {
                scanner.nextLine(); // For removing trailing \n whitespace on the next scanner read
                printMessage(LocalizationUtil.getErrorString("error.input.must_be_number"));
            }
        }
        return result;
    }

    private int readUserInputInteger(String message) {
        int result = 0;
        while (result <= 0) {
            try {
                System.out.println(message);
                result = scanner.nextInt();
                scanner.nextLine(); // For removing trailing \n whitespace on the next scanner read
                if (result <= 0) {
                    System.out.println(LocalizationUtil.getErrorString("error.input.smaller_then_1"));
                }
            } catch (Exception e) {
                scanner.nextLine(); // For removing trailing \n whitespace on the next scanner read
                printMessage(LocalizationUtil.getErrorString("error.input.must_be_number"));
            }
        }
        return result;
    }
}
