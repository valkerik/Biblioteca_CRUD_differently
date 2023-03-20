package ru.valkerik.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.valkerik.spring.models.Book;
import ru.valkerik.spring.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {


    private static final Logger log = LoggerFactory.getLogger(BookDAO.class);
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks(){
        log.info("BookDAO.getAllBook");
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        return books;
    }

    @Transactional(readOnly = true)
    public Book findBookById(int bookId){
        log.info("BookDAO.findBookById {}", bookId);
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, bookId);
        log.info("BookDAO.findBookById 2 step returned {}", book);
        return book;
    }

    @Transactional
    public void save(Book book){
        log.info("BookDAO.save {}", book);
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public void update(int id, Book updBook) {
        log.info("BookDAO.update {}", updBook);
        Session session = sessionFactory.getCurrentSession();
        Book bookToBeUpdated = session.get(Book.class, id);
        bookToBeUpdated.setBookName(updBook.getBookName());
        bookToBeUpdated.setBookAutor(updBook.getBookAutor());
        bookToBeUpdated.setBookDate(updBook.getBookDate());
    }

    @Transactional
    public void delete(int bookId) {
        log.info("BookDAO.delete {}", bookId);
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class,bookId);
        session.delete(book);
    }


    @Transactional
    public Optional<Person> getBookOwner(int id) {
        Book book = sessionFactory.getCurrentSession().get(Book.class, id);
        return Optional.ofNullable(book.getPerson());
    }

    //освобождаем книгу
    @Transactional
    public void release(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setPerson(null);
        session.update(book);
    }

    //назначаем книгу человеку
    @Transactional
    public void assign(int id, Person selectedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setPerson(selectedPerson);
        session.update(book);
    }
}
