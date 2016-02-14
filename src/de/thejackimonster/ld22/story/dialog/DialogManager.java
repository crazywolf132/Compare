// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.story.dialog;

import com.mojang.ld22.item.ToolItem;
import com.mojang.ld22.item.ToolType;
import java.util.Random;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.screen.Menu;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.item.resource.Resource;
import de.thejackimonster.ld22.modloader.ModLoader;

public class DialogManager
{
    public static DialogMenu karl_01;
    public static DialogMenu karl_02;
    public static DialogMenu karl_03;
    public static DialogMenu karl_04;
    public static DialogMenu karl_alternative;
    public static DialogMenu hans_01;
    public static DialogMenu hans_02;
    public static DialogMenu dieter_01;
    public static DialogMenu dieter_02;
    public static DialogMenu dieter_03;
    public static DialogMenu dieter_04;
    public static DialogMenu dieter_05;
    public static DialogMenu frank_01;
    public static DialogMenu frank_02;
    public static DialogMenu detlef_01;
    public static DialogMenu detlef_02;
    public static DialogMenu detlef_03;
    
    static {
        DialogManager.karl_01 = new DialogMenu("I'm Karl.    Who are you?", new String[] { "I don't know?!", "You!", "Notch?!" });
        DialogManager.karl_02 = new DialogMenu("Oh,          I'm sorry!", new String[] { "Bye!" });
        DialogManager.karl_03 = new DialogMenu("What the..   You lie!", new String[] { "Bye!" });
        DialogManager.karl_04 = new DialogMenu("Oh my god,   this is..    Here, I hope,that this    sword can    help you!", new String[] { "Thanks, bye!" });
        DialogManager.karl_alternative = new DialogMenu("Hello,       Come later   again!", new String[] { "Bye!" });
        DialogManager.hans_01 = new DialogMenu("I'm Hans.    Do you       want sell    sand for     dirt,        because I    have so much dirt!", new String[] { "Yes [1 Dirt]", "Yeah [10 Dirt]", "No!" });
        DialogManager.hans_02 = new DialogMenu("You haven't  enough sand! Come later   again!", new String[] { "Bye" });
        DialogManager.dieter_01 = new DialogMenu("What are you doing here?", new String[] { "I'm stranded.", "I fell down from the sky.", "I don't know?!" });
        DialogManager.dieter_02 = new DialogMenu("Then you mustcraft a boat or you must  build a      house,       because at   night come   very very..  dangerous    monsters!", new String[] { "How I can get wood?", "What kind of monsters?", "Okay, bye!" });
        DialogManager.dieter_03 = new DialogMenu("Ha! Ha!      Good joke!   That's       impossible..", new String[] { "Bye!" });
        DialogManager.dieter_04 = new DialogMenu("An axe,      when you haveone of them, otherwise    with your    fists!", new String[] { "Okay?!" });
        DialogManager.dieter_05 = new DialogMenu("They come outin the       darkness and some of them explode!     Be carefull!", new String[] { "Okay?!" });
        DialogManager.frank_01 = new DialogMenu("Zzzzzz...    (He's        sleeping..)", new String[] { "Hello?!", "Wake up!", "What ever!?." });
        DialogManager.frank_02 = new DialogMenu("What? ..yawn What is it?! I'm tired!", new String[] { "Can you help me?", "Sorry!" });
        DialogManager.detlef_01 = new DialogMenu("Do you need  a guard?     I can protectyou for      3 coal!", new String[] { "Yes [3 coal]", "No" });
        DialogManager.detlef_02 = new DialogMenu("You haven't  enough coal!", new String[] { "Bye" });
        DialogManager.detlef_03 = new DialogMenu("What?", new String[] { "Wait here!", "Come with me!" });
    }
    
