// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.screen;

import java.util.List;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.Item;
import de.thejackimonster.ld22.leveltree.Achievement;
import com.mojang.ld22.entity.Player;

public class InventoryMenu extends Menu
{
    private Player player;
    private int selected;
    
    public InventoryMenu(final Player player) {
        this.selected = 0;
        this.player = player;
        if (player.activeItem != null) {
            player.inventory.items.add(0, player.activeItem);
            player.activeItem = null;
        }
    }
    
    @Override
    public void tick() {
        if (this.input.menu.clicked) {
            this.game.setMenu(null);
            Achievement.openInventory.Done(this.game);
        }
        if (this.input.up.clicked) {
            --this.selected;
        }
        if (this.input.down.clicked) {
            ++this.selected;
        }
        final int len = this.player.inventory.items.size();
        if (len == 0) {
            this.selected = 0;
        }
        if (this.selected < 0) {
            this.selected += len;
        }
        if (this.selected >= len) {
            this.selected -= len;
        }
        if (this.input.slot1.clicked) {}
        if (this.input.slot2.clicked && len > 0) {
            final Item item = this.player.inventory.items.get(this.selected);
            this.player.Slots[1] = item;
        }
        if (this.input.slot3.clicked && len > 0) {
            final Item item = this.player.inventory.items.get(this.selected);
            this.player.Slots[2] = item;
        }
        if (this.input.slot4.clicked && len > 0) {
            final Item item = this.player.inventory.items.get(this.selected);
            this.player.Slots[3] = item;
        }
        if (this.input.slot5.clicked && len > 0) {
            final Item item = this.player.inventory.items.get(this.selected);
            this.player.Slots[4] = item;
        }
        if (this.input.slot6.clicked && len > 0) {
            final Item item = this.player.inventory.items.get(this.selected);
            this.player.Slots[5] = item;
        }
        if (this.input.slot7.clicked && len > 0) {
            final Item item = this.player.inventory.items.get(this.selected);
            this.player.Slots[6] = item;
        }
        if (this.input.slot8.clicked && len > 0) {
            final Item item = this.player.inventory.items.get(this.selected);
            this.player.Slots[7] = item;
        }
        if (this.input.slot9.clicked && len > 0) {
            final Item item = this.player.inventory.items.get(this.selected);
            this.player.Slots[8] = item;
        }
        if (this.input.attack.clicked && len > 0) {
            Item item = this.player.inventory.items.get(this.selected);
            if (item != null) {
                item = this.player.inventory.items.remove(this.selected);
            }
            this.player.activeItem = item;
            this.game.setMenu(null);
        }
    }
    
    @Override
    public void render(final Screen screen) {
        Font.renderFrame(screen, "inventory", 5, 5, 26, 16);
        this.renderItemList(screen, 5, 5, 16, 16, this.player.inventory.items, this.selected);
    }
}
