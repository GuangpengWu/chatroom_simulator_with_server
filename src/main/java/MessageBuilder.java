import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageBuilder {
  private static final int CONNECT_MESSAGE=19;
  private static final int CONNECT_RESPONSE=20;
  private static final int DISCONNECT_MESSAGE=21;
  private static final int QUERY_CONNECTED_USERS=22;
  private static final int QUERY_USER_RESPONSE=23;
  private static final int BROADCAST_MESSAGE=24;
  private static final int DIRECT_MESSAGE=25;
  private static final int FAILED_MESSAGE=26;
  private static final int SEND_INSULT=27;
  private static final String SPLIT=" ";
  private static final String WRONG_PARAM="Wrong message for %s";

  // formatters
  private static final String CONNECT_REQUEST_FORMAT="%d %d %s";
  private static final String CONNECT_RESPONSE_FORMAT="%d %s %d %s";
  private static final String DISCONNECT_RESPONSE_FORMAT="%d %d %s";
  private static final String BROADCAST_MESSAGE_FORMAT="%d %d %s %d %s";
  private static final String DIRECT_MESSAGE_FORMAT="%d %d %s %d %s %d %s";
  private static final String FAILED_MESSAGE_FORMAT="%d %d %s";
  private static final String SEND_INSULT_FORMAT="%d %d %s %d %s";

  public static ChatContent buildMessage(String message) throws WrongMessageException {
    String[] info = message.split(getSPLIT());
    int id = Integer.parseInt(info[0]);
    ChatContent msg;
    switch (id) {
      case CONNECT_MESSAGE:
        msg = buildConnectRequest(info);
        break;
      case CONNECT_RESPONSE:
        msg = buildConnectResponse(info);
        break;
      case DISCONNECT_MESSAGE:
        msg = buildDisconnectRequest(info);
        break;
      case QUERY_CONNECTED_USERS:
        msg = buildQueryRequest(info);
        break;
      case QUERY_USER_RESPONSE:
        msg = buildQueryResponse(info);
        break;
      case BROADCAST_MESSAGE:
        msg = buildBroadcastMessage(info);
        break;
      case DIRECT_MESSAGE:
        msg = buildDirectMessage(info);
        break;
      case FAILED_MESSAGE:
        msg = buildFailedMessage(info);
        break;
      case SEND_INSULT:
        msg = buildInsultMessage(info);
        break;
      default:
        throw new WrongMessageException("Unexpected value: " + id);
    }
    return msg;
  }

  /**
   * message is like “19 3 aha” as provided in the handout.
   * Information is separated by whitespaces where the first value is supposed to be the message type
   *
   * @param info split “19 3 aha” which contain all required message for ConnectRequest
   * @return ConnectRequest
   * @throws WrongMessageException If the information in the string are wrong or missing any content, raise exception.
   */
  private static ConnectRequest buildConnectRequest(String[] info) throws WrongMessageException{
    String KEYWORD = "CONNECT_MESSAGE";
    incorrectSize(info.length, ConnectRequest.getParamNumber(), KEYWORD);
    // skip the first id as we know already
    int usernameSize = Integer.parseInt(info[1]);
    incorrectSize(usernameSize, info[2].length(), KEYWORD);
    byte[] username = info[2].getBytes();

    return new ConnectRequest(usernameSize, username);
  }

  /**
   * Build Connect Response
   * @param info [id, success, msgSize, msg]
   * @return ConnectResponse
   * @throws WrongMessageException If the information in the string are wrong or missing any content, raise exception.
   */
  private static ConnectResponse buildConnectResponse(String[] info) throws WrongMessageException{
    String KEYWORD = "CONNECT_RESPONSE";
    // get message
    String message = String.join(SPLIT, Arrays.copyOfRange(info, 3, info.length));
    boolean success = Boolean.parseBoolean(info[1]);
    int size = Integer.parseInt(info[2]);
    incorrectSize(size, message.length(), KEYWORD);
    byte[] msg = message.getBytes();
    return new ConnectResponse(success, size, msg);
  }

  /**
   * Build Connect Response
   * @param info [id, size, username]
   * @return DisconnectRequest
   * @throws WrongMessageException If the information in the string are wrong or missing any content, raise exception.
   */
  private static DisconnectRequest buildDisconnectRequest(String[] info) throws WrongMessageException{
    String KEYWORD = "DISCONNECT_MESSAGE";
    incorrectSize(info.length, DisconnectRequest.getParamNumber(), KEYWORD);
    int size = Integer.parseInt(info[1]);
    incorrectSize(size, info[2].length(), KEYWORD);
    byte[] username = info[2].getBytes();
    return new DisconnectRequest(size, username);
  }

  /**
   *
   * @param message "id size message"
   * @return DisconnectResponse
   * @throws WrongMessageException If the information in the string are wrong or missing any content, raise exception.
   */
  public static DisconnectResponse buildDisconnectResponse(String message) throws WrongMessageException{
    String[] info = message.split(SPLIT);
    String KEYWORD = "DISCONNECT_RESPONSE";
    String jointMsg = String.join(SPLIT, Arrays.copyOfRange(info, 2, info.length));
    int size = Integer.parseInt(info[1]);
    incorrectSize(size, jointMsg.length(), KEYWORD);
    byte[] msg = jointMsg.getBytes();
    return new DisconnectResponse(size, msg);
  }

  /**
   * [id, size, username]
   * @param info [id, size, username]
   * @return QueryUserRequest
   * @throws WrongMessageException If the information in the string are wrong or missing any content, raise exception.
   */
  private static QueryUserRequest buildQueryRequest(String[] info) throws WrongMessageException{
    String KEYWORD = "QUERY_CONNECTED_USERS";
    incorrectSize(info.length, QueryUserRequest.getParamNumber(), KEYWORD);
    int size = Integer.parseInt(info[1]);
    incorrectSize(size, info[2].length(), KEYWORD);
    byte[] username = info[2].getBytes();
    return new QueryUserRequest(size, username);
  }

  /**
   * if numOfUsers is 0, it means the request user is the only one who is in the chatroom.
   * info size = 2 in this case
   * @param info [id, numOfUsers, usernameSize1, username1 .... usernameSizeX, usernameX]
   * @return QueryUserResponse or FailedMessage if there is no other user
   * @throws WrongMessageException If the information in the string are wrong or missing any content, raise exception.
   */
  private static ChatContent buildQueryResponse(String[] info) throws WrongMessageException{
    String KEYWORD = "QUERY_USER_RESPONSE";
    int userNameSize = Integer.parseInt(info[1]);
    if (info.length % 2 == 1) {
      throw new WrongMessageException(String.format(WRONG_PARAM, KEYWORD));
    }
    // if no other users
    if (info.length == 2){
      String MSG = "There is no other users in the chat room.";
      MSG = String.format(FAILED_MESSAGE_FORMAT, MessageBuilder.getFailedMessage(), MSG.length(), MSG);
      return MessageBuilder.buildMessage(MSG);
    }
    List<Integer> sizes = new ArrayList<>();
    List<byte[]> names = new ArrayList<>();
    for (int i=2; i<info.length; i+=2){
      int size = Integer.parseInt(info[i]);
      byte[] name = info[i+1].getBytes();
      incorrectSize(size, name.length, KEYWORD);
      sizes.add(size);
      names.add(name);
    }
    return new QueryUserResponse(userNameSize, names, sizes);
  }

  /**
   *
   * @param info [id, sender username size, sender username, message size, msg]
   * @return BroadcastMessage
   */
  private static BroadcastMessage buildBroadcastMessage(String[] info){
    int senderSize = Integer.parseInt(info[1]);
    byte[] senderName = info[2].getBytes();
    int msgSize = Integer.parseInt(info[3]);
    String message = String.join(SPLIT, Arrays.copyOfRange(info, 4, info.length));
    byte[] msg = message.getBytes();
    return new BroadcastMessage(senderSize, msgSize, senderName, msg);
  }

  /**
   *
   * @param info [id, sender Name size, sender name, recipient username size, recipient username, msgSize, msg]
   * @return DirectMessage
   */
  private static DirectMessage buildDirectMessage(String[] info){
    int senderSize = Integer.parseInt(info[1]);
    byte[] senderName = info[2].getBytes();
    int recipientSize = Integer.parseInt(info[3]);
    byte[] recipientName = info[4].getBytes();
    int msgSize = Integer.parseInt(info[5]);
    String message = String.join(SPLIT, Arrays.copyOfRange(info, 6, info.length));
    byte[] msg = message.getBytes();
    return new DirectMessage(senderSize, recipientSize, msgSize, senderName, recipientName, msg);
  }

  /**
   *
   * @param info [id, msgSize, msg]
   * @return FailedMessage
   * @throws WrongMessageException If the information in the string are wrong or missing any content, raise exception.
   */
  private static FailedMessage buildFailedMessage(String[] info) throws WrongMessageException{
    String KEYWORD = "FAILED_MESSAGE";
    int size = Integer.parseInt(info[1]);
    String message = String.join(SPLIT, Arrays.copyOfRange(info, 2, info.length));
    incorrectSize(size, message.length(), KEYWORD);
    byte[] username = message.getBytes();
    return new FailedMessage(size, username);
  }

  /**
   *
   * @param info [id, sender username size, sender username, recipient username size, recipient username]
   * @return InsultMessage
   * @throws WrongMessageException If the information in the string are wrong or missing any content, raise exception.
   */
  private static InsultRequest buildInsultMessage(String[] info) throws WrongMessageException{
    String KEYWORD = "SEND_INSULT";
    incorrectSize(info.length, InsultRequest.getParamNumber(), KEYWORD);
    int senderSize = Integer.parseInt(info[1]);
    byte[] senderName = info[2].getBytes();
    int recipientSize = Integer.parseInt(info[3]);
    byte[] recipientName = info[4].getBytes();
    return new InsultRequest(senderSize, recipientSize, senderName, recipientName);
  }


  private static void incorrectSize(int actual, int expect, String keyword) throws WrongMessageException {
    if (expect != actual){
      throw new WrongMessageException(String.format(WRONG_PARAM, keyword));
    }
  }

  public static int getConnectMessage() {
    return CONNECT_MESSAGE;
  }

  public static int getConnectResponse() {
    return CONNECT_RESPONSE;
  }

  public static int getDisconnectMessage() {
    return DISCONNECT_MESSAGE;
  }

  public static int getQueryConnectedUsers() {
    return QUERY_CONNECTED_USERS;
  }

  public static int getQueryUserResponse() {
    return QUERY_USER_RESPONSE;
  }

  public static int getBroadcastMessage() {
    return BROADCAST_MESSAGE;
  }

  public static int getDirectMessage() {
    return DIRECT_MESSAGE;
  }

  public static int getFailedMessage() {
    return FAILED_MESSAGE;
  }

  public static int getSendInsult() {
    return SEND_INSULT;
  }

  public static String getRequestFormat() {
    return CONNECT_REQUEST_FORMAT;
  }

  public static String getConnectResponseFormat() {
    return CONNECT_RESPONSE_FORMAT;
  }


  public static String getBroadcastMessageFormat() {
    return BROADCAST_MESSAGE_FORMAT;
  }

  public static String getDirectMessageFormat() {
    return DIRECT_MESSAGE_FORMAT;
  }


  public static String getSendInsultFormat() {
    return SEND_INSULT_FORMAT;
  }

  public static String getDisconnectResponseFormat() {
    return DISCONNECT_RESPONSE_FORMAT;
  }

  public static String getSPLIT() {
    return SPLIT;
  }
}
