// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.item;

import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.entity.Player;

public class Experience extends Item
{
    Player player;
    int exp;
    
    public Experience(final Player player, final int exp) {
        super("Experience");
        this.player = player;
        this.exp = exp;
    }
    
    @Override
    public int getColor() {
        return Color.get(-1, 50, 90, 70);
    }
    
    @Override
    public int getSprite() {
        return 128;
    }
    
    @Override
    public void onTake(final ItemEntity itemEntity) {
        final Player player = this.player;
        player.exp += this.exp;
    }
}
