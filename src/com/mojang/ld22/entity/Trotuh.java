// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import com.mojang.ld22.item.Experience;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.level.tile.Tile;

public class Trotuh extends Mob
{
    private int xa;
    private int ya;
    public int lvl;
    private int randomWalkTime;
    static boolean atkplayer;
    
    static {
        Trotuh.atkplayer = false;
    }
    
    public Trotuh(final int lvl) {
        super("Trutuh");
        this.randomWalkTime = 0;
        this.lvl = lvl;
        this.x = this.random.nextInt(1024);
        this.y = this.random.nextInt(1024);
        final int n = lvl * lvl * 10;
        this.maxHealth = n;
        this.health = n;
    }
    
    @Override
    public void tick() {
        if (this.level.game.mpstate != 1) {
            super.tick();
            this.level.setTile(this.x, this.y, Tile.dirt, 0);
            this.level.setTile(this.x / 16, this.y / 16, Tile.dirt, 0);
            if (this.level.player != null && Trotuh.atkplayer) {
                final int xd = this.level.player.x - this.x;
                final int yd = this.level.player.y - this.y;
                this.xa = 0;
                this.ya = 0;
                if (xd < 0) {
                    this.xa = -1;
                }
                if (xd > 0) {
                    this.xa = 1;
                }
                if (yd < 0) {
                    this.ya = -1;
                }
                if (yd > 0) {
                    this.ya = 1;
                }
            }
            final int speed = this.tickTime & 0x1;
            if (!this.move(this.xa * speed, this.ya * speed) || this.random.nextInt(200) == 0) {
                this.xa = (this.random.nextInt(3) - 1) * this.random.nextInt(2);
                this.ya = (this.random.nextInt(3) - 1) * this.random.nextInt(2);
            }
        }
    }
    
    @Override
    public boolean move(final int xa, final int ya) {
        this.level.setTile(this.x, this.y, Tile.dirt, 0);
        return super.move(xa, ya);
    }
    
    @Override
    public void render(final Screen screen) {
        int xt = 16;
        final int yt = 16;
        int flip1 = this.walkDist >> 3 & 0x1;
        int flip2 = this.walkDist >> 3 & 0x1;
        if (this.dir == 1) {
            xt += 2;
        }
        if (this.dir > 1) {
            flip1 = 0;
            flip2 = (this.walkDist >> 4 & 0x1);
            if (this.dir == 2) {
                flip1 = 1;
            }
            xt += 4 + (this.walkDist >> 3 & 0x1) * 2;
        }
        final int xo = this.x - 8;
        final int yo = this.y - 11;
        int col = Color.get(-1, 0, 502, 401);
        if (this.hurtTime > 0) {
            col = Color.get(-1, 555, 555, 555);
        }
        screen.render(xo + 8 * flip1, yo + 0, xt + yt * 32, col, flip1);
        screen.render(xo + 8 - 8 * flip1, yo + 0, xt + 1 + yt * 32, col, flip1);
        if (!this.isSwimming()) {
            screen.render(xo + 8 * flip2, yo + 8, xt + (yt + 1) * 32, col, flip2);
            screen.render(xo + 8 - 8 * flip2, yo + 8, xt + 1 + (yt + 1) * 32, col, flip2);
        }
    }
    
    @Override
    protected void touchedBy(final Entity entity) {
        if (entity instanceof Player && Trotuh.atkplayer) {
            entity.hurt(this, this.lvl + 1, this.dir);
        }
    }
    
    @Override
    public boolean canSwim() {
        return false;
    }
    
    @Override
    protected void die() {
        super.die();
        Trotuh.atkplayer = true;
        for (int count = this.random.nextInt(2) + 1, i = 0; i < count; ++i) {
            if (!this.isSwimming()) {
                this.level.add(new ItemEntity(new ResourceItem(Resource.cloth), this.x + this.random.nextInt(11) - 5, this.y + this.random.nextInt(11) - 5));
            }
        }
        if (this.level.player != null && !this.isSwimming()) {
            final Player player = this.level.player;
            player.score += 50 * this.lvl;
            this.level.add(new ItemEntity(new Experience(this.level.player, 10 * this.lvl), this.x + this.random.nextInt(11) - 5, this.y + this.random.nextInt(11) - 5));
        }
    }
}
