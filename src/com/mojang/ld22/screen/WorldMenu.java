// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.screen;

import de.thejackimonster.ld22.loadandsave.WorldSaveLoadMenu;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;

public class WorldMenu extends Menu
{
    private Menu parent;
    private int selectedx;
    private int selectedy;
    int lenx;
    int leny;
    private static final String[] optionsy;
    private static final String[] optionsx;
    public int[] options;
    
    static {
        optionsy = new String[] { "MapSize", "Water", "Grass", "Rock", "Trees", "Sand", "Generate Map" };
        optionsx = new String[] { "Less", "Normal", "Much" };
    }
    
    public WorldMenu(final Menu parent) {
        this.selectedx = 1;
        this.selectedy = 1;
        this.lenx = 4;
        this.leny = 7;
        this.parent = parent;
        this.options = new int[this.leny];
        for (int i = 0; i < this.leny; ++i) {
            this.options[i] = 2;
        }
    }
    
    @Override
    public void render(final Screen screen) {
        screen.clear(0);
        String msg = "";
        int col = Color.get(0, 500, 500, 500);
        Font.draw("More options and a bigger map", screen, 0, 80, col);
        Font.draw("increase Load time significantly", screen, 0, 88, col);
        for (int i = 0; i < this.leny; ++i) {
            for (int i2 = 0; i2 < this.lenx; ++i2) {
                if (this.options[i] == i2) {
                    col = Color.get(0, 500, 2, 255);
                }
                else {
                    col = Color.get(0, 500, 52, 52);
                }
                if (i2 == 0) {
                    col = Color.get(0, 70, 52, 222);
                }
                if (i == this.selectedy && i == this.leny - 1) {
                    col = Color.get(0, 60, 60, 60);
                }
                if (i2 == 0) {
                    msg = WorldMenu.optionsy[i];
                }
                else {
                    if (i != this.leny - 1) {
                        msg = WorldMenu.optionsx[i2 - 1];
                    }
                    else {
                        msg = "";
                    }
                    if (i2 == this.selectedx && i == this.selectedy) {
                        col = Color.get(0, 60, 60, 60);
                    }
                }
                Font.draw(msg, screen, 70 * i2, (1 + i) * 8, col);
            }
        }
    }
    
    @Override
    public void tick() {
        if (this.input.up.clicked) {
            --this.selectedy;
        }
        if (this.input.down.clicked) {
            ++this.selectedy;
        }
        if (this.input.left.clicked) {
            --this.selectedx;
        }
        if (this.input.right.clicked) {
            ++this.selectedx;
        }
        if (this.input.up.clicked || this.input.down.clicked || this.input.left.clicked || this.input.right.clicked) {
            Sound.select.play();
        }
        if (this.selectedy < 1) {
            this.selectedy += this.leny;
        }
        if (this.selectedy >= this.leny) {
            this.selectedy -= this.leny;
        }
        if (this.selectedx < 1) {
            this.selectedx += this.leny;
        }
        if (this.selectedx >= this.lenx) {
            this.selectedx -= this.lenx;
        }
        if (this.input.attack.clicked || this.input.menu.clicked) {
            if (this.selectedy == this.leny - 1) {
                this.generateMap();
            }
            else {
                this.options[this.selectedy] = this.selectedx;
            }
        }
    }
    
    public void generateMap() {
        Sound.test.play();
        this.game.mapoptions = this.options;
        this.game.resetGame();
        this.game.setMenu(null);
        if (!this.game.isApplet) {
            new WorldSaveLoadMenu().saveMap(this.game);
        }
    }
}
