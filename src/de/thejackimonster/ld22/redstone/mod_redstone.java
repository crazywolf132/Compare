// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.redstone;

import com.mojang.ld22.entity.Inventory;
import java.util.Random;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.crafting.FurnitureRecipe;
import de.thejackimonster.ld22.modloader.ModLoader;
import com.mojang.ld22.entity.Furniture;
import com.mojang.ld22.item.FurnitureItem;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.level.tile.OreTile;
import com.mojang.ld22.item.resource.PlantableResource;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.level.tile.Tile;
import de.thejackimonster.ld22.modloader.BaseMod;

public class mod_redstone extends BaseMod
{
    public static Tile wire;
    public static Resource redstone;
    public static Tile redstone_ore;
    public static final Entity redstoneTorch;
    public static Item redstone_power;
    public static Item redstone_torch;
    
    static {
        mod_redstone.wire = new RedstoneTile(50);
        mod_redstone.redstone = new PlantableResource("Redstone", 133, mod_redstone.wire, Color.get(-1, 100, 300, 500), new Tile[] { Tile.grass, Tile.dirt, Tile.sand, Tile.farmland });
        mod_redstone.redstone_ore = new OreTile(51, mod_redstone.redstone, 0);
        redstoneTorch = new RedstoneTorch();
        mod_redstone.redstone_power = new ResourceItem(mod_redstone.redstone);
        mod_redstone.redstone_torch = new FurnitureItem(new RedstoneTorch());
        try {
            ModLoader.wRecipes.add(new FurnitureRecipe(RedstoneTorch.class).addCost(mod_redstone.redstone, 1).addCost(Resource.wood, 1));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void load() {
    }
    
    @Override
    public void GenerateLevel(final Level level, final Random random, final int i, final int j) {
        if (level.depth < -1 && level.getTile(i, j) == Tile.rock && random.nextInt(30) == 0) {
            level.setTile(i, j, mod_redstone.redstone_ore, 0);
            if (ModLoader.getGameInstance() != null && ModLoader.getGameInstance().player != null) {
                final Inventory inventory = ModLoader.getGameInstance().player.inventory;
            }
        }
    }
    
    @Override
    public String getVersion() {
        return "Alpha";
    }
}
