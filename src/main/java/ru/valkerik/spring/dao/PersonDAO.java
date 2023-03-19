package ru.valkerik.spring.dao;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.valkerik.spring.models.Book;
import ru.valkerik.spring.models.Person;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class PersonDAO {

    private static final Logger log = LoggerFactory.getLogger(PersonDAO.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPerson() {
        log.info("getAllPerson");
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person findPersonById(int userId) {
        log.info("findPersonById {}", userId);
        return jdbcTemplate.query("SELECT * FROM person WHERE userId=?", new Object[]{userId}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(@NotNull Person person) {
       log.info("save {}", person);
        jdbcTemplate.update("INSERT INTO person(name, date) VALUES ( ?, ?)", person.getName(), person.getDate());
    }

    public void update(int userId, @NotNull Person updPerson) {
       log.info("update {}", updPerson);
        jdbcTemplate.update("UPDATE person SET name=?, date=? WHERE userId=?",
                updPerson.getName(), updPerson.getDate(), userId);
    }

    public void delete(int userId) {
        log.info("delete {}", userId);
        jdbcTemplate.update("DELETE FROM person WHERE userId=?", userId);
    }

    public Optional<Person> getPersonByName(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name=?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE userid=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class) );
    }
}