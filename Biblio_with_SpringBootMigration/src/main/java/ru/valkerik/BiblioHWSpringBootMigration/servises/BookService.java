package ru.valkerik.BiblioHWSpringBootMigration.servises;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.valkerik.BiblioHWSpringBootMigration.models.Book;
import ru.valkerik.BiblioHWSpringBootMigration.models.Person;
import ru.valkerik.BiblioHWSpringBootMigration.repositories.BookRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;


    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }

    public List<Book> findAll(boolean sortByBookDate){
       if(sortByBookDate)
        return bookRepository.findAll(Sort.by("bookDate"));
       else
           return bookRepository.findAll();
    }

    public Object findWithPagination(Integer page, Integer booksPerPage, boolean sortByBookDate) {
        if(sortByBookDate)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("bookDate"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findOne(int id){
        Optional<Book> findOneBook = bookRepository.findById(id);
        return findOneBook.orElse(null);
    }
    public List<Book> searchByBookName(String query){
        return bookRepository.findByBookNameStartingWith(query);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updBook){
        updBook.setBookId(id);
        updBook.setPerson(bookRepository.findById(id).get().getPerson()); //чтобы не терялась связь при обновлении
        bookRepository.save(updBook);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }


    public Optional<Person> findBookOwner(int bookId){
        Book book = bookRepository.getOne(bookId);
        return Optional.ofNullable(book.getPerson());
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        bookRepository.findById(id).ifPresent(book -> {book.setPerson(selectedPerson); book.setTakenBook(new Date());});
    }

    @Transactional
    public void release(int id) {
       bookRepository.findById(id).ifPresent(book -> {book.setPerson(null);book.setTakenBook(null);});
    }
}
