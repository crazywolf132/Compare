// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.modloader;

import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.entity.Inventory;
import com.mojang.ld22.item.Item;
import java.util.Random;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.screen.Menu;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import com.mojang.ld22.crafting.Recipe;
import java.util.List;
import com.mojang.ld22.Game;

public class ModLoader
{
    private static Game game;
    public static List<BaseMod> mods;
    public static List<Recipe> aRecipes;
    public static List<Recipe> oRecipes;
    public static List<Recipe> fRecipes;
    public static List<Recipe> wRecipes;
    
    static {
        ModLoader.mods = new ArrayList<BaseMod>();
        ModLoader.aRecipes = new ArrayList<Recipe>();
        ModLoader.oRecipes = new ArrayList<Recipe>();
        ModLoader.fRecipes = new ArrayList<Recipe>();
        ModLoader.wRecipes = new ArrayList<Recipe>();
    }
    
    public static void addGame(final Game g) {
        ModLoader.game = g;
    }
    
    public static void loadAllMods() {
        for (int i = 0; i < ModLoader.mods.size(); ++i) {
            ModLoader.mods.get(i).load();
            System.out.println("ModLoader loads " + ModLoader.mods.get(i).getName() + " " + ModLoader.mods.get(i).getVersion());
        }
    }
    
    public static void toggleKey(final KeyEvent ke, final boolean pressed) {
        for (int i = 0; i < ModLoader.mods.size(); ++i) {
            ModLoader.mods.get(i).KeyboardEvent(ke.getKeyCode(), pressed);
        }
    }
    
    public static void tickGame(final Game game) {
        for (int i = 0; i < ModLoader.mods.size(); ++i) {
            ModLoader.mods.get(i).onTickInGame(game);
        }
    }
    
    public static void tickMenu(final Menu menu) {
        for (int i = 0; i < ModLoader.mods.size(); ++i) {
            ModLoader.mods.get(i).onTickInMenu(menu);
        }
    }
    
    public static void tickPlayer(final Player player) {
        for (int i = 0; i < ModLoader.mods.size(); ++i) {
            ModLoader.mods.get(i).onTickByPlayer(player);
        }
    }
    
    public static void LevelGenerate(final Level level, final Random random, final int x, final int y) {
        for (int i = 0; i < ModLoader.mods.size(); ++i) {
            ModLoader.mods.get(i).GenerateLevel(level, random, x, y);
        }
    }
    
    public static void PickupItem(final Player player, final Item item) {
        for (int i = 0; i < ModLoader.mods.size(); ++i) {
            ModLoader.mods.get(i).onItemPickup(player, item);
        }
    }
    
    public static void Craft(final Player player, final Item item, final Inventory inventory) {
        for (int i = 0; i < ModLoader.mods.size(); ++i) {
            ModLoader.mods.get(i).TakenFromCrafting(player, item, inventory);
        }
    }
    
    public static void Furnace(final Player player, final Item item) {
        for (int i = 0; i < ModLoader.mods.size(); ++i) {
            ModLoader.mods.get(i).TakenFromFurnace(player, item);
        }
    }
    
    public static void RegisterTile(final Tile tile) {
        if (Tile.tiles[tile.id] != tile) {
            if (Tile.tiles[tile.id] != null) {
                throw new RuntimeException("Duplicate tile ids!");
            }
            Tile.tiles[tile.id] = tile;
        }
    }
    
    public static Game getGameInstance() {
        return ModLoader.game;
    }
}
