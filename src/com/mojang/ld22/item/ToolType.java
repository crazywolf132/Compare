// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.item;

public class ToolType
{
    public static ToolType hammer;
    public static ToolType shovel;
    public static ToolType hoe;
    public static ToolType sword;
    public static ToolType pickaxe;
    public static ToolType axe;
    public static ToolType bucket;
    public static ToolType flintnsteel;
    public static ToolType bow;
    public final String name;
    public final int sprite;
    
    static {
        ToolType.hammer = new ToolType("Hammer", 0);
        ToolType.shovel = new ToolType("Shvl", 1);
        ToolType.hoe = new ToolType("Hoe", 2);
        ToolType.sword = new ToolType("Swrd", 3);
        ToolType.pickaxe = new ToolType("Pick", 4);
        ToolType.axe = new ToolType("Axe", 5);
        ToolType.bucket = new ToolType("Bucket", 6);
        ToolType.flintnsteel = new ToolType("FlintNSteel", 7);
        ToolType.bow = new ToolType("Bow", 8);
    }
    
    private ToolType(final String name, final int sprite) {
        this.name = name;
        this.sprite = sprite;
    }
}
