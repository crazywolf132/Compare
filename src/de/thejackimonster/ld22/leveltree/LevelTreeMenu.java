// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.leveltree;

import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.screen.Menu;

public class LevelTreeMenu extends Menu
{
    private int ticks;
    private int choosed;
    
    public LevelTreeMenu() {
        this.ticks = 0;
        this.choosed = 0;
        this.ticks = -2;
    }
    
    @Override
    public void tick() {
        ++this.ticks;
        if (this.input.up.clicked) {
            this.toggleItem(-1);
        }
        else if (this.input.down.clicked) {
            this.toggleItem(1);
        }
        else if (this.input.attack.clicked || this.input.menu.clicked) {
            this.Skill(this.game.player);
        }
        else if (mod_leveltree.key_e.clicked && this.ticks >= 0) {
            Sound.toogle.play();
            this.game.setMenu(null);
        }
    }
    
    public void Skill(final Player player) {
        Sound.select.play();
        if (player != null) {
            final int points = player.plevel;
            if (!Skill.skills.get(this.choosed).isCompleteDone() && Skill.skills.get(this.choosed).Done(points)) {
                player.plevel -= Skill.skills.get(this.choosed).needPoints();
                Skill.skills.get(this.choosed).LevelUp();
            }
        }
    }
    
    public void toggleItem(final int i) {
        this.choosed += i;
        this.control();
        boolean end = false;
        while (Skill.skills.get(this.choosed).parent != null && !end) {
            if (!Skill.skills.get(this.choosed).parent.isCompleteDone()) {
                this.choosed += i;
                this.control();
            }
            else {
                end = true;
            }
        }
        Sound.toogle.play();
    }
    
    public void control() {
        if (this.choosed < 0) {
            this.choosed = Skill.skills.size() - 1;
        }
        else if (this.choosed >= Skill.skills.size()) {
            this.choosed = 0;
        }
    }
    
    @Override
    public void render(final Screen screen) {
        this.control();
        screen.clear(Color.get(0, 0, 0, 0));
        final String text = "Skillpoints: " + String.valueOf(this.game.player.plevel) + " ";
        Font.draw(text, screen, screen.w - text.length() * 8, 0, Color.get(-1, 111, 222, 333));
        for (int i = 0; i < Skill.skills.size(); ++i) {
            final String msg = Skill.skills.get(i).getName();
            final int x = Skill.skills.get(i).getX() * 8;
            final int y = Skill.skills.get(i).getY() * 8;
            Skill.skills.get(i).getRenderItem().renderIcon(screen, x, y);
            int col = Color.get(-1, 333, 333, 333);
            if (Skill.skills.get(i).parent != null && !Skill.skills.get(i).parent.isCompleteDone()) {
                col = Color.get(-1, 111, 111, 111);
            }
            if (Skill.skills.get(i).isCompleteDone()) {
                col = Color.get(-1, 555, 555, 555);
            }
            Font.draw(msg, screen, x + 8, y, col);
            if (this.choosed == i) {
                Font.draw(">", screen, x - 8, y, Color.get(-1, 111, 222, 333));
            }
        }
    }
}
