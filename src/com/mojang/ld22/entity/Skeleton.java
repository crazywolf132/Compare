// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;

public class Skeleton extends Zombie
{
    private int xa;
    private int ya;
    private int randomWalkTime;
    private int stamina;
    
    public Skeleton(final int lvl) {
        super(lvl);
        this.randomWalkTime = 0;
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.tickTime % 30 == 0 && this.stamina < 3) {
            ++this.stamina;
        }
        if (this.stamina > 3) {
            this.stamina = 3;
        }
        if (this.stamina < 0) {
            this.stamina = 0;
        }
        if (this.level.player != null && this.randomWalkTime == 0) {
            final int xd = this.level.player.x - this.x;
            final int yd = this.level.player.y - this.y;
            if (xd * xd + yd * yd < 3600) {
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
            if (xd * xd + yd * yd < 2500 && this.stamina == 3) {
                this.xa = 0;
                this.ya = 0;
                this.stamina -= 3;
                switch (this.dir) {
                    case 0: {
                        this.level.add(new Arrow(this, 0, 1, 1, this.walkoverwater));
                        break;
                    }
                    case 1: {
                        this.level.add(new Arrow(this, 0, -1, 1, this.walkoverwater));
                        break;
                    }
                    case 2: {
                        this.level.add(new Arrow(this, -1, 0, 1, this.walkoverwater));
                        break;
                    }
                    case 3: {
                        this.level.add(new Arrow(this, 1, 0, 1, this.walkoverwater));
                        break;
                    }
                }
            }
        }
        final int speed = this.tickTime & 0x1;
        if (!this.move(this.xa * speed, this.ya * speed) || this.random.nextInt(200) == 0 || this.stamina == 0) {
            this.randomWalkTime = 60;
            this.xa = (this.random.nextInt(3) - 1) * this.random.nextInt(2);
            this.ya = (this.random.nextInt(3) - 1) * this.random.nextInt(2);
        }
        if (this.randomWalkTime > 0) {
            --this.randomWalkTime;
        }
    }
    
    @Override
    public void render(final Screen screen) {
        int xt = 16;
        final int yt = 14;
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
        int col = Color.get(-1, 111, 444, 555);
        if (this.level.depth == -4) {
            col = Color.get(-1, 500, 444, 555);
        }
        if (this.hurtTime > 0) {
            col = Color.get(-1, 555, 555, 555);
        }
        screen.render(xo + 8 * flip1, yo + 0, xt + yt * 32, col, flip1);
        screen.render(xo + 8 - 8 * flip1, yo + 0, xt + 1 + yt * 32, col, flip1);
        screen.render(xo + 8 * flip2, yo + 8, xt + (yt + 1) * 32, col, flip2);
        screen.render(xo + 8 - 8 * flip2, yo + 8, xt + 1 + (yt + 1) * 32, col, flip2);
    }
    
    @Override
    public boolean canSwim() {
        return false;
    }
}
