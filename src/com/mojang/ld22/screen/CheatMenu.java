// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.screen;

import java.util.List;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.entity.Player;

public class CheatMenu extends Menu
{
    private Player player;
    private int selected;
    private static final String[] stringList;
    
    static {
        stringList = new String[] { "Unl. Stamina", "Unl. Health", "0 Nockback", "1 Shot Res.", "Full Bright", "1 Shot Mobs", "Walk O. Water", "Walk T. Walls", "Day", "Night" };
    }
    
    public CheatMenu(final Player player) {
        this.selected = 0;
        this.player = player;
        if (player.activeItem != null) {
            player.inventory.items.add(0, player.activeItem);
            player.activeItem = null;
        }
    }
    
    @Override
    public void tick() {
        if (this.input.cheatmenu.clicked) {
            this.game.setMenu(null);
        }
        if (this.input.up.clicked) {
            --this.selected;
        }
        if (this.input.down.clicked) {
            ++this.selected;
        }
        if (this.input.up.clicked || this.input.down.clicked) {
            Sound.select.play();
        }
        final int len = CheatMenu.stringList.length;
        if (len == 0) {
            this.selected = 0;
        }
        if (this.selected < 0) {
            this.selected += len;
        }
        if (this.selected >= len) {
            this.selected -= len;
        }
        if (this.input.attack.clicked && len > 0) {
            Sound.toogle.play();
            if (this.selected == 0) {
                if (!this.player.energy) {
                    this.player.energy = true;
                    this.player.stamina = this.player.maxStamina;
                }
                else {
                    this.player.energy = false;
                }
            }
            if (this.selected == 1) {
                if (!this.player.unlimitedhealth) {
                    this.player.unlimitedhealth = true;
                    this.player.health = this.player.maxHealth;
                }
                else {
                    this.player.unlimitedhealth = false;
                }
            }
            if (this.selected == 2) {
                if (!this.player.nonockback) {
                    this.player.nonockback = true;
                }
                else {
                    this.player.nonockback = false;
                }
            }
            if (this.selected == 3) {
                if (!this.player.oneshotresources) {
                    this.player.oneshotresources = true;
                }
                else {
                    this.player.oneshotresources = false;
                }
            }
            if (this.selected == 4) {
                if (!this.player.nodarkness) {
                    this.player.nodarkness = true;
                }
                else {
                    this.player.nodarkness = false;
                }
            }
            if (this.selected == 5) {
                if (!this.player.oneshotmobs) {
                    this.player.oneshotmobs = true;
                }
                else {
                    this.player.oneshotmobs = false;
                }
            }
            if (this.selected == 6) {
                if (!this.player.walkoverwater) {
                    this.player.walkoverwater = true;
                }
                else {
                    this.player.walkoverwater = false;
                }
            }
            if (this.selected == 7) {
                if (!this.player.walkthroughwalls) {
                    this.player.walkthroughwalls = true;
                }
                else {
                    this.player.walkthroughwalls = false;
                }
            }
            if (this.selected == 8) {
                Sound.select.play();
                this.game.lightlvl = 8;
                this.game.rising = false;
            }
            if (this.selected == 9) {
                Sound.select.play();
                this.game.lightlvl = 0;
                this.game.rising = false;
            }
            if (this.selected == 10) {
                if (this.player.level.depth == -4) {
                    this.player.game.changeLevel(3, true);
                }
                else {
                    this.player.game.changeLevel(5, true);
                }
            }
        }
    }
    
    @Override
    public void render(final Screen screen) {
        final int xoff = 4;
        final int yoff = 4;
        Font.renderFrame(screen, "Addon-Menu", 1 + xoff, 1 + yoff, 23, 18);
        final int aktivcol = Color.get(-1, 555, 555, 555);
        final int unaktivcol = Color.get(-1, 200, 12, 12);
        final int selfaktivcol = Color.get(-1, 500, 500, 500);
        for (int i = 0; i < CheatMenu.stringList.length; ++i) {
            int c = unaktivcol;
            String msg = CheatMenu.stringList[i];
            if (i == this.selected) {
                msg = "> " + msg;
            }
            if ((this.player.energy && i == 0) || (this.player.unlimitedhealth && i == 1) || (this.player.nonockback && i == 2) || (this.player.oneshotresources && i == 3) || (this.player.nodarkness && i == 4) || (this.player.oneshotmobs && i == 5) || (this.player.walkoverwater && i == 6) || (this.player.walkthroughwalls && i == 7)) {
                c = aktivcol;
            }
            else if (i == 8 || i == 9 || i == 10) {
                c = selfaktivcol;
            }
            Font.draw(msg, screen, 24 + xoff * 8, (2 + i) * 8 + yoff * 8, c);
        }
    }
    
    @Override
    public void renderItemList(final Screen screen, final int xo, final int yo, final int x1, final int y1, final List<? extends ListItem> listItems, int selected) {
        boolean renderCursor = true;
        if (selected < 0) {
            selected = -selected - 1;
            renderCursor = false;
        }
        final int h = y1 - yo - 1;
        final int i0 = 0;
        int i2 = listItems.size();
        if (i2 > h) {
            i2 = h;
        }
        int io = selected - h / 2;
        if (io > listItems.size() - h) {
            io = listItems.size() - h;
        }
        if (io < 0) {
            io = 0;
        }
        for (int j = i0; j < i2; ++j) {
            ((ListItem)listItems.get(j + io)).renderInventory(screen, (1 + xo) * 8, (j + 1 + yo) * 8);
        }
        if (renderCursor) {
            final int yy = selected + 1 - io + yo;
            Font.draw(">", screen, (xo + 0) * 8 + 40, yy * 8 + 32, Color.get(-1, -1, -1, 555));
        }
    }
}
