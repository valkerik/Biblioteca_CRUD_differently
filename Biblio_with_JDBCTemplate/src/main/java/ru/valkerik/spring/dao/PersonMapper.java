package ru.valkerik.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.valkerik.spring.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {

        Person person = new Person();

              person.setUserId(resultSet.getInt("userid"));
              person.setName(resultSet.getString("name"));
              person.setDate(resultSet.getInt("date"));
        return person;
    }
}
