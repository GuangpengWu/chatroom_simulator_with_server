# Group_Jiawen_Guangpeng - Assignment6

# Assumption Before Application

* Client will not send to specific client if there is no other client in the chatroom.
* use "logoff" to end chatroom clients first
* if all users logooff, can use "ctrl + c" to shut down server

# Test NOT Fully Covered
We tried to mock a process for client interaction, however not received a good solution. Thus, only covered 0.3 of the project.

# Other Notice:

* input grammar file & a4.jar is included.

# High Level Discription
* Overall Project Structure:
[![rujzb4.jpg](https://s3.ax1x.com/2020/12/15/rujzb4.jpg)](https://imgchr.com/i/rujzb4)
  * Server should be start first, and waiting to listen from client to join.
  * When a client start, it could send request to server.
  * Server received the request and process them, then send response back to the terminal.
  * Use Helper: CommandManager and MessageBuilder to manage I/O stream format.

* Overall Class Structure:
[![ruvpVJ.jpg](https://s3.ax1x.com/2020/12/15/ruvpVJ.jpg)](https://imgchr.com/i/ruvpVJ)

# Operation Instruction:
* Basically, we use 'java -jar [jar-path] [sever/client] [port] [hostname if client]' to run application.
# Operation Example:
* Use Terminal cd the pwd to the Assignment6 File, which jar should have the same cwd with input.
* Then, start a server using command line like below.

[![ruvXFA.png](https://s3.ax1x.com/2020/12/15/ruvXFA.png)](https://imgchr.com/i/ruvXFA)

* Then, start a new terminal window to add a new client - aaa.
* And Start another terminal window to add another client - bbb.

[![ruxlTJ.png](https://s3.ax1x.com/2020/12/15/ruxlTJ.png)](https://imgchr.com/i/ruxlTJ)

* Then, send different requests to get response

[![rux8YR.png](https://s3.ax1x.com/2020/12/15/rux8YR.png)](https://imgchr.com/i/rux8YR)

* logoff to quit chatroom

[![ruxtl6.png](https://s3.ax1x.com/2020/12/15/ruxtl6.png)](https://imgchr.com/i/ruxtl6)

* If already have 10 client in the chatroom, then it will call wait to the new enter.

[![ruxg6f.png](https://s3.ax1x.com/2020/12/15/ruxg6f.png)](https://imgchr.com/i/ruxg6f)

* If a client in a chatroom log off, then there will be room for the waiting client to join in the chatroom.

[![rKSbeU.png](https://s3.ax1x.com/2020/12/15/rKSbeU.png)](https://imgchr.com/i/rKSbeU)

* Use "logoff" for client to quit chatroom.

