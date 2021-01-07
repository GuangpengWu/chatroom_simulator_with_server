import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageWriter extends Thread {
  private PrintWriter writer;
  private Socket socket;
  private ChatroomClient clientThread;
  private final String RETRY="retry username\n";

  public MessageWriter(Socket socket, ChatroomClient clientThread) {
    this.socket = socket;
    this.clientThread = clientThread;

    try {
      OutputStream output = socket.getOutputStream();
      writer = new PrintWriter(output, true);

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public void run() {

    Console console = System.console();
    String username;
    do {
      do {
        username = console.readLine("\nChoose your username (Do not use whitespace, use '_' instead): ");
      } while (CommandManager.isNotValidUsername(username));

      clientThread.setUserName(username);

      // pass username to client thread (server)
      writer.println(username);
      // what for reset username if username is not unique
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    } while (clientThread.getUserName() == null);

    String text;
    // response from server to check if username is unique:



    do {
//            text = console.readLine("[" + username + "]: ");
      text = console.readLine();
      writer.println(text);


    } while (CommandManager.notLogOff(text));

    try {
      socket.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
