import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DisconnectResponseTest {

  private ConnectResponse connectResponseTest;
  private DisconnectResponse disConnectResponseTest;
  private byte[] msg;

  @Before
  public void setUp() throws Exception {
    this.connectResponseTest = (ConnectResponse) MessageBuilder.buildMessage("20 false 4 ahha");
    msg = connectResponseTest.getMessage();
    this.disConnectResponseTest = new DisconnectResponse(2, msg);
  }

  @Test
  public void testGetParamNum(){
    Assert.assertEquals(3, disConnectResponseTest.getParamNumber());
  }
}
