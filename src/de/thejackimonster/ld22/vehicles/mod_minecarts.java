// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.vehicles;

import com.mojang.ld22.entity.Player;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.Entity;
import de.thejackimonster.ld22.modloader.BaseMod;

public class mod_minecarts extends BaseMod
{
    public static final Entity mineCart;
    public static final Entity Boat;
    public static Item minecart;
    public static Item boat;
    
    static {
        mineCart = new Minecart();
        Boat = new Boat();
        mod_minecarts.minecart = new VehicleItem(new Minecart());
        mod_minecarts.boat = new VehicleItem(new Boat());
    }
    
    @Override
    public void load() {
    }
    
    @Override
    public void onTickByPlayer(final Player player) {
    }
    
    @Override
    public String getVersion() {
        return "Alpha";
    }
}
