// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import java.util.Collection;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.particle.TextParticle;
import com.mojang.ld22.entity.particle.SmashParticle;
import com.mojang.ld22.entity.particle.Particle;
import com.mojang.ld22.InputHandler;
import com.mojang.ld22.Game;
import java.util.ArrayList;
import java.util.List;
import com.mojang.ld22.level.Level;
import java.util.Random;
import java.io.Serializable;

public class Entity implements Serializable
{
    protected final Random random;
    public int x;
    public int y;
    public int xr;
    public int yr;
    public boolean removed;
    public boolean walkthroughwalls;
    public boolean nonockback;
    public String name;
    public static int id;
    public Level level;
    public static List<Entity> entities;
    public static final Entity player;
    public static final Entity airwizard;
    public static final Entity anvil;
    public static final Entity chest;
    public static final Entity furnace;
    public static final Entity lantern;
    public static final Entity oven;
    public static final Entity slime;
    public static final Entity spark;
    public static final Entity tnt;
    public static final Entity torch;
    public static final Entity workbench;
    public static final Entity zombie;
    public static final Entity paricle;
    public static final Entity smashparticle;
    public static final Entity textparticle;
    
    static {
        Entity.entities = new ArrayList<Entity>();
        player = new Player(new Game(), new InputHandler(new Game()));
        airwizard = new AirWizard();
        anvil = new Anvil();
        chest = new Chest();
        furnace = new Furnace();
        lantern = new Lantern();
        oven = new Oven();
        slime = new Slime(0);
        spark = new Spark(new AirWizard(), 0.0, 0.0);
        tnt = new TNT();
        torch = new Torch();
        workbench = new Workbench();
        zombie = new Zombie(0);
        paricle = new Particle();
        smashparticle = new SmashParticle(0, 0);
        textparticle = new TextParticle("", 0, 0, 0);
        for (int i = 0; i < Item.items.size(); ++i) {
            final Entity itementity = new ItemEntity(Item.items.get(i), 0, 0);
            itementity.remove();
        }
    }
    
    public Entity(final String name) {
        this.random = new Random();
        this.xr = 6;
        this.yr = 6;
        this.walkthroughwalls = false;
        this.nonockback = false;
        addEntity(this, this.name = name);
    }
    
    public static void addEntity(final Entity entity, final String s) {
        boolean flag = true;
        for (int i = 0; i < Entity.entities.size(); ++i) {
            if (Entity.entities.get(i).name.equalsIgnoreCase(s)) {
                flag = false;
                Entity.id = i;
            }
        }
        if (flag) {
            Entity.id = Entity.entities.size();
            Entity.entities.add(entity);
        }
    }
    
    public static int getIDOfName(final String name) {
        int i1 = 0;
        for (int j = 0; j < Entity.entities.size(); ++j) {
            if (Entity.entities.get(j).name.equalsIgnoreCase(name)) {
                i1 = j;
            }
        }
        return i1;
    }
    
    public void render(final Screen screen) {
    }
    
    public void tick() {
    }
    
    public void remove() {
        this.removed = true;
    }
    
    public final void init(final Level level) {
        this.level = level;
    }
    
    public boolean intersects(final int x0, final int y0, final int x1, final int y1) {
        return this.x + this.xr >= x0 && this.y + this.yr >= y0 && this.x - this.xr <= x1 && this.y - this.yr <= y1;
    }
    
    public boolean blocks(final Entity e) {
        return false;
    }
    
    public void hurt(final Mob mob, final int dmg, final int attackDir) {
    }
    
    public void hurt(final Tile tile, final int x, final int y, final int dmg) {
    }
    
    public boolean move(final int xa, final int ya) {
        if (xa != 0 || ya != 0) {
            boolean stopped = true;
            if (xa != 0 && this.move2(xa, 0)) {
                stopped = false;
            }
            if (ya != 0 && this.move2(0, ya)) {
                stopped = false;
            }
            if (!stopped) {
                final int xt = this.x >> 4;
                final int yt = this.y >> 4;
                this.level.getTile(xt, yt).steppedOn(this.level, xt, yt, this);
            }
            return !stopped;
        }
        return true;
    }
    
    protected boolean move2(final int xa, final int ya) {
        if (xa != 0 && ya != 0) {
            throw new IllegalArgumentException("Move2 can only move along one axis at a time!");
        }
        final int xto0 = this.x - this.xr >> 4;
        final int yto0 = this.y - this.yr >> 4;
        final int xto2 = this.x + this.xr >> 4;
        final int yto2 = this.y + this.yr >> 4;
        final int xt0 = this.x + xa - this.xr >> 4;
        final int yt0 = this.y + ya - this.yr >> 4;
        final int xt2 = this.x + xa + this.xr >> 4;
        final int yt2 = this.y + ya + this.yr >> 4;
        boolean blocked = false;
        for (int yt3 = yt0; yt3 <= yt2; ++yt3) {
            for (int xt3 = xt0; xt3 <= xt2; ++xt3) {
                if (xt3 < xto0 || xt3 > xto2 || yt3 < yto0 || yt3 > yto2) {
                    if (!this.walkthroughwalls && !this.nonockback) {
                        this.level.getTile(xt3, yt3).bumpedInto(this.level, xt3, yt3, this);
                    }
                    if (!this.level.getTile(xt3, yt3).mayPass(this.level, xt3, yt3, this) && !this.walkthroughwalls) {
                        blocked = true;
                        return false;
                    }
                }
            }
        }
        if (blocked) {
            return false;
        }
        final List<Entity> wasInside = this.level.getEntities(this.x - this.xr, this.y - this.yr, this.x + this.xr, this.y + this.yr);
        final List<Entity> isInside = this.level.getEntities(this.x + xa - this.xr, this.y + ya - this.yr, this.x + xa + this.xr, this.y + ya + this.yr);
        for (int i = 0; i < isInside.size(); ++i) {
            final Entity e = isInside.get(i);
            if (e != this) {
                e.touchedBy(this);
            }
        }
        isInside.removeAll(wasInside);
        for (int i = 0; i < isInside.size(); ++i) {
            final Entity e = isInside.get(i);
            if (e != this) {
                if (e.blocks(this)) {
                    return false;
                }
            }
        }
        this.x += xa;
        this.y += ya;
        return true;
    }
    
    protected void touchedBy(final Entity entity) {
    }
    
    public boolean isBlockableBy(final Mob mob) {
        return true;
    }
    
    public void touchItem(final ItemEntity itemEntity) {
    }
    
    public boolean canSwim() {
        return false;
    }
    
    public boolean interact(final Player player, final Item item, final int attackDir) {
        return item.interact(player, this, attackDir);
    }
    
    public boolean use(final Player player, final int attackDir) {
        return false;
    }
    
    public int getLightRadius() {
        return 0;
    }
}
