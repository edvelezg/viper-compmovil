package com.sitepoint;


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import javax.servlet.http.HttpServlet;


public class ServletEjemplo extends HttpServlet {

    public void init() throws ServletException {
        super.init();

    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String salida = null;
        DataInputStream din = null;
        PrintWriter out = null;

        ByteArrayOutputStream bout = null;
        DataOutputStream dos = null;


        try {
            out = response.getWriter();
            ServletInputStream in = request.getInputStream();
            din = new DataInputStream(in);
        } catch (IOException io) {
            System.out.println("Se ha producido una excepcion");
        }

        try {
//            Persona persona = new Persona();

            String nombre = din.readUTF();
            System.out.println("Nombre " + nombre);

            String telefono = din.readUTF();
            System.out.println("Telefono " + telefono);

            if (nombre != null) {
                ToDoList toDoList = (ToDoList) getServletContext().getAttribute("toDoList");
                toDoList.addItem(nombre, telefono);
            }
           
//            persona.setApellido(apellido);

//            if (guardarPersona(persona)) {
            bout = new ByteArrayOutputStream();
            dos = new DataOutputStream(bout);
            dos.writeUTF("ok");
            byte[] salidaB = bout.toByteArray();
            salida = new String(salidaB);
//            }

            out.print(salida);
            out.flush();

            int contentLength = salida.length();

            //System.out.println("ContentLength= " + contentLength);
            response.setContentLength(contentLength);
        } catch (IOException ae) {
            ae.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        out.print(salida);
    }

//    public boolean guardarPersona(Persona persona) {
//        System.out.println("Guardando Persona...");
//        System.out.println("Nombre:   " + persona.getNombre());
//        System.out.println("Apellido:     " + persona.getApellido());
//
//        return true;
//    }
}