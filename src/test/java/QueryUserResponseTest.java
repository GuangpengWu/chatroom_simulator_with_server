import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueryUserResponseTest {

  private QueryUserResponse queryUserResponseTest;
  private QueryUserResponse queryUserResponseTest_2;
  private ChatContent queryUserResponseTest_3;
  private List<String> testList;
  private List<Integer> testList2;

  @Before
  public void setUp() throws WrongMessageException {
    this.queryUserResponseTest = (QueryUserResponse) MessageBuilder.buildMessage("23 2 3 aha 2 ah");
  }

  // QueryUserResponse Part
  @Test
  public void testGetNumUsers() {
    Assert.assertEquals(2,queryUserResponseTest.getNumUsers());
  }

  @Test
  public void testGetUserNames(){
    testList = new ArrayList<>();
    for (byte[] name : queryUserResponseTest.getUsernames()){
      testList.add(new String(name));
    }
    Assert.assertEquals("[aha, ah]", testList.toString());
  }

  @Test
  public void testGetUserNameSizes() {
    testList2 = new ArrayList<>();
    for (Integer size : queryUserResponseTest.getUsernameSizes()){
      testList2.add(size);
    }
    Assert.assertEquals("[3, 2]", testList2.toString());
  }

  @Test
  public void testGetParamNum(){
    Assert.assertEquals(6, queryUserResponseTest.getParamNumber());
  }

  // ChatContent Part
  @Test
  public void testGetId(){
    Assert.assertEquals(23, queryUserResponseTest.getId());
  }

  @Test
  public void testToString(){
    Assert.assertEquals("Message{id=23}", queryUserResponseTest.toString());
  }

  // Exception
  @Test (expected = WrongMessageException.class)
  public void testException() throws WrongMessageException {
    queryUserResponseTest_2 = (QueryUserResponse) MessageBuilder.buildMessage("23 2 3 2 ah");
  }

  // No other users
  @Test
  public void testNoOther() throws WrongMessageException {
    queryUserResponseTest_3 = MessageBuilder.buildMessage("23 0");
    Assert.assertEquals(26, queryUserResponseTest_3.getId());
  }
}
