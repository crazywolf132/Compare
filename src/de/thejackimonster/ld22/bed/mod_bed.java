// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.bed;

import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.crafting.FurnitureRecipe;
import de.thejackimonster.ld22.modloader.ModLoader;
import com.mojang.ld22.entity.Furniture;
import com.mojang.ld22.item.FurnitureItem;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.Entity;
import de.thejackimonster.ld22.modloader.BaseMod;

public class mod_bed extends BaseMod
{
    public static Entity useBed;
    public static Item bed;
    
    static {
        mod_bed.useBed = new Bed();
        mod_bed.bed = new FurnitureItem(new Bed());
        try {
            ModLoader.wRecipes.add(new FurnitureRecipe(Bed.class).addCost(Resource.cloth, 10).addCost(Resource.wood, 10));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void load() {
    }
    
    @Override
    public String getVersion() {
        return "Alpha";
    }
}
