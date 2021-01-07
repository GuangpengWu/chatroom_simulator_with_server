import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommandManagerTest {
  private Client client1;

  @Test
  public void getLOGOFF() {
    Assert.assertEquals("logoff", CommandManager.getLOGOFF());
  }

  @Test
  public void getQUERY() {
    Assert.assertEquals("who", CommandManager.getQUERY());
  }

  @Test
  public void getMENU() {
    Assert.assertEquals("?",CommandManager.getMENU());
  }

  @Test
  public void getBROADCAST() {
    Assert.assertEquals("@all", CommandManager.getBROADCAST());
  }

  @Test
  public void getMENTION() {
    String a = "@";
    Assert.assertEquals(a.charAt(0), CommandManager.getMENTION());
  }

  @Test
  public void getINSULT() {
    String a = "!";
    Assert.assertEquals( a.charAt(0), CommandManager.getINSULT());
  }

  @Test
  public void getBanUsername() {
    Assert.assertEquals("all", CommandManager.getBanUsername());
  }

  @Test
  public void getMenuMsg() {
    Assert.assertEquals("logoff: Logoff from the chatroom.\n"
        + "who: Get all other usernames that are in the chatroom.\n"
        + "@user <message>: Send direct message. Replace user to the username that you want to send message directly to.\n"
        + "@all <message>: Broadcast your message.\n"
        + "!user: Send random insult message. Replace 'user' to the username that you want to insult. This message will broadcast to all users.", CommandManager.getMenuMsg());
  }

  @Test
  public void isNotValidUsername() {
    Assert.assertEquals(true, CommandManager.isNotValidUsername("all"));
    Assert.assertEquals(false, CommandManager.isNotValidUsername("aa"));
  }

  @Test
  public void notLogOff() {
    Assert.assertEquals(false, CommandManager.notLogOff("logoff"));
    Assert.assertEquals(true, CommandManager.notLogOff("logooff"));
  }

  @Test
  public void isQuery() {
    Assert.assertEquals(true, CommandManager.isQuery("who"));
  }

  @Test
  public void isMenu() {
    Assert.assertEquals(false, CommandManager.isMenu("notMenu"));
  }

  @Test
  public void extractName() {
    Assert.assertEquals("user", CommandManager.extractName("@user"));
  }
}
