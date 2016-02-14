// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.redstone;

import com.mojang.ld22.entity.Player;
import java.util.List;
import java.util.ArrayList;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.entity.Furniture;

public class RedstoneTorch extends Furniture
{
    public boolean on;
    private int ticks;
    public int tickTime;
    
    public RedstoneTorch() {
        super("RedstoneTorch");
        this.on = false;
        this.ticks = 0;
        this.tickTime = 0;
        this.col = Color.get(-1, 110, 0, 552);
        this.sprite = 6;
    }
    
    @Override
    public void tick() {
        super.tick();
        ++this.ticks;
        final int xt = this.x >> 4;
        final int yt = this.y >> 4;
        final boolean u = this.level.getTile(xt, yt - 1) == mod_redstone.wire;
        final boolean d = this.level.getTile(xt, yt + 1) == mod_redstone.wire;
        final boolean l = this.level.getTile(xt - 1, yt) == mod_redstone.wire;
        final boolean r = this.level.getTile(xt + 1, yt) == mod_redstone.wire;
        final List<RedstoneTile> connect = new ArrayList<RedstoneTile>();
        if (u) {
            connect.add((RedstoneTile)this.level.getTile(xt, yt - 1));
        }
        if (d) {
            connect.add((RedstoneTile)this.level.getTile(xt, yt + 1));
        }
        if (l) {
            connect.add((RedstoneTile)this.level.getTile(xt - 1, yt));
        }
        if (r) {
            connect.add((RedstoneTile)this.level.getTile(xt + 1, yt));
        }
        if (connect.size() > 0) {
            for (int i = 0; i < connect.size(); ++i) {
                if (!connect.get(i).signal && this.on) {
                    connect.get(i).signal = true;
                }
            }
        }
    }
    
    @Override
    public boolean use(final Player player, final int attackDir) {
        if (this.on) {
            this.on = false;
            this.col = Color.get(-1, 110, 0, 552);
        }
        else {
            this.on = true;
            this.col = Color.get(-1, 110, 500, 480);
        }
        return true;
    }
    
    @Override
    public int getLightRadius() {
        if (this.on) {
            return 3;
        }
        return 0;
    }
}
