package day24pmTestSimplify.dao.impl;

import day24pmTestSimplify.dao.BaseDao;
import day24pmTestSimplify.dao.PersonDao;
import day24pmTestSimplify.model.Person;
import day24pmTestSimplify.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl implements PersonDao {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/20220819_day24";
    public static final String USER = "root";
    public static final String PASSWORD = "root";

    @Override
    public boolean add(Person person) {
        String sql = "insert into person (name, age, salary, born) values (?, ?, ?, ?) ";
        boolean result = BaseDao.executeDML(sql, person.getName(), person.getAge(), person.getSalary(), person.getBorn());
        return result;
    }

    @Override
    public boolean update(Person person) {
        String sql = "update person set name = ?, age = ?, salary = ?, born =? where id = ?;";
        boolean result = BaseDao.executeDML(sql, person.getName(), person.getAge(),person.getSalary(),person.getBorn(),person.getId());
        return result;
    }

    @Override
    public boolean delete(int id) {

        String sql = "delete from person where id = ?";

        boolean result = BaseDao.executeDML(sql, id);
        return result;
    }

    @Override
    public List<Integer> selectId() {
        String sql = "select id from person";
        List<Person> people = BaseDao.executeDQL(sql, Person.class);
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < people.size(); i++) {
            ids.add(people.get(i).getId());
        }
        return ids;
    }

    @Override
    public List<Person> selectAll() {
        String sql = "select id, name, age, salary, born from person";
        List<Person> people = BaseDao.executeDQL(sql, Person.class);
        return people;
    }

    @Override
    public List<Person> selectByName(String name) {
        String sql = "select id, name, age, salary, born from person where name = ?";
        List<Person> people = BaseDao.executeDQL(sql, Person.class, name);
        return people;
    }

    @Override
    public Person selectById(int id) {
        String sql = "select id, name, age, salary, born from person where id = ?";
        List<Person> people = BaseDao.executeDQL(sql, Person.class, id);
        return people.size()>0? people.get(0): null;
    }
}
