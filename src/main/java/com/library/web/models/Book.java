package com.library.web.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    private String Title;
    private String Author;
    private int BookYear, Quantity;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public int getBookYear() {
        return BookYear;
    }

    public void setBookYear(int bookYear) {
        BookYear = bookYear;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Book() {
    }

    public Book(String title, String author, int bookYear, int quantity) {
        Title = title;
        Author = author;
        BookYear = bookYear;
        Quantity = quantity;
    }
}
