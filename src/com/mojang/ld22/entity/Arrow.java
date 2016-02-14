// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import com.mojang.ld22.gfx.Screen;
import java.util.List;
import de.thejackimonster.ld22.options.OptionFile;
import com.mojang.ld22.gfx.Color;

public class Arrow extends Entity
{
    private int lifeTime;
    private int xdir;
    private int ydir;
    private final int speed = 2;
    private int time;
    private int damage;
    private Mob owner;
    private int color;
    
    public Arrow(final Mob owner, final int dirx, final int diry, final int dmg, final boolean flag) {
        super("Arrow");
        this.owner = owner;
        this.xdir = dirx;
        this.ydir = diry;
        this.damage = dmg;
        this.color = Color.get(-1, 555, 555, 555);
        if (flag) {
            this.damage *= 2;
            this.color = Color.get(-1, 540, 540, 540);
        }
        this.x = owner.x;
        this.y = owner.y;
        this.lifeTime = 180 + this.random.nextInt(30);
        if (owner instanceof Player && ((Player)owner).oneshotmobs) {
            this.damage = 9999;
        }
    }
    
    @Override
    public void tick() {
        ++this.time;
        if (this.time >= this.lifeTime) {
            this.remove();
            return;
        }
        this.x += this.xdir * 2;
        this.y += this.ydir * 2;
        final List<Entity> entitylist = this.level.getEntities(this.x, this.y, this.x, this.y);
        for (int i = 0; i < entitylist.size(); ++i) {
            final Entity hit = entitylist.get(i);
            if (hit != null && hit instanceof Mob && hit != this.owner) {
                hit.hurt(this.owner, this.damage, ((Mob)hit).dir ^ OptionFile.difficulty);
            }
        }
        if (!this.level.getTile(this.x / 16, this.y / 16).mayPass(this.level, this.x / 16, this.y / 16, this)) {
            this.remove();
        }
    }
    
    @Override
    public boolean isBlockableBy(final Mob mob) {
        return false;
    }
    
    @Override
    public void render(final Screen screen) {
        if (this.time >= this.lifeTime - 120 && this.time / 6 % 2 == 0) {
            return;
        }
        if (this.xdir != 0) {
            final int xt = 2;
            final int yt = 11;
            screen.render(this.x - 4, this.y - 4 - 2, xt + yt * 32, this.color, this.random.nextInt(4));
            screen.render(this.x - 4, this.y - 4 + 2, xt + yt * 32, Color.get(-1, 0, 0, 0), this.random.nextInt(4));
        }
        else {
            final int xt = 1;
            final int yt = 11;
            screen.render(this.x - 4, this.y - 4 - 2, xt + yt * 32, this.color, this.random.nextInt(4));
            screen.render(this.x - 4, this.y - 4 + 2, xt + yt * 32, Color.get(-1, 0, 0, 0), this.random.nextInt(4));
        }
    }
}
