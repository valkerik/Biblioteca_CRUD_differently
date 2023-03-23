package ru.valkerik.BiblioHWSpringBootMigration.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int userId;


    @NotEmpty(message = "ФИО не может быть пустым!")
    @Size(min = 3, max = 150, message = "Минимальная длина ФИО 3 знака, максимальная 150 знаков")
    @Column(name = "name")
    private String name;

    @Min(value = 1900, message = "Год рождения не может быть меньше 1900")
    @Column(name = "date")
    private int date;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)// ,cascade = CascadeType.PERSIST)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Book> books;

    public Person(){
    }

    public Person( String name, int date) {
        this.name = name;
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
