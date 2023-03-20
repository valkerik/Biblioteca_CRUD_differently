package ru.valkerik.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.valkerik.spring.dao.PersonDAO;
import ru.valkerik.spring.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o; //посмотреть есть ли человек с таким же именем в БД
        if(personDAO.getPersonByName(person.getName()).isPresent()){
            errors.rejectValue("name", "", "Имя должно быть уникально");
        }

    }
}
