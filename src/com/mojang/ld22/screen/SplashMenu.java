// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.screen;

import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.InputHandler;
import com.mojang.ld22.Game;

public class SplashMenu extends Menu
{
    protected Game game;
    protected InputHandler input;
    private int rdm;
    private int tickc;
    
    public SplashMenu() {
        this.tickc = 0;
    }
    
    @Override
    public void init(final Game game, final InputHandler input) {
        this.input = input;
        this.game = game;
    }
    
    @Override
    public void tick() {
        ++this.tickc;
        if (this.tickc >= 200) {
            this.game.setMenu(new TitleMenu());
        }
    }
    
    @Override
    public void render(final Screen screen) {
        int h = 5;
        int w = 46;
        ++this.rdm;
        screen.clear(0);
        for (int y = 3; y < h; ++y) {
            for (int x = 17; x < w; ++x) {
                final int titleColor = Color.get(this.rdm + x * 8, this.rdm + x * 8, this.rdm + x * 8, this.rdm + x * 8);
                screen.render(x * 4, y * 8, 352, titleColor, 0);
            }
        }
        h = 25;
        w = 35;
        ++this.rdm;
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                final int titleColor = Color.get(0, 0, this.rdm + x * 5 + y * 2, 551);
                screen.render(x * 8, y * 8, 355, titleColor, 0);
            }
        }
        Font.renderFrame(screen, "", 2, 2, 28, 20);
        h = 20;
        w = 28;
        for (int y = 3; y < h; ++y) {
            for (int x = 3; x < w; ++x) {
                final int titleColor = Color.get(0, 0, 1, 551);
                screen.render(x * 8, y * 8, 355, titleColor, 0);
            }
        }
        final int h2 = 2;
        final int w2 = 16;
        final int titleColor = Color.get(-1, 107, 8, 999);
        final int xo = (screen.w - w2 * 8) / 2;
        final int yo = 24;
        for (int y2 = 0; y2 < h2; ++y2) {
            for (int x2 = 0; x2 < w2; ++x2) {
                screen.render(xo + x2 * 8, yo + y2 * 8, x2 + 14 + (y2 + 6) * 32, titleColor, 0);
            }
        }
        Font.draw("TheJackiMonster", screen, 66, 60, Color.get(-1, 500, 500, 500));
        Font.draw("Zelosfan", screen, 90, 76, Color.get(-1, 500, 500, 500));
        Font.draw("Minicraft: Notch", screen, 60, 140, Color.get(-1, 3, 3, 3));
    }
}
