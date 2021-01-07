public class InsultRequest extends Request {
  private int senderNameSize;
  private byte[] senderName;
  private int receiverNameSize;
  private byte[] receiverName;

  public InsultRequest(int senderSize, int receiverSize, byte[] senderName, byte[] receiverName) {
    super(MessageBuilder.getSendInsult());
    this.receiverName = receiverName;
    this.receiverNameSize = receiverSize;
    this.senderName = senderName;
    this.senderNameSize = senderSize;
  }

  public int getSenderNameSize() {
    return senderNameSize;
  }

  public byte[] getSenderName() {
    return senderName;
  }

  public int getReceiverNameSize() {
    return receiverNameSize;
  }

  public byte[] getReceiverName() {
    return receiverName;
  }

  public static int getParamNumber() {
    return 5;
  }

}
