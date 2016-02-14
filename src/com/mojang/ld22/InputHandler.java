// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22;

import de.thejackimonster.ld22.modloader.ModLoader;
import de.thejackimonster.ld22.options.OptionFile;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import de.thejackimonster.ld22.modloader.KeyBinding;
import java.util.List;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener
{
    public List<Key> keys;
    public Key up;
    public Key down;
    public Key left;
    public Key right;
    public Key attack;
    public Key menu;
    public Key cheatmenu;
    public Key run;
    public Key slot1;
    public Key slot2;
    public Key slot3;
    public Key slot4;
    public Key slot5;
    public Key slot6;
    public Key slot7;
    public Key slot8;
    public Key slot9;
    
    public void releaseAll() {
        for (int i = 0; i < this.keys.size(); ++i) {
            this.keys.get(i).down = false;
        }
        for (int i = 0; i < KeyBinding.keybindings.size(); ++i) {
            KeyBinding.keybindings.get(i).down = false;
        }
    }
    
    public void tick() {
        for (int i = 0; i < this.keys.size(); ++i) {
            this.keys.get(i).tick();
        }
        for (int i = 0; i < KeyBinding.keybindings.size(); ++i) {
            KeyBinding.keybindings.get(i).tick();
        }
    }
    
    public InputHandler(final Game game) {
        this.keys = new ArrayList<Key>();
        this.up = new Key();
        this.down = new Key();
        this.left = new Key();
        this.right = new Key();
        this.attack = new Key();
        this.menu = new Key();
        this.cheatmenu = new Key();
        this.run = new Key();
        this.slot1 = new Key();
        this.slot2 = new Key();
        this.slot3 = new Key();
        this.slot4 = new Key();
        this.slot5 = new Key();
        this.slot6 = new Key();
        this.slot7 = new Key();
        this.slot8 = new Key();
        this.slot9 = new Key();
        game.addKeyListener(this);
    }
    
    @Override
    public void keyPressed(final KeyEvent ke) {
        this.toggle(ke, true);
    }
    
    @Override
    public void keyReleased(final KeyEvent ke) {
        this.toggle(ke, false);
    }
    
    private void toggle(final KeyEvent ke, final boolean pressed) {
        if (ke.getKeyCode() == OptionFile.keys[0]) {
            this.up.toggle(pressed);
        }
        if (ke.getKeyCode() == OptionFile.keys[2]) {
            this.down.toggle(pressed);
        }
        if (ke.getKeyCode() == OptionFile.keys[1]) {
            this.left.toggle(pressed);
        }
        if (ke.getKeyCode() == OptionFile.keys[3]) {
            this.right.toggle(pressed);
        }
        if (ke.getKeyCode() == OptionFile.keys[9]) {
            this.cheatmenu.toggle(pressed);
        }
        if (ke.getKeyCode() == OptionFile.keys[5]) {
            this.run.toggle(pressed);
        }
        if (ke.getKeyCode() == 49) {
            this.slot1.toggle(pressed);
        }
        if (ke.getKeyCode() == 50) {
            this.slot2.toggle(pressed);
        }
        if (ke.getKeyCode() == 51) {
            this.slot3.toggle(pressed);
        }
        if (ke.getKeyCode() == 52) {
            this.slot4.toggle(pressed);
        }
        if (ke.getKeyCode() == 53) {
            this.slot5.toggle(pressed);
        }
        if (ke.getKeyCode() == 54) {
            this.slot6.toggle(pressed);
        }
        if (ke.getKeyCode() == 55) {
            this.slot7.toggle(pressed);
        }
        if (ke.getKeyCode() == 56) {
            this.slot8.toggle(pressed);
        }
        if (ke.getKeyCode() == 57) {
            this.slot9.toggle(pressed);
        }
        if (ke.getKeyCode() == OptionFile.keys[8]) {
            this.menu.toggle(pressed);
        }
        if (ke.getKeyCode() == OptionFile.keys[7]) {
            this.attack.toggle(pressed);
        }
        ModLoader.toggleKey(ke, pressed);
    }
    
    @Override
    public void keyTyped(final KeyEvent ke) {
    }
    
    public class Key
    {
        public int presses;
        public int absorbs;
        public boolean down;
        public boolean clicked;
        
        public Key() {
            InputHandler.this.keys.add(this);
        }
        
        public void toggle(final boolean pressed) {
            if (pressed != this.down) {
                this.down = pressed;
            }
            if (pressed) {
                ++this.presses;
            }
        }
        
        public void tick() {
            if (this.absorbs < this.presses) {
                ++this.absorbs;
                this.clicked = true;
            }
            else {
                this.clicked = false;
            }
        }
    }
}
