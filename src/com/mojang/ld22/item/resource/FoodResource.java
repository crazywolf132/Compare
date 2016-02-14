// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.item.resource;

import com.mojang.ld22.entity.Player;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.level.tile.Tile;

public class FoodResource extends Resource
{
    private int heal;
    private int staminaCost;
    
    public FoodResource(final String name, final int sprite, final int color, final int heal, final int staminaCost) {
        super(name, sprite, color);
        this.heal = heal;
        this.staminaCost = staminaCost;
    }
    
    @Override
    public boolean interactOn(final Tile tile, final Level level, final int xt, final int yt, final Player player, final int attackDir) {
        if (player.health < player.maxHealth && player.payStamina(this.staminaCost)) {
            player.heal(this.heal);
            return true;
        }
        return false;
    }
}
