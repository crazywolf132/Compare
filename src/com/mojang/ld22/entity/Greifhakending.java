// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import java.util.List;
import com.mojang.ld22.level.tile.Tile;

public class Greifhakending extends Entity
{
    private int lifeTime;
    private int xdir;
    private int ydir;
    private final int speed = 4;
    private int time;
    private int damage;
    private Mob owner;
    public boolean stuck;
    public boolean ispushing;
    public boolean ispulling;
    public boolean ispullingwood;
    public boolean isfallingback;
    private int treex;
    private int treey;
    private Mob stuckon;
    
    public Greifhakending(final Mob owner, final int dirx, final int diry, final int dmg) {
        super("Greifhakending");
        this.stuck = false;
        this.ispushing = false;
        this.ispulling = false;
        this.ispullingwood = false;
        this.isfallingback = false;
        this.treex = 0;
        this.treey = 0;
        this.owner = owner;
        this.xdir = dirx;
        this.ydir = diry;
        this.damage = dmg;
        this.x = owner.x;
        this.y = owner.y;
        this.lifeTime = 40;
        if (owner instanceof Player && ((Player)owner).oneshotmobs) {
            this.damage = 9999;
        }
    }
    
    @Override
    public void tick() {
        ++this.time;
        if (this.time >= this.lifeTime && !this.stuck) {
            this.level.player.isgrabbing = false;
            this.remove();
            return;
        }
        if (!this.stuck) {
            this.x += this.xdir * 4;
            this.y += this.ydir * 4;
        }
        final List<Entity> entitylist = this.level.getEntities(this.x - 10, this.y - 10, this.x + 10, this.y + 10);
        for (int i = 0; i < entitylist.size(); ++i) {
            final Entity hit = entitylist.get(i);
            if (hit != null) {
                if (hit instanceof Player && (this.stuck || this.ispullingwood)) {
                    this.level.player.isgrabbing = false;
                    this.remove();
                    return;
                }
                if (hit instanceof Mob && !(hit instanceof Player) && !this.stuck && !this.level.player.isgrabbing) {
                    this.stuck = true;
                    this.stuckon = (Mob)hit;
                    this.level.player.isgrabbing = true;
                }
                if (hit instanceof Mob && !(hit instanceof Player) && this.ispullingwood) {
                    ((Mob)hit).hurt(this.level.player, 6, 0);
                }
                if (hit instanceof ItemEntity) {
                    ((ItemEntity)hit).touchedBy(this.level.player);
                }
            }
        }
        if (this.level.getTile(this.x / 16, this.y / 16).id == Tile.tree.id) {
            this.stuck = true;
            this.treex = this.x - 8;
            this.treey = this.y - 20;
        }
        if (!this.level.getTile(this.x / 16, this.y / 16).mayPass(this.level, this.x / 16, this.y / 16, this) && this.level.getTile(this.x / 16, this.y / 16).id != Tile.water.id && this.level.getTile(this.x / 16, this.y / 16).id != Tile.lava.id) {
            this.stuck = true;
        }
        if (this.stuck && this.isfallingback) {
            this.level.remove(this);
            if (this.treex < this.level.player.x) {
                this.x += 4;
            }
            else if (this.treex > this.level.player.x) {
                this.x -= 4;
            }
            if (this.treey < this.level.player.y) {
                this.y += 4;
            }
            else if (this.treey > this.level.player.y) {
                this.y -= 4;
            }
            this.level.add(this);
        }
        if (this.stuck && this.ispushing) {
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
        if (this.stuck && this.ispulling) {
            this.level.remove(this.stuckon);
            this.level.remove(this);
            if (this.stuckon.x < this.level.player.x) {
                final Mob stuckon = this.stuckon;
                stuckon.x += 4;
                if (this.x < this.level.player.x) {
                    this.x += 4;
                }
            }
            else if (this.stuckon.x > this.level.player.x) {
                final Mob stuckon2 = this.stuckon;
                stuckon2.x -= 4;
                if (this.x > this.level.player.x) {
                    this.x -= 4;
                }
            }
            if (this.stuckon.y < this.level.player.y) {
                final Mob stuckon3 = this.stuckon;
                stuckon3.y += 4;
                if (this.y < this.level.player.y) {
                    this.y += 4;
                }
            }
            else if (this.stuckon.y > this.level.player.y) {
                final Mob stuckon4 = this.stuckon;
                stuckon4.y -= 4;
                if (this.y > this.level.player.y) {
                    this.y -= 4;
                }
            }
            this.level.add(this.stuckon);
            this.level.add(this);
        }
        if (this.stuck && this.ispullingwood) {
            this.level.remove(this);
            if (this.treex < this.level.player.x - 8) {
                this.treex += 4;
                if (this.x < this.level.player.x) {
                    this.x += 4;
                }
            }
            else if (this.treex > this.level.player.x - 8) {
                this.treex -= 4;
                if (this.x > this.level.player.x) {
                    this.x -= 4;
                }
            }
            if (this.treey < this.level.player.y - 10) {
                this.treey += 4;
                if (this.y < this.level.player.y) {
                    this.y += 4;
                }
            }
            else if (this.treey > this.level.player.y - 10) {
                this.treey -= 4;
                if (this.y > this.level.player.y) {
                    this.y -= 4;
                }
            }
            this.level.add(this);
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
        if (this.removed) {
            return;
        }
        boolean isread = false;
        int procx = this.level.player.x;
        int procy = this.level.player.y;
        int xdir = 0;
        if (this.ispullingwood) {
            final int col = Color.get(10, 30, 151, -1);
            final int barkCol1 = Color.get(10, 30, 430, -1);
            final int barkCol2 = Color.get(10, 30, 320, -1);
            screen.render(this.treex, this.treey, 9, col, 0);
            screen.render(this.treex + 8, this.treey + 0, 10, col, 0);
            screen.render(this.treex + 0, this.treey + 8, 41, barkCol1, 0);
            screen.render(this.treex + 8, this.treey + 8, 106, barkCol2, 0);
        }
        while (!isread) {
            if (procx < this.x) {
                ++procx;
                xdir = 1;
            }
            else if (procx > this.x) {
                --procx;
                xdir = 1;
            }
            if (procy < this.y) {
                ++procy;
                xdir = 0;
            }
            else if (procy > this.y) {
                --procy;
                xdir = 0;
            }
            int xt = 5;
            int yt = 11;
            if (xdir != 0) {
                screen.render(procx, procy, xt + yt * 32, Color.get(-1, 0, 0, 0), 0);
            }
            else {
                xt = 6;
                yt = 11;
                screen.render(procx, procy, xt + yt * 32, Color.get(-1, 0, 0, 0), 0);
            }
            if (procx == this.x && procy == this.y) {
                isread = true;
            }
        }
        if (xdir != 0) {
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
