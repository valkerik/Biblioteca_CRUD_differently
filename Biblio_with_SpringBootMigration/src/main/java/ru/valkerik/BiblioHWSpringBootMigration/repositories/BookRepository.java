package ru.valkerik.BiblioHWSpringBootMigration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.valkerik.BiblioHWSpringBootMigration.models.Book;
import ru.valkerik.BiblioHWSpringBootMigration.models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByPerson(Person person);
    List<Book> findByBookNameStartingWith(String bookName);
}
