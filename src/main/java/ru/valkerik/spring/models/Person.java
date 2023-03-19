package ru.valkerik.spring.models;


import javax.validation.constraints.*;


public class Person {
    private int userId;
    @NotEmpty(message = "ФИО не может быть пустым!")
    @Size(min = 3, max = 150, message = "Минимальная длина ФИО 3 знака, максимальная 150 знаков")
    private String name;

    @Min(value = 1900, message = "Год рождения не может быть меньше 1900")
    private int date;

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

    @Override
    public String toString() {
        return "Person{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
