// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.options;

import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.screen.TitleMenu;
import de.thejackimonster.ld22.leveltree.AchievementListMenu;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.screen.Menu;

public class OptionsMenu extends Menu
{
    private int ticks;
    private int selected;
    private int selDiff;
    private static final String[] diff;
    private static final String[] options;
    private final boolean ingame;
    
    static {
        diff = new String[] { "Peacefull", "Easy", "Normal", "Hard" };
        options = new String[] { "Difficulty: ", "AutoSave: ", "Achievements..", "Controls..", "Done" };
    }
    
    public OptionsMenu(final boolean flag) {
        this.selected = 0;
        this.selDiff = 0;
        this.ingame = flag;
        this.selDiff = OptionFile.difficulty;
    }
    
    @Override
    public void tick() {
        if (this.ticks == 0 && !this.game.isApplet) {
            OptionFile.readOpt();
        }
        ++this.ticks;
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
            this.selected = OptionsMenu.options.length - 1;
        }
        if (this.selected >= OptionsMenu.options.length) {
            this.selected = 0;
        }
        if (this.input.attack.clicked || this.input.menu.clicked) {
            Sound.toogle.play();
            if (this.selected == 0) {
                ++this.selDiff;
            }
            if (this.selected == 1) {
                this.game.autosave = !this.game.autosave;
            }
            if (this.selected == 2) {
                if (!this.game.isApplet) {
                    this.saveChanges();
                }
                this.game.setMenu(new AchievementListMenu(this.ingame));
            }
            if (this.selected == 3) {
                if (!this.game.isApplet) {
                    this.saveChanges();
                }
                this.game.setMenu(new ControlsMenu(this.ingame));
            }
            if (this.selected == 4) {
                if (!this.game.isApplet) {
                    this.saveChanges();
                }
                if (this.ingame) {
                    this.game.setMenu(new IngameMenu());
                }
                if (!this.ingame) {
                    this.game.setMenu(new TitleMenu());
                }
            }
        }
        if (this.selDiff >= OptionsMenu.diff.length) {
            this.selDiff = 0;
        }
        if (this.selDiff < 0) {
            this.selDiff = OptionsMenu.diff.length - 1;
        }
        OptionFile.difficulty = this.selDiff;
    }
    
    public void saveChanges() {
        OptionFile.difficulty = this.selDiff;
        if (!this.game.isApplet) {
            OptionFile.writeOpt();
        }
    }
    
    @Override
    public void render(final Screen screen) {
        final int h2 = 2;
        final int w2 = 16;
        final int titleColor = Color.get(-1, 107, 8, 255);
        final int xo = (screen.w - w2 * 8) / 2;
        final int yo = 24;
        Font.renderFrame(screen, "", 5, 5, 26, 13);
        for (int i = 0; i < OptionsMenu.options.length; ++i) {
            String msg = OptionsMenu.options[i];
            int col = Color.get(-1, 222, 222, 222);
            if (i == 0) {
                msg = String.valueOf(msg) + OptionsMenu.diff[this.selDiff];
            }
            if (i == 1) {
                if (this.game.autosave) {
                    msg = String.valueOf(msg) + "ON";
                }
                if (!this.game.autosave) {
                    msg = String.valueOf(msg) + "OFF";
                }
            }
            if (i == this.selected) {
                msg = "> " + msg + " <";
                col = Color.get(-1, 555, 555, 555);
            }
            Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, (8 + i) * 8 - 16, col);
        }
    }
}
