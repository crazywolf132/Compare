// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.story.dialog;

import com.mojang.ld22.gfx.Color;
import java.util.ArrayList;
import java.util.List;

public class NPCManager
{
    public static List<NPC> npcList;
    public static NPC Karl;
    public static NPC Hans;
    public static NPC Dieter;
    public static NPC Frank;
    public static NPC Detlef;
    
    static {
        NPCManager.npcList = new ArrayList<NPC>();
        NPCManager.Karl = addNPC(new NPC("Karl", true, Color.get(-1, 100, 16, 532), DialogManager.karl_01));
        NPCManager.Hans = addNPC(new NPC("Hans", true, Color.get(-1, 100, 16, 532), DialogManager.hans_01));
        NPCManager.Dieter = addNPC(new NPC("Dieter", true, Color.get(-1, 100, 16, 532), DialogManager.dieter_01));
        NPCManager.Frank = addNPC(new NPC("Frank", false, Color.get(-1, 100, 16, 532), DialogManager.frank_01));
        NPCManager.Detlef = addNPC(new NPC("Detlef", true, 24, 14, Color.get(-1, 333, 444, 532), DialogManager.detlef_01));
    }
    
    public static NPC addNPC(final NPC npc) {
        NPCManager.npcList.add(npc);
        return npc;
    }
    
    public static void remove(final NPC npc) {
        if (NPCManager.npcList.contains(npc)) {
            NPCManager.npcList.remove(npc);
        }
    }
}
