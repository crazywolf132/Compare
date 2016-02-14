// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.level.tile;

import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.entity.particle.TextParticle;
import com.mojang.ld22.entity.particle.SmashParticle;
import com.mojang.ld22.item.ToolType;
import com.mojang.ld22.item.ToolItem;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.resource.Resource;

public class OreTile extends Tile
{
    private Resource toDrop;
    private int color;
    private int oretype;
    private int count;
    
    public OreTile(final int id, final Resource toDrop, final int oretype) {
        super(id);
        this.oretype = oretype;
        this.toDrop = toDrop;
        this.color = (toDrop.color & 0xFFFF00);
    }
    
    public OreTile(final int id, final Resource toDrop, final int dcount, final int oretype) {
        super(id);
        this.oretype = oretype;
        this.toDrop = toDrop;
        this.color = (toDrop.color & 0xFFFF00);
    }
    
    @Override
    public void render(final Screen screen, final Level level, final int x, final int y) {
        this.color = (this.toDrop.color & 0xFFFFFF00) + Color.get(level.dirtColor);
        int tx = 17;
        int ty = 1;
        if (this.oretype == 0) {
            tx = 17;
            ty = 1;
        }
        if (this.oretype == 1) {
            tx = 6;
            ty = 20;
        }
        screen.render(x * 16 + 0, y * 16 + 0, tx + ty * 32, this.color, 0);
        screen.render(x * 16 + 8, y * 16 + 0, tx + 1 + ty * 32, this.color, 0);
        screen.render(x * 16 + 0, y * 16 + 8, tx + (ty + 1) * 32, this.color, 0);
        screen.render(x * 16 + 8, y * 16 + 8, tx + 1 + (ty + 1) * 32, this.color, 0);
        this.renderDmg(screen, level, x, y, 1, 2, 5);
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
        if (item instanceof ToolItem) {
            final ToolItem tool = (ToolItem)item;
            if (tool.type == ToolType.pickaxe && player.payStamina(6 - tool.level)) {
                this.hurt(level, xt, yt, 1);
                return true;
            }
            if (tool.type == ToolType.hammer && tool.level >= 2 && player.stamina >= 8 - tool.level) {
                player.payStamina(8 - tool.level);
                level.setTile(xt, yt, Tile.dirt, 0);
            }
        }
        return false;
    }
    
    public void hurt(final Level level, final int x, final int y, final int dmg) {
        final int damage = level.getData(x, y) + 1;
        if (dmg == 9999) {
            level.add(new SmashParticle(x * 16 + 8, y * 16 + 8));
            level.add(new TextParticle(new StringBuilder().append(dmg).toString(), x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500)));
            level.setTile(x, y, Tile.dirt, 0);
            for (int i = 0; i < this.count; ++i) {
                level.add(new ItemEntity(new ResourceItem(this.toDrop), x * 16 + this.random.nextInt(10) + 3 + i, y * 16 + this.random.nextInt(10) + 3 + i));
            }
        }
        else {
            int count = this.random.nextInt(2);
            level.add(new SmashParticle(x * 16 + 8, y * 16 + 8));
            level.add(new TextParticle(new StringBuilder().append(dmg).toString(), x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500)));
            if (dmg > 0) {
                if (damage >= this.random.nextInt(10) + 3) {
                    level.setTile(x, y, Tile.dirt, 0);
                    count += 2;
                }
                else {
                    level.setData(x, y, damage);
                }
                for (int j = 0; j < count; ++j) {
                    level.add(new ItemEntity(new ResourceItem(this.toDrop), x * 16 + this.random.nextInt(10) + 3, y * 16 + this.random.nextInt(10) + 3));
                }
            }
        }
    }
    
    @Override
    public void bumpedInto(final Level level, final int x, final int y, final Entity entity) {
        entity.hurt(this, x, y, 3);
        super.bumpedInto(level, x, y, entity);
    }
}
