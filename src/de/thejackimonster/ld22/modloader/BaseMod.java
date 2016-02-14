// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.modloader;

import com.mojang.ld22.entity.Inventory;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.screen.Menu;
import com.mojang.ld22.Game;
import java.util.Random;
import com.mojang.ld22.level.Level;

public abstract class BaseMod
{
    public BaseMod() {
        ModLoader.mods.add(this);
    }
    
    public abstract void load();
    
    public void GenerateLevel(final Level level, final Random random, final int i, final int j) {
    }
    
    public void onTickInGame(final Game game) {
    }
    
    public void onTickInMenu(final Menu menu) {
    }
    
    public void onTickByPlayer(final Player player) {
    }
    
    public abstract String getVersion();
    
    public String getName() {
        return this.getClass().getSimpleName();
    }
    
    public void KeyboardEvent(final int key, final boolean pressed) {
    }
    
    public void TakenFromCrafting(final Player player, final Item item, final Inventory inventory) {
    }
    
    public void TakenFromFurnace(final Player player, final Item item) {
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getName()) + ' ' + this.getVersion();
    }
    
    public void onItemPickup(final Player player, final Item item) {
    }
}
