package com.sitepoint;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class AddToDoServlet extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String nombre = request.getParameter("nombre");
    String telefono = request.getParameter("telefono");
    
    if (nombre != null && telefono != null) {
      ToDoList toDoList = (ToDoList)getServletContext().getAttribute("toDoList");
      toDoList.addItem(nombre, telefono);
    }
    response.sendRedirect("index.html");
  }
}
