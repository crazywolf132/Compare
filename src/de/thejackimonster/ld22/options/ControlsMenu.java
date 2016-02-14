// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.options;

import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Color;
import java.awt.event.KeyEvent;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.screen.Menu;

public class ControlsMenu extends Menu
{
    private int selX;
    private int selY;
    private boolean wait;
    private final boolean ingame;
    private int ticks;
    
    public ControlsMenu(final boolean flag) {
        this.ingame = flag;
    }
    
    public void onKeyClicked(final int key) {
        ++this.ticks;
        if (this.wait && this.ticks % 3 == 0) {
            if (this.selY + this.selX * OptionFile.keys.length / 2 >= 0 && this.selY + this.selX * OptionFile.keys.length / 2 < OptionFile.keys.length) {
                OptionFile.keys[this.selY + this.selX * OptionFile.keys.length / 2] = key;
            }
            this.wait = false;
        }
    }
    
    @Override
    public void tick() {
        if (!this.wait) {
            if (this.input.up.clicked) {
                --this.selY;
            }
            if (this.input.down.clicked) {
                ++this.selY;
            }
            if (this.input.left.clicked) {
                --this.selX;
            }
            if (this.input.right.clicked) {
                ++this.selX;
            }
            if (this.selY < 0) {
                this.selY = 0;
            }
            if (this.selY > OptionFile.keys.length / 2) {
                this.selY = OptionFile.keys.length / 2;
            }
            if (this.selX < 0) {
                this.selX = 0;
            }
            if (this.selX > 1) {
                this.selX = 1;
            }
            if (this.selY == OptionFile.keys.length / 2) {
                this.selX = 0;
            }
            if (this.input.up.clicked || this.input.down.clicked || this.input.left.clicked || this.input.right.clicked) {
                Sound.select.play();
            }
            if (this.input.attack.clicked || this.input.menu.clicked) {
                Sound.toogle.play();
                if (this.selX == 0 && this.selY == OptionFile.keys.length / 2) {
                    if (!this.game.isApplet) {
                        OptionFile.writeOpt();
                    }
                    this.game.setMenu(new OptionsMenu(this.ingame));
                }
                else {
                    this.ticks = 0;
                    if (this.selY + this.selX * OptionFile.keys.length / 2 >= 0 && this.selY + this.selX * OptionFile.keys.length / 2 < OptionFile.keys.length) {
                        OptionFile.keys[this.selY + this.selX * OptionFile.keys.length / 2] = -1;
                        this.wait = true;
                    }
                }
            }
        }
    }
    
    @Override
    public void render(final Screen screen) {
        screen.clear(0);
        int x = 0;
        int y = 0;
        for (int i = 0; i < OptionFile.keys.length; ++i) {
            x = screen.w / 4;
            y = screen.h / 10;
            if (y != 0) {
                y += i * 12;
            }
            if (i >= OptionFile.keys.length / 2) {
                x += screen.w / 2;
                y -= OptionFile.keys.length / 2 * 12;
            }
            String msg = KeyEvent.getKeyText(OptionFile.keys[i]);
            if (this.selY != OptionFile.keys.length / 2 && this.selY + this.selX * OptionFile.keys.length / 2 == i) {
                msg = "> " + msg + " <";
            }
            Font.draw(msg, screen, x - msg.length() * 4, y, Color.get(-1, -1, -1, 555));
        }
        x -= screen.w / 8;
        y += 16;
        String msg2 = "Done";
        if (this.selY == OptionFile.keys.length / 2 && this.selX == 0) {
            msg2 = "> " + msg2 + " <";
        }
        Font.draw(msg2, screen, x - msg2.length() * 4, y, Color.get(-1, -1, -1, 555));
    }
}
