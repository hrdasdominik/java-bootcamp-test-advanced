package main.java.com.javabootcamptestadvance.utils;

import java.util.*;

public class LocalizationUtil {
    private static ResourceBundle errorBundle;
    private static ResourceBundle infoBundle;
    private static ResourceBundle labelBundle;
    private static ResourceBundle menuBundle;
    private static ResourceBundle promptBundle;
    private static ResourceBundle searchBundle;

    public static void loadResources(Locale locale) {
        errorBundle = ResourceBundle.getBundle("main.resources.error", locale);
        infoBundle = ResourceBundle.getBundle("main.resources.info", locale);
        labelBundle = ResourceBundle.getBundle("main.resources.label", locale);
        menuBundle = ResourceBundle.getBundle("main.resources.menu", locale);
        promptBundle = ResourceBundle.getBundle("main.resources.prompt", locale);
        searchBundle = ResourceBundle.getBundle("main.resources.search", locale);
    }

    public static String getErrorString(String key) {
        return errorBundle.getString(key);
    }

    public static String getInfoString(String key) {
        return infoBundle.getString(key);
    }

    public static String getLabelString(String key) {
        return labelBundle.getString(key);
    }

    public static String getMenuString(String key) {
        return menuBundle.getString(key);
    }

    public static String getPromptString(String key) {
        return promptBundle.getString(key);
    }

    public static String getSearchString(String key) {
        return searchBundle.getString(key);
    }

    public static void printMenuBundle() {
        System.out.println(menuBundle.getString("menu.choice"));
        Set<String> keys = menuBundle.keySet();

        List<String> keyList = keys.stream()
                .filter(key -> key.startsWith("menu.option")).sorted().toList();

        for (String key : keyList) {
            System.out.println(menuBundle.getString(key));
        }

        System.out.println(menuBundle.getString("menu.choose"));
    }

    public static void printSearchBundle() {
        System.out.println(searchBundle.getString("search.search_by"));
        Set<String> keys = searchBundle.keySet();

        List<String> keyList = keys.stream()
                .filter(key -> key.startsWith("search.option")).sorted().toList();

        for (String key : keyList) {
            System.out.println(searchBundle.getString(key));
        }

        System.out.println(searchBundle.getString("search.choice"));
    }
}
