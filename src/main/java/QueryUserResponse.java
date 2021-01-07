import java.util.List;

public class QueryUserResponse extends ChatContent {
  private int numUsers;
  private List<byte[]> usernames;
  private List<Integer> usernameSizes;
  private static int PARAM_NUMBER;

  public QueryUserResponse(int num, List<byte[]> names, List<Integer> sizes) {
    super(MessageBuilder.getQueryUserResponse());
    this.numUsers = num;
    this.usernames = names;
    this.usernameSizes = sizes;
    QueryUserResponse.setParamNumber(names.size()*2 + 2);
  }

  public int getNumUsers() {
    return numUsers;
  }

  public List<byte[]> getUsernames() {
    return usernames;
  }

  public List<Integer> getUsernameSizes() {
    return usernameSizes;
  }

  public static int getParamNumber() {
    return PARAM_NUMBER;
  }

  private static void setParamNumber(int num) {
    PARAM_NUMBER = num;
  }
}
