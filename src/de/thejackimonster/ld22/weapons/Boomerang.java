// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.weapons;

import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import java.util.List;
import de.thejackimonster.ld22.story.dialog.NPC;
import de.thejackimonster.ld22.leveltree.mod_leveltree;
import de.thejackimonster.ld22.leveltree.Skill;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Entity;

public class Boomerang extends Entity
{
    public Mob owner;
    public int xdir;
    public int ydir;
    public int damage;
    public boolean comeback;
    private int time;
    
    public Boomerang(final Mob owner, final int dirx, final int diry, final int dmg) {
        super("Boomerang");
        this.owner = owner;
        this.xdir = dirx;
        this.ydir = diry;
        this.damage = dmg;
        this.comeback = false;
        if (owner != null) {
            this.x = owner.x;
            this.y = owner.y;
        }
        if (owner instanceof Player && ((Player)owner).oneshotmobs) {
            this.damage = 9999;
        }
    }
    
    @Override
    public void tick() {
        ++this.time;
        final int xa = this.xdir * 2;
        final int ya = this.ydir * 2;
        if (this.owner == null) {
            this.remove();
            return;
        }
        if (this.comeback) {
            if (this.x > this.owner.x) {
                --this.x;
            }
            else if (this.x < this.owner.x) {
                ++this.x;
            }
            else {
                this.x += 0;
            }
            if (this.y > this.owner.y) {
                --this.y;
            }
            else if (this.y < this.owner.y) {
                ++this.y;
            }
            else {
                this.y += 0;
            }
            if (this.x == this.owner.x && this.y == this.owner.y) {
                if (this.owner instanceof Player) {
                    ((Player)this.owner).activeItem = new BoomerangItem();
                }
                this.remove();
            }
        }
        final List<Entity> entitylist = this.level.getEntities(this.x, this.y, this.x, this.y);
        for (int i = 0; i < entitylist.size(); ++i) {
            final Entity hit = entitylist.get(i);
            if (hit != null && hit instanceof Mob && hit != this.owner) {
                hit.hurt(this.owner, this.damage, ((Mob)hit).dir ^ 0x1);
                this.comeback = true;
            }
        }
        if (this.comeback) {
            return;
        }
        if (this.time > 100) {
            this.comeback = true;
        }
        if (Skill.weapon4.isCompleteDone() && mod_leveltree.coolDownSkill <= 0) {
            final int r = 4;
            final List<Entity> ents = this.level.getEntities(this.x - r * 8, this.y - r * 8, this.x + r * 8, this.y + r * 8);
            if (ents.size() > 1) {
                for (int j = 0; j < ents.size(); ++j) {
                    if (ents.get(j) instanceof Mob && !(ents.get(j) instanceof Player) && !(ents.get(j) instanceof NPC)) {
                        if (this.x < ents.get(j).x) {
                            ++this.x;
                        }
                        if (this.x > ents.get(j).x) {
                            --this.x;
                        }
                        if (this.y < ents.get(j).y) {
                            ++this.y;
                        }
                        if (this.y > ents.get(j).y) {
                            --this.y;
                        }
                        if (this.time % 45 == 0) {
                            ++mod_leveltree.coolDownSkill;
                        }
                        return;
                    }
                }
            }
        }
        if (!this.move(xa, ya) || !this.level.getTile(this.x / 16, this.y / 16).mayPass(this.level, this.x / 16, this.y / 16, this)) {
            this.comeback = true;
        }
    }
    
    @Override
    public boolean isBlockableBy(final Mob mob) {
        return false;
    }
    
    @Override
    public void render(final Screen screen) {
        if (this.time % 2 == 0) {
            screen.render(this.x, this.y, 356, Color.get(-1, 320, 540, -1), this.random.nextInt(4));
        }
        else {
            screen.render(this.x, this.y, 357, Color.get(-1, 320, 540, -1), this.random.nextInt(4));
        }
    }
}
