// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.redstone;

import java.util.List;
import java.util.ArrayList;
import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.entity.particle.TextParticle;
import com.mojang.ld22.entity.particle.SmashParticle;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.level.tile.Tile;

public class RedstoneTile extends Tile
{
    public boolean signal;
    private int ticks;
    
    public RedstoneTile(final int id) {
        super(id);
        this.signal = false;
        this.ticks = 0;
    }
    
    @Override
    public void render(final Screen screen, final Level level, final int x, final int y) {
        int c = 100;
        if (this.signal) {
            c = 200;
        }
        final int col = Color.get(level.dirtColor, c + 100, c + 200, c + 300);
        final boolean u = level.getTile(x, y - 1) == this;
        final boolean d = level.getTile(x, y + 1) == this;
        final boolean l = level.getTile(x - 1, y) == this;
        final boolean r = level.getTile(x + 1, y) == this;
        int t1 = 61;
        int t2 = 62;
        int t3 = 93;
        int t4 = 94;
        if (u) {
            if (!l) {
                t1 = 21;
            }
            if (!r) {
                t2 = 22;
            }
        }
        if (d) {
            if (!l) {
                t3 = 23;
            }
            if (!r) {
                t4 = 24;
            }
        }
        if ((u || d) && (!l || !d)) {
            if (!l) {
                t1 = 21;
                t3 = 23;
            }
            if (!r) {
                t2 = 22;
                t4 = 24;
            }
        }
        if (l) {
            if (!u) {
                t1 = 25;
            }
            if (!d) {
                t3 = 26;
            }
        }
        if (r) {
            if (!u) {
                t2 = 27;
            }
            if (!d) {
                t4 = 28;
            }
        }
        if ((l || r) && (!u || !d)) {
            if (!u) {
                t1 = 25;
                t2 = 27;
            }
            if (!d) {
                t3 = 26;
                t4 = 28;
            }
        }
        final int clear = 1023;
        if ((l || r) && (!l || !r) && (u || d) && (!u || !d)) {
            if (u) {
                if (l) {
                    t4 = clear;
                }
                else if (r) {
                    t3 = clear;
                }
            }
            else if (d) {
                if (l) {
                    t2 = clear;
                }
                else if (r) {
                    t1 = clear;
                }
            }
        }
        screen.render(x * 16 + 0, y * 16 + 0, t1, col, 0);
        screen.render(x * 16 + 8, y * 16 + 0, t2, col, 0);
        screen.render(x * 16 + 0, y * 16 + 8, t3, col, 0);
        screen.render(x * 16 + 8, y * 16 + 8, t4, col, 0);
    }
    
    @Override
    public boolean mayPass(final Level level, final int x, final int y, final Entity e) {
        return true;
    }
    
    @Override
    public void hurt(final Level level, final int x, final int y, final Mob source, final int dmg, final int attackDir) {
        this.hurt(level, x, y, dmg);
    }
    
    @Override
    public boolean interact(final Level level, final int xt, final int yt, final Player player, final Item item, final int attackDir) {
        if (player.payStamina(1)) {
            this.hurt(level, xt, yt, this.random.nextInt(10) + 10);
            return true;
        }
        return false;
    }
    
    public void hurt(final Level level, final int x, final int y, final int dmg) {
        final int damage = level.getData(x, y) + dmg;
        level.add(new SmashParticle(x * 16 + 8, y * 16 + 8));
        level.add(new TextParticle(new StringBuilder().append(dmg).toString(), x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500)));
        if (damage >= 1) {
            level.add(new ItemEntity(new ResourceItem(mod_redstone.redstone, 1), x * 16 + this.random.nextInt(10) + 3, y * 16 + this.random.nextInt(10) + 3));
            level.setTile(x, y, Tile.dirt, 0);
        }
        else {
            level.setData(x, y, damage);
        }
    }
    
    @Override
    public void tick(final Level level, final int xt, final int yt) {
        ++this.ticks;
        final int damage = level.getData(xt, yt);
        if (damage > 0) {
            level.setData(xt, yt, damage - 1);
        }
        final boolean u = level.getTile(xt, yt - 1) == this;
        final boolean d = level.getTile(xt, yt + 1) == this;
        final boolean l = level.getTile(xt - 1, yt) == this;
        final boolean r = level.getTile(xt + 1, yt) == this;
        final List<RedstoneTile> connect = new ArrayList<RedstoneTile>();
        if (u) {
            connect.add((RedstoneTile)level.getTile(xt, yt - 1));
        }
        if (d) {
            connect.add((RedstoneTile)level.getTile(xt, yt + 1));
        }
        if (l) {
            connect.add((RedstoneTile)level.getTile(xt - 1, yt));
        }
        if (r) {
            connect.add((RedstoneTile)level.getTile(xt + 1, yt));
        }
        this.signal = false;
        if (connect.size() > 0) {
            for (int i = 0; i < connect.size(); ++i) {
                if (!connect.get(i).signal) {
                    if (this.signal) {
                        connect.get(i).signal = true;
                    }
                }
                else if (!this.signal) {
                    this.signal = true;
                }
            }
        }
    }
    
    @Override
    public int getLightRadius(final Level level, final int x, final int y) {
        if (this.signal) {
            return 1;
        }
        return 0;
    }
}
