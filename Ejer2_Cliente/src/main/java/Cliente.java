import java.io.*;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Cliente {
    public static final int PORT = 3000;

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        BufferedReader input = null;
        PrintWriter output = null;
        Scanner reader = new Scanner(System.in);

        try {
            socket=new Socket("localhost",PORT);
            input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
        } catch (IOException e) {
            System.err.println("No se ha podido conectar al socket");
            System.exit(-1);
        }

        System.out.println("Introduce un mensaje:");
        String mensaje = reader.nextLine();
        String mensajeEnB64= String.format(Base64.getEncoder().encodeToString(mensaje.getBytes()));
        output.println(mensajeEnB64);


        output.close();
        input.close();
        socket.close();
    }
}