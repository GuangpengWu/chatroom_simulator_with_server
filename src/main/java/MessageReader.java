import java.io.*;
import java.net.*;

public class MessageReader extends Thread {
  private BufferedReader reader;
  private Socket socket;
  private ChatroomClient clientThread;

  public MessageReader(Socket socket, ChatroomClient clientThread) {
    this.socket = socket;
    this.clientThread = clientThread;

    try {
      InputStream input = socket.getInputStream();
      reader = new BufferedReader(new InputStreamReader(input));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public void run() {
    try {
      String response;
      String NOT_UNIQUE = "RETRY";

      while (socket.isConnected()) {

        response = reader.readLine();
        if (response.equals(NOT_UNIQUE)){
          clientThread.resetUsername();
        } else {
          System.out.println(response);
        }
      }
    } catch (SocketException e){
      // when logged out
      System.out.println("Bye");
      close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void close(){
    try {
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
