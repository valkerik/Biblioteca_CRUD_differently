package ru.valkerik.spring.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Book {

    private int bookId;
    @NotEmpty(message = "Название книги не может быть пустым")
    private String bookName;
    @NotEmpty(message = "Имя автора не может быть пустым")
    private String bookAutor;

    @Min(value = 1000, message = "Минимальное значение может быть 1000")
    private int bookDate;


    public int getBookId() {return bookId;}

    public String getBookName() {
        return bookName;
    }

    public String getBookAutor() {
        return bookAutor;
    }

    public int getBookDate() {
        return bookDate;
    }


    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookAutor(String bookAutor) {
        this.bookAutor = bookAutor;
    }

    public void setBookDate(int bookDate) {
        this.bookDate = bookDate;
    }

    public Book() {
    }

    public Book(String bookName, String bookAutor, int bookDate) {
        this.bookName = bookName;
        this.bookAutor = bookAutor;
        this.bookDate = bookDate;
    }


    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookAutor='" + bookAutor + '\'' +
                ", bookDate=" + bookDate +
                '}';
    }
}
