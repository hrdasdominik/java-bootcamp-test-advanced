package main.java.com.javabootcamptestadvance.interfaces;

import main.java.com.javabootcamptestadvance.models.Book;
import main.java.com.javabootcamptestadvance.models.Dvd;
import main.java.com.javabootcamptestadvance.models.Item;
import main.java.com.javabootcamptestadvance.models.Magazine;
import main.java.com.javabootcamptestadvance.utils.LocalizationUtil;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface LibraryUtils {
    static <T extends Item> List<T> filterItems(List<T> items, Predicate<T> filterCriteria) {
        return items.stream()
                .filter(filterCriteria)
                .collect(Collectors.toList());
    }

    static <T extends Item> void printAveragePublicationYear(List<T> items) {
        OptionalDouble average = items.stream()
                .mapToInt(Item::getPublicationYear)
                .average();
        if (average.isPresent()) {
            System.out.println(LocalizationUtil.getInfoString("info.avg_publication_year") + average.getAsDouble());
        } else {
            System.out.println(LocalizationUtil.getErrorString("error.list.empty"));
        }
    }

    static <T extends Item> void printOldestItem(List<T> items) {
        T oldestItem = items.stream()
                .min(Comparator.comparingInt(Item::getPublicationYear)).orElse(null);

        if (oldestItem != null)  {
            System.out.println(LocalizationUtil.getInfoString("info.oldest_item"));
            oldestItem.displayItemDetails();
        } else {
            System.out.println(LocalizationUtil.getErrorString("error.list.empty"));
        }
    }

    static <T extends Item> List<T> sortItems(List<T> items, Comparator<T> sortCriteria) {
        return items.stream()
                .sorted(sortCriteria)
                .collect(Collectors.toList());
    }
}
