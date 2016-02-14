// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.portal;

import com.mojang.ld22.sound.Sound;
import java.awt.Point;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.entity.Entity;

public class Portal extends Entity
{
    private final int color;
    private int dir;
    private final int speed = 2;
    
    public Portal(final int xt, final int yt, final int i, final int DIR) {
        super("Portal");
        this.color = i;
        this.x = xt * 16 + 8;
        this.y = yt * 16 + 8;
        this.dir = DIR;
    }
    
    @Override
    public void render(final Screen screen) {
        int col = Color.get(-1, 4, 3, 5);
        if (this.color == 0) {
            col = Color.get(-1, 420, 310, 530);
        }
        screen.render(this.x + 0 - 4, this.y + 0 - 8, 29, col, 0);
        super.render(screen);
    }
    
    @Override
    public void tick() {
        switch (this.dir) {
            case 0: {
                this.y += 2;
                break;
            }
            case 1: {
                this.y -= 2;
                break;
            }
            case 2: {
                this.x -= 2;
                break;
            }
            case 3: {
                this.x += 2;
                break;
            }
        }
        if (this.level != null) {
            final int xt = this.x / 16;
            final int yt = this.y / 16;
            final Tile tile = this.level.getTile(xt, yt);
            if (tile == null) {
                this.remove();
            }
            if (tile == Tile.rock) {
                this.level.setTile(xt, yt, mod_portal.portal, PortalTile.color);
                if (PortalTile.portal_[PortalTile.color] != null) {
                    this.level.setTile(PortalTile.portal_[PortalTile.color].x, PortalTile.portal_[PortalTile.color].y, Tile.rock, 0);
                }
                PortalTile.portal_[PortalTile.color] = new Point(xt, yt);
                Sound.powerup.play();
                this.remove();
            }
            if (!tile.mayPass(this.level, xt, yt, this)) {
                Sound.craft.play();
                this.remove();
            }
        }
    }
    
    @Override
    public boolean canSwim() {
        return true;
    }
    
    @Override
    public int getLightRadius() {
        return mod_portal.portal.getLightRadius(this.level, 0, 0);
    }
}
