// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.leveltree;

import com.mojang.ld22.entity.Player;
import de.thejackimonster.ld22.weapons.mod_weapons;
import java.util.ArrayList;
import com.mojang.ld22.item.Item;
import java.util.List;

public abstract class Skill
{
    public static List<Skill> skills;
    public static Skill health1;
    public static Skill health2;
    public static Skill health3;
    public static Skill stamina1;
    public static Skill stamina2;
    public static Skill stamina3;
    public static Skill swimming1;
    public static Skill swimming2;
    public static Skill attack1;
    public static Skill attack2;
    public static Skill attack3;
    public static Skill weapon1;
    public static Skill weapon2;
    public static Skill weapon3;
    public static Skill weapon4;
    protected boolean done;
    protected int lvl;
    protected String name;
    protected int Xpos;
    protected int Ypos;
    protected Skill parent;
    protected Item item;
    private int tick;
    
    static {
        Skill.skills = new ArrayList<Skill>();
        Skill.health1 = new HealthSkill("Bread-time", 2, 1, null, Item.bread, 1);
        Skill.health2 = new HealthSkill("Apples from paradise", 1, 1, Skill.health1, Item.apple, 2);
        Skill.health3 = new HealthSkill("A heart for a hero", 1, 1, Skill.health2, Item.heartcontainer, 3);
        Skill.stamina1 = new StaminaSkill("A little man", 2, 4, null, Item.powerglove, 1);
        Skill.stamina2 = new StaminaSkill("Robin Hood", 1, 1, Skill.stamina1, Item.stonebow, 2);
        Skill.stamina3 = new StaminaSkill("The king comes home", 1, 1, Skill.stamina2, Item.gembow, 3);
        Skill.swimming1 = new SwimSkill("Dark waters..", 0, 3, Skill.stamina1, Item.ironbukket, 0);
        Skill.swimming2 = new SwimSkill("I don't swim, I go!", 1, 1, Skill.swimming1, Item.water, 1);
        Skill.attack1 = new AttackSkill("Damage!", 2, 9, null, Item.stoneSword, 0);
        Skill.attack2 = new AttackSkill("I won't hurt you..", 1, 1, Skill.attack1, Item.ironAxe, 1);
        Skill.attack3 = new AttackSkill("Knock knock, who's.. dead?", 1, 1, Skill.attack2, Item.gemHammer, 2);
        Skill.weapon1 = new WeaponSkill("Angry Arrow", -1, 7, Skill.stamina2, Item.stonebow, 0);
        Skill.weapon2 = new WeaponSkill("Compass-Bow", 1, 1, Skill.weapon1, Item.gembow, 1);
        Skill.weapon3 = new WeaponSkill("Special-Regeneration++", -2, 8, Skill.stamina3, Item.powerglove, 2);
        Skill.weapon4 = new WeaponSkill("Booooomeerr..rraaangg", 0, 11, Skill.stamina1, mod_weapons.boome_rang, 3);
    }
    
    public Skill(final String s, final int x, final int y, final Skill parent, final Item item) {
        this.tick = 0;
        this.name = s;
        this.Xpos = x;
        this.Ypos = y;
        if (parent != null) {
            this.Xpos += parent.Xpos;
            this.Ypos += parent.Ypos;
        }
        this.done = false;
        this.lvl = 0;
        this.parent = parent;
        this.item = item;
        addSkill(this, s);
    }
    
    private static void addSkill(final Skill skill, final String name) {
        boolean flag = true;
        for (int i = 0; i < Skill.skills.size(); ++i) {
            if (Skill.skills.get(i).getName().equalsIgnoreCase(name)) {
                flag = false;
            }
        }
        if (flag) {
            Skill.skills.add(skill);
        }
    }
    
    public int needPoints() {
        return 1;
    }
    
    public boolean Done(final int points) {
        if (points >= this.needPoints()) {
            this.done = true;
        }
        return this.done;
    }
    
    public int getLevel() {
        return this.lvl;
    }
    
    public int MAXLevel() {
        return 1;
    }
    
    public boolean onTick() {
        ++this.tick;
        if (this.tick > 600) {
            this.tick = 0;
            return true;
        }
        return false;
    }
    
    public void LevelUp() {
        if (this.lvl < this.MAXLevel()) {
            ++this.lvl;
        }
    }
    
    public abstract void use(final Player p0);
    
    public Item getRenderItem() {
        return this.item;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Skill getParent() {
        return this.parent;
    }
    
    public boolean isCompleteDone() {
        return this.getLevel() == this.MAXLevel() && this.done;
    }
    
    public int getX() {
        return this.Xpos;
    }
    
    public int getY() {
        return this.Ypos;
    }
}
