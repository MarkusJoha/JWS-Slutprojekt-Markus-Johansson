import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server är nu redo!");

        //Initialiserar saker
        Socket socket;
        InputStreamReader inputStreamReader;
        OutputStreamWriter outputStreamWriter;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        ServerSocket serverSocket;

        //Startar servern
        try {
            //Kontrollerar att Socket numret är ledigt, avbryter om socket är upptagen
            serverSocket = new ServerSocket(69420);
            System.out.println(serverSocket.getInetAddress());
            System.out.println(serverSocket.getLocalSocketAddress());
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

        try {
            //Väntar på specifik socket efter trafik
            socket = serverSocket.accept();

            //Initierar Reader och Writer och kopplar dem till socket
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            while (true) {
                //Hämtar klientens meddelande och skickar den till openData()
                //Returnerar ett färdigt JSON objekt som ska tillbaka till klienten
                String message = bufferedReader.readLine();
                String returnData = openData(message);

                System.out.println("Message received and sent back!");

                //Skickar acknowledgement svar tillbaka
                bufferedWriter.write(returnData);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                //Avslutar om klienten skriver "quit"
                if (message.equalsIgnoreCase("quit")) break;
            }
            //Stänger kopplingen
            socket.close();
            inputStreamReader.close();
            outputStreamWriter.close();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        } catch (ParseException e) {
            System.out.println(e);
        } finally {
            System.out.println("Server Avslutas!");
        }
    }
}