// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import com.mojang.ld22.level.tile.WaterTile;
import com.mojang.ld22.level.tile.CarpetTile;
import com.mojang.ld22.level.tile.Woodfloor;
import com.mojang.ld22.level.tile.AntiMob;
import com.mojang.ld22.level.tile.StairsTile;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.entity.particle.TextParticle;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.tile.Tile;
import de.thejackimonster.ld22.story.dialog.NPC;
import de.thejackimonster.ld22.options.OptionFile;
import de.thejackimonster.ld22.vehicles.Vehicle;

public class Mob extends Entity
{
    public boolean walkoverwater;
    public int walkDist;
    public int dir;
    public int hurtTime;
    public int xKnockback;
    public int yKnockback;
    public int maxHealth;
    public int health;
    public int swimTimer;
    public int tickTime;
    public Vehicle toDrive;
    
    public Mob(final String name) {
        super(name);
        this.walkoverwater = false;
        this.walkDist = 0;
        this.dir = 0;
        this.hurtTime = 0;
        this.maxHealth = 10;
        this.health = this.maxHealth;
        this.swimTimer = 0;
        this.tickTime = 0;
        final int n = 8;
        this.y = n;
        this.x = n;
        this.xr = 4;
        this.yr = 3;
    }
    
    @Override
    public void tick() {
        ++this.tickTime;
        if (this.toDrive != null) {
            this.x = this.toDrive.x;
            this.y = this.toDrive.y;
        }
        if (OptionFile.difficulty <= 0 && !(this instanceof Player) && !(this instanceof NPC)) {
            this.remove();
        }
        if (this.level.getTile(this.x >> 4, this.y >> 4) == Tile.lava) {
            this.hurt(this, 4, this.dir ^ 0x1);
        }
        if (this.health <= 0) {
            this.die();
        }
        if (this.hurtTime > 0) {
            --this.hurtTime;
        }
    }
    
    protected void die() {
        this.level.game.mobdied = true;
        this.remove();
    }
    
    @Override
    public boolean move(final int xa, final int ya) {
        if (this.toDrive != null) {
            return true;
        }
        if (this.isSwimming() && !this.walkoverwater) {
            if (this instanceof Player) {
                if (this.swimTimer++ % 2 == 0) {
                    return true;
                }
            }
            else {
                if (this.swimTimer++ % 60 == 0) {
                    this.hurt(this, 1, 0);
                }
                this.xKnockback = 0;
                this.yKnockback = 0;
            }
        }
        if (this.xKnockback < 0) {
            this.move2(-1, 0);
            ++this.xKnockback;
        }
        if (this.xKnockback > 0) {
            this.move2(1, 0);
            --this.xKnockback;
        }
        if (this.yKnockback < 0) {
            this.move2(0, -1);
            ++this.yKnockback;
        }
        if (this.yKnockback > 0) {
            this.move2(0, 1);
            --this.yKnockback;
        }
        if (this.hurtTime > 0) {
            return true;
        }
        if (xa != 0 || ya != 0) {
            ++this.walkDist;
            if (xa < 0) {
                this.dir = 2;
            }
            if (xa > 0) {
                this.dir = 3;
            }
            if (ya < 0) {
                this.dir = 1;
            }
            if (ya > 0) {
                this.dir = 0;
            }
        }
        return super.move(xa, ya);
    }
    
    protected boolean isSwimming() {
        final Tile tile = this.level.getTile(this.x >> 4, this.y >> 4);
        return tile == Tile.water || tile == Tile.lava;
    }
    
    @Override
    public boolean blocks(final Entity e) {
        return e.isBlockableBy(this);
    }
    
    @Override
    public void hurt(final Tile tile, final int x, final int y, final int damage) {
        final int attackDir = this.dir ^ 0x1;
        this.doHurt(damage, attackDir);
    }
    
    @Override
    public void hurt(final Mob mob, final int damage, final int attackDir) {
        this.doHurt(damage, attackDir);
    }
    
    public void heal(final int heal) {
        if (this.hurtTime > 0) {
            return;
        }
        this.level.add(new TextParticle(new StringBuilder().append(heal).toString(), this.x, this.y, Color.get(-1, 50, 50, 50)));
        this.health += heal;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }
    
    protected void doHurt(final int damage, final int attackDir) {
        if (this.hurtTime > 0) {
            return;
        }
        if (this.level.player != null) {
            final int xd = this.level.player.x - this.x;
            final int yd = this.level.player.y - this.y;
            if (xd * xd + yd * yd < 6400) {
                Sound.monsterHurt.play();
            }
        }
        if (this.level.game.mpstate != 1) {
            this.level.add(new TextParticle(new StringBuilder().append(damage).toString(), this.x, this.y, Color.get(-1, 500, 500, 500)));
        }
        if (this.level.game.mpstate == 2 && damage > 1) {
            this.level.game.server.sendTextParticle(this.x, this.y, Color.get(-1, 500, 500, 500), damage);
        }
        this.health -= damage;
        if (attackDir == 0) {
            this.yKnockback = 6;
        }
        if (attackDir == 1) {
            this.yKnockback = -6;
        }
        if (attackDir == 2) {
            this.xKnockback = -6;
        }
        if (attackDir == 3) {
            this.xKnockback = 6;
        }
        this.hurtTime = 10;
    }
    
    public boolean findStartPos(final Level level) {
        final int x = this.random.nextInt(level.w);
        final int y = this.random.nextInt(level.h);
        final int xx = x * 16 + 8;
        final int yy = y * 16 + 8;
        if (level.player != null) {
            final int xd = level.player.x - xx;
            final int yd = level.player.y - yy;
            if (xd * xd + yd * yd < 6400) {
                return false;
            }
        }
        if (level.getTile(x, y) instanceof StairsTile) {
            return false;
        }
        if (level.getTile(x, y) instanceof AntiMob) {
            return false;
        }
        if (level.getTile(x, y) instanceof Woodfloor) {
            return false;
        }
        if (level.getTile(x, y) instanceof CarpetTile) {
            return false;
        }
        for (int i = -3; i < 3; ++i) {
            for (int i2 = -3; i2 < 3; ++i2) {
                if (level.getTile(x + i, y + i2) instanceof StairsTile) {
                    return false;
                }
            }
        }
        final int r = level.monsterDensity * 16;
        if (level.getEntities(xx - r, yy - r, xx + r, yy + r).size() > 0) {
            return false;
        }
        if (!level.getTile(x, y).mayPass(level, x, y, this)) {
            return false;
        }
        final byte[] biomes = level.biomes;
        if (level.getTile(x, y) instanceof WaterTile) {
            return false;
        }
        this.x = xx;
        this.y = yy;
        return true;
    }
}
