package day24pmTestSimplify.dao;

import day24pmTestSimplify.model.Person;

import java.util.List;

public interface PersonDao {
    boolean add(Person person);
    boolean update(Person person);
    boolean delete(int id);
    List<Integer> selectId();
    List<Person> selectAll();
    List<Person> selectByName(String name);
    Person selectById(int id);
}
