public class QueryUserRequest extends Request {
  private int size;
  private byte[] username;

  public QueryUserRequest(int size, byte[] name) {
    super(MessageBuilder.getQueryConnectedUsers());
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
