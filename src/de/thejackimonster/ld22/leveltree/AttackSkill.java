// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.leveltree;

import java.util.List;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.item.Item;

public class AttackSkill extends SpecialSkill
{
    public AttackSkill(final String s, final int x, final int y, final Skill parent, final Item item, final int i) {
        super(s, x, y, parent, item, i);
    }
    
    @Override
    public void use(final Player player) {
        if (this.level == 2) {
            final List<Entity> list = player.level.getEntities(player.x - 1, player.y - 1, player.x + 1, player.y + 1);
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i) instanceof Mob && !(list.get(i) instanceof Player)) {
                    list.get(i).hurt(player, player.getAttackDamage(list.get(i)), player.dir);
                }
            }
        }
    }
    
    @Override
    public void playerUse(final Player player) {
    }
    
    @Override
    public int getAttack() {
        return this.level * 2;
    }
}
