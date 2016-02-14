// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound
{
    public static final Sound playerHurt;
    public static final Sound playerDeath;
    public static final Sound monsterHurt;
    public static final Sound test;
    public static final Sound pickup;
    public static final Sound bossdeath;
    public static final Sound craft;
    public static final Sound powerup;
    public static final Sound select;
    public static final Sound toogle;
    public static final Sound arrowshoot;
    public static final Sound special;
    public static final Sound tntzisch;
    public static final Sound lvlup;
    public static final Sound smallexplosion;
    public static final Sound jump;
    private AudioClip clip;
    
    static {
        playerHurt = new Sound("/xD/playerhurt.wav");
        playerDeath = new Sound("/xD/death.wav");
        monsterHurt = new Sound("/xD/monsterhurt.wav");
        test = new Sound("/xD/test.wav");
        pickup = new Sound("/xD/pickup.wav");
        bossdeath = new Sound("/xD/bossdeath.wav");
        craft = new Sound("/xD/craft.wav");
        powerup = new Sound("/xD/powerup.wav");
        select = new Sound("/xD/select.wav");
        toogle = new Sound("/xD/toogle.wav");
        arrowshoot = new Sound("/xD/arrow.wav");
        special = new Sound("/xD/special.wav");
        tntzisch = new Sound("/xD/zisch.wav");
        lvlup = new Sound("/xD/lvlup.wav");
        smallexplosion = new Sound("/xD/smlexp.wav");
        jump = new Sound("/xD/jump.wav");
    }
    
    private Sound(final String name) {
        try {
            this.clip = Applet.newAudioClip(Sound.class.getResource(name));
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    public void play() {
        try {
            new Thread() {
                @Override
                public void run() {
                    Sound.this.clip.play();
                }
            }.start();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
