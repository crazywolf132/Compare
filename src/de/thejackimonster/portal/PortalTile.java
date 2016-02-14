// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.portal;

import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.gfx.Screen;
import java.awt.Point;
import com.mojang.ld22.level.tile.Tile;

public class PortalTile extends Tile
{
    public static int color;
    public static Point[] portal_;
    
    static {
        PortalTile.color = 0;
        PortalTile.portal_ = new Point[2];
    }
    
    public PortalTile(final int id) {
        super(id);
    }
    
    public static void useCanon() {
        if (PortalTile.color == 1) {
            PortalTile.color = 0;
        }
        else {
            PortalTile.color = 1;
        }
    }
    
    @Override
    public void render(final Screen screen, final Level level, final int x, final int y) {
        super.render(screen, level, x, y);
        final int data = level.getData(x, y);
        final int shape = data / 16 % 2;
        int col = Color.get(level.dirtColor, 4, 3, 5);
        if (data == 0) {
            col = Color.get(level.dirtColor, 420, 310, 530);
        }
        screen.render(x * 16 + 0, y * 16 + 0, 125, col, 0);
        screen.render(x * 16 + 8, y * 16 + 0, 126, col, 0);
        screen.render(x * 16 + 0, y * 16 + 8, 157, col, 0);
        screen.render(x * 16 + 8, y * 16 + 8, 158, col, 0);
        super.render(screen, level, x, y);
    }
    
    @Override
    public void bumpedInto(final Level level, final int xt, final int yt, final Entity entity) {
        for (int i = 0; i < 2; ++i) {
            if (PortalTile.portal_[0] == null) {
                return;
            }
            if (PortalTile.portal_[1] == null) {
                return;
            }
            if (PortalTile.portal_[i] != null && xt == PortalTile.portal_[i].x && yt == PortalTile.portal_[i].y) {
                if (i == 0) {
                    entity.x = PortalTile.portal_[1].x * 16 + entity.x % 16;
                    entity.y = PortalTile.portal_[1].y * 16 + entity.y % 16;
                }
                else {
                    entity.x = PortalTile.portal_[0].x * 16 + entity.x % 16;
                    entity.y = PortalTile.portal_[0].y * 16 + entity.y % 16;
                }
            }
        }
    }
    
    @Override
    public int getLightRadius(final Level level, final int x, final int y) {
        return 1;
    }
}
