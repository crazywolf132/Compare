// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import de.thejackimonster.ld22.options.OptionFile;
import com.mojang.ld22.gfx.Color;
import java.util.Random;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.Experience;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.item.resource.Resource;

public class Slime extends Mob
{
    private int xa;
    private int ya;
    public int jumpTime;
    public final int lvl;
    
    public Slime(final int lvl) {
        super("Slime");
        this.jumpTime = 0;
        this.lvl = lvl;
        this.x = this.random.nextInt(1024);
        this.y = this.random.nextInt(1024);
        final int n = lvl * lvl * 5;
        this.maxHealth = n;
        this.health = n;
    }
    
    @Override
    public void tick() {
        if (this.level.game.mpstate != 1) {
            super.tick();
            final int speed = 1;
            if ((!this.move(this.xa * speed, this.ya * speed) || this.random.nextInt(40) == 0) && this.jumpTime <= -10) {
                this.xa = this.random.nextInt(3) - 1;
                this.ya = this.random.nextInt(3) - 1;
                if (this.level.player != null) {
                    final int xd = this.level.player.x - this.x;
                    final int yd = this.level.player.y - this.y;
                    if (xd * xd + yd * yd < 2500) {
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
                }
                if (this.xa != 0 || this.ya != 0) {
                    this.jumpTime = 10;
                }
            }
            --this.jumpTime;
            if (this.jumpTime == 0) {
                final boolean b = false;
                this.ya = (b ? 1 : 0);
                this.xa = (b ? 1 : 0);
            }
        }
    }
    
    @Override
    protected void die() {
        super.die();
        for (int count = this.random.nextInt(2) + 1, i = 0; i < count; ++i) {
            this.level.add(new ItemEntity(new ResourceItem(Resource.slime), this.x + this.random.nextInt(11) - 5, this.y + this.random.nextInt(11) - 5));
        }
        if (this.level.player != null) {
            final Player player = this.level.player;
            player.score += 25 * this.lvl;
            this.level.add(new ItemEntity(new Experience(this.level.player, 10 * this.lvl), this.x + this.random.nextInt(11) - 5, this.y + this.random.nextInt(11) - 5));
        }
    }
    
    @Override
    public void render(final Screen screen) {
        final Random random = new Random();
        final int rnd = random.nextInt(455);
        int xt = 0;
        final int yt = 18;
        final int xo = this.x - 8;
        int yo = this.y - 11;
        if (this.jumpTime > 0) {
            xt += 2;
            yo -= 4;
        }
        int col = Color.get(-1, 10, 252, 555);
        if (this.lvl == 2) {
            col = Color.get(-1, 100, 522, 555);
        }
        if (this.lvl == 3) {
            col = Color.get(-1, 111, 444, 555);
        }
        if (this.lvl == 4) {
            col = Color.get(-1, 0, rnd + 111, 224);
        }
        if (this.hurtTime > 0) {
            col = Color.get(-1, 555, 555, 555);
        }
        screen.render(xo + 0, yo + 0, xt + yt * 32, col, 0);
        screen.render(xo + 8, yo + 0, xt + 1 + yt * 32, col, 0);
        screen.render(xo + 0, yo + 8, xt + (yt + 1) * 32, col, 0);
        screen.render(xo + 8, yo + 8, xt + 1 + (yt + 1) * 32, col, 0);
    }
    
    @Override
    protected void touchedBy(final Entity entity) {
        if (entity instanceof Player) {
            entity.hurt(this, this.lvl, this.dir * OptionFile.difficulty);
        }
    }
}
