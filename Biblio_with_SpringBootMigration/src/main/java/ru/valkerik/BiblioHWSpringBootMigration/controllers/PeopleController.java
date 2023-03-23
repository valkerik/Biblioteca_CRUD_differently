package ru.valkerik.BiblioHWSpringBootMigration.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.valkerik.BiblioHWSpringBootMigration.models.Person;
import ru.valkerik.BiblioHWSpringBootMigration.servises.BookService;
import ru.valkerik.BiblioHWSpringBootMigration.servises.PeopleService;


@Controller
@RequestMapping("/people")
public class PeopleController {


    private final PeopleService peopleService;
    private final BookService bookService;


    @Autowired
    public PeopleController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String index(Model model){
        //получим всех людей из DAO и передадим их в представление
        model.addAttribute("people", peopleService.findAll());

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model){

        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.findBooksByOwner(id));

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
 // public String newPerson(Model model){
      //  model.addAttribute("person", new Person());

        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){

     //   personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Validated Person person, BindingResult bindingResult,
                         @PathVariable("id") int id){
       //    personValidator.validate(person, bindingResult);
           person.setUserId(id);
        if(bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id, person);
        return "redirect:/people/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }

}

