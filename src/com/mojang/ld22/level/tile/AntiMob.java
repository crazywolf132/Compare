// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.level.tile;

import com.mojang.ld22.item.Item;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.gfx.Screen;

public class AntiMob extends Tile
{
    public AntiMob(final int id) {
        super(id);
    }
    
    @Override
    public void render(final Screen screen, final Level level, final int x, final int y) {
        final int color = Color.get(level.dirtColor, 111, 110, 320);
        final int xt = 27;
        screen.render(x * 16 + 0, y * 16 + 0, xt + 32, color, 0);
        screen.render(x * 16 + 8, y * 16 + 0, xt + 1 + 32, color, 0);
        screen.render(x * 16 + 0, y * 16 + 8, xt + 64, color, 0);
        screen.render(x * 16 + 8, y * 16 + 8, xt + 1 + 64, color, 0);
        super.render(screen, level, xt, y);
    }
    
    @Override
    public boolean mayPass(final Level level, final int x, final int y, final Entity e) {
        return e instanceof Player;
    }
    
    @Override
    public void hurt(final Level level, final int x, final int y, final Mob source, final int dmg, final int attackDir) {
        Sound.monsterHurt.play();
    }
    
    @Override
    public boolean interact(final Level level, final int xt, final int yt, final Player player, final Item item, final int attackDir) {
        return false;
    }
    
    private void hurt(final Level level, final int x, final int y, final int dmg) {
    }
}
