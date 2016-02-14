// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.leveltree;

import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.item.Item;

public class SwimSkill extends SpecialSkill
{
    public SwimSkill(final String s, final int x, final int y, final Skill parent, final Item item, final int i) {
        super(s, x, y, parent, item, i);
    }
    
    @Override
    public void use(final Player player) {
        final Tile tile = player.level.getTile(player.x >> 4, player.y >> 4);
        if ((tile == Tile.water || tile == Tile.lava) && player.tickTime % 60 == 0 && player.stamina > 0 && !player.energy && !player.walkoverwater) {
            if (this.level == 0) {
                ++player.stamina;
            }
            else if (this.level == 1) {
                player.walkoverwater = true;
            }
        }
    }
    
    @Override
    public void playerUse(final Player player) {
    }
    
    @Override
    public int getAttack() {
        return 0;
    }
}
