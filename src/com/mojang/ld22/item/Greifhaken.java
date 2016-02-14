// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.item;

import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.entity.Greifhakending;

public class Greifhaken extends Item
{
    public Greifhakending grfkend;
    
    public Greifhaken() {
        super("Cross-Hook");
    }
    
    @Override
    public int getColor() {
        return Color.get(-1, 111, 555, 333);
    }
    
    @Override
    public int getSprite() {
        return 142;
    }
    
    @Override
    public void renderIcon(final Screen screen, final int x, final int y) {
        screen.render(x, y, this.getSprite(), this.getColor(), 0);
    }
    
    @Override
    public void renderInventory(final Screen screen, final int x, final int y) {
        screen.render(x, y, this.getSprite(), this.getColor(), 0);
        Font.draw(this.getName(), screen, x + 15, y, Color.get(-1, 555, 555, 555));
    }
    
    @Override
    public String getName() {
        return "Greifhaken";
    }
    
    @Override
    public boolean interact(final Player player, final Entity entity, final int attackDir) {
        return false;
    }
}
