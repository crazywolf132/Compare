// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.item.resource;

import com.mojang.ld22.entity.Player;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.gfx.Color;

public class Resource
{
    public static Resource wood;
    public static Resource stone;
    public static Resource flower;
    public static Resource acorn;
    public static Resource dirt;
    public static Resource sand;
    public static Resource cactusFlower;
    public static Resource seeds;
    public static Resource wheat;
    public static Resource bread;
    public static Resource apple;
    public static Resource rock;
    public static Resource tree;
    public static Resource water;
    public static Resource ironoreblock;
    public static Resource woodplank;
    public static Resource woodfloor;
    public static Resource wooddoor;
    public static Resource stoneplank;
    public static Resource carpet;
    public static Resource glowstone;
    public static Resource glowstoneOre;
    public static Resource coal;
    public static Resource ironOre;
    public static Resource goldOre;
    public static Resource ironIngot;
    public static Resource goldIngot;
    public static Resource slime;
    public static Resource glass;
    public static Resource cloth;
    public static Resource cloud;
    public static Resource gem;
    public final String name;
    public final int sprite;
    public final int color;
    
    static {
        Resource.wood = new Resource("Wood", 129, Color.get(-1, 200, 531, 430));
        Resource.stone = new Resource("Stone", 130, Color.get(-1, 111, 333, 555));
        Resource.flower = new PlantableResource("Flower", 128, Tile.flower, Color.get(-1, 10, 444, 330), new Tile[] { Tile.grass });
        Resource.acorn = new PlantableResource("Acorn", 131, Tile.treeSapling, Color.get(-1, 100, 531, 320), new Tile[] { Tile.grass });
        Resource.dirt = new PlantableResource("Dirt", 130, Tile.dirt, Color.get(-1, 100, 322, 432), new Tile[] { Tile.hole, Tile.water, Tile.lava });
        Resource.sand = new PlantableResource("Sand", 130, Tile.sand, Color.get(-1, 110, 440, 550), new Tile[] { Tile.grass, Tile.dirt });
        Resource.cactusFlower = new PlantableResource("Cactus", 132, Tile.cactusSapling, Color.get(-1, 10, 40, 50), new Tile[] { Tile.sand });
        Resource.seeds = new PlantableResource("Seeds", 133, Tile.wheat, Color.get(-1, 10, 40, 50), new Tile[] { Tile.farmland });
        Resource.wheat = new Resource("Wheat", 134, Color.get(-1, 110, 330, 550));
        Resource.bread = new FoodResource("Bread", 136, Color.get(-1, 110, 330, 550), 2, 5);
        Resource.apple = new FoodResource("Apple", 137, Color.get(-1, 100, 300, 500), 1, 5);
        Resource.rock = new PlantableResource("Rock", 321, Tile.rock, Color.get(-1, 444, 333, 333), new Tile[] { Tile.grass, Tile.dirt, Tile.farmland, Tile.sand });
        Resource.tree = new PlantableResource("Tree", 321, Tile.tree, Color.get(-1, 420, 430, 430), new Tile[] { Tile.grass, Tile.dirt, Tile.farmland });
        Resource.water = new PlantableResource("Water Bucket", 166, Tile.water, Color.get(-1, 1, 444, 333), new Tile[] { Tile.hole });
        Resource.ironoreblock = new PlantableResource("Iron Ore", 321, Tile.tree, Color.get(-1, 220, 430, 430), new Tile[] { Tile.grass, Tile.dirt, Tile.farmland, Tile.sand });
        Resource.woodplank = new PlantableResource("Wooden Plank", 321, Tile.woodplanktile, Color.get(-1, 220, 430, 430), new Tile[] { Tile.grass, Tile.dirt, Tile.farmland, Tile.sand });
        Resource.woodfloor = new PlantableResource("Wooden Floor", 321, Tile.woodfloor, Color.get(-1, 220, 430, 430), new Tile[] { Tile.grass, Tile.dirt, Tile.farmland, Tile.sand });
        Resource.wooddoor = new PlantableResource("Wooden Door", 321, Tile.woodendoor, Color.get(-1, 220, 430, 430), new Tile[] { Tile.grass, Tile.dirt, Tile.farmland, Tile.sand });
        Resource.stoneplank = new PlantableResource("Stone Plank", 321, Tile.stoneplank, Color.get(-1, 111, 444, 555), new Tile[] { Tile.grass, Tile.dirt, Tile.farmland, Tile.sand });
        Resource.carpet = new PlantableResource("Red Carpet", 321, Tile.carpet, Color.get(-1, 220, 500, 510), new Tile[] { Tile.woodfloor });
        Resource.glowstone = new PlantableResource("Glowstone", 321, Tile.glowstone, Color.get(-1, 220, 540, 560), new Tile[] { Tile.grass, Tile.dirt, Tile.farmland, Tile.sand });
        Resource.glowstoneOre = new Resource("Glowstone Ore", 138, Color.get(-1, 100, 540, 544));
        Resource.coal = new Resource("COAL", 138, Color.get(-1, 0, 111, 111));
        Resource.ironOre = new Resource("I.ORE", 138, Color.get(-1, 100, 322, 544));
        Resource.goldOre = new Resource("G.ORE", 138, Color.get(-1, 110, 440, 553));
        Resource.ironIngot = new Resource("IRON", 139, Color.get(-1, 100, 322, 544));
        Resource.goldIngot = new Resource("GOLD", 139, Color.get(-1, 110, 330, 553));
        Resource.slime = new Resource("SLIME", 138, Color.get(-1, 10, 30, 50));
        Resource.glass = new Resource("glass", 140, Color.get(-1, 555, 555, 555));
        Resource.cloth = new Resource("cloth", 129, Color.get(-1, 25, 252, 141));
        Resource.cloud = new PlantableResource("cloud", 130, Tile.cloud, Color.get(-1, 222, 555, 444), new Tile[] { Tile.infiniteFall });
        Resource.gem = new Resource("gem", 141, Color.get(-1, 101, 404, 545));
    }
    
    public Resource(final String name, final int sprite, final int color) {
        this.name = name;
        this.sprite = sprite;
        this.color = color;
    }
    
    public boolean interactOn(final Tile tile, final Level level, final int xt, final int yt, final Player player, final int attackDir) {
        return false;
    }
}
