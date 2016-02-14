// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.leveltree;

import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Arrow;
import com.mojang.ld22.item.ToolType;
import com.mojang.ld22.item.ToolItem;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.item.Item;

public class WeaponSkill extends SpecialSkill
{
    private int weaponDamage;
    
    public WeaponSkill(final String s, final int x, final int y, final Skill parent, final Item item, final int i) {
        super(s, x, y, parent, item, i);
    }
    
    @Override
    public void use(final Player player) {
        if (player.attackItem instanceof ToolItem) {
            this.weaponDamage = ((ToolItem)player.attackItem).level + this.level;
        }
    }
    
    @Override
    public int getAttack() {
        return this.weaponDamage;
    }
    
    @Override
    public void playerUse(final Player player) {
        boolean flag = false;
        if (player.attackItem instanceof ToolItem && mod_leveltree.coolDownSkill == 0) {
            final ToolItem tool = (ToolItem)player.attackItem;
            if (tool.type == ToolType.bow && player.stamina - 3 >= 0 && this.level == 1) {
                if (mod_leveltree.coolDownSkill <= 0 && Skill.weapon1.isCompleteDone()) {
                    mod_leveltree.coolDownSkill += this.weaponDamage * 5 * 3;
                    flag = true;
                }
                player.level.add(new Arrow(player, 0, -1, this.weaponDamage, flag));
                player.level.add(new Arrow(player, 0, 1, this.weaponDamage, flag));
                player.level.add(new Arrow(player, 1, 0, this.weaponDamage, flag));
                player.level.add(new Arrow(player, -1, 0, this.weaponDamage, flag));
                flag = true;
            }
        }
        if (flag) {
            mod_leveltree.coolDownSkill += this.weaponDamage * 10;
        }
    }
}
