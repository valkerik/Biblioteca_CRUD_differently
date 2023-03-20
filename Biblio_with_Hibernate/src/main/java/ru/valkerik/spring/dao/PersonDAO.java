package ru.valkerik.spring.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.valkerik.spring.models.Book;
import ru.valkerik.spring.models.Person;


import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private static final Logger log = LoggerFactory.getLogger(PersonDAO.class);
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> getAllPerson() {
        log.info("getAllPerson");
        Session session = sessionFactory.getCurrentSession();
       List<Person> people =  session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        return people;
    }

    @Transactional
    public Person findPersonById(int userId) {
        log.info("findPersonById {}", userId);
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, userId);
    }

    @Transactional
    public void save(@NotNull Person person) {
       log.info("save {}", person);
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int userId, @NotNull Person updPerson) {
       log.info("update {}", updPerson);
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class, userId);
        personToBeUpdated.setName(updPerson.getName());
        personToBeUpdated.setDate(updPerson.getDate());
//        session.update(updPerson);
    }

    @Transactional
    public void delete(int userId) {
        log.info("delete {}", userId);
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, userId);
        session.delete(person);
    }

    @Transactional
    public Optional<Person> getPersonByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "FROM Person WHERE name = :name";
        Query<Person> query = session.createQuery(queryString, Person.class);
        query.setParameter("name", name);
        List<Person> results = query.getResultList();
        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    @Transactional
    public List<Book> getBooksByPersonId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        Hibernate.initialize(person.getBooks());
        return person.getBooks();
    }
}