// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.level.tile;

import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.item.ToolType;
import com.mojang.ld22.item.ToolItem;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.gfx.Screen;

public class Woodendoor extends Tile
{
    public Woodendoor(final int id) {
        super(id);
    }
    
    @Override
    public void render(final Screen screen, final Level level, final int x, final int y) {
        int color;
        if (level.getData(x, y) == 0) {
            color = Color.get(level.dirtColor, 430, 420, 320);
        }
        else {
            color = Color.get(level.dirtColor, level.dirtColor, level.dirtColor, 320);
        }
        final int xt = 25;
        screen.render(x * 16 + 0, y * 16 + 0, xt + 32, color, 0);
        screen.render(x * 16 + 8, y * 16 + 0, xt + 1 + 32, color, 0);
        screen.render(x * 16 + 0, y * 16 + 8, xt + 64, color, 0);
        screen.render(x * 16 + 8, y * 16 + 8, xt + 1 + 64, color, 0);
        super.render(screen, level, x, y);
    }
    
    @Override
    public boolean mayPass(final Level level, final int x, final int y, final Entity e) {
        return level.getData(x, y) != 0;
    }
    
    @Override
    public void hurt(final Level level, final int x, final int y, final Mob source, final int dmg, final int attackDir) {
        if (level.getData(x, y) == 0) {
            level.setData(x, y, 1);
        }
        else if (level.getData(x, y) == 1) {
            level.setData(x, y, 0);
        }
        Sound.monsterHurt.play();
    }
    
    @Override
    public boolean interact(final Level level, final int xt, final int yt, final Player player, final Item item, final int attackDir) {
        if (level.getData(xt, yt) == 0) {
            level.setData(xt, yt, 1);
        }
        else if (level.getData(xt, yt) == 1) {
            level.setData(xt, yt, 0);
        }
        if (item instanceof ToolItem) {
            final ToolItem tool = (ToolItem)item;
            if (tool.type == ToolType.axe && player.payStamina(4 - tool.level)) {
                this.hurt(level, xt, yt, this.random.nextInt(10) + tool.level * 5 + 10);
                return true;
            }
            if (tool.type == ToolType.hammer && tool.level >= 0) {
                player.payStamina(6 - tool.level);
                level.add(new ItemEntity(new ResourceItem(Resource.wooddoor), xt * 16, yt * 16));
                level.setTile(xt, yt, Tile.dirt, 0);
            }
        }
        return false;
    }
    
    private void hurt(final Level level, final int x, final int y, final int dmg) {
    }
    
    @Override
    public boolean canBurn() {
        return true;
    }
}
