import a4.NonTerminalNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

public class Client extends Thread {
  private Socket socket;
  private ChatroomServer server;
  private PrintWriter writer;
  private String username;
  private boolean SUCCESS=false;


  public Client(Socket socket, ChatroomServer server) {
    this.socket = socket;
    this.server = server;
  }

  public void run() {
    try {
      InputStream input = socket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));

      OutputStream output = socket.getOutputStream();
      writer = new PrintWriter(output, true);

      String clientMessage=null;

      do {
        String username = reader.readLine();
        // Send connection Request
        if (!SUCCESS){
          this.username = username;
          server.sendRequest(getRequest(MessageBuilder.getConnectMessage()), this);
        } else {
          clientMessage = username;
          break;
        }

      } while (!SUCCESS);

      do {
        // message user entered from console.
        if (clientMessage == null){
          clientMessage = reader.readLine();
        }

        if (CommandManager.isMenu(clientMessage)){
          writer.println(CommandManager.getMenuMsg());
        } else if (CommandManager.isQuery(clientMessage)){
          server.sendRequest(getRequest(MessageBuilder.getQueryConnectedUsers()), this);
        } else if (CommandManager.isBroadcast(clientMessage, this)){
          server.broadcast(clientMessage, this);
        } else if (CommandManager.isDirect(clientMessage, this)){
          server.sendDirectMessage(clientMessage, this);
        } else if (CommandManager.isInsult(clientMessage, this)){
          server.sendRequest(getInsultRequest(clientMessage), this);
        } else if (!CommandManager.notLogOff(clientMessage)) {
          break;
        } else {
          // assume all other goes to broadcast
          server.broadcast(clientMessage, this);
        }

        if (CommandManager.notLogOff(clientMessage)) {
          clientMessage = null;
        } else {
          break;
        }

      } while (server.notClosed());

      // if server is not closed, it means user entered logoff
      if (server.notClosed()) {
        server.sendRequest(getRequest(MessageBuilder.getDisconnectMessage()), this);
      }

      socket.close();

    } catch (IOException | WrongMessageException | InterruptedException | NonTerminalNotFoundException e) {
      System.out.println("Client failed: " + e.getMessage());
    }
  }



  public void sendMessage(ChatContent message) throws WrongMessageException {
    if (message instanceof ResponseMessage){
      if (message instanceof ConnectResponse){
        ConnectResponse cr = (ConnectResponse) message;
        boolean success = cr.isSuccessful();
        if (success){
          SUCCESS = true;
          String notification = "\nNew user connected: " + username;
          server.broadcast(notification, this);
        }
      }
      ResponseMessage res = (ResponseMessage) message;
      writer.println(new String(res.getMessage()));

    } else if (message instanceof QueryUserResponse) {
      QueryUserResponse res = (QueryUserResponse) message;
      String newMessage = getQueryNameList(res);
      writer.println(newMessage);
    }
  }

  private Request getRequest(int requestId) throws WrongMessageException {
    String MESSAGE = String.format(MessageBuilder.getRequestFormat(), requestId, username.length(), username);
    return (Request) MessageBuilder.buildMessage(MESSAGE);
  }

  public List<String> getAllUsername(){
    return server.getUserNames();
  }

  /**
   * Process name list
   * @param res QueryUserResponse
   * @return a string of list of users currently in the chat room except this client
   */
  private String getQueryNameList(QueryUserResponse res){
    StringBuilder builder = new StringBuilder("[");
    List<byte[]> names = res.getUsernames();
    for (byte[] name:names) {
      builder.append(new String(name)).append(", ");
    }
    return builder.substring(0, builder.length()-2) + "]";
  }

  /**
   *
   * @param message !user
   * @return InsultRequest
   * @throws WrongMessageException when invalid
   */
  private Request getInsultRequest(String message) throws WrongMessageException {
    String targetName = CommandManager.extractName(message);
    String MSG = String.format(MessageBuilder.getSendInsultFormat(), MessageBuilder.getSendInsult(), username.length(), username, targetName.length(), targetName);
    return (Request) MessageBuilder.buildMessage(MSG);
  }


  /**
   * Assume clients have unique usernames
   * @param o the other client
   * @return true if they have same client
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Client client = (Client) o;
    return Objects.equals(username, client.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username);
  }

  public String getUsername() {
    return username;
  }

  public void retry(){
    String NOT_UNIQUE = "RETRY";
    writer.println(NOT_UNIQUE);
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
