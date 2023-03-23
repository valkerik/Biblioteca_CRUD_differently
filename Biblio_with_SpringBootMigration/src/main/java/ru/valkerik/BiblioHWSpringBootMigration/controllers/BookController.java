package ru.valkerik.BiblioHWSpringBootMigration.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.valkerik.BiblioHWSpringBootMigration.models.Book;
import ru.valkerik.BiblioHWSpringBootMigration.models.Person;
import ru.valkerik.BiblioHWSpringBootMigration.servises.BookService;
import ru.valkerik.BiblioHWSpringBootMigration.servises.PeopleService;

import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BookController {


    private final PeopleService peopleService;
    private final BookService bookService;

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_bookDate", required = false) boolean sortByBookDate) {

        if (page == null || booksPerPage == null)
            model.addAttribute("books", bookService.findAll(sortByBookDate));
        else
            model.addAttribute("books", bookService.findWithPagination(page, booksPerPage, sortByBookDate));

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));
        Optional<Person> owner = bookService.findBookOwner(id);

        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {

        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/books/new";
        }

        bookService.save(book);
        log.info("create step 4 {}", book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        log.info("update step 1 {}", book);
        book.setBookId(id);

        if (bindingResult.hasErrors()) {
            log.info("update step 2 {}", book);
            return "books/edit";
        }

        bookService.update(id, book);
        log.info("update step 4 {}", book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")  //освобождаем книгу
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")  //назначаем книгу человеку
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage(){
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query){
        model.addAttribute("books", bookService.searchByBookName(query));
        return "books/search";
    }


}
