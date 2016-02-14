// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import com.mojang.ld22.screen.Menu;
import com.mojang.ld22.screen.ContainerMenu;
import com.mojang.ld22.gfx.Color;

public class Chest extends Furniture
{
    public Inventory inventory;
    
    public Chest() {
        super("Chest");
        this.inventory = new Inventory();
        this.col = Color.get(-1, 110, 331, 552);
        this.sprite = 1;
    }
    
    @Override
    public boolean use(final Player player, final int attackDir) {
        player.game.setMenu(new ContainerMenu(player, "Chest", this.inventory));
        return true;
    }
}
