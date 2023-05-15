package main.java.com.javabootcamptestadvance.models;

public class Dvd extends Item {
    private int durationMinutes;
    private String director;
    private Rating rating;

    public Dvd(int id, String title, String author, int publicationYear, Type type, int durationMinutes, String director, Rating rating) {
        super(id, title, author, publicationYear, type);
        this.durationMinutes = durationMinutes;
        this.director = director;
        this.rating = rating;
    }

    public enum Rating {
        G("G"),
        PG("PG"),
        PG13("PG-13"),
        R("R"),
        NC17("NC-17");

        private final String value;

        Rating(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Rating fromValue(String value) {
            for (Rating rating : Rating.values()) {
                if (rating.value.equalsIgnoreCase(value)) {
                    return rating;
                }
            }
            return null;
        }

        public static void printValues() {
            for (Rating rating : Rating.values()) {
                System.out.println(rating.value);
            }
        }
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getDirector() {
        return director;
    }

    public Rating getRating() {
        return rating;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public void displayItemDetails() {
        System.out.println("ID: " + getId());
        System.out.println("DVD Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("Publication Year: " + getPublicationYear());
        System.out.println("Item type: " + getItemType());
        System.out.println("Duration: " + getDurationMinutes() + " minutes");
        System.out.println("Director: " + getDirector());
        System.out.println("Rating: " + getRating());
        System.out.println("-----------------------------------");
    }
}
