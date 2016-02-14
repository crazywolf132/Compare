// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.leveltree;

import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import de.thejackimonster.ld22.options.OptionsMenu;
import de.thejackimonster.ld22.options.mod_options_ingamemenu;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.screen.Menu;

public class AchievementListMenu extends Menu
{
    public int offA;
    public int selected;
    public final int size = 10;
    public boolean ingame;
    
    public AchievementListMenu(final boolean flag) {
        this.selected = 0;
        this.offA = 0;
        this.ingame = flag;
    }
    
    @Override
    public void tick() {
        if (this.input.up.clicked || this.input.down.clicked) {
            Sound.toogle.play();
            if (this.input.up.clicked) {
                --this.selected;
            }
            if (this.input.down.clicked) {
                ++this.selected;
            }
            if (this.selected < 0) {
                if (this.offA > 0) {
                    this.selected = 9;
                    --this.offA;
                }
                else {
                    this.selected = 0;
                }
            }
            if (this.selected >= 10) {
                if (this.offA < Achievement.achievementList.size() / 10) {
                    this.selected = 0;
                    ++this.offA;
                }
                else {
                    this.selected = 9;
                }
            }
            if (this.offA < 0) {
                this.offA = 0;
            }
            if (this.offA > Achievement.achievementList.size() / 10) {
                this.offA = Achievement.achievementList.size() / 10 - 1;
            }
            if (this.offA * 10 + this.selected >= Achievement.achievementList.size()) {
                --this.selected;
            }
        }
        if (mod_options_ingamemenu.key_esc.clicked) {
            Sound.select.play();
            this.game.setMenu(new OptionsMenu(this.ingame));
        }
    }
    
    @Override
    public void render(final Screen screen) {
        screen.clear(0);
        final int xx = 24;
        final int yy = 40;
        for (int s = 10, i = 0; i < s; ++i) {
            if (this.offA * s + i < Achievement.achievementList.size()) {
                int col = Color.get(-1, 555, 555, 555);
                if (!Achievement.achievementList.get(this.offA * s + i).isDone()) {
                    col = Color.get(-1, 333, 333, 333);
                }
                String msg = Achievement.achievementList.get(this.offA * s + i).title;
                if (i == this.selected) {
                    msg = ">  " + msg;
                    final String acm = Achievement.achievementList.get(this.offA * s + i).text;
                    String msg2 = "";
                    for (int j = 0; j < acm.length() / 2; ++j) {
                        msg2 = String.valueOf(msg2) + String.valueOf(acm.charAt(j));
                    }
                    Font.draw(msg2, screen, xx - 8, yy - 24, col);
                    msg2 = "";
                    for (int j = acm.length() / 2; j < acm.length(); ++j) {
                        msg2 = String.valueOf(msg2) + String.valueOf(acm.charAt(j));
                    }
                    Font.draw(msg2, screen, xx - 8, yy - 16, col);
                }
                else {
                    msg = "   " + msg;
                }
                Achievement.achievementList.get(this.offA * s + i).renderIcon(screen, xx + 16, yy + i * 9);
                Font.draw(msg, screen, xx, yy + i * 9, col);
            }
        }
    }
}
