// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.entity;

import com.mojang.ld22.item.ToolType;
import com.mojang.ld22.item.ToolItem;
import com.mojang.ld22.gfx.Color;
import java.util.Random;

public class Torch extends Furniture
{
    public Inventory inventory;
    public boolean on;
    public boolean poweredup;
    Random random;
    
    public Torch() {
        super("Torch");
        this.inventory = new Inventory();
        this.on = false;
        this.poweredup = false;
        this.random = new Random();
        this.col = Color.get(-1, 110, 0, 111);
        this.sprite = 6;
    }
    
    @Override
    public boolean use(final Player player, final int attackDir) {
        if (this.on) {
            this.on = false;
            this.poweredup = false;
            this.col = Color.get(-1, 110, 0, 111);
        }
        else {
            this.on = true;
            this.col = Color.get(-1, 110, 550, 480);
            if (player.activeItem instanceof ToolItem) {
                final ToolItem tool = (ToolItem)player.activeItem;
                if (tool.type == ToolType.flintnsteel) {
                    this.poweredup = true;
                    this.col = Color.get(-1, 110, 550, 1);
                }
                else {
                    this.poweredup = false;
                }
            }
        }
        return true;
    }
    
    @Override
    public int getLightRadius() {
        if (!this.on) {
            return 0;
        }
        if (this.poweredup) {
            return 7;
        }
        return 5;
    }
}
