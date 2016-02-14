// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.matrix;

import com.mojang.ld22.entity.Furniture;
import com.mojang.ld22.entity.Mob;
import de.thejackimonster.ld22.story.dialog.NPC;
import com.mojang.ld22.entity.Player;
import java.util.Comparator;
import java.util.Collections;
import com.mojang.ld22.screen.TitleMenu;
import java.util.List;
import java.util.Collection;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.Game;
import com.mojang.ld22.level.Level;

public class Matrix extends Level
{
    public boolean matrixMode;
    
    public Matrix(final boolean flag, final Game game, final int w, final int h, final int level, final Level parentLevel, final int mapsize, final int waterc, final int grassc, final int rockc, final int treec) {
        super(game, w, h, level, parentLevel, mapsize, waterc, grassc, rockc, treec);
        this.matrixMode = flag;
    }
    
    public void setTo(final boolean flag) {
        this.matrixMode = flag;
    }
    
    @Override
    public void renderBackground(final Screen screen, final int xScroll, final int yScroll) {
        if (!this.matrixMode) {
            super.renderBackground(screen, xScroll, yScroll);
            return;
        }
        screen.clear(0);
        final int xo = xScroll >> 4;
        final int yo = yScroll >> 4;
        final int w = screen.w + 15 >> 4;
        final int h = screen.h + 15 >> 4;
        screen.setOffset(xScroll, yScroll);
        for (int y = yo; y <= h + yo; ++y) {
            for (int x = xo; x <= w + xo; ++x) {
                final int[] r = new int[4];
                if (this.getTile(x, y).mayPass(this, x, y, this.player)) {
                    r[0] = 0;
                }
                else {
                    r[0] = 1;
                }
                r[1] = this.random.nextInt(6);
                if (this.getTile(x, y).id > 0) {
                    r[2] = 1;
                    final int id = this.getTile(x, y).id;
                    if (id == Tile.water.id) {
                        r[2] = 2;
                    }
                    if (id == Tile.lava.id) {
                        r[2] = 3;
                    }
                }
                else {
                    r[2] = 0;
                }
                if ((r[2] == 1 || r[2] == 0) && this.fireTicks[x][y] > 0) {
                    r[2] = 4;
                }
                r[3] = this.random.nextInt(6);
                for (int i = 0; i < 4; ++i) {
                    if (r[i] > 5) {
                        r[i] = 5;
                    }
                }
                Font.draw(String.valueOf(r[0]), screen, x * 16, y * 16, Color.get(-1, -1, -1, r[0] * 10));
                Font.draw(String.valueOf(r[1]), screen, x * 16 + 8, y * 16, Color.get(-1, -1, -1, r[1] * 10));
                Font.draw(String.valueOf(r[2]), screen, x * 16, y * 16 + 8, Color.get(-1, -1, -1, r[2] * 10));
                Font.draw(String.valueOf(r[3]), screen, x * 16 + 8, y * 16 + 8, Color.get(-1, -1, -1, r[3] * 10));
            }
        }
        screen.setOffset(0, 0);
    }
    
    @Override
    public void renderSprites(final Screen screen, final int xScroll, final int yScroll) {
        if (!this.matrixMode) {
            super.renderSprites(screen, xScroll, yScroll);
            return;
        }
        final int xo = xScroll >> 4;
        final int yo = yScroll >> 4;
        final int w = screen.w + 15 >> 4;
        final int h = screen.h + 15 >> 4;
        screen.setOffset(xScroll, yScroll);
        for (int y = yo; y <= h + yo; ++y) {
            for (int x = xo; x <= w + xo; ++x) {
                if (x >= 0 && y >= 0 && x < this.w) {
                    if (y < this.h) {
                        this.rowSprites.addAll(this.entitiesInTiles[x + y * this.w]);
                    }
                }
            }
            if (this.rowSprites.size() > 0) {
                this.sortAndRender(screen, this.rowSprites);
            }
            this.rowSprites.clear();
        }
        screen.setOffset(0, 0);
    }
    
    @Override
    public void sortAndRender(final Screen screen, final List<Entity> list) {
        if (!this.matrixMode) {
            super.sortAndRender(screen, list);
            return;
        }
        if (!(this.game.menu instanceof TitleMenu)) {
            Collections.sort(list, this.spriteSorter);
            for (int i = 0; i < list.size(); ++i) {
                final int[] r = new int[4];
                if (list.get(i) instanceof Player) {
                    r[1] = (r[0] = 4);
                }
                else if (list.get(i) instanceof NPC) {
                    r[1] = (r[0] = 3);
                }
                else if (list.get(i) instanceof Mob) {
                    r[r[0] = 1] = 1;
                }
                else if (list.get(i) instanceof Furniture) {
                    r[1] = (r[0] = 2);
                }
                else {
                    r[1] = (r[0] = 0);
                }
                if (list.get(i).x > 0) {
                    r[2] = list.get(i).x % 10;
                }
                else {
                    r[2] = 0;
                }
                if (list.get(i).y > 0) {
                    r[3] = list.get(i).y % 10;
                }
                else {
                    r[3] = 0;
                }
                for (int j = 0; j < 4; ++j) {
                    if (r[j] > 5) {
                        r[j] = 5;
                    }
                }
                Font.draw(String.valueOf(r[0]), screen, list.get(i).x - 8, list.get(i).y - 8, Color.get(-1, -1, -1, r[0] * 10));
                Font.draw(String.valueOf(r[1]), screen, list.get(i).x, list.get(i).y - 8, Color.get(-1, -1, -1, r[1] * 10));
                Font.draw(String.valueOf(r[2]), screen, list.get(i).x - 8, list.get(i).y, Color.get(-1, -1, -1, r[2] * 10));
                Font.draw(String.valueOf(r[3]), screen, list.get(i).x, list.get(i).y, Color.get(-1, -1, -1, r[3] * 10));
            }
        }
    }
}
