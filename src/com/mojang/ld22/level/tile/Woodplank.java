// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.level.tile;

import com.mojang.ld22.entity.particle.TextParticle;
import com.mojang.ld22.entity.particle.SmashParticle;
import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.item.ToolType;
import com.mojang.ld22.item.ToolItem;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.gfx.Screen;

public class Woodplank extends Tile
{
    public Woodplank(final int id) {
        super(id);
    }
    
    @Override
    public void render(final Screen screen, final Level level, final int x, final int y) {
        final int color = Color.get(level.dirtColor, 430, 420, 320);
        final int xt = 21;
        screen.render(x * 16 + 0, y * 16 + 0, xt + 32, color, 0);
        screen.render(x * 16 + 8, y * 16 + 0, xt + 1 + 32, color, 0);
        screen.render(x * 16 + 0, y * 16 + 8, xt + 64, color, 0);
        screen.render(x * 16 + 8, y * 16 + 8, xt + 1 + 64, color, 0);
        super.render(screen, level, x, y);
    }
    
    @Override
    public boolean mayPass(final Level level, final int x, final int y, final Entity e) {
        return false;
    }
    
    @Override
    public void hurt(final Level level, final int x, final int y, final Mob source, final int dmg, final int attackDir) {
    }
    
    @Override
    public boolean interact(final Level level, final int xt, final int yt, final Player player, final Item item, final int attackDir) {
        if (item instanceof ToolItem) {
            final ToolItem tool = (ToolItem)item;
            if (tool.type == ToolType.axe && player.payStamina(4 - tool.level)) {
                this.hurt(level, xt, yt, this.random.nextInt(10) + tool.level * 5 + 10);
                return true;
            }
            if (tool.type == ToolType.hammer && tool.level >= 0) {
                player.payStamina(6 - tool.level);
                level.add(new ItemEntity(new ResourceItem(Resource.woodplank), xt * 16, yt * 16));
                level.setTile(xt, yt, Tile.dirt, 0);
            }
        }
        return false;
    }
    
    private void hurt(final Level level, final int x, final int y, final int dmg) {
        for (int count = (this.random.nextInt(10) == 0) ? 1 : 0, i = 0; i < count; ++i) {
            level.add(new ItemEntity(new ResourceItem(Resource.apple), x * 16 + this.random.nextInt(10) + 3, y * 16 + this.random.nextInt(10) + 3));
        }
        final int damage = level.getData(x, y) + dmg;
        level.add(new SmashParticle(x * 16 + 8, y * 16 + 8));
        level.add(new TextParticle(new StringBuilder().append(dmg).toString(), x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500)));
        if (damage >= 20) {
            for (int count2 = this.random.nextInt(4) + 1, j = 0; j < count2; ++j) {
                level.add(new ItemEntity(new ResourceItem(Resource.wood), x * 16 + this.random.nextInt(10) + 3, y * 16 + this.random.nextInt(10) + 3));
            }
            level.setTile(x, y, Tile.grass, 0);
        }
        else {
            level.setData(x, y, damage);
        }
    }
    
    @Override
    public boolean canBurn() {
        return true;
    }
}
