// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.screen;

import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import java.util.Random;

public class LevelTransitionMenu extends Menu
{
    private int dir;
    private int time;
    int rnd;
    int rnd2;
    Random random;
    
    public LevelTransitionMenu(final int dir) {
        this.time = 0;
        this.rnd = 0;
        this.rnd2 = 0;
        this.random = new Random();
        this.dir = dir;
    }
    
    @Override
    public void tick() {
        this.time += 2;
        if (this.time == 2) {
            this.rnd2 = this.random.nextInt(3);
        }
        if (this.time == 4 && this.rnd2 == 2) {
            this.rnd = this.random.nextInt(255);
        }
        if (this.time == 90) {
            this.game.changeLevel(this.dir);
        }
        if (this.time == 160) {
            this.game.setMenu(null);
        }
    }
    
    @Override
    public void render(final Screen screen) {
        int clr = Color.get(0, 1, 50, 55);
        if (this.rnd2 == 0) {
            this.rnd = this.random.nextInt(255);
        }
        for (int x = 0; x < 40; ++x) {
            for (int y = 0; y < 34; ++y) {
                if (this.rnd2 == 1) {
                    this.rnd = this.random.nextInt(255);
                }
                clr = Color.get(0, 1, this.rnd, this.rnd + 5);
                final int dd = x + y % 2 * 2 + y / 3 - this.time;
                if ((dd < 0 && dd > -1) || (dd < -4 && dd > -6) || (dd < -8 && dd > -11) || (dd < -13 && dd > -90)) {
                    screen.render(screen.w - x * 16, y * 16 + 8, 49, clr, 32);
                    screen.render(screen.w - x * 16 + 8, y * 16 + 8, 50, clr, 32);
                    screen.render(screen.w - x * 16, y * 16, 81, clr, 32);
                    screen.render(screen.w - x * 16 + 8, y * 16, 82, clr, 32);
                }
            }
        }
    }
}
