package ru.valkerik.spring.servises;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.valkerik.spring.models.Book;
import ru.valkerik.spring.models.Person;
import ru.valkerik.spring.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;


    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> findOnePerson = peopleRepository.findById(id);
        return findOnePerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setUserId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> getPersonByName(String name){
        return peopleRepository.findByName(name);
    }

    public List<Book> findBooksByOwner(int id) {
        Person person = peopleRepository.getOne(id);
        Hibernate.initialize(person.getBooks());
        List<Book> books = person.getBooks();
       if(!books.isEmpty() ) {
           for (Book book : books) {
               book.checkDelayDebt();
           }
       }
        return books;
    }

}
