// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.leveltree;

import com.mojang.ld22.entity.Player;
import com.mojang.ld22.item.Item;

public abstract class SpecialSkill extends Skill
{
    protected int level;
    
    public SpecialSkill(final String s, final int x, final int y, final Skill parent, final Item item, final int i) {
        super(s, x, y, parent, item);
        this.level = i;
    }
    
    @Override
    public abstract void use(final Player p0);
    
    public abstract int getAttack();
    
    public abstract void playerUse(final Player p0);
    
    @Override
    public int needPoints() {
        return this.level + 1;
    }
}
