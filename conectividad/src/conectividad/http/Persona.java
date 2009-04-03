package conectividad.http;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author juaninsitu
 */
class Persona{
        String nombre;
        String apellidos;

    public Persona() {
        nombre = "";
        apellidos = "";
    }
       
        public void setNombre(String nombre){
            this.nombre = nombre;
        }
        public void setApellido(String apellido){
            this.apellidos = apellido;
        }
       
        public String getNombre(){
            return nombre;
        }
        public String getApellido(){
            return apellidos;
        }
        
    
}
