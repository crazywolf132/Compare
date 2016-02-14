// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.portal;

import com.mojang.ld22.entity.Player;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.level.tile.Tile;
import de.thejackimonster.ld22.modloader.BaseMod;

public class mod_portal extends BaseMod
{
    private int count;
    public static Tile portal;
    public static Item portal_gun;
    
    static {
        mod_portal.portal = new PortalTile(88);
        mod_portal.portal_gun = new PortalItem();
    }
    
    public mod_portal() {
        this.count = 0;
    }
    
    @Override
    public void load() {
    }
    
    @Override
    public void onItemPickup(final Player player, final Item item) {
    }
    
    @Override
    public String getVersion() {
        return "Alpha";
    }
}
