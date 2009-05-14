/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.Vector;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.wireless.messaging.*;

// Main MIDlet class. It controls the user interface and the
// MMS connection
public class MMSMIDlet
        extends MIDlet
        implements MessageListener, CommandListener, Runnable {

    private final String APPLICATION_ID = "mmsdemo";
    private CameraScreen cameraScreen = null;
//  private ReceiveScreen receiveScreen;
//  private SendScreen sendScreen;
//  private InfoScreen infoScreen;
    private Displayable resumeDisplay = null;
    private MessageConnection messageConnection;
    private boolean closing;
    private Message nextMessage = null;
    public Form form;
    public Form form1;
    /*-->*/
    public Command getImage,  storeImage,  exit,  atras,  camera,  guardar,  crear,  editar,  eliminar,  buscar,  transferir,  getMap,  cmdSI,  cmdNO;
    public Command Salir,  Buscar;
    Image img;
    Image mapa;
    /*-->*/
    byte[] pngMap;
    int cont = 0;
    public TextField nombre,  tel,  dir,  buscanombre,  nombre1,  tel1,  dir1;
    public ChoiceGroup intra,  intranet,  acceso,  intra1,  intranet1,  acceso1;
    private List lista;
    private Vector dato;
    /*-->*/
    private Datos datos = new Datos("", "", "", "", "", "", null, null, null, null);
    private Datos d;
    private ComunicacionHttp http;
    private Thread hiloEnvio;
    private boolean seleccion = false;
    byte[] pngImage;
    private int posicion = 0;
    /*-->*/ String error = "";
    /*-->*/ double latitud = 0;
    /*-->*/ double longitud = 0;
    /*-->*/    //public Display displayMapa = null;
    TextBox txtIChannel;
//    private Image person;
    public Form form2;

    public MMSMIDlet() throws IOException {
        http = new ComunicacionHttp(this);
        /*-->*/ d = new Datos("", "", "", "", "", "", null, null, null, null);
        hiloEnvio = new Thread(this);

        form = new Form("Encuesta a Clientes");
        form1 = new Form("Resultado de la Encuesta");
        form2 = new Form("Busqueda de Encuesta");

        txtIChannel = new TextBox("¡", null, 256, TextField.ANY);
        lista = new List("Encuestas", List.IMPLICIT);
        nombre = new TextField("Nombre:", "", 10, TextField.ANY);
        buscanombre = new TextField("Nombre a Buscar:", "", 10, TextField.ANY);
        exit = new Command("Salir", Command.EXIT, 1);
        atras = new Command("Atras", Command.EXIT, 1);
//        storeImage = new Command("StoreImage", Command.SCREEN, 1);
//        getImage = new Command("GetImage", Command.SCREEN, 1);
        camera = new Command("Agregar Foto", Command.SCREEN, 1);
        /*-->*/ getMap = new Command("Agregar Mapa", Command.SCREEN, 1);
        guardar = new Command("Guardar", Command.OK, 1);
        crear = new Command("Crear", Command.OK, 1);
        editar = new Command("Editar", Command.OK, 1);
        transferir = new Command("Transferir", Command.OK, 1);
        eliminar = new Command("Eliminar", Command.OK, 1);
        buscar = new Command("Buscar Encuesta", Command.OK, 1);
        Buscar = new Command("Buscar", Command.OK, 4);
        Salir = new Command("Atras", Command.EXIT, 1);
//        img = Image.createImage("/persona.png");
//        img = createThumbnail(img);
//        ImageItem imgItem = new ImageItem("", img,
//                ImageItem.LAYOUT_TOP |
//                ImageItem.LAYOUT_RIGHT, null);
////            form.insert(0, imgItem);
////            form.delete(1);
//        form.append(imgItem);
        cmdNO = new Command("No", Command.EXIT, 4);
        cmdSI = new Command("Si", Command.OK, 5);
        txtIChannel.addCommand(cmdNO);
        txtIChannel.addCommand(cmdSI);
        txtIChannel.setCommandListener(this);

        inicializar();
        lista.addCommand(exit);
        lista.addCommand(crear);
        lista.addCommand(editar);
        lista.addCommand(transferir);
        lista.addCommand(buscar);
        lista.addCommand(eliminar);
        lista.setCommandListener(this);
        form.addCommand(atras);
        form.addCommand(guardar);
//        form.addCommand(storeImage);
//        form.addCommand(getImage);
        form.addCommand(camera);
        /*-->*/ form.addCommand(getMap);
        form.setCommandListener(this);
        form2.append(buscanombre);
        form2.addCommand(Salir);
        form2.addCommand(Buscar);
        form2.setCommandListener(this);
//        form1.addCommand(Salir);
//        form1.setCommandListener(this);



    }

    public void run() {
        try {
            http.enviarPorHttp(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inicializar() throws IOException {
        form.deleteAll();
        nombre = new TextField("Nombre:", "", 20, TextField.ANY);
        nombre1 = new TextField("Nombre:", "", 20, TextField.ANY);
        tel = new TextField("Telefono:", "", 20, TextField.ANY);
        dir = new TextField("Direccion:", "", 20, TextField.ANY);
        intra = new ChoiceGroup("Hace uso de la Intranet:", ChoiceGroup.EXCLUSIVE);
        CargarImagenes mp = new CargarImagenes(this);
        mp.start();
//        img = Image.createImage("/persona.png");
//        img = createThumbnail(img);
//        ImageItem imgItem = new ImageItem("", img,
//                ImageItem.LAYOUT_TOP |
//                ImageItem.LAYOUT_RIGHT, null);
////            form.insert(0, imgItem);
////            form.delete(1);
//        form.insert(0, imgItem);
//
//        /*-->*/ mapa = Image.createImage("/mapa2.png");
//        /*-->*/ mapa = createThumbnail(mapa);
//        /*-->*/ ImageItem map = new ImageItem("", mapa, ImageItem.LAYOUT_TOP | ImageItem.LAYOUT_RIGHT, null);
//        /*-->*/ form.append(map);
        intra.append("Si", null);
        intra.append("No", null);
        intranet = new ChoiceGroup("La Intranet le ha servido como una herramienta de trabajo:", ChoiceGroup.EXCLUSIVE);
        intranet.append("Nunca", null);
        intranet.append("Algunas Veces", null);
        intranet.append("Casi siempre", null);
        intranet.append("Siempre", null);
        acceso = new ChoiceGroup("El acceso a la Intranet se realiza de manera rápida y sencilla:", ChoiceGroup.EXCLUSIVE);
        acceso.append("Nunca", null);
        acceso.append("Algunas Veces", null);
        acceso.append("Casi siempre", null);
        acceso.append("Siempre", null);

        tel1 = new TextField("Telefono:", "", 20, TextField.ANY);
        dir1 = new TextField("Direccion:", "", 20, TextField.ANY);
        intra1 = new ChoiceGroup("Hace uso de la Intranet:", ChoiceGroup.EXCLUSIVE);
//        img = Image.createImage("/persona.png");
//        img = createThumbnail(img);
//        imgItem = new ImageItem("", img,
//                ImageItem.LAYOUT_TOP |
//                ImageItem.LAYOUT_RIGHT, null);
////            form.insert(0, imgItem);
////            form.delete(1);
//        form1.insert(0, imgItem);
//
//        /*-->*/ mapa = Image.createImage("/mapa2.png");
//        /*-->*/ mapa = createThumbnail(mapa);
//        map = new ImageItem("", mapa, ImageItem.LAYOUT_TOP | ImageItem.LAYOUT_RIGHT, null);
//        /*-->*/ form1.append(map);
        intra1.append("Si", null);
        intra1.append("No", null);
        intranet1 = new ChoiceGroup("La Intranet le ha servido como una herramienta de trabajo:", ChoiceGroup.EXCLUSIVE);
        intranet1.append("Nunca", null);
        intranet1.append("Algunas Veces", null);
        intranet1.append("Casi siempre", null);
        intranet1.append("Siempre", null);
        acceso1 = new ChoiceGroup("El acceso a la Intranet se realiza de manera rápida y sencilla:", ChoiceGroup.EXCLUSIVE);
        acceso1.append("Nunca", null);
        acceso1.append("Algunas Veces", null);
        acceso1.append("Casi siempre", null);
        acceso1.append("Siempre", null);
        form.append(nombre);
        form.append(tel);
        form.append(dir);
        form.append(intra);
        form.append(intranet);
        form.append(acceso);

//        form1.append(nombre1);
//        form1.append(tel1);
//        form1.append(dir1);
//        form1.append(intra1);
//        form1.append(intranet1);
//        form1.append(acceso1);
        form1.addCommand(Salir);
        form1.setCommandListener(this);
    }

    public void startApp() {
        Llenar();
        Display.getDisplay(this).setCurrent(lista);
    }

    public void Camera() {

        if (resumeDisplay == null) {
            System.out.println("aqui");
            // Start the MMS connection
//      startConnection(this);
            // //Create the user interface
            cameraScreen = new CameraScreen(this);
//      infoScreen = new InfoScreen();
//      sendScreen = new SendScreen(this);
            Display.getDisplay(this).setCurrent(cameraScreen);

            resumeDisplay = cameraScreen;
            cameraScreen.start();
        } else {
            cameraScreen = new CameraScreen(this);
//      infoScreen = new InfoScreen();
//      sendScreen = new SendScreen(this);
            Display.getDisplay(this).setCurrent(cameraScreen);

            resumeDisplay = cameraScreen;
            cameraScreen.start();
            Display.getDisplay(this).setCurrent(resumeDisplay);
//            System.out.println("aca");
        }
    }

    public void pauseApp() {
        if (Display.getDisplay(this).getCurrent() == cameraScreen) {
            cameraScreen.stop();
        }
    }

    public void destroyApp(boolean unconditional) {
        if (Display.getDisplay(this).getCurrent() == cameraScreen) {
            cameraScreen.stop();
        }
    }

    void exitApplication() {
        closeConnection();
//        Display.getDisplay(this).setCurrent(form);
//        destroyApp(false);
//        notifyDestroyed();
        Display.getDisplay(this).setCurrent(form);
    }

//  private synchronized void receive(Message incomingMessage) {
//    if (receiveScreen==null) {
//      receiveScreen = new ReceiveScreen(this);
//    }
//    receiveScreen.setMessage(incomingMessage);
//    Display.getDisplay(this).setCurrent(receiveScreen);
//  }
    public void notifyIncomingMessage(MessageConnection conn) {
        // Callback for inbound message.
        // Start a new thread to receive the message.
        new Thread() {

            public void run() {
                try {
                    Message incomingMessage = messageConnection.receive();
                    // this may be called multiple times if
                    // multiple messages arrive simultaneously
                    if (incomingMessage != null) {
//            receive(incomingMessage);   
                    }
                } catch (IOException ioe) {
                    showError("Exception while receiving message: " + ioe.getMessage());
                }
            }
        }.start();
    }

    /*-->*/
    public void show(Image img, byte[] pngImage) {
        this.img = img;
        this.pngImage = pngImage;
        img = createThumbnail(img);
        ImageItem imgItem = new ImageItem("", img,
                ImageItem.LAYOUT_TOP |
                ImageItem.LAYOUT_RIGHT, null);

///*-->*/ this.mapa = map;
/*-->*/ //this.pngMap = pngMap;
/*-->*/ //img = createThumbnail(mapa);
//        /*-->*/ ImageItem imgItemMap = new ImageItem("", mapa,
//                /*-->*/ ImageItem.LAYOUT_TOP |
//                /*-->*/ ImageItem.LAYOUT_RIGHT, null);

        String n = nombre.getString();
        String d = dir.getString();
        String t = tel.getString();
        String it = null;
        if (intra.isSelected(0)) {
            it = intra.getString(0);
        } else {
            it = intra.getString(1);
        }
        String in = null;
        if (intranet.isSelected(0)) {
            in = intranet.getString(0);
        } else if (intranet.isSelected(1)) {
            in = intranet.getString(1);
        } else if (intranet.isSelected(2)) {
            in = intranet.getString(2);
        } else {
            in = intranet.getString(3);
        }
        String access = null;
        if (acceso.isSelected(0)) {
            access = acceso.getString(0);
        } else if (acceso.isSelected(1)) {
            access = acceso.getString(1);
        } else if (acceso.isSelected(2)) {
            access = acceso.getString(2);
        } else {
            access = acceso.getString(3);
        }
//        form.deleteAll();
        form.delete(0);
        form.insert(0, imgItem);
        //form.append(imgItem);

        /*-->*/ //form.insert(0,imgItemMap);
        salvar(n, d, t, it, in, access);
        Display.getDisplay(this).setCurrent(this.form);
    }

    public boolean exist(String persona) {
        boolean encontro = false;
        try {

            //RecordStore.deleteRecordStore("personas");
            RecordStore rstore = RecordStore.openRecordStore("datos", true);
            //RecordStore.deleteRecordStore("personas");
            persona = persona.toUpperCase();

            RecordEnumeration re = rstore.enumerateRecords(null, null, true);

            while (re.hasPreviousElement()) {
                byte[] b = re.previousRecord();
                ByteArrayInputStream bais = new ByteArrayInputStream(b);
                DataInputStream dis = new DataInputStream(bais);
                String nombre = dis.readUTF();
                nombre = nombre.toUpperCase();
//                int pos = dis.readInt();
//                this.posicion = pos;
                if (nombre.equals(persona)) {
                    encontro = true;
                    break;
                }

                bais.reset();
                dis.reset();

                bais.close();
                dis.close();

            }

            rstore.closeRecordStore();

        } catch (Exception e) {
        }
        return encontro;
    }

    public void salvar(String n, String d, String t, String it, String in, String access) {
//        nombre = new TextField("Nombre:", "", 20, TextField.ANY);
//        tel = new TextField("Telefono:", "", 20, TextField.ANY);
//        dir = new TextField("Direccion:", "", 20, TextField.ANY);
//        intra = new ChoiceGroup("Hace uso de la Intranet:", ChoiceGroup.EXCLUSIVE);
//        intra.append("Si", null);
//        intra.append("No", null);
//        intranet = new ChoiceGroup("La Intranet le ha servido como una herramienta de trabajo:", ChoiceGroup.EXCLUSIVE);
//        intranet.append("Nunca", null);
//        intranet.append("Algunas Veces", null);
//        intranet.append("Casi siempre", null);
//        intranet.append("Siempre", null);
//        acceso = new ChoiceGroup("El acceso, navegación y consulta de la Intranet se realizan de manera rápida y sencilla:", ChoiceGroup.EXCLUSIVE);
//        acceso.append("Nunca", null);
//        acceso.append("Algunas Veces", null);
//        acceso.append("Casi siempre", null);
//        acceso.append("Siempre", null);
        nombre.setString(n);
        dir.setString(d);
        tel.setString(t);
        it = it.trim();
        System.out.println(it);
        if (it.equals(intra.getString(0))) {
            intra.setSelectedIndex(0, true);
        } else {
            intra.setSelectedIndex(1, true);
        }
        if (in.equals(intranet.getString(0))) {
            intranet.setSelectedIndex(0, true);
        } else if (in.equals(intranet.getString(1))) {
            intranet.setSelectedIndex(1, true);
        } else if (in.equals(intranet.getString(2))) {
            intranet.setSelectedIndex(2, true);
        } else {
            intranet.setSelectedIndex(3, true);
        }
        if (access.equals(acceso.getString(0))) {
            acceso.setSelectedIndex(0, true);
        } else if (access.equals(acceso.getString(1))) {
            acceso.setSelectedIndex(1, true);
        } else if (access.equals(acceso.getString(2))) {
            acceso.setSelectedIndex(2, true);
        } else {
            acceso.setSelectedIndex(3, true);
        }
//        form.append(nombre);
//        form.append(tel);
//        form.append(dir);
//        form.append(intra);
//        form.append(intranet);
//        form.append(acceso);
    }

    public void show1(Image imag) {
        this.img = imag;
        Display.getDisplay(this).setCurrent(form);
    }
//Create a method for fetching the application ID.

    // return the application id, either from the
    // jad file or from a hardcoded value
    String getApplicationID() {
        String applicationID = this.getAppProperty("Application-ID");
        return applicationID == null ? APPLICATION_ID : applicationID;
    }

    //Create the methods for showing the various screens and displays used by the application.
    public Display getDisplay() {
        return Display.getDisplay(this);
    }

    // Upon capturing an image, show the compose screen
    void imageCaptured(byte[] imageData) {
        cameraScreen.stop();
    }

    // Displays the error screen
    void showError(String messageString) {
        Alert alerta = new Alert(messageString);
        Display.getDisplay(this).setCurrent(alerta);
//    infoScreen.showError(messageString, Display.getDisplay(this) );
    }

//Create methods for starting and closing the message connection. The setMessageListener method registers a MessageListener object that the platform can notify when a message has been received on this MessageConnection.
//For more information, see setMessageListener in the WMAPI 2.0 specification.
    // Closes the message connection when the application
    // is stopped
    private void closeConnection() {
        closing = true;
        if (messageConnection != null) {
            try {
                messageConnection.close();
            } catch (IOException ioe) {
                // Ignore errors on shutdown
            }
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == exit) {
            destroyApp(true);
            notifyDestroyed();
        } else if (c == buscar) {
            Display.getDisplay(this).setCurrent(form2);
        } else if (c == Salir) {
            Display.getDisplay(this).setCurrent(lista);
        } else if (c == Buscar) {
            Lookup mp = new Lookup(this);
            mp.start();

        } else if (c == cmdSI) {
            String it = null;
            if (intra.isSelected(0)) {
                it = intra.getString(0);
            } else {
                it = intra.getString(1);
            }
            String in = null;
            if (intranet.isSelected(0)) {
                in = intranet.getString(0);
            } else if (intranet.isSelected(1)) {
                in = intranet.getString(1);
            } else if (intranet.isSelected(2)) {
                in = intranet.getString(2);
            } else {
                in = intranet.getString(3);
            }
            String access = null;
            if (acceso.isSelected(0)) {
                access = acceso.getString(0);
            } else if (acceso.isSelected(1)) {
                access = acceso.getString(1);
            } else if (acceso.isSelected(2)) {
                access = acceso.getString(2);
            } else {
                access = acceso.getString(3);
            }


            datos.setNombre(nombre.getString());
            datos.setTel(tel.getString());
            datos.setDir(dir.getString());
            datos.setIntra(intra.getString(intra.getSelectedIndex()));
            datos.setIntranet(intranet.getString(intranet.getSelectedIndex()));
            datos.setAcceso(acceso.getString(acceso.getSelectedIndex()));
            datos.setImagen(img);
            datos.setPngImage(pngImage);
            datos.setMapa(mapa);
            datos.setPngMap(pngMap);
            try {
                int pos = lista.getSelectedIndex();
                String person = lista.getString(pos);
                //lista.delete(pos);
                System.out.println(pos);
                Encuesta.modificarEncuesta(datos, person);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Llenar();
            Display.getDisplay(this).setCurrent(lista);
            posicion++;
        } else if (c == cmdNO) {
            Display.getDisplay(this).setCurrent(lista);
        } else if (c == crear) {
            try {
                inicializar();
                seleccion = false;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Display.getDisplay(this).setCurrent(form);
        } else if (c == atras) {
            Display.getDisplay(this).setCurrent(lista);
        } else if (c == guardar) {
            if (exist(nombre.getString()) == true && seleccion == false) {
                //txtIChannel = new TextBox("¡", "Este nombre de contacto ya existe, ¿Reemplazar? "+nombre.getString(), 256, TextField.ANY);
                txtIChannel.setString("Este nombre de contacto ya existe, ¿Reemplazar? " + nombre.getString());
                Display.getDisplay(this).setCurrent(txtIChannel);
            } else {
                String it = null;
                if (intra.isSelected(0)) {
                    it = intra.getString(0);
                } else {
                    it = intra.getString(1);
                }
                String in = null;
                if (intranet.isSelected(0)) {
                    in = intranet.getString(0);
                } else if (intranet.isSelected(1)) {
                    in = intranet.getString(1);
                } else if (intranet.isSelected(2)) {
                    in = intranet.getString(2);
                } else {
                    in = intranet.getString(3);
                }
                String access = null;
                if (acceso.isSelected(0)) {
                    access = acceso.getString(0);
                } else if (acceso.isSelected(1)) {
                    access = acceso.getString(1);
                } else if (acceso.isSelected(2)) {
                    access = acceso.getString(2);
                } else {
                    access = acceso.getString(3);
                }


                datos.setNombre(nombre.getString());
                datos.setTel(tel.getString());
                datos.setDir(dir.getString());
                datos.setIntra(intra.getString(intra.getSelectedIndex()));
                datos.setIntranet(intranet.getString(intranet.getSelectedIndex()));
                datos.setAcceso(acceso.getString(acceso.getSelectedIndex()));
                datos.setImagen(img);
                datos.setPngImage(pngImage);
                datos.setMapa(mapa);
                datos.setPngMap(pngMap);
                System.out.println(seleccion);
                if (seleccion == false) {
                    Encuesta.almacenaEncuesta(datos);
                } else if (seleccion == true) {
                    int pos = lista.getSelectedIndex();
                    String person = lista.getString(pos);
                    //lista.delete(pos);
                    System.out.println(pos);
                    Encuesta.modificarEncuesta(datos, person);
                }
                Llenar();
                Display.getDisplay(this).setCurrent(lista);
                posicion++;
            }
        } else if (c == editar) {
            if (lista.size() != 0) {
                form.deleteAll();
                leerResultados();
                seleccion = true;
                Display.getDisplay(this).setCurrent(form);
            } else {

                Alert alerta = new Alert("No hay algun objeto seleccionado");
                Display.getDisplay(this).setCurrent(alerta);
            }
        } else if (c == camera) {
            Camera();
        } else if (c == transferir) {
            if (lista.size() != 0) {
                Transferir();
            } else {

                Alert alerta = new Alert("No hay algun objeto seleccionado");
                Display.getDisplay(this).setCurrent(alerta);
            }
        } else if (c == eliminar) {
            if (lista.size() != 0) {

                try {

                    int k = lista.getSelectedIndex();
                    String nomb = lista.getString(k);
                    nomb = nomb.trim();
                    lista.delete(k);
                    Alert alerta = new Alert("Eliminando....");
                    Display.getDisplay(this).setCurrent(alerta);
                    Encuesta.delete(nomb);

                } catch (Exception ex) {

                    ex.printStackTrace();
                }


            } else {
                Alert alerta = new Alert("No hay algun objeto seleccionado");
                Display.getDisplay(this).setCurrent(alerta);
            }
        /*-->*/ } else if (c == getMap) {
            /*-->*/ MapServices mp = new MapServices(this);
            mp.start();
        /*-->*/ }
    }

    public void Transferir() {
        try {

            String p = lista.getString(lista.getSelectedIndex());


//            System.out.println(p);
            Vector v = Encuesta.leerEncuesta();
            datos = (Datos) v.elementAt(lista.getSelectedIndex());
            String nomb = datos.getNombre();
            System.out.println("Nombre" + nomb);
            String tele = datos.getTel();
            System.out.println("Television" + tele);
            String direccion = datos.getDir();
            System.out.println("Television" + direccion);
            String it = datos.getIntra();
            System.out.println("Intranet" + it);
            String in = datos.getIntranet();
            System.out.println("Intranet" + in);
            String access = datos.getAcceso();
            System.out.println("Intranet" + access);
            img = datos.getImagen();
            img = createThumbnail(img);
            ImageItem imgItem = new ImageItem("", img,
                    ImageItem.LAYOUT_TOP |
                    ImageItem.LAYOUT_RIGHT, null);
            System.out.println("Image" + imgItem);
            mapa = datos.getMapa();
            d.setNombre(nomb);
            d.setTel(tele);
            d.setDir(direccion);
            d.setIntra(it);
            d.setIntranet(in);
            d.setAcceso(access);
            d.setImagen(img);
            d.setPngImage(datos.getPngImage());
            d.setMapa(mapa);
            d.setPngMap(datos.getPngMap());
            byte[] pngImage = datos.getPngImage();
            System.out.println("Transferir:prim:ult:" + pngImage[0] + " " + pngImage[pngImage.length - 1]);
            byte[] pngMap = datos.getPngMap();
            hiloEnvio = new Thread(this);
            hiloEnvio.start();

        /* textBoxResultados.insert(nom, 0);
        textBoxResultados.insert(gen, textBoxResultados.size());
        textBoxResultados.insert(afs, textBoxResultados.size());

        display.setCurrent(textBoxResultados);*/

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void traerDatos(String nomb, int telef, String dire) throws IOException {
        inicializar();

        nombre.setString(nomb);
        tel.setString(String.valueOf(tel));
        dir.setString(dire);
        Display.getDisplay(this).setCurrent(form);

    }

    public void leerResultados() {
        try {

            String p = lista.getString(lista.getSelectedIndex());
            System.out.println(p);
            Vector v = Encuesta.leerEncuesta();
            datos = (Datos) v.elementAt(lista.getSelectedIndex());
            String nomb = datos.getNombre();
            String tele = datos.getTel();
            String direcc = datos.getDir();
            String it = datos.getIntra();
            it = it.trim();
            String in = datos.getIntranet();

            String access = datos.getAcceso();
            Image imag = datos.getImagen();
            imag = createThumbnail(imag);
            ImageItem imgItem = new ImageItem("", imag,
                    ImageItem.LAYOUT_TOP |
                    ImageItem.LAYOUT_RIGHT, null);
            form.insert(0, imgItem);
            pngImage = datos.getPngImage();
            Image map = datos.getMapa();
//            map=createThumbnail(map);
            ImageItem imgItem1 = new ImageItem("", map,
                    ImageItem.LAYOUT_TOP |
                    ImageItem.LAYOUT_RIGHT, null);
            form.insert(1, imgItem1);
            pngMap = datos.getPngMap();
            nombre = new TextField("Nombre:", "", 20, TextField.ANY);
            tel = new TextField("Telefono:", "", 20, TextField.ANY);
            dir = new TextField("Direccion:", "", 20, TextField.ANY);
            intra = new ChoiceGroup("Hace uso de la Intranet:", ChoiceGroup.EXCLUSIVE);
            intra.append("Si", null);
            intra.append("No", null);
            intranet = new ChoiceGroup("La Intranet le ha servido como una herramienta de trabajo:", ChoiceGroup.EXCLUSIVE);
            intranet.append("Nunca", null);
            intranet.append("Algunas Veces", null);
            intranet.append("Casi siempre", null);
            intranet.append("Siempre", null);
            acceso = new ChoiceGroup("El acceso a la Intranet se realiza de manera rápida y sencilla:", ChoiceGroup.EXCLUSIVE);
            acceso.append("Nunca", null);
            acceso.append("Algunas Veces", null);
            acceso.append("Casi siempre", null);
            acceso.append("Siempre", null);
            form.append(nombre);
            form.append(tel);
            form.append(dir);
            form.append(intra);
            form.append(intranet);
            form.append(acceso);
            nombre.setString(nomb);
            tel.setString(tele);
            dir.setString(direcc);
            if (it.equals(intra.getString(0))) {
                intra.setSelectedIndex(0, true);
            } else {
                intra.setSelectedIndex(1, true);
            }
            if (in.equals(intranet.getString(0))) {
                intranet.setSelectedIndex(0, true);
            } else if (in.equals(intranet.getString(1))) {
                intranet.setSelectedIndex(1, true);
            } else if (in.equals(intranet.getString(2))) {
                intranet.setSelectedIndex(2, true);
            } else {
                intranet.setSelectedIndex(3, true);
            }
            if (access.equals(acceso.getString(0))) {
                acceso.setSelectedIndex(0, true);
            } else if (access.equals(acceso.getString(1))) {
                acceso.setSelectedIndex(1, true);
            } else if (access.equals(acceso.getString(2))) {
                acceso.setSelectedIndex(2, true);
            } else {
                acceso.setSelectedIndex(3, true);
            }

        /* textBoxResultados.insert(nom, 0);
        textBoxResultados.insert(gen, textBoxResultados.size());
        textBoxResultados.insert(afs, textBoxResultados.size());

        display.setCurrent(textBoxResultados);*/

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Llenar() {
        lista.deleteAll();
        try {
            dato = Encuesta.leerEncuesta();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dato.isEmpty()) {
            Alert alerta = new Alert("No hay datos");
            Display.getDisplay(this).setCurrent(alerta);
        } else {
            System.out.println("tamaño" + dato.size());
            for (int i = 0; i < dato.size(); i++) {
                datos = (Datos) dato.elementAt(i);
                System.out.println("nombre" + i);
                lista.append(datos.getNombre(), createThumbnail(datos.getImagen()));
            }
        }
    }

    private Image createThumbnail(Image image) {
        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();

        int thumbWidth = 30;//64
        int thumbHeight = -1;//

        if (thumbHeight == -1) {
            thumbHeight = thumbWidth * sourceHeight / sourceWidth;
        }

        Image thumb = Image.createImage(thumbWidth, thumbHeight);
        Graphics g = thumb.getGraphics();

        for (int y = 0; y < thumbHeight; y++) {
            for (int x = 0; x < thumbWidth; x++) {
                g.setClip(x, y, 1, 1);
                int dx = x * sourceWidth / thumbWidth;
                int dy = y * sourceHeight / thumbHeight;
                g.drawImage(image, x - dx, y - dy, Graphics.LEFT | Graphics.TOP);
            }
        }

        Image immutableThumb = Image.createImage(thumb);

        return immutableThumb;
    }

    /*-->*/
    private void obtenerMapa() {
        MapServices mp = new MapServices(this);
        mp.run();
    }
    /*-->*/
}