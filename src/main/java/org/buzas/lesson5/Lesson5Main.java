package org.buzas.lesson5;


import org.buzas.lesson5.entities.Student;
import org.buzas.lesson5.entities.StudentDao;

public class Lesson5Main {

    private static final StudentDao dao = new StudentDao();

    public static void main(String[] args) {
//        Успешно
//        dao.save(new Student("test", 3));

//        Успешно
//        System.out.println(dao.findAll());
//        Student student = dao.findById(2L);
//        System.out.println(student);

//        Успешно
//        student.setMark(4);
//        dao.update(student);

//        Успешно
//        dao.deleteById(2L);

//        Успешно(подтверждение в screens/resul.png)
//        for (int i = 0; i < 1000; i++) {
//            int mark = (int) (Math.random() * 5);
//            dao.save(new Student("test" + i, mark));
//        }

//        Успешно(кастомный метод, чтобы не доставать вообще всё, а от и до)
//        System.out.println(dao.findAmongTwoNumbers(715, 820));
    }
}
