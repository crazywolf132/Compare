// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import java.util.List;
import com.mojang.ld22.level.tile.Tile;

public class Enterhakending extends Entity
{
    private int lifeTime;
    private int xdir;
    private int ydir;
    private final int speed = 4;
    private int time;
    private int damage;
    private Mob owner;
    private boolean stuck;
    
    public Enterhakending(final Mob owner, final int dirx, final int diry, final int dmg) {
        super("Enterhakending");
        this.stuck = false;
        this.owner = owner;
        this.xdir = dirx;
        this.ydir = diry;
        this.damage = dmg;
        this.x = owner.x;
        this.y = owner.y;
        this.lifeTime = 60;
        if (owner instanceof Player && ((Player)owner).oneshotmobs) {
            this.damage = 9999;
        }
    }
    
    @Override
    public void tick() {
        ++this.time;
        if (this.time >= this.lifeTime && !this.stuck) {
            this.level.player.ishooking = false;
            this.remove();
            return;
        }
        if (!this.stuck) {
            this.level.add(new Enterhakenseil(this.xdir, this.ydir, this));
            this.x += this.xdir * 4;
            this.y += this.ydir * 4;
        }
        final List<Entity> entitylist = this.level.getEntities(this.x - 10, this.y - 10, this.x + 10, this.y + 10);
        for (int i = 0; i < entitylist.size(); ++i) {
            final Entity hit = entitylist.get(i);
            if (hit != null) {
                if (hit instanceof Player && this.stuck) {
                    ((Player)hit).ishooking = false;
                    this.remove();
                }
                if (hit instanceof Mob && !(hit instanceof Player)) {
                    this.stuck = true;
                    ((Mob)hit).hurt(this.level.player, 1, 0);
                }
                if (hit instanceof ItemEntity) {
                    ((ItemEntity)hit).touchedBy(this.level.player);
                }
            }
        }
        if (!this.level.getTile(this.x / 16, this.y / 16).mayPass(this.level, this.x / 16, this.y / 16, this) && this.level.getTile(this.x / 16, this.y / 16).id != Tile.water.id && this.level.getTile(this.x / 16, this.y / 16).id != Tile.lava.id) {
            this.stuck = true;
        }
        if (this.stuck) {
            this.level.remove(this.level.player);
            if (this.level.player.x < this.x) {
                final Player player = this.level.player;
                player.x += 4;
            }
            else if (this.level.player.x > this.x) {
                final Player player2 = this.level.player;
                player2.x -= 4;
            }
            if (this.level.player.y < this.y) {
                final Player player3 = this.level.player;
                player3.y += 4;
            }
            else if (this.level.player.y > this.y) {
                final Player player4 = this.level.player;
                player4.y -= 4;
            }
            this.level.add(this.level.player);
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
            screen.render(this.x - 4, this.y - 4 - 2, xt + yt * 32, Color.get(-1, 555, 555, 555), this.random.nextInt(4));
            screen.render(this.x - 4, this.y - 4 + 2, xt + yt * 32, Color.get(-1, 0, 0, 0), this.random.nextInt(4));
        }
        else {
            final int xt = 1;
            final int yt = 11;
            screen.render(this.x - 4, this.y - 4 - 2, xt + yt * 32, Color.get(-1, 555, 555, 555), this.random.nextInt(4));
            screen.render(this.x - 4, this.y - 4 + 2, xt + yt * 32, Color.get(-1, 0, 0, 0), this.random.nextInt(4));
        }
    }
}
