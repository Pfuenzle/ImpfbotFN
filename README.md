# ImpfbotFN
Checks for new available covid vaccination appointments at the Messe Friedrichshafen by parsing the [official website](https://bodenseekreis-impfzentrum.connect.giria.io/).
Found appointments are then printed to the console and sent to a configured Pushbullet account, together with a link to sign up and a screenshot of the available dates.

![Pushbullet example](https://i.ibb.co/r0RfwNy/vaccine.png)

## Requirements:
- Java
- Maven
- IntelliJ IDEA
- A Pushbullet account

## Instructions:
  Compile project by importing it in IntelliJ IDEA and pressing `Maven -> Lifecycle -> compile` on the right side
  
  Now, open "src/main/java/io/pfuenzle/impfbotfn/main.java" and change lines 15 to 17 according to your needs:
  ```
    private static final String pushbulletApiToken = "";
    public static int sleepTime = 60000;
    public static int months = 3;
  ```
  In the first line, put your [Pushbullet Access Token](https://www.pushbullet.com/#settings) in the empty brackets.
  
  The second line is the time the bot is waiting between each checks (in miliseconds).
  
  The third line is the number of months going forward (including the current one) that are checked by the bots.
  
  </br>
  After this, the programm can be started by running the main configuration in the top right corner or by clicking the Run-Button appearing in line 27 while viewing main.
  
  </br>
  ## Used Libraries
  [JPushbullet](https://github.com/salahsheikh/jpushbullet) to access the Pushbullet API
  [Playwright](https://playwright.dev/) to access the appointment website and parse available appointments
