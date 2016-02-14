// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.crafting;

import com.mojang.ld22.entity.Player;
import com.mojang.ld22.item.Item;

public class ItemRecipe extends Recipe
{
    public ItemRecipe(final Item resultTemplate) {
        super(resultTemplate);
    }
    
    @Override
    public void craft(final Player player) {
        try {
            player.inventory.add(0, (Item)this.resultTemplate.getClass().newInstance());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
