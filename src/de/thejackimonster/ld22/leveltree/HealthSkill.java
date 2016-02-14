// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.leveltree;

import com.mojang.ld22.entity.Player;
import com.mojang.ld22.item.Item;

public class HealthSkill extends Skill
{
    private int heal;
    
    public HealthSkill(final String s, final int x, final int y, final Skill parent, final Item item, final int i) {
        super(s, x, y, parent, item);
        this.heal = i;
    }
    
    @Override
    public void use(final Player player) {
        if (this.onTick()) {
            player.heal(this.heal);
        }
    }
}
