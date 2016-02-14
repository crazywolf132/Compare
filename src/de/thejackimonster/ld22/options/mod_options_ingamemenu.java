// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.options;

import com.mojang.ld22.screen.TitleMenu;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.screen.Menu;
import com.mojang.ld22.entity.Player;
import de.thejackimonster.ld22.modloader.ModLoader;
import de.thejackimonster.ld22.modloader.KeyBinding;
import de.thejackimonster.ld22.modloader.BaseMod;

public class mod_options_ingamemenu extends BaseMod
{
    public static KeyBinding key_esc;
    
    static {
        mod_options_ingamemenu.key_esc = new KeyBinding();
    }
    
    @Override
    public void load() {
        OptionFile.create(ModLoader.getGameInstance());
    }
    
    @Override
    public void KeyboardEvent(final int key, final boolean pressed) {
        if (key == OptionFile.keys[6]) {
            mod_options_ingamemenu.key_esc.toggle(pressed);
        }
        if (ModLoader.getGameInstance().menu != null && ModLoader.getGameInstance().menu instanceof ControlsMenu) {
            ((ControlsMenu)ModLoader.getGameInstance().menu).onKeyClicked(key);
        }
    }
    
    @Override
    public void onTickByPlayer(final Player player) {
        if (player != null) {
            if (player.game.menu == null && mod_options_ingamemenu.key_esc.clicked) {
                player.game.setMenu(new IngameMenu());
                Sound.toogle.play();
            }
            if (player.game.menu instanceof TitleMenu && mod_options_ingamemenu.key_esc.clicked) {
                for (int i = 0; i < OptionFile.keys.length; ++i) {
                    OptionFile.keys[i] = OptionFile.bkeys[i];
                }
                OptionFile.writeOpt();
            }
        }
    }
    
    @Override
    public String getVersion() {
        return "Alpha";
    }
}
