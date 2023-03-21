package ru.valkerik.spring.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookid")
    private int bookId;


    @NotEmpty(message = "Название книги не может быть пустым")
    @Column(name = "bookname")
    private String bookName;

    @NotEmpty(message = "Имя автора не может быть пустым")
    @Column(name = "bookautor")
    private String bookAutor;

    @Min(value = 1000, message = "Минимальное значение может быть 1000")
    @Column(name = "bookdate")
    private int bookDate;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userId")
    private Person person;

    @Column(name = "takenbook")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenBook;

    @Transient
    private boolean delayDebt;

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isDelayDebt() {
        return delayDebt;
    }

    public void setDelayDebt(boolean delayDebt) {
        this.delayDebt = delayDebt;
    }

    public Date getTakenBook() {
        return takenBook;
    }

    public void setTakenBook(Date takenBook) {
        this.takenBook = takenBook;
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

    public void checkDelayDebt(){
        if (takenBook != null) {
            long check = System.currentTimeMillis() - this.takenBook.getTime();
            this.setDelayDebt(check >= 864_000_000);
        }
    }
}
