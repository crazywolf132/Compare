// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.modloader;

import java.util.ArrayList;
import java.util.List;

public class KeyBinding
{
    public static final List<KeyBinding> keybindings;
    public int presses;
    public int absorbs;
    public boolean down;
    public boolean clicked;
    
    static {
        keybindings = new ArrayList<KeyBinding>();
    }
    
    public KeyBinding() {
        KeyBinding.keybindings.add(this);
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
