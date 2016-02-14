// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.loadandsave;

import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.Game;
import com.mojang.ld22.screen.Menu;

public class WorldSaveLoadMenu extends Menu
{
    private int tick;
    
    public WorldSaveLoadMenu() {
        this.tick = 0;
    }
    
    @Override
    public void tick() {
        ++this.tick;
        if (this.tick > 600) {
            this.game.setMenu(null);
        }
    }
    
    public void saveMap(final Game game) {
        if (game != null && !game.issavingmap) {
            final SaveLevel save = new SaveLevel("world", game);
            System.out.println("[SYSTEM][I/O] Saving map");
            save.start();
        }
    }
    
    public void loadMap(final Game game) {
        final LoadLevel load = new LoadLevel("world", game);
        System.out.println("[SYSTEM][I/O] Loading map");
        load.start();
    }
    
    @Override
    public void render(final Screen screen) {
        final String msg = "Save map..";
        final int col = Color.get(-1, 222, 222, 222);
        Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, (screen.h - 8) / 2, col);
    }
}
