// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.weapons;

import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.crafting.ItemRecipe;
import de.thejackimonster.ld22.modloader.ModLoader;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.Entity;
import de.thejackimonster.ld22.modloader.BaseMod;

public class mod_weapons extends BaseMod
{
    public static Entity boomerang;
    public static Item boome_rang;
    
    static {
        mod_weapons.boomerang = new Boomerang(null, 0, 0, 0);
        mod_weapons.boome_rang = new BoomerangItem();
        try {
            ModLoader.wRecipes.add(new ItemRecipe(new BoomerangItem()).addCost(Resource.wood, 5));
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
