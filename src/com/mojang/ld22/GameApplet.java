// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22;

import de.thejackimonster.ld22.modloader.UseMods;
import de.thejackimonster.ld22.modloader.ModLoader;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.applet.Applet;

public class GameApplet extends Applet
{
    private static final long serialVersionUID = 1L;
    private Game game;
    
    public GameApplet() {
        this.game = new Game();
    }
    
    @Override
    public void init() {
        this.setName("InfinityTale");
        this.setSize(new Dimension(780, 540));
        this.setLayout(new BorderLayout());
        this.add(this.game, "Center");
        this.game.isApplet = true;
        ModLoader.addGame(this.game);
        UseMods.addMods();
        ModLoader.loadAllMods();
    }
    
    @Override
    public void start() {
        this.game.start(true);
    }
    
    @Override
    public void stop() {
        this.game.stop();
    }
}
