package com.sitepoint;

import java.awt.*;
import java.awt.image.MemoryImageSource;
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

            int width = din.readInt();
            int height = din.readInt();

            Image i;

            int[] pixels = new int[width * height];
            int c;
            double radianConversion = Math.PI / 180.0;
            for (int index = 0, y = 0; y < height; y++) {
                c = ((0xff) &
                        (byte) (Math.abs(Math.sin((y + height) * radianConversion)) * 255));
                for (int x = 0; x < width; x++) {
                    pixels[index++] = ((0xff << 24) | (c << 16) | (c << 8) | c);
                }
            }
            i = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, pixels, 0, width));

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