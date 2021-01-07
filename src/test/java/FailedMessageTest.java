import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FailedMessageTest {

  private FailedMessage failedMessageTest;
  private byte[] msg;

  @Before
  public void setUp() throws Exception {
    this.failedMessageTest = (FailedMessage) MessageBuilder.buildMessage("26 4 ahha");
  }

  @Test
  public void testGetParamNum(){
    Assert.assertEquals(3, FailedMessage.getParamNumber());
  }
}
