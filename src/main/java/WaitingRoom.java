import java.util.ArrayList;
import java.util.List;

public class WaitingRoom {
  private List<Client> waitRoom;
  private List<Request> connectionRequest;

  public WaitingRoom(){
    this.waitRoom = new ArrayList<>();
    this.connectionRequest = new ArrayList<>();
  }

  public void addClient(Client client, Request request){
    waitRoom.add(client);
    connectionRequest.add(request);
  }

  public void removeFromWaitRoom(){
    if (!waitRoom.isEmpty()){
      waitRoom.remove(0);
    }
  }

  public Client getFirstClient(){
    if (!waitRoom.isEmpty()){
      return waitRoom.get(0);
    }
    return null;
  }

  public Request getFirstClientRequest(){
    if (!connectionRequest.isEmpty()){
      return connectionRequest.get(0);
    }
    return null;
  }

}
