// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.matrix;

import com.mojang.ld22.entity.Player;
import de.thejackimonster.ld22.modloader.KeyBinding;
import de.thejackimonster.ld22.modloader.BaseMod;

public class mod_matrix extends BaseMod
{
    public KeyBinding key_strg;
    public KeyBinding key_m;
    public KeyBinding key_n;
    
    public mod_matrix() {
        this.key_strg = new KeyBinding();
        this.key_m = new KeyBinding();
        this.key_n = new KeyBinding();
    }
    
    @Override
    public void load() {
    }
    
    @Override
    public void KeyboardEvent(final int key, final boolean pressed) {
        if (key == 17) {
            this.key_strg.toggle(pressed);
        }
        if (key == 77) {
            this.key_m.toggle(pressed);
        }
        if (key == 78) {
            this.key_n.toggle(pressed);
        }
    }
    
    @Override
    public void onTickByPlayer(final Player player) {
        if (player.isrunning) {
            if (this.key_m.clicked && player.level instanceof Matrix) {
                ((Matrix)player.level).matrixMode = !((Matrix)player.level).matrixMode;
            }
            final boolean clicked = this.key_n.clicked;
        }
    }
    
    @Override
    public String getVersion() {
        return "Alpha";
    }
}
