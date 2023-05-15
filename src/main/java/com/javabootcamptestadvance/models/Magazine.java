package main.java.com.javabootcamptestadvance.models;

public class Magazine extends Item {
    private int issueNumber;
    private String publisher;

    public Magazine(int id, String title, String author, int publicationYear, Type type, int issueNumber, String publisher) {
        super(id, title, author, publicationYear, type);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public void displayItemDetails() {
        System.out.println("ID: " + getId());
        System.out.println("Magazine Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("Publication Year: " + getPublicationYear());
        System.out.println("Item type: " + getItemType());
        System.out.println("Issue Number: " + getIssueNumber());
        System.out.println("Publisher: " + getPublisher());
        System.out.println("-----------------------------------");
    }
}
