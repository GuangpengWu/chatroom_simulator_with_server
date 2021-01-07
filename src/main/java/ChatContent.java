import java.io.Serializable;

public abstract class ChatContent implements Serializable {
  private int id;

  public ChatContent(int id){
    this.id = id;
  }

  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Message{" +
        "id=" + id +
        '}';
  }
}
