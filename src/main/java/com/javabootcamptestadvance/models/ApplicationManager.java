package main.java.com.javabootcamptestadvance.models;

import main.java.com.javabootcamptestadvance.interfaces.LibraryUtils;
import main.java.com.javabootcamptestadvance.repository.RepositoryPostGreSQL;
import main.java.com.javabootcamptestadvance.services.ApplicationManagerService;
import main.java.com.javabootcamptestadvance.services.ItemContainerManagerService;
import main.java.com.javabootcamptestadvance.services.LibraryManagerService;

import java.util.Locale;
import java.util.Scanner;

public class ApplicationManager {
    private final ApplicationManagerService applicationManagerService;

    public ApplicationManager() {
        Scanner scanner = new Scanner(System.in);

        ItemContainer<Item> itemContainer = new ItemContainer<>();
        ItemContainer<Item> deletionContainer = new ItemContainer<>();
        ItemContainer<Item> updateContainer = new ItemContainer<>();

        RepositoryPostGreSQL<Item> itemRepository = new RepositoryPostGreSQL<>();

        ItemContainerManagerService<Item> itemContainerManagerService = new ItemContainerManagerService<>(itemContainer);
        ItemContainerManagerService<Item> deletionContainerManagerService = new ItemContainerManagerService<>(deletionContainer);
        ItemContainerManagerService<Item> updateContainerManagerService = new ItemContainerManagerService<>(updateContainer);

        LibraryManagerService<Item> itemLibraryManagerService = new LibraryManagerService<>(itemRepository);

        this.applicationManagerService = new ApplicationManagerService(scanner,
                itemContainerManagerService,
                deletionContainerManagerService,
                updateContainerManagerService,
                itemLibraryManagerService);
    }

    public ApplicationManagerService getApplicationManagerService() {
        return applicationManagerService;
    }

}
