// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.screen;

import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.entity.Player;

public class LineMessage extends Menu
{
    private Player player;
    private String message;
    private int tickc;
    private int sec;
    
    public LineMessage(final Player player, final String message, final int sec) {
        this.player = player;
        this.message = message;
        this.sec = (sec + 1) * 5;
    }
    
    @Override
    public void tick() {
        ++this.tickc;
        if ((this.tickc / 60 == this.sec && this.tickc % 60 == 0) || this.input.attack.clicked || this.input.menu.clicked) {
            this.game.setMenu(null);
        }
    }
    
    @Override
    public void render(final Screen screen) {
        final int xoff = 1;
        final int yoff = 18;
        Font.renderFrame(screen, "", 1 + xoff, 1 + yoff, 28 + xoff, 2 + yoff);
        Font.draw(this.message, screen, xoff * 8 + 18, yoff * 8 + 12, Color.get(-1, 111, 222, 333));
    }
}
