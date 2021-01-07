public class ConnectRequest extends Request {
  private int usernameSize;
  private byte[] username;

  public ConnectRequest(int size, byte[] name){
    super(MessageBuilder.getConnectMessage());
    this.usernameSize = size;
    this.username = name;
  }

  public int getUsernameSize() {
    return usernameSize;
  }

  public byte[] getUsername() {
    return username;
  }

  public static int getParamNumber() {
    return 3;
  }
}
