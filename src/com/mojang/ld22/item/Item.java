// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.item;

import com.mojang.ld22.level.Level;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.entity.Workbench;
import com.mojang.ld22.entity.Torch;
import com.mojang.ld22.entity.TNT;
import com.mojang.ld22.entity.Oven;
import com.mojang.ld22.entity.Lantern;
import com.mojang.ld22.entity.Furnace;
import com.mojang.ld22.entity.Chest;
import com.mojang.ld22.entity.Furniture;
import com.mojang.ld22.entity.Anvil;
import java.util.ArrayList;
import java.util.List;
import com.mojang.ld22.screen.ListItem;

public class Item implements ListItem
{
    public static List<Item> items;
    public static int id;
    public static Item woodenSword;
    public static Item woodenPickaxe;
    public static Item woodenAxe;
    public static Item woodenShovel;
    public static Item woodenHoe;
    public static Item woodenHammer;
    public static Item woodenBow;
    public static Item stoneSword;
    public static Item stonePickaxe;
    public static Item stoneAxe;
    public static Item stoneShovel;
    public static Item stoneHoe;
    public static Item stoneHammer;
    public static Item ironSword;
    public static Item ironPickaxe;
    public static Item ironAxe;
    public static Item ironShovel;
    public static Item ironHoe;
    public static Item ironHammer;
    public static Item goldSword;
    public static Item goldPickaxe;
    public static Item goldAxe;
    public static Item goldShovel;
    public static Item goldHoe;
    public static Item goldHammer;
    public static Item gemSword;
    public static Item gemPickaxe;
    public static Item gemAxe;
    public static Item gemShovel;
    public static Item gemHoe;
    public static Item gemHammer;
    public static Item ironbukket;
    public static Item powerglove;
    public static Item heartcontainer;
    public static Item flintandsteel;
    public static Item stonebow;
    public static Item gembow;
    public static Item anvil;
    public static Item chest;
    public static Item furnace;
    public static Item lantern;
    public static Item oven;
    public static Item tnt;
    public static Item torch;
    public static Item workbench;
    public static Item wood;
    public static Item stone;
    public static Item flower;
    public static Item acorn;
    public static Item dirt;
    public static Item sand;
    public static Item cactusFlower;
    public static Item seeds;
    public static Item wheat;
    public static Item bread;
    public static Item apple;
    public static Item rock;
    public static Item tree;
    public static Item water;
    public static Item ironoreblock;
    public static Item wooddoor;
    public static Item woodfloor;
    public static Item woodplank;
    public static Item glowstone;
    public static Item glowOre;
    public static Item carpet;
    public static Item stoneplank;
    public static Item coal;
    public static Item ironOre;
    public static Item goldOre;
    public static Item ironIngot;
    public static Item goldIngot;
    public static Item slime;
    public static Item glass;
    public static Item cloth;
    public static Item gem;
    public static Item grHook;
    public static Item crHook;
    
