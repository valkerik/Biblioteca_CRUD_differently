package ru.valkerik.spring.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.valkerik.spring.dao.BookDAO;
import ru.valkerik.spring.dao.PersonDAO;
import ru.valkerik.spring.models.Book;
import ru.valkerik.spring.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.getAllBooks());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book", bookDAO.findBookById(id));
        Optional<Person> owner = bookDAO.getBookOwner(id);

        if(owner.isPresent()){
            model.addAttribute("owner", owner.get());
        }else {
            model.addAttribute("people", personDAO.getAllPerson());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book){

        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "/books/new";}

        bookDAO.save(book);
        log.info("create step 4 {}", book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.findBookById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        log.info("update step 1 {}", book);
        book.setBookId(id);

        if(bindingResult.hasErrors()){
            log.info("update step 2 {}", book);
            return "books/edit";}

        bookDAO.update(id, book);
        log.info("update step 4 {}", book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")  //освобождаем книгу
    public String release(@PathVariable ("id") int id){
        bookDAO.release(id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/assign")  //назначаем книгу человеку
    public String assign(@PathVariable ("id") int id, @ModelAttribute ("person") Person selectedPerson){
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/"+id;
    }



}
