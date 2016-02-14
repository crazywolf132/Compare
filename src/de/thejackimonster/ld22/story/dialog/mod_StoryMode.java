// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.story.dialog;

import de.thejackimonster.ld22.story.character.NewCharacterMenu;
import de.thejackimonster.ld22.modloader.ModLoader;
import de.thejackimonster.ld22.options.OptionFile;
import de.thejackimonster.ld22.modloader.KeyBinding;
import de.thejackimonster.ld22.modloader.BaseMod;

public class mod_StoryMode extends BaseMod
{
    public static final KeyBinding key_r;
    
    static {
        key_r = new KeyBinding();
    }
    
    @Override
    public void KeyboardEvent(final int key, final boolean pressed) {
        if (key == OptionFile.keys[11]) {
            mod_StoryMode.key_r.toggle(pressed);
        }
        if (ModLoader.getGameInstance().menu != null && ModLoader.getGameInstance().menu instanceof NewCharacterMenu) {
            ((NewCharacterMenu)ModLoader.getGameInstance().menu).onKeyClicked(key);
        }
    }
    
    @Override
    public void load() {
    }
    
    @Override
    public String getVersion() {
        return "Beta";
    }
}
