// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.options;

import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.screen.TitleMenu;
import de.thejackimonster.ld22.loadandsave.WorldSaveLoadMenu;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.screen.Menu;

public class IngameMenu extends Menu
{
    private int selected;
    private static final String[] options;
    
    static {
        options = new String[] { "Back To Game", "Save World", "Options", "Save And Quit" };
    }
    
    public IngameMenu() {
        this.selected = 0;
    }
    
    @Override
    public void tick() {
        if (this.input.up.clicked) {
            --this.selected;
        }
        if (this.input.down.clicked) {
            ++this.selected;
        }
        if (this.input.up.clicked || this.input.down.clicked) {
            Sound.select.play();
        }
        if (this.selected < 0) {
            this.selected = IngameMenu.options.length - 1;
        }
        if (this.selected >= IngameMenu.options.length) {
            this.selected = 0;
        }
        if (this.input.attack.clicked || this.input.menu.clicked) {
            Sound.toogle.play();
            if (this.selected == 0) {
                this.game.setMenu(null);
            }
            if (this.selected == 1 && !this.game.isApplet) {
                new WorldSaveLoadMenu().saveMap(this.game);
            }
            if (this.selected == 2) {
                this.game.setMenu(new OptionsMenu(true));
            }
            if (this.selected == 3) {
                if (!this.game.isApplet) {
                    new WorldSaveLoadMenu().saveMap(this.game);
                }
                this.game.setMenu(new TitleMenu());
            }
        }
    }
    
    @Override
    public void render(final Screen screen) {
        final int h2 = 2;
        final int w2 = 16;
        final int titleColor = Color.get(-1, 107, 8, 255);
        final int xo = (screen.w - w2 * 8) / 2;
        final int yo = 24;
        Font.renderFrame(screen, "", 9, 5, 22, 14);
        for (int y = 0; y < h2; ++y) {
            for (int x = 0; x < w2; ++x) {
                screen.render(xo + x * 8, yo + y * 8, x + 14 + (y + 6) * 32, titleColor, 0);
            }
        }
        for (int i = 0; i < IngameMenu.options.length; ++i) {
            String msg = IngameMenu.options[i];
            int col = Color.get(-1, 222, 222, 222);
            if (i == this.selected) {
                msg = "> " + msg + " <";
                col = Color.get(-1, 555, 555, 555);
            }
            Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, (8 + i) * 8 - 16, col);
        }
    }
}
