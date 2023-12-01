import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public class Cliente {
    public static BufferedReader getFlujo(InputStream is){
        InputStreamReader isr= new InputStreamReader(is);
        BufferedReader bfr= new BufferedReader(isr);

        return bfr;
    }

    public static void main(String[] args) throws IOException {
        InetSocketAddress direccion = new InetSocketAddress("localhost", 9876);
        try {
            Socket socket = new Socket();
            socket.connect(direccion);
            BufferedReader bfr = Cliente.getFlujo(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream());

            pw.print("time");
            //pw.flush();

            String resultado = bfr.readLine();
            System.out.println
                    ("La hora actual es:" + resultado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}