import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ChatroomClient {

  private final String hostname;
  private final int port;
  private String userName;

  public ChatroomClient(String hostname, int port) {
    this.hostname = hostname;
    this.port = port;
  }

  public void execute() {
    try {

      Socket socket = new Socket(hostname, port);

      System.out.println("Trying to connect to the chat server");

      new MessageReader(socket, this).start();
      new MessageWriter(socket, this).start();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return this.userName;
  }

  public void resetUsername() {
    this.userName = null;
  }
}
