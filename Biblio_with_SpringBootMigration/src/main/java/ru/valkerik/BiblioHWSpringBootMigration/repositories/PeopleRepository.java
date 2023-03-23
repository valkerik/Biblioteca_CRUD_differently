package ru.valkerik.BiblioHWSpringBootMigration.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.valkerik.BiblioHWSpringBootMigration.models.Book;
import ru.valkerik.BiblioHWSpringBootMigration.models.Person;

import java.util.List;
import java.util.Optional;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Book> findByUserId(int id);
    Optional<Person> findByName(String name);
}
