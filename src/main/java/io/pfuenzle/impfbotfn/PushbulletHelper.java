package io.pfuenzle.impfbotfn;

import com.shakethat.jpushbullet.PushbulletClient;

import java.io.File;

public class PushbulletHelper {
    private PushbulletClient pushbullet;

    public PushbulletHelper(String pushbulletApiToken){
        this.pushbullet = new PushbulletClient(pushbulletApiToken);
    }

    public void sendTextPush(String title, String message){
        this.pushbullet.sendNote("", title, message);
    }

    public void sendImagePush(String title, String message, File file){
        this.sendTextPush(title, message);
        try {
            this.pushbullet.sendFile("", file);
        }catch (Exception e) {
            this.sendTextPush("Error", "A file message has failed to be sent: " + e.toString());
        }
    }
}
