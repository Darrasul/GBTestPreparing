package org.buzas.lesson6.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// По заданию требовалось вывести не http://localhost:8080/GBTestPrep/hello , а http://localhost:8080/hello
// Не стал переделывать, т.к. в проекте находится не только 6ой урок. Тут можно было бы просто сделать
// urlPatterns = "/" и в pom'нике <build><finalName>hello</finalName></build> - оно бы подошло под условие.
// Но, опять-таки, в проекте не только 6ой урок, потому сделано так.

// Результат запроса в screens/homeworkResult.png
@WebServlet(urlPatterns = "/hello")
public class HelloWorldServlet extends HttpServlet {
    private String returnStatement;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.printf("<html><body>");

        writer.println("<h2>" + returnStatement + "</h2>");

        writer.printf("</body></html>");
        writer.close();
    }

    @Override
    public void init() throws ServletException {
        returnStatement = "HelloWorld";
    }

    @Override
    public void destroy() {
//        Никакие ресурсы мы не открывали, так что тут пусто
        super.destroy();
    }
}
