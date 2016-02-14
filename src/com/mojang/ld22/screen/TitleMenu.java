// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.screen;

import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.entity.Player;
import de.thejackimonster.ld22.options.OptionsMenu;
import de.zelosfan.ld22.server.Client;
import de.zelosfan.ld22.server.Server;
import com.mojang.ld22.entity.MultiPlayer;
import de.thejackimonster.ld22.story.character.NewCharacterMenu;
import de.thejackimonster.ld22.leveltree.Achievement;
import com.mojang.ld22.entity.Entity;
import de.thejackimonster.ld22.loadandsave.WorldSaveLoadMenu;
import com.mojang.ld22.sound.Sound;
import java.util.Random;

public class TitleMenu extends Menu
{
    private int selected;
    private int tickc;
    private int tickcount;
    private boolean cnt;
    private int rdm;
    private Random rndm;
    private int modx;
    private int mody;
    int titleColor;
    private static final String[] options;
    
    static {
        options = new String[] { "Load Game", "New Game", "Create Character", "World Menu", "Join Server", "Host Server", "Options", "How to play", "About" };
    }
    
    public TitleMenu() {
        this.selected = 0;
        this.tickc = 0;
        this.tickcount = 0;
        this.cnt = false;
        this.rndm = new Random();
        this.modx = this.rndm.nextInt(3) - 1;
        this.mody = this.rndm.nextInt(3) - 1;
    }
    
    @Override
    public void tick() throws Exception {
        ++this.tickcount;
        if (this.input.up.clicked) {
            --this.selected;
        }
        if (this.input.down.clicked) {
            ++this.selected;
        }
        if (this.input.up.clicked || this.input.down.clicked) {
            Sound.select.play();
        }
        final int len = TitleMenu.options.length;
        if (this.game.isApplet) {
            if (this.selected < 1) {
                this.selected += len;
            }
        }
        else if (this.selected < 0) {
            this.selected += len;
        }
        if (this.selected >= len) {
            this.selected -= len;
        }
        if (this.selected == 0 && this.game.isApplet) {
            this.selected = 1;
        }
        if (this.cnt) {
            ++this.tickc;
        }
        if (this.tickc == 20) {
            Sound.test.play();
            this.game.setMenu(null);
        }
        if (this.tickcount % 2 == 0) {
            final Player player = this.game.player;
            player.x += this.modx;
            final Player player2 = this.game.player;
            player2.y += this.mody;
        }
        if (this.tickcount % 1000 == 0 && !this.game.isloadingmap) {
            this.modx = this.rndm.nextInt(3) - 1;
            this.mody = this.rndm.nextInt(3) - 1;
        }
        if (this.input.attack.clicked || this.input.menu.clicked) {
            if (this.selected == 0 && !this.game.isloadingmap && !this.game.isApplet) {
                this.modx = 0;
                this.mody = 0;
                this.game.mpstate = 0;
                if (!this.game.isApplet && !this.game.isloadingmap) {
                    new WorldSaveLoadMenu().loadMap(this.game);
                }
                if (this.game.isApplet) {
                    this.game.setMenu(null);
                }
            }
            if (this.selected == 1 && !this.game.isloadingmap) {
                this.game.mpstate = 0;
                this.game.level.remove(this.game.player);
                this.game.player.findStartPos(this.game.level);
                this.cnt = true;
                this.game.level.add(this.game.player);
                Achievement.newWorld.Done(this.game);
            }
            if (this.selected == 2 && this.game.player != null) {
                this.game.setMenu(new NewCharacterMenu(this.game.isApplet));
            }
            if (this.selected == 3 && !this.game.isloadingmap) {
                this.game.setMenu(new WorldMenu(this));
            }
            if (this.selected == 5 && !this.game.isApplet && !this.game.isloadingmap) {
                this.game.mpstate = 2;
                new WorldSaveLoadMenu().loadMap(this.game);
                this.game.player2 = new MultiPlayer(this.game);
                this.game.level.add(this.game.player2);
                this.game.server = new Server(this.game);
                this.game.player2.x = this.game.player.y;
                this.game.player2.y = this.game.player.y;
            }
            if (this.selected == 4 && !this.game.isloadingmap && !this.game.isApplet) {
                this.game.mpstate = 1;
                this.game.client = new Client(this.game, true);
                this.game.player2 = new MultiPlayer(this.game);
                this.game.level.add(this.game.player2);
            }
            if (this.selected == 6) {
                this.game.setMenu(new OptionsMenu(false));
            }
            if (this.selected == 7) {
                this.game.setMenu(new InstructionsMenu(this));
            }
            if (this.selected == 8) {
                this.game.setMenu(new AboutMenu(this));
            }
        }
    }
    
    @Override
    public void render(final Screen screen) {
        final int h = 25;
        final int w = 35;
        ++this.rdm;
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                this.titleColor = Color.get(0, 0, this.rdm + x * 5 + y * 2, 551);
            }
        }
        final int frX = 6;
        final int frY = 5;
        final int frW = 20;
        final int frH = 12;
        if (!this.cnt) {
            Font.renderFrame(screen, "", frX, frY, frX + frW, frY + frH);
        }
        else if (!this.game.isApplet) {
            new WorldSaveLoadMenu().saveMap(this.game);
        }
        final int h2 = 2;
        final int w2 = 16;
        final int titleColor = Color.get(-1, 107, 8, 255);
        final int xo = (screen.w - w2 * 8) / 2;
        final int yo = 24;
        for (int y2 = 0; y2 < h2; ++y2) {
            for (int x2 = 0; x2 < w2; ++x2) {
                screen.render(xo + x2 * 8, yo + y2 * 8, x2 + 14 + (y2 + 6) * 32, titleColor, 0);
            }
        }
        for (int i = 0; i < TitleMenu.options.length; ++i) {
            String msg = TitleMenu.options[i];
            int col = Color.get(-1, 222, 222, 222);
            if (i == this.selected) {
                msg = "> " + msg + " <";
                col = Color.get(-1, 555, 555, 555);
            }
            if (i == 0 && this.game.isApplet) {
                col = Color.get(-1, 111, 111, 111);
            }
            if (i == 4 && this.game.isApplet) {
                col = Color.get(-1, 111, 111, 111);
            }
            if (i == 5 && this.game.isApplet) {
                col = Color.get(-1, 111, 111, 111);
            }
            Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, (8 + i) * 8 - 16, col);
        }
        final StringBuilder sb = new StringBuilder("[");
        this.game.getClass();
        final String msg2 = sb.append("1.1.0").append("]").toString();
        Font.draw(msg2, screen, (frX + frW - msg2.length()) * 8, (frY + frH - 1) * 8, Color.get(-1, 500, 500, 500));
        if (this.game.isApplet) {
            Font.renderFrame(screen, "", -1, 18, 100, 20);
            Font.draw("You can not save in Browser-Mode", screen, 4, 150, Color.get(-1, 511, 511, 511));
        }
    }
}
