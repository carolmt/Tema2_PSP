/*Aplicación 1:
El cliente envía una petición al servidor (“time”) y espera a que el servidor le envíe un mensaje con una cadena de texto con la hora.
El servidor está en un bucle a la espera de peticiones. Cada vez que recibe un mensaje con el contenido “time”, devuelve la hora actual
en formato texto: “23:55”.*/
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Servidor {
    public static String devolverLaHora(String hora){

        String textoHora;
        if(hora == "time") {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            textoHora = dateFormat.format(hora);
        }
        else {
            textoHora = "No se ha envíado el mensaje 'time'. ";
        }
        return textoHora;
    }

    public static void escuchar() throws IOException {
        System.out.println("Arrancado el servidor");
        ServerSocket socketEscucha=null;
        try {
            socketEscucha=new ServerSocket(9876);
        } catch (IOException e) {
            System.out.println(
                    "No se pudo poner un socket "+
                            "a escuchar en TCP 9876");
            return;
        }
        while (true){
            Socket conexion=socketEscucha.accept();
            System.out.println("Conexion recibida! " );
            InputStream is=conexion.getInputStream();

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bf = new BufferedReader(isr);

            String hora = bf.readLine();

            String resul = devolverLaHora(hora);

            OutputStream os=conexion.getOutputStream();
            PrintWriter pw=new PrintWriter(os);
            pw.write(resul);
            pw.flush();
            System.out.println("La hora actual es: " + resul );
        }
    }
    public  static void main(String[] args) throws IOException {
        escuchar();
    }
}


