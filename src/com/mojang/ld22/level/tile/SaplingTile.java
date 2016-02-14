// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.level.tile;

import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.gfx.Screen;

public class SaplingTile extends Tile
{
    private Tile onType;
    private Tile growsTo;
    
    public SaplingTile(final int id, final Tile onType, final Tile growsTo) {
        super(id);
        this.onType = onType;
        this.growsTo = growsTo;
        this.connectsToSand = onType.connectsToSand;
        this.connectsToGrass = onType.connectsToGrass;
        this.connectsToWater = onType.connectsToWater;
        this.connectsToLava = onType.connectsToLava;
    }
    
    @Override
    public void render(final Screen screen, final Level level, final int x, final int y) {
        this.onType.render(screen, level, x, y);
        final int col = Color.get(10, 40, 50, -1);
        screen.render(x * 16 + 4, y * 16 + 4, 107, col, 0);
        super.render(screen, level, x, y);
    }
    
    @Override
    public void tick(final Level level, final int x, final int y) {
        final int age = level.getData(x, y) + 1;
        if (age > 100) {
            level.setTile(x, y, this.growsTo, 0);
        }
        else {
            level.setData(x, y, age);
        }
        super.tick(level, x, y);
    }
    
    @Override
    public boolean canBurn() {
        return true;
    }
    
    @Override
    public void hurt(final Level level, final int x, final int y, final Mob source, final int dmg, final int attackDir) {
        level.setTile(x, y, this.onType, 0);
    }
}
