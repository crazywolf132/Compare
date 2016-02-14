// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.leveltree;

import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.screen.Menu;
import com.mojang.ld22.entity.Player;
import de.thejackimonster.ld22.options.OptionFile;
import de.thejackimonster.ld22.modloader.KeyBinding;
import de.thejackimonster.ld22.modloader.BaseMod;

public class mod_leveltree extends BaseMod
{
    public static final KeyBinding key_e;
    public static int coolDownSkill;
    private int tick;
    private int ticks;
    
    static {
        key_e = new KeyBinding();
    }
    
    @Override
    public void load() {
    }
    
    @Override
    public void KeyboardEvent(final int key, final boolean pressed) {
        if (key == OptionFile.keys[10]) {
            mod_leveltree.key_e.toggle(pressed);
        }
    }
    
    @Override
    public void onTickByPlayer(final Player player) {
        ++this.tick;
        ++this.ticks;
        boolean cool = false;
        if (player != null && this.tick > 3 && player.game.menu == null && mod_leveltree.key_e.clicked) {
            player.game.setMenu(new LevelTreeMenu());
            Sound.toogle.play();
            this.tick = 0;
        }
        for (int i = 0; i < Skill.skills.size(); ++i) {
            if (Skill.skills.get(i).isCompleteDone()) {
                Skill.skills.get(i).use(player);
                if (Skill.skills.get(i) instanceof WeaponSkill) {
                    cool = true;
                }
            }
        }
        if (!cool) {
            return;
        }
        if (Skill.weapon3.isCompleteDone() && this.ticks % 59 == 0) {
            --mod_leveltree.coolDownSkill;
        }
        if (this.ticks % 60 == 0) {
            --mod_leveltree.coolDownSkill;
        }
        if (mod_leveltree.coolDownSkill < 0) {
            mod_leveltree.coolDownSkill = 0;
        }
        if (mod_leveltree.coolDownSkill > 60) {
            mod_leveltree.coolDownSkill = 60;
        }
    }
    
    @Override
    public String getVersion() {
        return "Alpha";
    }
}
