// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.FurnitureItem;
import com.mojang.ld22.item.PowerGloveItem;

public class Furniture extends Entity
{
    private int pushTime;
    private int pushDir;
    public int col;
    public int sprite;
    private Player shouldTake;
    
    public Furniture(final String name) {
        super(name);
        this.pushTime = 0;
        this.pushDir = -1;
        this.name = name;
        this.xr = 3;
        this.yr = 3;
    }
    
    @Override
    public void tick() {
        if (this.shouldTake != null) {
            if (this.shouldTake.activeItem instanceof PowerGloveItem) {
                this.remove();
                this.shouldTake.inventory.add(0, this.shouldTake.activeItem);
                this.shouldTake.activeItem = new FurnitureItem(this);
            }
            this.shouldTake = null;
        }
        if (this.pushDir == 0) {
            this.move(0, 1);
        }
        if (this.pushDir == 1) {
            this.move(0, -1);
        }
        if (this.pushDir == 2) {
            this.move(-1, 0);
        }
        if (this.pushDir == 3) {
            this.move(1, 0);
        }
        this.pushDir = -1;
        if (this.pushTime > 0) {
            --this.pushTime;
        }
    }
    
    @Override
    public void render(final Screen screen) {
        if (this instanceof Torch) {
            if (((Torch)this).on) {
                screen.render(this.x - 8, this.y - 8 - 4, 51, this.col, 0);
                screen.render(this.x - 0, this.y - 8 - 4, 52, this.col, 0);
                screen.render(this.x - 8, this.y - 0 - 4, 83, this.col, 0);
                screen.render(this.x - 0, this.y - 0 - 4, 84, this.col, 0);
            }
            else {
                screen.render(this.x - 8, this.y - 8 - 4, this.sprite * 2 + 256, this.col, 0);
                screen.render(this.x - 0, this.y - 8 - 4, this.sprite * 2 + 256 + 1, this.col, 0);
                screen.render(this.x - 8, this.y - 0 - 4, this.sprite * 2 + 256 + 32, this.col, 0);
                screen.render(this.x - 0, this.y - 0 - 4, this.sprite * 2 + 256 + 33, this.col, 0);
            }
        }
        else {
            screen.render(this.x - 8, this.y - 8 - 4, this.sprite * 2 + 256, this.col, 0);
            screen.render(this.x - 0, this.y - 8 - 4, this.sprite * 2 + 256 + 1, this.col, 0);
            screen.render(this.x - 8, this.y - 0 - 4, this.sprite * 2 + 256 + 32, this.col, 0);
            screen.render(this.x - 0, this.y - 0 - 4, this.sprite * 2 + 256 + 33, this.col, 0);
        }
    }
    
    @Override
    public boolean blocks(final Entity e) {
        return true;
    }
    
    @Override
    protected void touchedBy(final Entity entity) {
        if (entity instanceof Player && this.pushTime == 0) {
            this.pushDir = ((Player)entity).dir;
            if (((Player)entity).activeItem instanceof PowerGloveItem) {
                this.pushTime = 3;
            }
            else {
                this.pushTime = 10;
            }
            if (((Player)entity).game.mpstate == 2) {
                ((Player)entity).game.server.updatefurnature();
            }
        }
    }
    
    public void take(final Player player) {
        this.shouldTake = player;
    }
}
