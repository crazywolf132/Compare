// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.vehicles;

import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.Item;

public class VehicleItem extends Item
{
    public Vehicle vehicle;
    public boolean placed;
    
    public VehicleItem(final Vehicle vehc) {
        super(vehc.name);
        this.placed = false;
        this.vehicle = vehc;
    }
    
    @Override
    public int getColor() {
        return this.vehicle.col;
    }
    
    @Override
    public int getSprite() {
        return this.vehicle.sprite + 320;
    }
    
    @Override
    public void renderIcon(final Screen screen, final int x, final int y) {
        screen.render(x, y, this.getSprite(), this.getColor(), 0);
    }
    
    @Override
    public void renderInventory(final Screen screen, final int x, final int y) {
        screen.render(x, y, this.getSprite(), this.getColor(), 0);
        Font.draw(this.vehicle.name, screen, x + 8, y, Color.get(-1, 555, 555, 555));
    }
    
    @Override
    public void onTake(final ItemEntity itemEntity) {
    }
    
    @Override
    public boolean canAttack() {
        return false;
    }
    
    @Override
    public boolean interactOn(final Tile tile, final Level level, final int xt, final int yt, final Player player, final int attackDir) {
        if (tile.mayPass(level, xt, yt, this.vehicle)) {
            this.vehicle.x = xt * 16 + 8;
            this.vehicle.y = yt * 16 + 8;
            level.add(this.vehicle);
            return this.placed = true;
        }
        return false;
    }
    
    @Override
    public boolean isDepleted() {
        return this.placed;
    }
    
    @Override
    public String getName() {
        return this.vehicle.name;
    }
}
