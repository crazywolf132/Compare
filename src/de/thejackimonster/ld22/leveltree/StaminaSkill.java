// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.leveltree;

import com.mojang.ld22.entity.Player;
import com.mojang.ld22.item.Item;

public class StaminaSkill extends Skill
{
    private int stamina;
    
    public StaminaSkill(final String name, final int x, final int y, final Skill parent, final Item item, final int i) {
        super(name, x, y, parent, item);
        this.stamina = i;
    }
    
    @Override
    public void use(final Player player) {
        if (this.onTick() && player.stamina < player.maxStamina) {
            player.stamina += this.stamina;
        }
    }
    
    @Override
    public int needPoints() {
        return this.stamina;
    }
}
