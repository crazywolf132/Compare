// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.level.tile;

import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.entity.particle.TextParticle;
import com.mojang.ld22.entity.particle.SmashParticle;
import com.mojang.ld22.item.ToolItem;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.gfx.Screen;

public class GlowStoneTile extends Tile
{
    public GlowStoneTile(final int id) {
        super(id);
    }
    
    @Override
    public void render(final Screen screen, final Level level, final int x, final int y) {
        final int col = Color.get(430, 430, 430, 430);
        final int transitionColor = Color.get(220, 530, 530, level.dirtColor);
        final boolean u = level.getTile(x, y - 1) != this;
        final boolean d = level.getTile(x, y + 1) != this;
        final boolean l = level.getTile(x - 1, y) != this;
        final boolean r = level.getTile(x + 1, y) != this;
        final boolean ul = level.getTile(x - 1, y - 1) != this;
        final boolean dl = level.getTile(x - 1, y + 1) != this;
        final boolean ur = level.getTile(x + 1, y - 1) != this;
        final boolean dr = level.getTile(x + 1, y + 1) != this;
        if (!u && !l) {
            if (!ul) {
                screen.render(x * 16 + 0, y * 16 + 0, 0, col, 0);
            }
            else {
                screen.render(x * 16 + 0, y * 16 + 0, 707, transitionColor, 3);
            }
        }
        else {
            screen.render(x * 16 + 0, y * 16 + 0, (l ? 2 : 1) + (u ? 24 : 23) * 32, transitionColor, 3);
        }
        if (!u && !r) {
            if (!ur) {
                screen.render(x * 16 + 8, y * 16 + 0, 1, col, 0);
            }
            else {
                screen.render(x * 16 + 8, y * 16 + 0, 708, transitionColor, 3);
            }
        }
        else {
            screen.render(x * 16 + 8, y * 16 + 0, (r ? 0 : 1) + (u ? 24 : 23) * 32, transitionColor, 3);
        }
        if (!d && !l) {
            if (!dl) {
                screen.render(x * 16 + 0, y * 16 + 8, 2, col, 0);
            }
            else {
                screen.render(x * 16 + 0, y * 16 + 8, 739, transitionColor, 3);
            }
        }
        else {
            screen.render(x * 16 + 0, y * 16 + 8, (l ? 2 : 1) + (d ? 22 : 23) * 32, transitionColor, 3);
        }
        if (!d && !r) {
            if (!dr) {
                screen.render(x * 16 + 8, y * 16 + 8, 3, col, 0);
            }
            else {
                screen.render(x * 16 + 8, y * 16 + 8, 740, transitionColor, 3);
            }
        }
        else {
            screen.render(x * 16 + 8, y * 16 + 8, (r ? 0 : 1) + (d ? 22 : 23) * 32, transitionColor, 3);
        }
        this.renderDmg(screen, level, x, y, 10, 25, 40);
    }
    
    @Override
    public boolean mayPass(final Level level, final int x, final int y, final Entity e) {
        return false;
    }
    
    @Override
    public void hurt(final Level level, final int x, final int y, final Mob source, final int dmg, final int attackDir) {
        this.hurt(level, x, y, dmg);
    }
    
    @Override
    public boolean interact(final Level level, final int xt, final int yt, final Player player, final Item item, final int attackDir) {
        if (item instanceof ToolItem) {
            final ToolItem tool = (ToolItem)item;
            this.hurt(level, xt, yt, tool.level * 5 + 10 + this.random.nextInt(5));
        }
        return false;
    }
    
    public void hurt(final Level level, final int x, final int y, final int dmg) {
        final int damage = level.getData(x, y) + dmg;
        level.add(new SmashParticle(x * 16 + 8, y * 16 + 8));
        level.add(new TextParticle(new StringBuilder().append(dmg).toString(), x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500)));
        if (damage >= 50) {
            for (int count = this.random.nextInt(4) + 1, i = 0; i < count; ++i) {
                level.add(new ItemEntity(new ResourceItem(Resource.glowstoneOre), x * 16 + this.random.nextInt(10) + 3, y * 16 + this.random.nextInt(10) + 3));
            }
            level.setTile(x, y, Tile.dirt, 0);
        }
        else {
            level.setData(x, y, damage);
        }
    }
    
    @Override
    public void tick(final Level level, final int xt, final int yt) {
        final int damage = level.getData(xt, yt);
        if (damage > 0) {
            level.setData(xt, yt, damage - 1);
        }
    }
    
    @Override
    public int getLightRadius(final Level level, final int x, final int y) {
        return 5;
    }
}
