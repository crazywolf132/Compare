// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.vehicles;

import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.gfx.Color;

public class Boat extends Vehicle
{
    public Boat() {
        super("Boat", 5, Color.get(-1, 351, 324, 534), new Tile[] { Tile.water });
    }
    
    @Override
    public boolean canSwim() {
        return true;
    }
}
