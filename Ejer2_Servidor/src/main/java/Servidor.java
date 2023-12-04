/*El cliente lee por consola texto del usuario (podría ser de cualquier tamaño). Y envía el texto al servidor. Para enviarlo el
 cliente hace lo siguiente:
- Escribe primero un carácter de delimitación de mensaje #.
- Después el texto codificado en base64.
- Después escribe un último carácter # para delimitar el mensaje.
El servidor está en un bucle a la espera de peticiones. Cada vez que recibe un mensaje, coge el contenido del mensaje (sin delimitadores)
lo decodifica y lo escribe en una línea de un fichero.*/
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public class Servidor {
    public static final int PORT = 3000;
    public static final String FILEPATH = "messages.txt";
    public static void main(String[] args) throws IOException {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("No se puede conectar el socket: " + PORT);
            System.exit(-1);
        }
        Socket clientSocket;
        BufferedReader input;

        while (true) {
            try {
                clientSocket = socket.accept();
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String mensajeEnB64 = input.readLine();
                if (mensajeEnB64.charAt(0) == mensajeEnB64.charAt(mensajeEnB64.length() - 1) && mensajeEnB64.charAt(0) == '#') {
                    mensajeEnB64 = mensajeEnB64.replace("#", "");
                    String mensaje = new String(Base64.getDecoder().decode(mensajeEnB64));
                    Path filename = Path.of(".", FILEPATH);

                    try (BufferedWriter bw = Files.newBufferedWriter(filename, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                        bw.write(mensaje);
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}