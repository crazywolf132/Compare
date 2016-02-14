// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.sound;

import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.screen.TitleMenu;
import com.mojang.ld22.gfx.Color;
import de.thejackimonster.ld22.story.character.TextBox;
import com.mojang.ld22.screen.Menu;

public class NewCharacterMenu extends Menu
{
    private int[] c1;
    private int[] c2;
    private int[] c3;
    private int selected;
    private int selectC;
    private TextBox[] tb;
    private boolean ok;
    private int ticks;
    
    public NewCharacterMenu() {
        this.c1 = new int[3];
        this.c2 = new int[3];
        this.c3 = new int[3];
        this.tb = new TextBox[5];
        this.ok = false;
        this.selectC = 0;
        this.selected = 0;
        String s = "";
        for (int i = 0; i < 3; ++i) {
            if (i == 0) {
                this.c1[i] = 1;
                this.c2[i] = 2;
                this.c3[i] = 5;
            }
            if (i == 1) {
                this.c1[i] = 0;
                this.c2[i] = 2;
                this.c3[i] = 3;
            }
            if (i == 2) {
                this.c1[i] = 0;
                this.c2[i] = 0;
                this.c3[i] = 2;
            }
        }
        for (int i = 0; i < 5; ++i) {
            if (i == 0) {
                s = "NAME";
            }
            if (i == 1) {
                s = "COLOR: " + String.valueOf(this.selectC);
            }
            if (i == 2) {
                s = "RED: " + this.c1[0];
            }
            if (i == 3) {
                s = "GREEN: " + this.c1[1];
            }
            if (i == 4) {
                s = "BLUE: " + this.c1[2];
            }
            (this.tb[i] = new TextBox(s, 64, 8 + i * 32, 160)).setEnabled(false);
        }
    }
    
    public void onKeyClicked(final int key) {
        ++this.ticks;
        if (this.selected == 0 && this.tb[0].getEnabled() && this.tb.length > 0) {
            if (this.ticks % 2 == 0) {
                if (key == 10) {
                    this.tb[0].setEnabled(false);
                }
                else {
                    this.tb[0].Write(key);
                }
            }
        }
        else if (this.selected == 0 && !this.tb[0].getEnabled() && this.tb.length > 0 && this.ticks % 2 == 0 && key == 10) {
            this.tb[0].setEnabled(true);
        }
    }
    
    @Override
    public void tick() {
        if (!this.tb[this.selected].getEnabled() && !this.ok) {
            if (this.input.up.clicked) {
                --this.selected;
            }
            if (this.input.down.clicked) {
                ++this.selected;
            }
            if (this.selected < 0) {
                this.selected = this.tb.length - 1;
            }
            if (this.selected >= this.tb.length) {
                this.selected = 0;
            }
        }
        if (this.ok && this.input.attack.clicked && this.game != null) {
            if (this.game.player != null) {
                this.game.player.color = Color.get(-1, this.getC(this.c1), this.getC(this.c2), this.getC(this.c3));
                this.game.player.username = this.tb[0].getText();
            }
            this.game.setMenu(new TitleMenu());
        }
        if (this.selected == 0) {
            if (this.input.attack.clicked && !this.tb[this.selected].getEnabled() && !this.ok) {
                this.tb[this.selected].setEnabled(true);
            }
            if (this.input.right.clicked && !this.ok) {
                this.ok = true;
            }
            if (this.input.left.clicked && this.ok) {
                this.ok = false;
            }
        }
        if (this.selected == 1) {
            if (this.input.left.clicked) {
                --this.selectC;
            }
            if (this.input.right.clicked) {
                ++this.selectC;
            }
            if (this.selectC < 0) {
                this.selectC = 2;
            }
            if (this.selectC > 2) {
                this.selectC = 0;
            }
        }
        if (this.selected == 2 || this.selected == 3 || this.selected == 4) {
            if (this.input.left.clicked) {
                if (this.selectC == 0) {
                    final int[] c1 = this.c1;
                    final int n = this.selected - 2;
                    --c1[n];
                }
                if (this.selectC == 1) {
                    final int[] c2 = this.c2;
                    final int n2 = this.selected - 2;
                    --c2[n2];
                }
                if (this.selectC == 2) {
                    final int[] c3 = this.c3;
                    final int n3 = this.selected - 2;
                    --c3[n3];
                }
            }
            if (this.input.right.clicked) {
                if (this.selectC == 0) {
                    final int[] c4 = this.c1;
                    final int n4 = this.selected - 2;
                    ++c4[n4];
                }
                if (this.selectC == 1) {
                    final int[] c5 = this.c2;
                    final int n5 = this.selected - 2;
                    ++c5[n5];
                }
                if (this.selectC == 2) {
                    final int[] c6 = this.c3;
                    final int n6 = this.selected - 2;
                    ++c6[n6];
                }
            }
            if (this.c1[this.selected - 2] > 9) {
                this.c1[this.selected - 2] = 9;
            }
            if (this.c1[this.selected - 2] < 0) {
                this.c1[this.selected - 2] = 0;
            }
            if (this.c2[this.selected - 2] > 9) {
                this.c2[this.selected - 2] = 9;
            }
            if (this.c2[this.selected - 2] < 0) {
                this.c2[this.selected - 2] = 0;
            }
            if (this.c3[this.selected - 2] > 9) {
                this.c3[this.selected - 2] = 9;
            }
            if (this.c3[this.selected - 2] < 0) {
                this.c3[this.selected - 2] = 0;
            }
        }
        this.tb[1].setText("COLOR: " + String.valueOf(this.selectC));
        if (this.selectC == 0) {
            this.tb[2].setText("RED: " + String.valueOf(this.c1[0]));
            this.tb[3].setText("GREEN: " + String.valueOf(this.c1[1]));
            this.tb[4].setText("BLUE: " + String.valueOf(this.c1[2]));
        }
        if (this.selectC == 1) {
            this.tb[2].setText("RED: " + String.valueOf(this.c2[0]));
            this.tb[3].setText("GREEN: " + String.valueOf(this.c2[1]));
            this.tb[4].setText("BLUE: " + String.valueOf(this.c2[2]));
        }
        if (this.selectC == 2) {
            this.tb[2].setText("RED: " + String.valueOf(this.c3[0]));
            this.tb[3].setText("GREEN: " + String.valueOf(this.c3[1]));
            this.tb[4].setText("BLUE: " + String.valueOf(this.c3[2]));
        }
    }
    