    static {
        Item.items = new ArrayList<Item>();
        Item.woodenSword = new ToolItem(ToolType.sword, 0);
        Item.woodenPickaxe = new ToolItem(ToolType.pickaxe, 0);
        Item.woodenAxe = new ToolItem(ToolType.axe, 0);
        Item.woodenShovel = new ToolItem(ToolType.shovel, 0);
        Item.woodenHoe = new ToolItem(ToolType.hoe, 0);
        Item.woodenHammer = new ToolItem(ToolType.hammer, 0);
        Item.woodenBow = new ToolItem(ToolType.bow, 0);
        Item.stoneSword = new ToolItem(ToolType.sword, 1);
        Item.stonePickaxe = new ToolItem(ToolType.pickaxe, 1);
        Item.stoneAxe = new ToolItem(ToolType.axe, 1);
        Item.stoneShovel = new ToolItem(ToolType.shovel, 1);
        Item.stoneHoe = new ToolItem(ToolType.hoe, 1);
        Item.stoneHammer = new ToolItem(ToolType.hammer, 1);
        Item.ironSword = new ToolItem(ToolType.sword, 2);
        Item.ironPickaxe = new ToolItem(ToolType.pickaxe, 2);
        Item.ironAxe = new ToolItem(ToolType.axe, 2);
        Item.ironShovel = new ToolItem(ToolType.shovel, 2);
        Item.ironHoe = new ToolItem(ToolType.hoe, 2);
        Item.ironHammer = new ToolItem(ToolType.hammer, 2);
        Item.goldSword = new ToolItem(ToolType.sword, 3);
        Item.goldPickaxe = new ToolItem(ToolType.pickaxe, 3);
        Item.goldAxe = new ToolItem(ToolType.axe, 3);
        Item.goldShovel = new ToolItem(ToolType.shovel, 3);
        Item.goldHoe = new ToolItem(ToolType.hoe, 3);
        Item.goldHammer = new ToolItem(ToolType.hammer, 3);
        Item.gemSword = new ToolItem(ToolType.sword, 4);
        Item.gemPickaxe = new ToolItem(ToolType.pickaxe, 4);
        Item.gemAxe = new ToolItem(ToolType.axe, 4);
        Item.gemShovel = new ToolItem(ToolType.shovel, 4);
        Item.gemHoe = new ToolItem(ToolType.hoe, 4);
        Item.gemHammer = new ToolItem(ToolType.hammer, 4);
        Item.ironbukket = new ToolItem(ToolType.bucket, 2);
        Item.powerglove = new PowerGloveItem();
        Item.heartcontainer = new HeartContainer();
        Item.flintandsteel = new ToolItem(ToolType.flintnsteel, 2);
        Item.stonebow = new ToolItem(ToolType.bow, 1);
        Item.gembow = new ToolItem(ToolType.bow, 4);
        Item.anvil = new FurnitureItem(new Anvil());
        Item.chest = new FurnitureItem(new Chest());
        Item.furnace = new FurnitureItem(new Furnace());
        Item.lantern = new FurnitureItem(new Lantern());
        Item.oven = new FurnitureItem(new Oven());
        Item.tnt = new FurnitureItem(new TNT());
        Item.torch = new FurnitureItem(new Torch());
        Item.workbench = new FurnitureItem(new Workbench());
        Item.wood = new ResourceItem(Resource.wood);
        Item.stone = new ResourceItem(Resource.stone);
        Item.flower = new ResourceItem(Resource.flower);
        Item.acorn = new ResourceItem(Resource.acorn);
        Item.dirt = new ResourceItem(Resource.dirt);
        Item.sand = new ResourceItem(Resource.sand);
        Item.cactusFlower = new ResourceItem(Resource.cactusFlower);
        Item.seeds = new ResourceItem(Resource.seeds);
        Item.wheat = new ResourceItem(Resource.wheat);
        Item.bread = new ResourceItem(Resource.bread);
        Item.apple = new ResourceItem(Resource.apple);
        Item.rock = new ResourceItem(Resource.rock);
        Item.tree = new ResourceItem(Resource.tree);
        Item.water = new ResourceItem(Resource.water);
        Item.ironoreblock = new ResourceItem(Resource.ironoreblock);
        Item.wooddoor = new ResourceItem(Resource.wooddoor);
        Item.woodfloor = new ResourceItem(Resource.woodfloor);
        Item.woodplank = new ResourceItem(Resource.woodplank);
        Item.glowstone = new ResourceItem(Resource.glowstone);
        Item.glowOre = new ResourceItem(Resource.glowstoneOre);
        Item.carpet = new ResourceItem(Resource.carpet);
        Item.stoneplank = new ResourceItem(Resource.stoneplank);
        Item.coal = new ResourceItem(Resource.coal);
        Item.ironOre = new ResourceItem(Resource.ironOre);
        Item.goldOre = new ResourceItem(Resource.goldOre);
        Item.ironIngot = new ResourceItem(Resource.ironIngot);
        Item.goldIngot = new ResourceItem(Resource.goldIngot);
        Item.slime = new ResourceItem(Resource.slime);
        Item.glass = new ResourceItem(Resource.glass);
        Item.cloth = new ResourceItem(Resource.cloth);
        Item.gem = new ResourceItem(Resource.gem);
        Item.grHook = new Enterhaken();
        Item.crHook = new Greifhaken();
    }
    
    public Item(final String name) {
        if (this instanceof ResourceItem) {
            ((ResourceItem)this).count = 1;
        }
        addItem(this, name);
    }
    
    public static void addItem(final Item item, final String s) {
        boolean flag = true;
        for (int i = 0; i < Item.items.size(); ++i) {
            if (Item.items.get(i).getName().equalsIgnoreCase(s)) {
                flag = false;
                Item.id = i;
            }
        }
        if (flag) {
            Item.id = Item.items.size();
            Item.items.add(item);
        }
    }
    
    public static int getIDOfName(final String name) {
        int i1 = 0;
        for (int j = 0; j < Item.items.size(); ++j) {
            if (Item.items.get(j).getName().equalsIgnoreCase(name)) {
                i1 = j;
            }
        }
        return i1;
    }
    
    public int getColor() {
        return 0;
    }
    
    public int getSprite() {
        return 0;
    }
    
    public void onTake(final ItemEntity itemEntity) {
    }
    
    @Override
    public void renderInventory(final Screen screen, final int x, final int y) {
    }
    
    public boolean interact(final Player player, final Entity entity, final int attackDir) {
        return false;
    }
    
    public void renderIcon(final Screen screen, final int x, final int y) {
    }
    
    public boolean interactOn(final Tile tile, final Level level, final int xt, final int yt, final Player player, final int attackDir) {
        return false;
    }
    
    public boolean isDepleted() {
        return false;
    }
    
    public boolean canAttack() {
        return false;
    }
    
    public int getAttackDamageBonus(final Entity e) {
        return 0;
    }
    
    public String getName() {
        return "";
    }
    
    public boolean matches(final Item item) {
        return item.getClass() == this.getClass();
    }
}
