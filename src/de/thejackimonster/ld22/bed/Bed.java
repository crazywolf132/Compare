// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.bed;

import com.mojang.ld22.entity.Player;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.entity.Furniture;

public class Bed extends Furniture
{
    public boolean on;
    private int ticks;
    public int tickTime;
    
    public Bed() {
        super("Bed");
        this.on = false;
        this.ticks = 0;
        this.tickTime = 0;
        this.col = Color.get(-1, Color.getRGB(80, 44, 22), Color.getRGB(112, 7, 7), Color.getRGB(253, 253, 253));
        this.sprite = 8;
    }
    
    @Override
    public boolean use(final Player player, final int attackDir) {
        player.game.respawnx = this.x;
        player.game.respawny = this.y;
        if (player.game.lightlvl < 3 && !player.game.rising) {
            player.game.lightlvl = 2;
            return player.game.rising = true;
        }
        return false;
    }
}
