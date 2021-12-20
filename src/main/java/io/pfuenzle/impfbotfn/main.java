package io.pfuenzle.impfbotfn;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import com.microsoft.playwright.ElementHandle;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class main {
    public static final String IMPFURL = "https://bodenseekreis-impfzentrum.connect.giria.io/";


    private static final String pushbulletApiToken = "";
    public static int sleepTime = 60000;     //time to wait between checks in MS
    public static int months = 3;           //How many months (including the current) should be checked for appointments

    static String xPathButtonCalendarLeft = "//*[@id=\"IhrTermin1\"]/div/div/div[1]/div/div/div[1]/span[1]";
    static String xPathButtonCalendarRight = "//*[@id=\"IhrTermin1\"]/div/div/div[1]/div/div/div[1]/span[2]";
    static String xPathCalendarEntry = "//span[@class='flatpickr-day ']";
    static String xPathMonthDropdown = "//*[@id=\"IhrTermin1\"]/div/div/div[1]/div/div/div[1]/div/div/select";
    static String xPathCalendar = "//*[@id=\"IhrTermin1\"]/div/div/div[1]/div/div";

    static String[] oldAppointments = {"", "" ,""};

    public static void main(String[] args) {
        Browser browser;

        try (Playwright playwright = Playwright.create()) {
            browser = playwright.chromium().launch();
            System.out.println("Created Browser");

            while (true) {
                Page mainpage = browser.newPage();
                mainpage.navigate(IMPFURL, new Page.NavigateOptions()
                        .setWaitUntil(WaitUntilState.NETWORKIDLE)); //Wait until site is fully loaded
                System.out.println("Site fully loaded");

                for(int i = 0; i < months; i++){
                    System.out.println("Current month: " + i);
                    int numTermine = mainpage.querySelectorAll(xPathCalendarEntry).size();
                    List<ElementHandle> appointmentList = mainpage.querySelectorAll(xPathCalendarEntry);
                    if(numTermine > 0) {    //Appointments are available
                        System.out.println(numTermine + " appointments found");
                        String appointmentDates = "";
                        for(ElementHandle handle : appointmentList){
                            appointmentDates += handle.innerHTML();
                            appointmentDates += ", ";
                        }
                        if(oldAppointments[i].equals(appointmentDates) == false) {
                            System.out.println("Found appointments: " + appointmentDates);
                            System.out.println("Found appointments are new, sending to Pushbullet...");
                            foundTermin(i, appointmentDates, mainpage); //Found appointments are new, sending to Pushbullet
                        }else
                            System.out.println("Appointmets have already been sent, skipping");
                        oldAppointments[i] = appointmentDates;

                        mainpage.click(xPathButtonCalendarRight);
                    }
                    else
                        System.out.println("No appointments found");
                    Thread.sleep(2000);
                }
                System.out.println("Found all appointments, sleeping...");
                try {
                    Thread.sleep(sleepTime);     //Wait for set time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            System.out.println("Exception: ");
            e.printStackTrace();
        }
    }

    public static void foundTermin(int month, String appointmentDates, Page mainpage){
        long unixTime = System.currentTimeMillis() / 1000L; //Get Unix time
        String tempFolder = "tempImages";
        File directory = new File(tempFolder);
        if (! directory.exists()){
            directory.mkdir();
        }
        String screenshotFilename = tempFolder + "/" + String.valueOf(unixTime) + ".png";  //Create temporary filename from current time
        ElementHandle calendar = mainpage.querySelectorAll(xPathCalendar).get(0);
        calendar.screenshot(new ElementHandle.ScreenshotOptions().setPath(Paths.get(screenshotFilename)));      //Get screenshot of calendar element
        PushbulletHelper pushbullet = new PushbulletHelper(pushbulletApiToken);
        pushbullet.sendImagePush("New appointments in " + month + " months", "Available on " + appointmentDates + "\n" + IMPFURL, new File(screenshotFilename));
    }
}