// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.crafting;

import de.thejackimonster.ld22.modloader.ModLoader;
import com.mojang.ld22.item.ToolType;
import com.mojang.ld22.entity.TNT;
import com.mojang.ld22.entity.Torch;
import com.mojang.ld22.entity.Anvil;
import com.mojang.ld22.entity.Chest;
import com.mojang.ld22.entity.Workbench;
import com.mojang.ld22.entity.Furnace;
import com.mojang.ld22.entity.Oven;
import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.entity.Furniture;
import com.mojang.ld22.entity.Lantern;
import java.util.ArrayList;
import java.util.List;

public class Crafting
{
    public static final List<Recipe> anvilRecipes;
    public static final List<Recipe> ovenRecipes;
    public static final List<Recipe> furnaceRecipes;
    public static final List<Recipe> workbenchRecipes;
    
    static {
        anvilRecipes = new ArrayList<Recipe>();
        ovenRecipes = new ArrayList<Recipe>();
        furnaceRecipes = new ArrayList<Recipe>();
        workbenchRecipes = new ArrayList<Recipe>();
        try {
            Crafting.workbenchRecipes.add(new FurnitureRecipe(Lantern.class).addCost(Resource.wood, 5).addCost(Resource.slime, 10).addCost(Resource.glass, 4));
            Crafting.workbenchRecipes.add(new FurnitureRecipe(Oven.class).addCost(Resource.stone, 15));
            Crafting.workbenchRecipes.add(new FurnitureRecipe(Furnace.class).addCost(Resource.stone, 20));
            Crafting.workbenchRecipes.add(new FurnitureRecipe(Workbench.class).addCost(Resource.wood, 20));
            Crafting.workbenchRecipes.add(new FurnitureRecipe(Chest.class).addCost(Resource.wood, 20));
            Crafting.workbenchRecipes.add(new FurnitureRecipe(Anvil.class).addCost(Resource.ironIngot, 5));
            Crafting.workbenchRecipes.add(new FurnitureRecipe(Torch.class).addCost(Resource.coal, 2).addCost(Resource.wood, 2));
            Crafting.workbenchRecipes.add(new FurnitureRecipe(TNT.class).addCost(Resource.coal, 10));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.sword, 0).addCost(Resource.wood, 5));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.axe, 0).addCost(Resource.wood, 5));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.hoe, 0).addCost(Resource.wood, 5));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.pickaxe, 0).addCost(Resource.wood, 5));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.shovel, 0).addCost(Resource.wood, 5));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.hammer, 0).addCost(Resource.wood, 15));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.sword, 1).addCost(Resource.wood, 5).addCost(Resource.stone, 5));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.axe, 1).addCost(Resource.wood, 5).addCost(Resource.stone, 5));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.hoe, 1).addCost(Resource.wood, 5).addCost(Resource.stone, 5));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.pickaxe, 1).addCost(Resource.wood, 5).addCost(Resource.stone, 5));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.shovel, 1).addCost(Resource.wood, 5).addCost(Resource.stone, 5));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.hammer, 1).addCost(Resource.wood, 5).addCost(Resource.stone, 15));
            Crafting.workbenchRecipes.add(new ToolRecipe(ToolType.bow, 1).addCost(Resource.wood, 5).addCost(Resource.stone, 15));
            Crafting.workbenchRecipes.add(new ResourceRecipe(Resource.woodplank).addCost(Resource.wood, 4));
            Crafting.workbenchRecipes.add(new ResourceRecipe(Resource.woodfloor).addCost(Resource.woodplank, 1));
            Crafting.workbenchRecipes.add(new ResourceRecipe(Resource.wooddoor).addCost(Resource.woodplank, 2));
            Crafting.workbenchRecipes.add(new ResourceRecipe(Resource.carpet).addCost(Resource.cloth, 10));
            Crafting.workbenchRecipes.add(new ResourceRecipe(Resource.stoneplank).addCost(Resource.stone, 4));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.bucket, 2).addCost(Resource.ironIngot, 3));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.flintnsteel, 2).addCost(Resource.ironIngot, 5).addCost(Resource.coal, 10));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.sword, 2).addCost(Resource.wood, 5).addCost(Resource.ironIngot, 5));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.axe, 2).addCost(Resource.wood, 5).addCost(Resource.ironIngot, 5));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.hoe, 2).addCost(Resource.wood, 5).addCost(Resource.ironIngot, 5));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.pickaxe, 2).addCost(Resource.wood, 5).addCost(Resource.ironIngot, 5));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.shovel, 2).addCost(Resource.wood, 5).addCost(Resource.ironIngot, 5));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.hammer, 2).addCost(Resource.wood, 5).addCost(Resource.ironIngot, 15));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.bow, 2).addCost(Resource.wood, 5).addCost(Resource.ironIngot, 15));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.sword, 3).addCost(Resource.wood, 5).addCost(Resource.goldIngot, 5));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.axe, 3).addCost(Resource.wood, 5).addCost(Resource.goldIngot, 5));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.hoe, 3).addCost(Resource.wood, 5).addCost(Resource.goldIngot, 5));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.pickaxe, 3).addCost(Resource.wood, 5).addCost(Resource.goldIngot, 5));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.shovel, 3).addCost(Resource.wood, 5).addCost(Resource.goldIngot, 5));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.hammer, 3).addCost(Resource.wood, 5).addCost(Resource.goldIngot, 15));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.sword, 4).addCost(Resource.wood, 5).addCost(Resource.gem, 50));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.axe, 4).addCost(Resource.wood, 5).addCost(Resource.gem, 50));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.hoe, 4).addCost(Resource.wood, 5).addCost(Resource.gem, 50));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.pickaxe, 4).addCost(Resource.wood, 5).addCost(Resource.gem, 50));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.shovel, 4).addCost(Resource.wood, 5).addCost(Resource.gem, 50));
            Crafting.anvilRecipes.add(new ToolRecipe(ToolType.hammer, 4).addCost(Resource.wood, 5).addCost(Resource.gem, 75));
            Crafting.furnaceRecipes.add(new ResourceRecipe(Resource.ironIngot).addCost(Resource.ironOre, 4).addCost(Resource.coal, 1));
            Crafting.furnaceRecipes.add(new ResourceRecipe(Resource.goldIngot).addCost(Resource.goldOre, 4).addCost(Resource.coal, 1));
            Crafting.furnaceRecipes.add(new ResourceRecipe(Resource.glass).addCost(Resource.sand, 4).addCost(Resource.coal, 1));
            Crafting.furnaceRecipes.add(new ResourceRecipe(Resource.glowstone).addCost(Resource.glowstoneOre, 8).addCost(Resource.coal, 2));
            Crafting.ovenRecipes.add(new ResourceRecipe(Resource.bread).addCost(Resource.wheat, 4));
            for (int i = 0; i < ModLoader.aRecipes.size(); ++i) {
                Crafting.anvilRecipes.add(ModLoader.aRecipes.get(i));
            }
            for (int i = 0; i < ModLoader.oRecipes.size(); ++i) {
                Crafting.ovenRecipes.add(ModLoader.oRecipes.get(i));
            }
            for (int i = 0; i < ModLoader.fRecipes.size(); ++i) {
                Crafting.furnaceRecipes.add(ModLoader.fRecipes.get(i));
            }
            for (int i = 0; i < ModLoader.wRecipes.size(); ++i) {
                Crafting.workbenchRecipes.add(ModLoader.wRecipes.get(i));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
