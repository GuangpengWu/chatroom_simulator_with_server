import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConnectResponseTest {

  private ConnectResponse connectResponseTest;
  private byte[] msg;

  @Before
  public void setUp() throws Exception {
    this.connectResponseTest = (ConnectResponse) MessageBuilder.buildMessage("20 true 4 ahha");
  }

  @Test
  public void testIsSuccessful(){
    Assert.assertEquals(true, connectResponseTest.isSuccessful());
  }

  @Test
  public void testGetMsg(){
    msg = connectResponseTest.getMessage();
    Assert.assertEquals("ahha", new String(msg));
  }

  @Test
  public void testGetMsgSize(){
    Assert.assertEquals(4, connectResponseTest.getMessageSize());
  }

  @Test
  public void testGetParamNum(){
    Assert.assertEquals(4, connectResponseTest.getParamNumber());
  }
}
