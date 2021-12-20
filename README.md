# ImpfbotFN
Checks for new available covid vaccination appointments at the Messe Friedrichshafen by parsing the [official website](https://bodenseekreis-impfzentrum.connect.giria.io/).
Found appointments are then print to console and sent to a configured Pushbullet account, together with a link to sign up and a screenshot of the available dates.

![Pushbullet example](https://i.ibb.co/r0RfwNy/vaccine.png)

## Requirements:
- Java
- Maven
- IntelliJ IDEA
- A Pushbullet account

## Instructions:
Either:
  - Compile project by importing it in IntelliJ IDEA and pressing "Maven -> Lifecycle -> compile" on the right side
 Or:
  - run "mvn compile exec:java -Dexec.mainClass="io.pfuenzle.impfbotfn.main"
  
  Now, open "src/main/java/io/pfuenzle/impfbotfn/main.java" and change lines 15 to 17 according to your needs:
  ```
    private static final String pushbulletApiToken = "";
    public static int sleepTime = 60000;
    public static int months = 3;
  ```
  In the first line, put your (Pushbullet Access Token)[https://www.pushbullet.com/#settings] in the empty brackets.
  
  The second line is the time that the bot is waiting between each checks.
  
  The third line is the number of months going forward (including the current one) that are checked by the bots.
  
  </br>
  After this, the programm can be started by running the main configuration or by executing "mvn compile exec:java -Dexec.mainClass="io.pfuenzle.impfbotfn.main"