    public static void say(final String dialog, final int answer) {
        if (dialog == DialogManager.detlef_01.dialog) {
            if (answer == 0) {
                if (ModLoader.getGameInstance().player.inventory.count(new ResourceItem(Resource.coal)) >= 3) {
                    ModLoader.getGameInstance().player.inventory.removeResource(Resource.coal, 3);
                    NPCManager.Detlef.follow(ModLoader.getGameInstance().player, true);
                    NPCManager.Detlef.dialog = DialogManager.detlef_03;
                    ModLoader.getGameInstance().setMenu(null);
                }
                else {
                    ModLoader.getGameInstance().setMenu(DialogManager.detlef_02);
                }
            }
            if (answer == 1) {
                ModLoader.getGameInstance().setMenu(null);
            }
        }
        if (dialog == DialogManager.detlef_02.dialog && answer == 0) {
            ModLoader.getGameInstance().setMenu(null);
        }
        if (dialog == DialogManager.detlef_03.dialog) {
            if (answer == 0) {
                NPCManager.Detlef.canWalk = false;
                NPCManager.Detlef.follow(null, false);
                ModLoader.getGameInstance().setMenu(null);
            }
            if (answer == 1) {
                NPCManager.Detlef.canWalk = true;
                NPCManager.Detlef.follow(ModLoader.getGameInstance().player, true);
                ModLoader.getGameInstance().setMenu(null);
            }
        }
        if (dialog == DialogManager.frank_01.dialog) {
            if (answer == 0) {
                ModLoader.getGameInstance().setMenu(DialogManager.frank_01);
            }
            if (answer == 1) {
                if (new Random().nextInt(3) == 0) {
                    ModLoader.getGameInstance().setMenu(DialogManager.frank_02);
                }
                else {
                    ModLoader.getGameInstance().setMenu(DialogManager.frank_01);
                }
            }
            if (answer == 2) {
                ModLoader.getGameInstance().setMenu(null);
            }
        }
        if (dialog == DialogManager.frank_02.dialog) {
            if (answer == 0) {
                ModLoader.getGameInstance().setMenu(DialogManager.frank_01);
            }
            if (answer == 1) {
                ModLoader.getGameInstance().setMenu(null);
            }
        }
        if (dialog == DialogManager.dieter_01.dialog) {
            if (answer == 0) {
                ModLoader.getGameInstance().setMenu(DialogManager.dieter_02);
            }
            if (answer == 1) {
                ModLoader.getGameInstance().setMenu(DialogManager.dieter_03);
            }
            if (answer == 2) {
                ModLoader.getGameInstance().setMenu(null);
            }
        }
        if (dialog == DialogManager.dieter_02.dialog) {
            if (answer == 0) {
                ModLoader.getGameInstance().setMenu(DialogManager.dieter_04);
            }
            if (answer == 1) {
                ModLoader.getGameInstance().setMenu(DialogManager.dieter_05);
            }
        }
        if ((dialog == DialogManager.dieter_02.dialog && answer == 2) || (dialog == DialogManager.dieter_03.dialog && answer == 0) || (dialog == DialogManager.dieter_04.dialog && answer == 0) || (dialog == DialogManager.dieter_05.dialog && answer == 0)) {
            ModLoader.getGameInstance().setMenu(null);
        }
        if (dialog == DialogManager.hans_01.dialog) {
            if (answer == 2) {
                ModLoader.getGameInstance().setMenu(null);
            }
            else {
                int x = 1;
                if (answer == 0) {
                    x = 1;
                }
                if (answer == 1) {
                    x = 10;
                }
                for (int i = 0; i < x; ++i) {
                    if (ModLoader.getGameInstance().player.inventory.count(new ResourceItem(Resource.sand)) > 0) {
                        ModLoader.getGameInstance().player.inventory.removeResource(Resource.sand, 1);
                        ModLoader.getGameInstance().player.inventory.add(new ResourceItem(Resource.dirt));
                    }
                    else {
                        x = i + 1;
                        ModLoader.getGameInstance().setMenu(DialogManager.hans_02);
                    }
                }
            }
        }
        if (dialog == DialogManager.hans_02.dialog && answer == 0) {
            ModLoader.getGameInstance().setMenu(null);
        }
        if (dialog == DialogManager.karl_01.dialog) {
            if (answer == 0) {
                ModLoader.getGameInstance().setMenu(DialogManager.karl_02);
            }
            if (answer == 1) {
                ModLoader.getGameInstance().setMenu(DialogManager.karl_03);
            }
            if (answer == 2) {
                ModLoader.getGameInstance().setMenu(DialogManager.karl_04);
            }
            NPCManager.Karl.dialog = DialogManager.karl_alternative;
        }
        if ((dialog == DialogManager.karl_02.dialog || dialog == DialogManager.karl_03.dialog || dialog == DialogManager.karl_alternative.dialog) && answer == 0) {
            ModLoader.getGameInstance().setMenu(null);
        }
        if (dialog == DialogManager.karl_04.dialog && answer == 0) {
            ModLoader.getGameInstance().player.inventory.add(new ToolItem(ToolType.sword, 2));
            ModLoader.getGameInstance().setMenu(null);
        }
    }
}
