/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package conectividad.http;

/*
 * ComunicacionHttp.java
 *
 * Created on 21 de septiembre de 2006, 04:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.*;
import javax.microedition.io.StreamConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
/**
 *
 * @author insitu
 */
public class ComunicacionHttp {
    public static final byte ENVIAR_PERSONA = 0;
    public static final byte TRAER_PERSONAS = 1;
    public static final byte BYE = 2;
    
    
            
    Display pantalla;
    
    String server = "localhost";
    String port = "8080";
    String aplicacion = "/SimpleMVC/ServletEjemplo";
    
    //IndicadorProgreso indPr;
    Thread t;
    
    public ComunicacionHttp(TercerMIDlet eje) {
        pantalla = eje.getDisplay();
        //indPr = new IndicadorProgreso();
        //t = new Thread(indPr);
        
    }
    
    //Envio de datos por HTTP
     public void enviarPorHttp(Persona persona) throws IOException{
         Displayable anterior = pantalla.getCurrent();
         //t.start();
         //pantalla.setCurrent(indPr);
         //Abrir conexion
        String url = "http://" + server + ":" + port + aplicacion;
        HttpConnection conn = (HttpConnection)Connector.open(url, Connector.READ_WRITE);
        conn.setRequestMethod(HttpConnection.GET);
        //Abrir Streams de salida para envio de peticion
        
        DataOutputStream dos = conn.openDataOutputStream();
        
      
        dos.writeUTF(persona.getNombre());
        dos.writeUTF(persona.getApellido());
        
        
        if (conn.getResponseCode( ) == HttpConnection.HTTP_OK) {
            //Abrir Streams de entrada para la captura de la respuesta
            InputStream is = conn.openInputStream();
            final int MAX_LENGTH = 1280;
            byte[] buf = new byte[MAX_LENGTH];
            int total = 0;
            while (total < MAX_LENGTH) {
                int count = is.read(buf, total, MAX_LENGTH - total);
                if (count < 0) {
                    break;
                }
            total += count;
            }
            is.close();
            ByteArrayInputStream bin = new ByteArrayInputStream(buf);
            DataInputStream dis = new DataInputStream(bin);
            String conf = dis.readUTF();
            if(conf.equals("ok")){
                Alert a = new Alert("Info", "Envio exitoso", null, AlertType.CONFIRMATION);
                a.setTimeout(1000);
                pantalla.setCurrent(a, anterior);
            }
            //indPr.stop();
            
        }else{
            System.out.print("ERROR: " + conn.getResponseCode( ));
        }
    }	
}
