// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.weapons;

import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.item.Item;

public class BoomerangItem extends Item
{
    public BoomerangItem() {
        super("Boomerang");
    }
    
    @Override
    public int getColor() {
        return Color.get(-1, 100, 320, 430);
    }
    
    @Override
    public int getSprite() {
        return 357;
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
        return "Boomerang";
    }
}
