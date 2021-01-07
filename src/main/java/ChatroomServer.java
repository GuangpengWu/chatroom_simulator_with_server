import a4.Generator;
import a4.InvalidDirectoryException;
import a4.NonTerminalNotFoundException;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ChatroomServer {
  private int port;
  private static final int MAX_THRESHOLD=10;
  private List<String> userNames = new ArrayList<>();;
  private List<Client> clients = new ArrayList<>();
  private BlockingQueue<Request> currentRequests= new LinkedBlockingQueue<>();
  private final String MESSAGE_FORMATTER="[%s] says: %s";
  private final String LOGOUT="logged out.";
  private Generator generator;
  private ServerSocket serverSocket;
  private final WaitingRoom waitingRoom = new WaitingRoom();
  private final int SUCCESS=0;
  private final int FULL=1;


  public ChatroomServer(int port) {
    this.port = port;
  }

  public void execute() {
    try {
      this.serverSocket = new ServerSocket(port);
      this.generator = new Generator("input");

      System.out.println("Server is listening on port " + port);
      while (true) {
        Socket socket = serverSocket.accept();
        Client newClient = new Client(socket, this);

        newClient.start();

      }

    } catch (IOException | ParseException | InvalidDirectoryException e) {
      try {
        serverSocket.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      System.out.println(e.getMessage());
    }

  }

  public boolean notClosed(){
    return !this.serverSocket.isClosed();
  }

  public void sendRequest(Request request, Client client) throws WrongMessageException, InterruptedException, NonTerminalNotFoundException {
    this.currentRequests.add(request);
    processResponse(client);
  }

  private void processResponse(Client client) throws InterruptedException, WrongMessageException, NonTerminalNotFoundException {
    ResponseMessage response;
    Request request = currentRequests.take();
    // process connect response
    if (request instanceof ConnectRequest) {
      if (isUnique(client.getUsername())){
        response = getConnectionResponse((ConnectRequest) request, client);
        client.sendMessage(response);
      } else {
        response = getConnectionResponse((ConnectRequest) request, client);
        client.sendMessage(response);
        client.retry();
      }

    } else if (request instanceof QueryUserRequest){
      // process query request: get all username except this client
      client.sendMessage(getQueryUserResponse(client));
    } else if (request instanceof DisconnectRequest) {
      removeClient(client);
      client.sendMessage(getDisconnectResponse());
      broadcast(LOGOUT, client);
      pullFromWaitingRoom();
    } else if (request instanceof InsultRequest) {
      getInsultResponse(request, client);
    }
  }

  private void pullFromWaitingRoom() throws InterruptedException, NonTerminalNotFoundException, WrongMessageException {
    Client client = waitingRoom.getFirstClient();
    Request request = waitingRoom.getFirstClientRequest();
    waitingRoom.removeFromWaitRoom();
    // if there is client waiting in the queue
    if (client != null){
      sendRequest(request, client);
    }
  }

  private void getInsultResponse(Request request, Client client) throws NonTerminalNotFoundException, WrongMessageException {
    String targetName = new String(((InsultRequest) request).getReceiverName());
    Client target = findTarget(targetName);
    String insult = getInsultMessage();
    String sender = client.getUsername();
    String MESSAGE = String.format("[%s -> %s]: %s", client.getUsername(), targetName, insult);
    String FULL_MSG = String.format(MessageBuilder.getDirectMessageFormat(), MessageBuilder.getDirectMessage(), sender.length(), sender, targetName.length(), targetName, MESSAGE.length(), MESSAGE);
    ChatContent response = MessageBuilder.buildMessage(FULL_MSG);
    assert target != null;
    target.sendMessage(response);
  }


  private Client findTarget(String targetName){
    for (Client client: clients) {
      if (client.getUsername().equals(targetName)){
        return client;
      }
    }
    return null;
  }

  private ResponseMessage connectionMsg(int flag, String name) throws WrongMessageException {
    String SUCCESS_MESSAGE = String.format("There are %d other connected clients", clients.size());
    String FULL_MSG = "Server is currently full, please wait.";
    String MESSAGE;
    String FORMATTER = MessageBuilder.getConnectResponseFormat();
    if (flag == SUCCESS){
      MESSAGE = String.format(FORMATTER, MessageBuilder.getConnectResponse(), "true", SUCCESS_MESSAGE.length(), SUCCESS_MESSAGE);
    } else if (flag == FULL) {
      MESSAGE = String.format(FORMATTER, MessageBuilder.getConnectResponse(), "false", FULL_MSG.length(), FULL_MSG);
    } else {
      String NOT_UNIQUE_MSG = "Name %s is already in use, please try another username.";
      String MSG = String.format(NOT_UNIQUE_MSG, name);
      MESSAGE = String.format(FORMATTER, MessageBuilder.getConnectResponse(), "false", MSG.length(), MSG);
    }
    return (ResponseMessage) MessageBuilder.buildMessage(MESSAGE);
  }

  private ResponseMessage getConnectionResponse(ConnectRequest request, Client newClient) throws WrongMessageException {
    String username = new String(request.getUsername());
    System.out.println(username + " is asking for connection");
    ResponseMessage response;

    boolean success = this.clients.size() < MAX_THRESHOLD;
    if (isUnique(username)){
      if (success){
        response = connectionMsg(SUCCESS, username);
        this.clients.add(newClient);
        userNames.add(username);
      } else {
        response = connectionMsg(FULL, username);
        waitingRoom.addClient(newClient, request);
      }
    } else {
      int NOT_UNIQUE = 2;
      response = connectionMsg(NOT_UNIQUE, username);
    }

    return response;
  }

  private ChatContent getQueryUserResponse(Client excludeUser) throws WrongMessageException {
    String exclude_name = excludeUser.getUsername();
    String header = String.format("%d %d", MessageBuilder.getQueryUserResponse(), userNames.size()-1);
    StringBuilder builder = new StringBuilder(header);
    for (String name:userNames) {
      if (!name.equals(exclude_name)){
        String sub = String.format(" %d %s", name.length(), name);
        builder.append(sub);
      }
    }
    return MessageBuilder.buildMessage(builder.toString());
  }


  public void broadcast(String message, Client excludeUser) throws WrongMessageException {
    String exclude_name = excludeUser.getUsername();
    String broadcastMsg = String.format(MESSAGE_FORMATTER, exclude_name, message);
    String FULL_MSG = String.format(MessageBuilder.getBroadcastMessageFormat(), MessageBuilder.getBroadcastMessage(), exclude_name.length(), exclude_name, broadcastMsg.length(), broadcastMsg);
    ChatContent broadcast = MessageBuilder.buildMessage(FULL_MSG);
    for (Client client : clients) {
      if (!client.equals(excludeUser)) {
        client.sendMessage(broadcast);
      }
    }
  }


  public void sendDirectMessage(String message, Client client) throws WrongMessageException {
    String[] info = message.split(MessageBuilder.getSPLIT());
    // get rid off the @
    String targetName = info[0].substring(1);
    String sender = client.getUsername();
    String directMsg = String.format(MESSAGE_FORMATTER, sender, message);
    String FULL_MSG = String.format(MessageBuilder.getDirectMessageFormat(), MessageBuilder.getDirectMessage(), sender.length(), sender, targetName.length(), targetName, directMsg.length(), directMsg);
    DirectMessage response = (DirectMessage) MessageBuilder.buildMessage(FULL_MSG);
    Client target = findTarget(targetName);
    assert target != null;
    target.sendMessage(response);
  }


  private void removeClient(Client client) {
    String username = client.getUsername();
    boolean removed = userNames.remove(username);
    if (removed) {
      clients.remove(client);
    }
  }

  private DisconnectResponse getDisconnectResponse() throws WrongMessageException {
    String MSG = String.format(MessageBuilder.getDisconnectResponseFormat(), MessageBuilder.getConnectResponse(), LOGOUT.length(), LOGOUT);
    return MessageBuilder.buildDisconnectResponse(MSG);
  }

  public List<String> getUserNames() {
    return userNames;
  }

  private String getInsultMessage() throws NonTerminalNotFoundException {
    return this.generator.getNext();
  }

  public boolean isUnique(String name){
    return !userNames.contains(name);
  }
}
