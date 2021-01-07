public class DisconnectRequest extends Request {
  private int size;
  private byte[] username;

  public DisconnectRequest(int size, byte[] name) {
    super(MessageBuilder.getDisconnectMessage());
    this.size = size;
    this.username = name;
  }

  public int getSize() {
    return size;
  }

  public byte[] getUsername() {
    return username;
  }

  public static int getParamNumber() {
    return 3;
  }
}
