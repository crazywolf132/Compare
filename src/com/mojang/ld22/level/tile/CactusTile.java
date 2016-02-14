// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.level.tile;

import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.entity.particle.TextParticle;
import com.mojang.ld22.entity.particle.SmashParticle;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.gfx.Screen;

public class CactusTile extends Tile
{
    public CactusTile(final int id) {
        super(id);
        this.connectsToSand = true;
    }
    
    @Override
    public void render(final Screen screen, final Level level, final int x, final int y) {
        final int col = Color.get(20, 40, 50, level.sandColor);
        screen.render(x * 16 + 0, y * 16 + 0, 72, col, 0);
        screen.render(x * 16 + 8, y * 16 + 0, 73, col, 0);
        screen.render(x * 16 + 0, y * 16 + 8, 104, col, 0);
        screen.render(x * 16 + 8, y * 16 + 8, 105, col, 0);
        this.renderDmg(screen, level, x, y, 1, 4, 7);
        super.render(screen, level, x, y);
    }
    
    @Override
    public boolean mayPass(final Level level, final int x, final int y, final Entity e) {
        return false;
    }
    
    @Override
    public void hurt(final Level level, final int x, final int y, final Mob source, final int dmg, final int attackDir) {
        final int damage = level.getData(x, y) + dmg;
        level.add(new SmashParticle(x * 16 + 8, y * 16 + 8));
        level.add(new TextParticle(new StringBuilder().append(dmg).toString(), x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500)));
        if (damage >= 10) {
            for (int count = this.random.nextInt(2) + 1, i = 0; i < count; ++i) {
                level.add(new ItemEntity(new ResourceItem(Resource.cactusFlower), x * 16 + this.random.nextInt(10) + 3, y * 16 + this.random.nextInt(10) + 3));
            }
            level.setTile(x, y, Tile.sand, 0);
        }
        else {
            level.setData(x, y, damage);
        }
    }
    
    @Override
    public void bumpedInto(final Level level, final int x, final int y, final Entity entity) {
        entity.hurt(this, x, y, 1);
        super.bumpedInto(level, x, y, entity);
    }
    
    @Override
    public void tick(final Level level, final int xt, final int yt) {
        final int damage = level.getData(xt, yt);
        if (damage > 0) {
            level.setData(xt, yt, damage - 1);
        }
        super.tick(level, xt, yt);
    }
}
