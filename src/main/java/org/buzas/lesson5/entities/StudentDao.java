package org.buzas.lesson5.entities;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.cfg.Configuration;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class StudentDao implements Closeable {
    private static final EntityManagerFactory factory = FactoryCreator.createFactory();
    private static final EntityManager manager = factory.createEntityManager();

    public Student findById(Long id) {
        return manager.find(Student.class, id);
    }

    public List<Student> findAmongTwoNumbers(int from, int to) {
        return Collections.unmodifiableList(
                manager.createQuery("select s from students s where s.id >= " + from + " and s.id <= " + to, Student.class).getResultList()
        );
    }

    public List<Student> findAll() {
        return Collections.unmodifiableList(
                manager.createQuery("select s from students s", Student.class).getResultList()
        );
    }

    public void deleteById(Long id) {
        manager.getTransaction().begin();
        try {
            manager.createQuery("delete from students s where s.id=" + id).executeUpdate();
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong argument for deleting by id " + id);
            manager.getTransaction().rollback();
        }
        manager.getTransaction().commit();
    }

    public void save(String name, int mark) {
        save(new Student(name, mark));
    }

    public void save(Student student) {
        manager.getTransaction().begin();
        try {
            manager.persist(student);
        } catch (EntityExistsException e) {
            System.out.println(student + " already exists in Database, use update method");
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong argument(s) for save in " + student);
            manager.getTransaction().rollback();
        }
        manager.getTransaction().commit();
    }

    public void update(Student student) {
        manager.getTransaction().begin();
        try {
            manager.merge(student);
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong argument(s) for update in " + student);
            manager.getTransaction().rollback();
        }
        manager.getTransaction().commit();
    }

//    Знаю, что обычно это плохая практика, завязывать что-то на отлове ошибок, но конкретно в этом случае
//    persist выкидывает ошибку EntityExistsException. Это дает возможность выловить конкретно данную ошибку и
//    преобразовать добавление к обновлению данных. Плохим такой подход еще является еще по той причине, что желание
//    добавить сущность не означает желание ее обновить. Но в целом, подумал и такой подход показать, т.к., на удивление,
//    мало где такую ошибку прокидывают
    public void saveOrUpdate(Student student) {
        manager.getTransaction().begin();
        try {
            manager.persist(student);
        } catch (EntityExistsException e) {
            try{
                manager.merge(student);
            } catch (IllegalArgumentException e2) {
                System.out.println("Wrong argument(s) for update in " + student);
                manager.getTransaction().rollback();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Wrong argument(s) for save in " + student);
            manager.getTransaction().rollback();
        }
        manager.getTransaction().commit();
    }

    @Override
    public void close() throws IOException {
        manager.close();
        factory.close();
    }
}
