// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.screen;

import java.awt.event.KeyEvent;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Color;
import de.thejackimonster.ld22.options.OptionFile;
import com.mojang.ld22.gfx.Screen;

public class InstructionsMenu extends Menu
{
    private Menu parent;
    
    public InstructionsMenu(final Menu parent) {
        this.parent = parent;
    }
    
    @Override
    public void tick() {
        if (this.input.attack.clicked || this.input.menu.clicked) {
            this.game.setMenu(this.parent);
        }
    }
    
    @Override
    public void render(final Screen screen) {
        screen.clear(0);
        if (!this.game.isApplet) {
            OptionFile.readOpt();
        }
        Font.draw("HOW TO PLAY", screen, 36, 8, Color.get(0, 555, 555, 555));
        Font.draw("Move your character", screen, 4, 24, Color.get(0, 333, 333, 333));
        Font.draw("with the keys: " + KeyEvent.getKeyText(OptionFile.keys[0]) + ", " + KeyEvent.getKeyText(OptionFile.keys[1]) + ", " + KeyEvent.getKeyText(OptionFile.keys[2]) + " and " + KeyEvent.getKeyText(OptionFile.keys[3]), screen, 4, 32, Color.get(0, 333, 333, 333));
        Font.draw("press " + KeyEvent.getKeyText(OptionFile.keys[7]) + " to attack", screen, 4, 40, Color.get(0, 333, 333, 333));
        Font.draw("and " + KeyEvent.getKeyText(OptionFile.keys[8]) + " to open the", screen, 4, 48, Color.get(0, 333, 333, 333));
        Font.draw("inventory and to", screen, 4, 56, Color.get(0, 333, 333, 333));
        Font.draw("use items.", screen, 4, 64, Color.get(0, 333, 333, 333));
        Font.draw("Select an item in", screen, 4, 72, Color.get(0, 333, 333, 333));
        Font.draw("the inventory to", screen, 4, 80, Color.get(0, 333, 333, 333));
        Font.draw("equip it.", screen, 4, 88, Color.get(0, 333, 333, 333));
        Font.draw("Kill the air wizard", screen, 4, 96, Color.get(0, 333, 333, 333));
        Font.draw("to win the game!", screen, 4, 104, Color.get(0, 333, 333, 333));
    }
}
