package ru.valkerik.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.valkerik.spring.models.Book;
import ru.valkerik.spring.models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByPerson(Person person);
    List<Book> findByBookNameStartingWith(String bookName);
}
