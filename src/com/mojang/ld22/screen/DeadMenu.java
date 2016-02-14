// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.screen;

import java.awt.event.KeyEvent;
import de.thejackimonster.ld22.options.OptionFile;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import de.thejackimonster.ld22.loadandsave.WorldSaveLoadMenu;
import de.thejackimonster.ld22.loadandsave.LoadLevel;

public class DeadMenu extends Menu
{
    private int inputDelay;
    
    public DeadMenu() {
        this.inputDelay = 60;
    }
    
    @Override
    public void tick() {
        if (this.inputDelay > 0) {
            --this.inputDelay;
        }
        else if (this.input.attack.clicked) {
            this.game.player.removed = false;
            this.game.playerDeadTime = 0;
            this.game.player.health = this.game.player.maxHealth;
            this.game.player.stamina = this.game.player.maxStamina;
            this.game.setMenu(new TitleMenu());
        }
        else if (this.input.menu.clicked) {
            if (!this.game.isApplet) {
                final LoadLevel lvl = new LoadLevel("world", this.game);
                lvl.start();
            }
        }
        else if (this.input.cheatmenu.clicked && this.game.mpstate == 0) {
            this.game.player.removed = false;
            this.game.playerDeadTime = 0;
            this.game.player.x = this.game.respawnx;
            this.game.player.y = this.game.respawny;
            this.game.player.health = this.game.player.maxHealth;
            this.game.player.stamina = this.game.player.maxStamina;
            this.game.changeLevel(-this.game.player.level.depth);
            if (this.game.mpstate == 1) {
                this.game.multiplayerrespawned = true;
            }
            if (!this.game.isApplet) {
                new WorldSaveLoadMenu().saveMap(this.game);
            }
            this.game.setMenu(null);
        }
    }
    
    @Override
    public void render(final Screen screen) {
        Font.renderFrame(screen, "", 1, 4, 27, 11);
        Font.draw("You died! Aww!", screen, 16, 40, Color.get(-1, 555, 555, 555));
        int seconds = this.game.gameTime / 60;
        int minutes = seconds / 60;
        final int hours = minutes / 60;
        minutes %= 60;
        seconds %= 60;
        String timeString = "";
        if (hours > 0) {
            timeString = String.valueOf(hours) + "h" + ((minutes < 10) ? "0" : "") + minutes + "m";
        }
        else {
            timeString = String.valueOf(minutes) + "m " + ((seconds < 10) ? "0" : "") + seconds + "s";
        }
        Font.draw("Time:", screen, 16, 48, Color.get(-1, 555, 555, 555));
        Font.draw(timeString, screen, 56, 48, Color.get(-1, 550, 550, 550));
        Font.draw("Score:", screen, 16, 56, Color.get(-1, 555, 555, 555));
        Font.draw(new StringBuilder().append(this.game.player.score).toString(), screen, 64, 56, Color.get(-1, 550, 550, 550));
        Font.draw("Press " + KeyEvent.getKeyText(OptionFile.keys[9]) + " to respawn", screen, 16, 72, Color.get(-1, 333, 333, 333));
        Font.draw("Press " + KeyEvent.getKeyText(OptionFile.keys[8]) + " to load last save", screen, 16, 64, Color.get(-1, 333, 333, 333));
        Font.draw("Press " + KeyEvent.getKeyText(OptionFile.keys[7]) + " to lose", screen, 16, 80, Color.get(-1, 333, 333, 333));
    }
}
