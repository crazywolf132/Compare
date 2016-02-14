// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.vehicles;

import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.entity.Entity;

public class Vehicle extends Entity
{
    protected int pushTime;
    private int pushDir;
    public int col;
    public int sprite;
    public final Tile[] canMoveOn;
    protected int mx;
    protected int my;
    public Mob mover;
    
    public Vehicle(final String name, final int sp, final int c, final Tile... tiles) {
        super(name);
        this.pushTime = 0;
        this.pushDir = -1;
        this.mover = null;
        this.sprite = sp;
        this.col = c;
        this.canMoveOn = tiles;
    }
    
    @Override
    public void tick() {
        if (this.mover != null && (this.mover.removed || this.mover.health <= 0)) {
            this.mover = null;
        }
        if (this.mover instanceof Player) {
            this.playerControl((Player)this.mover);
        }
        if (this.pushDir == 0) {
            this.my = 1;
        }
        if (this.pushDir == 1) {
            this.my = -1;
        }
        if (this.pushDir == 2) {
            this.mx = -1;
        }
        if (this.pushDir == 3) {
            this.mx = 1;
        }
        if (this.mx != 0 || this.my != 0) {
            if (!this.move(this.mx, this.my)) {
                this.mx = 0;
                this.my = 0;
            }
            if (this.x != 0 && this.y != 0) {
                this.drive(this.x / 16, this.y / 16);
            }
            else {
                this.drive(0, 0);
            }
        }
        this.pushDir = -1;
        if (this.pushTime > 0) {
            --this.pushTime;
        }
    }
    
    public void playerControl(final Player p) {
        if (p.game.input.up.clicked) {
            this.my = -1;
        }
        if (p.game.input.down.clicked) {
            this.my = 1;
        }
        if (p.game.input.left.clicked) {
            this.mx = -1;
        }
        if (p.game.input.right.clicked) {
            this.mx = 1;
        }
    }
    
    @Override
    public void render(final Screen screen) {
        screen.render(this.x - 8, this.y - 8 - 4, this.sprite * 2 + 256, this.col, 0);
        screen.render(this.x - 0, this.y - 8 - 4, this.sprite * 2 + 256 + 1, this.col, 0);
        screen.render(this.x - 8, this.y - 0 - 4, this.sprite * 2 + 256 + 32, this.col, 0);
        screen.render(this.x - 0, this.y - 0 - 4, this.sprite * 2 + 256 + 33, this.col, 0);
    }
    
    public void drive(final int xt, final int yt) {
        boolean flag = false;
        for (int i = 0; i < this.canMoveOn.length; ++i) {
            if (this.level.getTile(xt, yt).id == this.canMoveOn[i].id) {
                if (this.mx < 0 && this.mx > -2) {
                    --this.mx;
                }
                if (this.mx < 0 && this.mx < 2) {
                    ++this.mx;
                }
                if (this.mx > 0 && this.my > -2) {
                    --this.my;
                }
                if (this.mx > 0 && this.my < 2) {
                    ++this.my;
                }
                flag = true;
            }
        }
        if (!flag) {
            this.mx = 0;
            this.my = 0;
        }
    }
    
    @Override
    public boolean use(final Player player, final int attackDir) {
        if (player.toDrive == null && this.mover == null) {
            player.toDrive = this;
            this.mover = player;
        }
        else {
            player.toDrive = null;
            this.mover = null;
        }
        return true;
    }
    
    @Override
    public boolean blocks(final Entity e) {
        return true;
    }
    
    @Override
    protected void touchedBy(final Entity entity) {
        if (entity instanceof Mob && this.pushTime == 0 && ((Mob)entity).toDrive != this) {
            this.pushDir = ((Mob)entity).dir;
            this.pushTime = 9;
            if (!(entity instanceof Player) && this.mover == null) {
                ((Mob)entity).toDrive = this;
                this.mover = (Mob)entity;
            }
        }
    }
}
