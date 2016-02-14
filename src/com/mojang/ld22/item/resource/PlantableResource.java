// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.item.resource;

import com.mojang.ld22.item.Item;
import com.mojang.ld22.item.ToolItem;
import com.mojang.ld22.item.ToolType;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.level.Level;
import java.util.Arrays;
import com.mojang.ld22.level.tile.Tile;
import java.util.List;

public class PlantableResource extends Resource
{
    private List<Tile> sourceTiles;
    private Tile targetTile;
    
    public PlantableResource(final String name, final int sprite, final Tile targetTile, final int color, final Tile... sourceTiles1) {
        this(name, sprite, targetTile, color, Arrays.asList(sourceTiles1));
    }
    
    public PlantableResource(final String name, final int sprite, final Tile targetTile, final int color, final List<Tile> sourceTiles) {
        super(name, sprite, color);
        this.sourceTiles = sourceTiles;
        this.targetTile = targetTile;
    }
    
    @Override
    public boolean interactOn(final Tile tile, final Level level, final int xt, final int yt, final Player player, final int attackDir) {
        if (this.sourceTiles.contains(tile)) {
            level.setTile(xt, yt, this.targetTile, 0);
            if (this.targetTile == Tile.water) {
                player.inventory.add(new ToolItem(ToolType.bucket, 2));
            }
            return true;
        }
        return false;
    }
}
