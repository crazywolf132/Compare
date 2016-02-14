// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.portal;

import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.item.Item;

public class PortalItem extends Item
{
    public PortalItem() {
        super("PortalGun");
    }
    
    @Override
    public int getColor() {
        int c = 5;
        if (PortalTile.color == 1) {
            c = 530;
        }
        return Color.get(-1, 111, c, 555);
    }
    
    @Override
    public int getSprite() {
        return 143;
    }
    
    @Override
    public void renderIcon(final Screen screen, final int x, final int y) {
        screen.render(x, y, this.getSprite(), this.getColor(), 0);
    }
    
    @Override
    public void renderInventory(final Screen screen, final int x, final int y) {
        screen.render(x, y, this.getSprite(), this.getColor(), 0);
        Font.draw(this.getName(), screen, x + 8, y, Color.get(-1, 555, 555, 555));
    }
    
    @Override
    public String getName() {
        return "Portal-Gun";
    }
    
    @Override
    public boolean interactOn(final Tile tile, final Level level, final int xt, final int yt, final Player player, final int attackDir) {
        PortalTile.useCanon();
        level.add(new Portal(xt, yt, PortalTile.color, attackDir));
        Sound.special.play();
        return false;
    }
}
