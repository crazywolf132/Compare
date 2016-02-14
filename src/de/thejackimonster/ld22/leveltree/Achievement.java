// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.leveltree;

import com.mojang.ld22.screen.Menu;
import com.mojang.ld22.Game;
import com.mojang.ld22.gfx.Screen;
import java.util.ArrayList;
import com.mojang.ld22.item.Item;
import java.util.List;

public class Achievement
{
    public static List<Achievement> achievementList;
    public static Achievement newWorld;
    public static Achievement openInventory;
    public static Achievement crafting;
    public static Achievement wood;
    public static Achievement craftPickaxe;
    public static Achievement craftAxe;
    public static Achievement craftHoe;
    public static Achievement craftHammer;
    public static Achievement craftShovel;
    public static Achievement craftSword;
    public static Achievement creeperNoob;
    public final String title;
    public final String text;
    private final Achievement need;
    private final Item itemc;
    private boolean done;
    
    static {
        Achievement.achievementList = new ArrayList<Achievement>();
        Achievement.newWorld = new Achievement("New World", "You have created a   world in InfinityTale!", null, Item.apple);
        Achievement.openInventory = new Achievement("Open Inventory", "You have openedyour inventory!", Achievement.newWorld, Item.chest);
        Achievement.crafting = new Achievement("Workbench", "You have set   your workbench!", Achievement.openInventory, Item.workbench);
        Achievement.wood = new Achievement("In The Forest", "You have collectedyour first wood!  ", Achievement.newWorld, Item.wood);
        Achievement.craftPickaxe = new Achievement("Pickaxe", "You have crafted     your first pickaxe!  ", Achievement.crafting, Item.woodenPickaxe);
        Achievement.craftAxe = new Achievement("Axe", "You have craftedyour first axe! ", Achievement.crafting, Item.woodenAxe);
        Achievement.craftHoe = new Achievement("Hoe", "You have craftedyour first hoe! ", Achievement.crafting, Item.woodenHoe);
        Achievement.craftHammer = new Achievement("Hammer", "You have crafted your first hammer!", Achievement.crafting, Item.woodenHammer);
        Achievement.craftShovel = new Achievement("Shovel", "You have crafted your first shovel!", Achievement.crafting, Item.woodenShovel);
        Achievement.craftSword = new Achievement("Sword", "You have crafted your first sword!", Achievement.crafting, Item.woodenSword);
        Achievement.creeperNoob = new Achievement("Creeper!!!", "A creeper explored   with you to the death!", Achievement.newWorld, Item.tnt);
    }
    
    public Achievement(final String name, final String msg, final Achievement parent, final Item item) {
        this.title = name;
        this.text = msg;
        this.need = parent;
        this.itemc = item;
        this.done = false;
        Achievement.achievementList.add(this);
    }
    
    public void renderIcon(final Screen screen, final int x, final int y) {
        if (this.itemc != null) {
            this.itemc.renderIcon(screen, x, y);
        }
    }
    
    public boolean Done(final Game game) {
        final boolean flag = this.done;
        if (this.need != null) {
            if (this.need.Done(game)) {
                this.done = true;
            }
        }
        else {
            this.done = true;
        }
        if (this.done && !flag) {
            game.setMenu(new AchievementMenu(this));
        }
        return this.done;
    }
    
    public boolean isDone() {
        return this.done;
    }
}