    @Override
    public void render(final Screen screen) {
        screen.clear(0);
        int xo = 24;
        final int yo = 40;
        final int col = Color.get(-1, this.getC(this.c1), this.getC(this.c2), this.getC(this.c3));
        if (this.ok) {
            Font.draw(">OK", screen, this.tb[0].x0 + this.tb[0].x1 + 8, this.tb[0].y0 + 4, Color.get(-1, -1, -1, 555));
        }
        else {
            Font.draw("OK", screen, this.tb[0].x0 + this.tb[0].x1 + 16, this.tb[0].y0 + 4, Color.get(-1, -1, -1, 555));
        }
        for (int i = 0; i < this.tb.length; ++i) {
            this.tb[i].render(screen);
            if (this.selected == i && !this.ok) {
                if (this.tb[this.selected].getEnabled()) {
                    Font.draw(">", screen, this.tb[i].x0 - 8, this.tb[i].y0 + 4, Color.get(-1, -1, -1, 550));
                }
                else {
                    Font.draw(">", screen, this.tb[i].x0 - 8, this.tb[i].y0 + 4, Color.get(-1, -1, -1, 555));
                }
            }
        }
        xo -= 16;
        screen.render(xo, yo + 0, 454, col, 0);
        screen.render(xo + 8, yo + 0, 455, col, 0);
        screen.render(xo, yo + 8, 486, col, 0);
        screen.render(xo + 8, yo + 8, 487, col, 0);
        xo += 16;
        screen.render(xo, yo + 0, 448, col, 0);
        screen.render(xo + 8, yo + 0, 449, col, 0);
        screen.render(xo, yo + 8, 480, col, 0);
        screen.render(xo + 8, yo + 8, 481, col, 0);
        xo += 16;
        screen.render(xo + 8, yo + 0, 454, col, 1);
        screen.render(xo, yo + 0, 455, col, 1);
        screen.render(xo + 8, yo + 8, 486, col, 1);
        screen.render(xo, yo + 8, 487, col, 1);
    }
    
    public int getC(final int[] ix) {
        if (ix.length == 3) {
            return ix[0] * 100 + ix[1] * 10 + ix[2] * 1;
        }
        return 0;
    }
    
    public void add(final int[] ix, final int i, final int x) {
        if (ix.length == 3 && i < 3 && i > -1) {
            ix[i] += x;
        }
    }
}
