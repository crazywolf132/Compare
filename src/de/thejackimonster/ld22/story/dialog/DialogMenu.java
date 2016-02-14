// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.story.dialog;

import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.sound.Sound;
import java.util.ArrayList;
import java.util.List;
import com.mojang.ld22.screen.Menu;

public class DialogMenu extends Menu
{
    public List<String> answerList;
    public List<String> dialogList;
    public final String dialog;
    private static final int line = 12;
    private int selected;
    private int level;
    
    public DialogMenu(final String dialog, final String... answers) {
        this.answerList = new ArrayList<String>();
        this.dialogList = new ArrayList<String>();
        this.dialog = dialog;
        String text = "";
        int j = 0;
        final List<String> s = new ArrayList<String>();
        for (int i = 0; i < dialog.length(); ++i) {
            ++j;
            if (String.valueOf(dialog.charAt(i)) != "_") {
                text = String.valueOf(text) + String.valueOf(dialog.charAt(i));
            }
            if (j > 12 || i + 1 == dialog.length() || String.valueOf(dialog.charAt(i)) == "_") {
                this.dialogList.add(text);
                text = "";
                j = 0;
            }
        }
        for (int i = 0; i < answers.length; ++i) {
            this.answerList.add(answers[i]);
        }
    }
    
    @Override
    public void tick() {
        if (mod_StoryMode.key_r.clicked) {
            this.game.setMenu(null);
        }
        if (this.input.down.clicked) {
            ++this.selected;
            Sound.toogle.play();
        }
        if (this.input.up.clicked) {
            --this.selected;
            Sound.toogle.play();
        }
        if (this.selected < 0) {
            this.selected = this.answerList.size() - 1;
        }
        if (this.selected >= this.answerList.size()) {
            this.selected = 0;
        }
        if (this.input.attack.clicked || this.input.menu.clicked || this.input.cheatmenu.clicked) {
            Sound.select.play();
            this.say(this.dialog, this.selected);
        }
    }
    
    public void say(final String dialog, final int i) {
        this.selected = 0;
        DialogManager.say(dialog, i);
    }
    
    @Override
    public void render(final Screen screen) {
        Font.renderFrame(screen, "", 0, 0, 14, screen.h / 8 - 1);
        for (int i = 0; i < this.dialogList.size(); ++i) {
            Font.draw(this.dialogList.get(i), screen, 8, 8 + i * 10, Color.get(-1, -1, -1, 555));
        }
        int ypos = 16;
        for (int j = 0; j < this.answerList.size(); ++j) {
            if (j == this.selected) {
                Font.renderFrame(screen, "", screen.w / 8 - 3 - this.answerList.get(j).length(), ypos / 8, screen.w / 8 - 3 - this.answerList.get(j).length() + this.answerList.get(j).length() + 2, 2 + ypos / 8);
                Font.draw(">" + this.answerList.get(j), screen, screen.w - 16 - this.answerList.get(j).length() * 8, 8 + ypos, Color.get(-1, -1, -1, 555));
            }
            else {
                Font.renderFrame(screen, "", screen.w / 8 - 3 - this.answerList.get(j).length() / 2, ypos / 8, screen.w / 8 - 3 - this.answerList.get(j).length() / 2 + this.answerList.get(j).length() + 2, 2 + ypos / 8);
                Font.draw(this.answerList.get(j), screen, screen.w - 16 - this.answerList.get(j).length() * 8 / 2, 8 + ypos, Color.get(-1, -1, -1, 555));
            }
            ypos += 32;
        }
    }
}
