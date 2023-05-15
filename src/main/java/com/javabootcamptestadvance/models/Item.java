package main.java.com.javabootcamptestadvance.models;

public abstract class Item {
    protected int id;
    protected String title;
    protected String author;
    protected int publicationYear;
    protected Type type;
    protected boolean isForUpdate;

    public Item(int id, String title, String author, int publicationYear, Type type) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.type = type;
        this.isForUpdate = false;
    }

    @Override
    public String toString() {
        return "Item{ " +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                ", itemType=" + type +
                " }";
    }

    public enum Type {
        BOOK("Book"),
        MAGAZINE("Magazine"),
        DVD("DVD");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Type fromValue(String value) {
            for (Type type : Type.values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            return null;
        }

        public static void printValues() {
            for (Type type : Type.values()) {
                System.out.println(type.value);
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public Type getItemType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setItemType(Type type) {
        this.type = type;
    }

    public void setForUpdate(boolean forUpdate) {
        isForUpdate = forUpdate;
    }

    public boolean isForUpdate() {
        return isForUpdate;
    }

    public abstract void displayItemDetails();
}
