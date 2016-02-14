// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import java.util.List;

public class Greifhakenseil extends Entity
{
    private int lifeTime;
    private int xdir;
    private int ydir;
    private final int speed = 2;
    private int time;
    private int damage;
    private Mob owner;
    private Greifhakending ent;
    
    public Greifhakenseil(final int dirx, final int diry, final Greifhakending ent) {
        super("Greifhakenseil");
        this.ent = ent;
        this.xdir = dirx;
        this.ydir = diry;
        this.x = ent.x;
        this.y = ent.y;
    }
    
    @Override
    public void tick() {
        final List<Entity> entitylist = this.level.getEntities(this.x, this.y, this.x, this.y);
        for (int i = 0; i < entitylist.size(); ++i) {
            final Entity hit = entitylist.get(i);
            if (hit != null && hit instanceof Player) {
                this.remove();
            }
        }
        if (this.ent.removed) {
            this.remove();
        }
    }
    
    @Override
    public boolean isBlockableBy(final Mob mob) {
        return false;
    }
    
    @Override
    public void render(final Screen screen) {
        if (this.xdir != 0) {
            final int xt = 5;
            final int yt = 11;
            screen.render(this.x - 4, this.y - 4 - 2, xt + yt * 32, Color.get(-1, 555, 555, 555), this.random.nextInt(4));
            screen.render(this.x - 4, this.y - 4 + 2, xt + yt * 32, Color.get(-1, 0, 0, 0), this.random.nextInt(4));
        }
        else {
            final int xt = 6;
            final int yt = 11;
            screen.render(this.x - 4, this.y - 4 - 2, xt + yt * 32, Color.get(-1, 555, 555, 555), this.random.nextInt(4));
            screen.render(this.x - 4, this.y - 4 + 2, xt + yt * 32, Color.get(-1, 0, 0, 0), this.random.nextInt(4));
        }
    }
}
