// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.loadandsave;

import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.ItemEntity;
import java.util.Random;
import com.mojang.ld22.item.ResourceItem;
import de.thejackimonster.portal.PortalTile;
import de.thejackimonster.portal.PortalItem;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.entity.Player;
import de.thejackimonster.ld22.modloader.KeyBinding;
import de.thejackimonster.ld22.modloader.BaseMod;

public class mod_worldoptions extends BaseMod
{
    public static final KeyBinding key_f;
    public static final KeyBinding key_q;
    private int tick;
    
    static {
        key_f = new KeyBinding();
        key_q = new KeyBinding();
    }
    
    @Override
    public void load() {
    }
    
    @Override
    public void KeyboardEvent(final int key, final boolean pressed) {
        if (key == 70) {
            mod_worldoptions.key_f.toggle(pressed);
        }
        if (key == 81) {
            mod_worldoptions.key_q.toggle(pressed);
        }
    }
    
    @Override
    public void onTickByPlayer(final Player player) {
        ++this.tick;
        if (player != null && this.tick > 3) {
            if (player.game.menu == null && mod_worldoptions.key_f.clicked && !player.game.isApplet && !player.game.isloadingmap) {
                new WorldSaveLoadMenu().saveMap(player.game);
                Sound.special.play();
                this.tick = 0;
            }
            final Item item = player.activeItem;
            if (mod_worldoptions.key_q.clicked && item != null) {
                if (item instanceof PortalItem) {
                    PortalTile.useCanon();
                }
                else {
                    if (player.activeItem instanceof ResourceItem) {
                        if (player.inventory.count(player.activeItem) > 0) {
                            player.inventory.removeResource(((ResourceItem)player.activeItem).resource, 1);
                            if (player.inventory.count(player.activeItem) <= 0) {
                                player.activeItem = null;
                            }
                        }
                        else {
                            for (int i = 0; i < 9; ++i) {
                                if (player.Slots[i] != null && player.activeItem.matches(player.Slots[i])) {
                                    player.Slots[i] = null;
                                }
                            }
                            player.inventory.items.remove(player.activeItem);
                            player.activeItem = null;
                        }
                    }
                    else {
                        for (int i = 0; i < 9; ++i) {
                            if (player.Slots[i] != null && player.activeItem.matches(player.Slots[i])) {
                                player.Slots[i] = null;
                            }
                        }
                        player.inventory.items.remove(player.activeItem);
                        player.activeItem = null;
                    }
                    int drop = new Random().nextInt(4) + 1 + new Random().nextInt(4) + 1;
                    drop *= (int)2.5;
                    int xx = 0;
                    int yy = 0;
                    switch (player.dir) {
                        case 0: {
                            yy = 1;
                            break;
                        }
                        case 1: {
                            yy = -1;
                            break;
                        }
                        case 2: {
                            xx = -1;
                            break;
                        }
                        case 3: {
                            xx = 1;
                            break;
                        }
                    }
                    player.level.add(new ItemEntity(item, player.x + xx * drop, player.y + yy * drop));
                    Sound.pickup.play();
                    this.tick = 0;
                }
            }
        }
    }
    
    @Override
    public String getVersion() {
        return "Beta";
    }
}
