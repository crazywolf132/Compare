// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import com.mojang.ld22.screen.Menu;
import com.mojang.ld22.screen.CraftingMenu;
import com.mojang.ld22.crafting.Crafting;
import com.mojang.ld22.gfx.Color;

public class Furnace extends Furniture
{
    public Furnace() {
        super("Furnace");
        this.col = Color.get(-1, 0, 222, 333);
        this.sprite = 3;
        this.xr = 3;
        this.yr = 2;
    }
    
    @Override
    public boolean use(final Player player, final int attackDir) {
        player.game.setMenu(new CraftingMenu(Crafting.furnaceRecipes, player));
        return true;
    }
}
