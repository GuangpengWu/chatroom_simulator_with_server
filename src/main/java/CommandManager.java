import java.util.List;

public class CommandManager {
  private static final String LOGOFF="logoff";
  private static final String QUERY="who";
  private static final String MENU="?";
  private static final String BROADCAST="@all";
  private static final char MENTION='@';
  private static final char INSULT='!';
  private static final String MENU_MSG="logoff: Logoff from the chatroom.\nwho: Get all other usernames that are in the chatroom.\n"
      + "@user <message>: Send direct message. Replace user to the username that you want to send message directly to.\n" +
      "@all <message>: Broadcast your message.\n!user: Send random insult message. Replace 'user' to the username that you want to insult. " +
      "This message will broadcast to all users.";
  // since we want to use @all to broadcast, we don't want a user with all as name
  private static final String BAN_USERNAME="all";


  public static String getLOGOFF() {
    return LOGOFF;
  }

  public static String getQUERY() {
    return QUERY;
  }

  public static String getMENU() {
    return MENU;
  }

  public static String getBROADCAST() {
    return BROADCAST;
  }

  public static char getMENTION() {
    return MENTION;
  }

  public static char getINSULT() {
    return INSULT;
  }

  public static String getBanUsername() {
    return BAN_USERNAME;
  }

  public static String getMenuMsg() {
    return MENU_MSG;
  }

  public static boolean isNotValidUsername(String name){
    boolean valid = !name.equals(BAN_USERNAME);
    if (!valid){
      System.out.printf("Please choose a name other than %s\n", name);
    }
    return !valid;
  }

  public static boolean notLogOff(String message){
    return !message.equals(LOGOFF);
  }

  public static boolean isQuery(String message){
    return message.equals(QUERY);
  }

  public static boolean isMenu(String message){
    return message.equals(MENU);
  }

  /**
   * The message is considered to be a direct message if it starts with @ and follow a valid username.
   * @param message string that client entered in the console.
   * @param client server side client
   * @return true if message satisfy the above requirements.
   */
  public static boolean isDirect(String message, Client client){
    String[] messages = message.split(MessageBuilder.getSPLIT());
    boolean first = messages[0].charAt(0) == MENTION;
    // name = username after @. i.e. @user, name = user
    String name = messages[0].substring(1);
    List<String> allNames = client.getAllUsername();
    return first && allNames.contains(name);
  }

  public static boolean isInsult(String message, Client client){
    String[] messages = message.split(MessageBuilder.getSPLIT());
    boolean first = messages[0].charAt(0) == INSULT;
    // name = username after !. i.e. !user, name = user
    String name = messages[0].substring(1);
    List<String> allNames = client.getAllUsername();
    return first && allNames.contains(name);
  }

  /**
   * The message is considered to be a broadcast message if it starts with @all,
   * or it does not contain @all nor any other command.
   * @param message string that client entered in the console.
   * @param client the client who send the message
   * @return true if message satisfy the above requirements.
   */
  public static boolean isBroadcast(String message, Client client){
    String[] messages = message.split(MessageBuilder.getSPLIT());
    return messages[0].equals(BROADCAST) || (!isDirect(message, client) && !isInsult(message, client) && notLogOff(message) && !isMenu(message) && !isQuery(message));
  }

  /**
   * Assume message starts with one of @user, !user, extract username
   * @param message message is one of @user, !user
   * @return username
   */
  public static String extractName(String message){
    String[] info = message.split(MessageBuilder.getSPLIT());
    return info[0].substring(1);
  }

}
