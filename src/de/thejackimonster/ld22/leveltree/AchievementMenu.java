// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.leveltree;

import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.screen.Menu;
import com.mojang.ld22.screen.FreeMenu;

public class AchievementMenu extends FreeMenu
{
    private final Achievement acm;
    private int tickc;
    private final int sec;
    
    public AchievementMenu(final Achievement achievement) {
        this.sec = 5;
        this.acm = achievement;
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
        final int w = 224;
        final int h = 32;
        final int x = screen.w / 2 - w / 2;
        final int y = screen.h - h;
        Font.renderFrame(screen, this.acm.title, x / 8, y / 8, (x + w) / 8, (y + h) / 8);
        this.acm.renderIcon(screen, x + 8, y + h / 2);
        String msg = "";
        for (int i = 0; i < this.acm.text.length() / 2; ++i) {
            msg = String.valueOf(msg) + String.valueOf(this.acm.text.charAt(i));
        }
        Font.draw(msg, screen, x + 8 + 10, y + h / 3, Color.get(-1, 111, 222, 333));
        msg = "";
        for (int i = this.acm.text.length() / 2; i < this.acm.text.length(); ++i) {
            msg = String.valueOf(msg) + String.valueOf(this.acm.text.charAt(i));
        }
        Font.draw(msg, screen, x + 8 + 10, y + h / 3 * 2, Color.get(-1, 111, 222, 333));
    }
}
