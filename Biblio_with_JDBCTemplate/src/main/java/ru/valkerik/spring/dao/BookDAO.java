package ru.valkerik.spring.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.valkerik.spring.models.Book;
import ru.valkerik.spring.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {


    private static final Logger log = LoggerFactory.getLogger(BookDAO.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks(){
        log.info("BookDAO.getAllBook");
        return jdbcTemplate.query("SELECT * FROM biblio.public.book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book findBookById(int bookId){
        log.info("BookDAO.findBookById {}", bookId);
        Book book =  jdbcTemplate.query("SELECT * FROM biblio.public.book WHERE bookId=?", new Object[]{bookId}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
        log.info("BookDAO.findBookById 2 step returned {}", book);
        return book;
    }

    public void save(Book book){
        log.info("BookDAO.save {}", book);
        jdbcTemplate.update("INSERT INTO biblio.public.book (bookname, bookautor, bookdate) VALUES (?,?,?)",
                book.getBookName(), book.getBookAutor(), book.getBookDate());
    }

    public void update(int id, Book updBook) {
        log.info("BookDAO.update {}", updBook);
        jdbcTemplate.update("UPDATE biblio.public.book SET bookname=?, bookautor=?, bookdate=? WHERE bookId=?",
                updBook.getBookName(), updBook.getBookAutor(), updBook.getBookDate(), id);
    }

    public void delete(int bookId) {
        log.info("BookDAO.delete {}", bookId);
        jdbcTemplate.update("DELETE FROM biblio.public.book WHERE bookId=?", bookId);
    }


    public Optional<Person> getBookOwner(int id) {
      return   jdbcTemplate.query("SELECT biblio.public.person.* FROM biblio.public.book JOIN biblio.public.person USING(userid) WHERE bookid=?"
              , new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    //освобождаем книгу
    public void release(int id) {
        jdbcTemplate.update("UPDATE biblio.public.book SET userid=NULL WHERE bookid=?", id);
    }

    //назначаем книгу человеку
    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE biblio.public.book SET userid=? WHERE bookid=?", selectedPerson.getUserId(), id);
    }
}
