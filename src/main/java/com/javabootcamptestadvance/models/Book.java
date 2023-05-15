package main.java.com.javabootcamptestadvance.models;

public class Book extends Item {
    private String genre;
    private int pages;
    private String isbn;

    public Book(int id, String title, String author, int publicationYear, Type type, String genre, int pages, String isbn) {
        super(id, title, author, publicationYear, type);
        this.genre = genre;
        this.pages = pages;
        this.isbn = isbn;
    }
    public String getGenre() {
        return genre;
    }

    public int getPages() {
        return pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setGenre(String genre) { this.genre = genre; }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public void displayItemDetails() {
        System.out.println("ID: " + getId());
        System.out.println("Book Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("Publication Year: " + getPublicationYear());
        System.out.println("Item type: " + getItemType());
        System.out.println("Genre: " + getGenre());
        System.out.println("Pages: " + getPages());
        System.out.println("ISBN: " + getIsbn());
        System.out.println("-----------------------------------");
    }
}
