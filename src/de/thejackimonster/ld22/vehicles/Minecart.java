// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.vehicles;

import com.mojang.ld22.entity.Player;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.gfx.Color;
import java.awt.Point;

public class Minecart extends Vehicle
{
    public Point lastTile;
    
    public Minecart() {
        super("Minecart", 5, Color.get(-1, 453, 234, 230), new Tile[] { Tile.hole });
    }
    
    @Override
    public void playerControl(final Player p) {
    }
    
    @Override
    public boolean canSwim() {
        return false;
    }
    
    @Override
    public void tick() {
        super.tick();
        int dir = 0;
        final boolean[] dirs = { false, false, false, false };
        for (int i = 0; i < this.canMoveOn.length; ++i) {
            if (this.level.getTile(this.x / 16 - 1, this.y / 16).id == this.canMoveOn[i].id) {
                dirs[0] = true;
                ++dir;
            }
            if (this.level.getTile(this.x / 16 + 1, this.y / 16).id == this.canMoveOn[i].id) {
                dirs[1] = true;
                ++dir;
            }
            if (this.level.getTile(this.x / 16, this.y / 16 - 1).id == this.canMoveOn[i].id) {
                dirs[2] = true;
                ++dir;
            }
            if (this.level.getTile(this.x / 16, this.y / 16 + 1).id == this.canMoveOn[i].id) {
                dirs[3] = true;
                ++dir;
            }
        }
        if (this.lastTile == null) {
            this.lastTile = new Point(this.x / 16, this.y / 16);
        }
        if ((dir > 0 && (this.mx != 0 || this.my != 0) && this.lastTile.x == this.x / 16 && this.lastTile.y == this.y / 16) || this.pushTime > 0) {
            for (int i = 0; i < 4; ++i) {
                if (dirs[i] && this.random.nextInt(dir) == 0) {
                    this.lastTile.x = this.x / 16;
                    this.lastTile.y = this.y / 16;
                    if (i == 0) {
                        this.mx = -1;
                        this.my = 0;
                        final Point lastTile = this.lastTile;
                        --lastTile.x;
                    }
                    if (i == 1) {
                        this.mx = 1;
                        this.my = 0;
                        final Point lastTile2 = this.lastTile;
                        ++lastTile2.x;
                    }
                    if (i == 2) {
                        this.mx = 0;
                        this.my = -1;
                        final Point lastTile3 = this.lastTile;
                        --lastTile3.y;
                    }
                    if (i == 3) {
                        this.mx = 0;
                        this.my = 1;
                        final Point lastTile4 = this.lastTile;
                        ++lastTile4.y;
                    }
                }
            }
        }
    }
}
