import static org.junit.Assert.*;

import java.util.Collections;
import a4.NonTerminalNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ServersideTest {

    private ChatroomServer mockServer;
    private Client mockServerClient1;
    private Client mockServerClient2;
    private Client mockServerClient3;
    private ChatroomClient mockClient1;
    private ChatroomClient mockClient2;

    @Before
    public void setUp(){
        mockServerClient1 = mock(Client.class);
        mockServerClient1.setUsername("aa");
        mockServerClient2 = mock(Client.class);
        mockServerClient2.setUsername("aa");
        mockServerClient3 = mock(Client.class);
        mockServerClient3.setUsername("bb");
    }

    @Test
    public void testConnectionRequestSuccess() throws WrongMessageException, NonTerminalNotFoundException, InterruptedException {
        mockServer = spy(new ChatroomServer(8989));

        int requestId = MessageBuilder.getConnectMessage();
        String username = "aa";
        String MESSAGE = String.format(MessageBuilder.getRequestFormat(), requestId, username.length(), username);
        ConnectRequest request = (ConnectRequest) MessageBuilder.buildMessage(MESSAGE);
        mockServer.sendRequest(request, mockServerClient1);
        assertEquals(Collections.singletonList("aa"), mockServer.getUserNames());

    }

    @Test
    public void testConnectionRequestFailed() throws WrongMessageException, NonTerminalNotFoundException, InterruptedException {
        mockServer = spy(new ChatroomServer(8990));

        int requestId = MessageBuilder.getConnectMessage();
        String username = "aa";
        String MESSAGE = String.format(MessageBuilder.getRequestFormat(), requestId, username.length(), username);
        ConnectRequest request = (ConnectRequest) MessageBuilder.buildMessage(MESSAGE);
        mockServer.sendRequest(request, mockServerClient1);
        mockServer.sendRequest(request, mockServerClient2);
        assertEquals(Collections.singletonList("aa"), mockServer.getUserNames());
    }

    @Test
    public void testUniqueName() throws InterruptedException, NonTerminalNotFoundException, WrongMessageException {
        mockServer = spy(new ChatroomServer(8991));
        int requestId = MessageBuilder.getConnectMessage();
        String username = "aa";
        String MESSAGE = String.format(MessageBuilder.getRequestFormat(), requestId, username.length(), username);
        ConnectRequest request = (ConnectRequest) MessageBuilder.buildMessage(MESSAGE);
        mockServer.sendRequest(request, mockServerClient1);
        assertFalse(mockServer.isUnique("aa"));
    }

//    @Test
//    public void testDirectMessage() {
//
//        try {
//            new Thread(() -> {
//                try {
//                    mockServer = new ChatroomServer(8992);
//                    Thread server = new Thread(mockServer);
//                    server.start();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }).start();
//
//            new Thread(() -> {
//                mockClient1 = new ChatroomClient("localhost", 8992);
//                Thread client1 = new Thread(mockClient1);
//                client1.start();
//
//                System.out.println(mockClient1.getUserName());
//
//            }).start();
//
//            new Thread(() -> {
//                mockClient2 = new ChatroomClient("localhost", 8992);
//                Thread client2 = new Thread(mockClient2);
//                client2.start();
//                System.out.println(System.console());
//
//            }).start();
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//
//    }
}
