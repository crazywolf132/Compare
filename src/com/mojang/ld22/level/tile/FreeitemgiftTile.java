// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.level.tile;

import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.Item;

public class FreeitemgiftTile extends Tile
{
    private Item toDrop;
    private int color;
    private int count;
    private int ilevel;
    
    public FreeitemgiftTile(final int id, final Item toDrop, final int level) {
        super(id);
        this.toDrop = toDrop;
        this.color = 16776960;
        this.ilevel = level;
    }
    
    @Override
    public void render(final Screen screen, final Level level, final int x, final int y) {
        screen.render(x * 16 + 0, y * 16 + 0, 49, this.color = Color.get(level.dirtColor, this.random.nextInt(555), this.random.nextInt(555), this.random.nextInt(555)), 0);
        screen.render(x * 16 + 8, y * 16 + 0, 50, this.color, 0);
        screen.render(x * 16 + 0, y * 16 + 8, 81, this.color, 0);
        screen.render(x * 16 + 8, y * 16 + 8, 82, this.color, 0);
        super.render(screen, level, x, y);
    }
    
    @Override
    public boolean mayPass(final Level level, final int x, final int y, final Entity e) {
        return false;
    }
    
    @Override
    public void hurt(final Level level, final int x, final int y, final Mob source, final int dmg, final int attackDir) {
        if (dmg == 9999) {
            this.hurt(level, x, y, 9999);
        }
        else {
            this.hurt(level, x, y, 0);
        }
    }
    
    @Override
    public boolean interact(final Level level, final int xt, final int yt, final Player player, final Item item, final int attackDir) {
        return false;
    }
    
    public void hurt(final Level level, final int x, final int y, final int dmg) {
        if (this.toDrop instanceof ResourceItem) {
            ((ResourceItem)this.toDrop).count = 1;
        }
        level.add(new ItemEntity(this.toDrop, x * 16 + this.random.nextInt(10) + 3, y * 16 + this.random.nextInt(10) + 3));
        level.setTile(x, y, Tile.dirt, 0);
        Sound.special.play();
    }
    
    @Override
    public void bumpedInto(final Level level, final int x, final int y, final Entity entity) {
        entity.hurt(this, x, y, 3);
        super.bumpedInto(level, x, y, entity);
    }
}
