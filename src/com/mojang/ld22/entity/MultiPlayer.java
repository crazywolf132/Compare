/*
 * Decompiled with CFR 0_110.
 */
package com.mojang.ld22.entity;

import com.mojang.ld22.Game;
import com.mojang.ld22.InputHandler;
import com.mojang.ld22.entity.Arrow;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.Furniture;
import com.mojang.ld22.entity.Inventory;
import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.particle.TextParticle;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.FurnitureItem;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.item.ToolItem;
import com.mojang.ld22.item.ToolType;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.sound.Sound;
import de.thejackimonster.ld22.leveltree.Skill;
import de.thejackimonster.ld22.leveltree.SpecialSkill;
import de.thejackimonster.ld22.modloader.ModLoader;
import de.zelosfan.ld22.server.Server;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

public class MultiPlayer
extends Player {
    public int attackTime;
    public int attackDir;
    public Game game;
    public Inventory inventory = new Inventory();
    public Item[] Slots = new Item[9];
    public boolean energy = false;
    public boolean unlimitedhealth = false;
    public int maxHealth = 10;
    public int plevel = 0;
    public int maxplevel = 5;
    public final int expperlevel = 500;
    public int skillpoint = 0;
    public int exp = 0;
    public int sight = 2;
    public boolean oneshotresources = false;
    public boolean nodarkness = false;
    public boolean oneshotmobs = false;
    public boolean allowrun = true;
    public boolean isrunning = false;
    public boolean invuln = true;
    public Item attackItem;
    public Item activeItem;
    public int stamina;
    public int staminaRecharge;
    public int staminaRechargeDelay;
    public int score;
    public int maxStamina = 10;
    private int onStairDelay;
    public int invulnerableTime = 0;
    public int tickmessage = 0;

    public MultiPlayer(Game game) {
        super(game, null);
        this.game = game;
        this.name = "MPlayer";
        this.energy = true;
        this.unlimitedhealth = game.mpstate == 1;
        this.x = 24;
        this.y = 24;
        this.stamina = this.maxStamina;
        this.color = Color.get(-1, 555, 0, 0);
    }

    @Override
    public void tick() {
        Tile onTile;
        super.tick();
        if (this.invulnerableTime > 0) {
            --this.invulnerableTime;
        }
        if ((onTile = this.level.getTile(this.x >> 4, this.y >> 4)) == Tile.stairsDown || onTile == Tile.stairsUp) {
            if (this.onStairDelay == 0) {
                this.changeLevel(onTile == Tile.stairsUp ? 1 : -1);
                this.onStairDelay = 10;
                return;
            }
            this.onStairDelay = 10;
        } else if (this.onStairDelay > 0) {
            --this.onStairDelay;
        }
        if (this.stamina <= 0 && this.staminaRechargeDelay == 0 && this.staminaRecharge == 0) {
            this.staminaRechargeDelay = this.plevel >= 3 ? 30 : 40;
        }
        if (this.staminaRechargeDelay > 0) {
            --this.staminaRechargeDelay;
        }
        if (this.exp > 500) {
            Sound.lvlup.play();
            this.exp = 0;
            ++this.plevel;
        }
        if (this.staminaRechargeDelay == 0) {
            ++this.staminaRecharge;
            if (this.plevel >= 3 && this.staminaRecharge % 2 == 0) {
                ++this.staminaRecharge;
            }
            if (this.isSwimming() && !this.walkoverwater || this.isrunning) {
                this.staminaRecharge = 0;
            }
            while (this.staminaRecharge > this.maxStamina) {
                this.staminaRecharge -= this.maxStamina;
                if (this.stamina >= this.maxStamina) continue;
                ++this.stamina;
            }
        }
        boolean xa = false;
        boolean ya = false;
    }

    private boolean use() {
        int yo = -2;
        if (this.dir == 0 && this.use(this.x - 8, this.y + 4 + yo, this.x + 8, this.y + 12 + yo)) {
            return true;
        }
        if (this.dir == 1 && this.use(this.x - 8, this.y - 12 + yo, this.x + 8, this.y - 4 + yo)) {
            return true;
        }
        if (this.dir == 3 && this.use(this.x + 4, this.y - 8 + yo, this.x + 12, this.y + 8 + yo)) {
            return true;
        }
        if (this.dir == 2 && this.use(this.x - 12, this.y - 8 + yo, this.x - 4, this.y + 8 + yo)) {
            return true;
        }
        int xt = this.x >> 4;
        int yt = this.y + yo >> 4;
        int r = 12;
        if (this.attackDir == 0) {
            yt = this.y + r + yo >> 4;
        }
        if (this.attackDir == 1) {
            yt = this.y - r + yo >> 4;
        }
        if (this.attackDir == 2) {
            xt = this.x - r >> 4;
        }
        if (this.attackDir == 3) {
            xt = this.x + r >> 4;
        }
        if (xt >= 0 && yt >= 0 && xt < this.level.w && yt < this.level.h && this.level.getTile(xt, yt).use(this.level, xt, yt, this, this.attackDir)) {
            return true;
        }
        int i = 0;
        while (i < Skill.skills.size()) {
            if (Skill.skills.get(i).isCompleteDone() && Skill.skills.get(i) instanceof SpecialSkill) {
                ((SpecialSkill)Skill.skills.get(i)).playerUse(this);
            }
            ++i;
        }
        return false;
    }

    @Override
    public void attack() {
        int r;
        int xt;
        int range;
        int yt;
        this.walkDist += 8;
        this.attackDir = this.dir;
        this.attackItem = this.activeItem;
        boolean done = false;
        if (this.attackItem instanceof ToolItem && this.stamina - 3 >= 0) {
            ToolItem tool = (ToolItem)this.attackItem;
            if (tool.type == ToolType.bow) {
                this.stamina -= 3;
                Sound.arrowshoot.play();
                switch (this.attackDir) {
                    case 0: {
                        this.level.add(new Arrow(this, 0, 1, tool.level, done));
                        break;
                    }
                    case 1: {
                        this.level.add(new Arrow(this, 0, -1, tool.level, done));
                        break;
                    }
                    case 2: {
                        this.level.add(new Arrow(this, -1, 0, tool.level, done));
                        break;
                    }
                    case 3: {
                        this.level.add(new Arrow(this, 1, 0, tool.level, done));
                        break;
                    }
                }
                done = true;
            }
        }
        if (this.activeItem != null) {
            this.attackTime = 10;
            int yo = -2;
            range = 12;
            if (this.dir == 0 && this.interact(this.x - 8, this.y + 4 + yo, this.x + 8, this.y + range + yo)) {
                done = true;
            }
            if (this.dir == 1 && this.interact(this.x - 8, this.y - range + yo, this.x + 8, this.y - 4 + yo)) {
                done = true;
            }
            if (this.dir == 3 && this.interact(this.x + 4, this.y - 8 + yo, this.x + range, this.y + 8 + yo)) {
                done = true;
            }
            if (this.dir == 2 && this.interact(this.x - range, this.y - 8 + yo, this.x - 4, this.y + 8 + yo)) {
                done = true;
            }
            if (done) {
                return;
            }
            xt = this.x >> 4;
            yt = this.y + yo >> 4;
            r = 12;
            if (this.attackDir == 0) {
                yt = this.y + r + yo >> 4;
            }
            if (this.attackDir == 1) {
                yt = this.y - r + yo >> 4;
            }
            if (this.attackDir == 2) {
                xt = this.x - r >> 4;
            }
            if (this.attackDir == 3) {
                xt = this.x + r >> 4;
            }
            if (xt >= 0 && yt >= 0 && xt < this.level.w && yt < this.level.h) {
                if (this.activeItem.interactOn(this.level.getTile(xt, yt), this.level, xt, yt, this, this.attackDir)) {
                    done = true;
                } else if (this.level.getTile(xt, yt).interact(this.level, xt, yt, this, this.activeItem, this.attackDir)) {
                    done = true;
                }
                if (this.activeItem != null && this.activeItem.isDepleted()) {
                    int i = 0;
                    while (i < 9) {
                        if (this.Slots[i] != null && this.activeItem.matches(this.Slots[i])) {
                            this.Slots[i] = null;
                        }
                        ++i;
                    }
                    this.inventory.items.remove(this.activeItem);
                    this.activeItem = null;
                }
            }
        }
        if (done) {
            return;
        }
        if (this.activeItem == null || this.activeItem.canAttack()) {
            this.attackTime = 5;
            int yo = -2;
            range = 20;
            if (this.dir == 0) {
                this.hurt(this.x - 8, this.y + 4 + yo, this.x + 8, this.y + range + yo);
            }
            if (this.dir == 1) {
                this.hurt(this.x - 8, this.y - range + yo, this.x + 8, this.y - 4 + yo);
            }
            if (this.dir == 3) {
                this.hurt(this.x + 4, this.y - 8 + yo, this.x + range, this.y + 8 + yo);
            }
            if (this.dir == 2) {
                this.hurt(this.x - range, this.y - 8 + yo, this.x - 4, this.y + 8 + yo);
            }
            xt = this.x >> 4;
            yt = this.y + yo >> 4;
            r = 12;
            if (this.attackDir == 0) {
                yt = this.y + r + yo >> 4;
            }
            if (this.attackDir == 1) {
                yt = this.y - r + yo >> 4;
            }
            if (this.attackDir == 2) {
                xt = this.x - r >> 4;
            }
            if (this.attackDir == 3) {
                xt = this.x + r >> 4;
            }
            if (xt >= 0 && yt >= 0 && xt < this.level.w && yt < this.level.h) {
                if (this.oneshotresources) {
                    this.level.getTile(xt, yt).hurt(this.level, xt, yt, this, 9999, this.attackDir);
                } else {
                    this.level.getTile(xt, yt).hurt(this.level, xt, yt, this, this.random.nextInt(3) + 1, this.attackDir);
                }
            }
        }
    }

    private boolean use(int x0, int y0, int x1, int y1) {
        List<Entity> entities = this.level.getEntities(x0, y0, x1, y1);
        int i = 0;
        while (i < entities.size()) {
            Entity e = entities.get(i);
            if (e != this && e.use(this, this.attackDir)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    private boolean interact(int x0, int y0, int x1, int y1) {
        List<Entity> entities = this.level.getEntities(x0, y0, x1, y1);
        int i = 0;
        while (i < entities.size()) {
            Entity e = entities.get(i);
            if (e != this && e.interact(this, this.activeItem, this.attackDir)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    @Override
    public void hurt(int x0, int y0, int x1, int y1) {
        List<Entity> entities = this.level.getEntities(x0, y0, x1, y1);
        int i = 0;
        while (i < entities.size()) {
            Entity e = entities.get(i);
            if (e != this) {
                e.hurt(this, this.getAttackDamage(e), this.attackDir);
            }
            ++i;
        }
    }

    @Override
    public void hurt(int x0, int y0, int x1, int y1, int dmg) {
        System.out.println("Hurt mobs");
        List<Entity> entities = this.game.level.getEntities(x0, y0, x1, y1);
        int i = 0;
        while (i < entities.size()) {
            System.out.println("Hurt: " + i);
            Entity e = entities.get(i);
            if (e != this) {
                e.hurt(this, dmg, this.attackDir);
            }
            ++i;
        }
    }

    @Override
    public int getAttackDamage(Entity e) {
        int dmg = this.plevel == 0 ? this.random.nextInt(3) + 1 : this.random.nextInt(4) + 2;
        if (this.attackItem != null) {
            dmg += this.attackItem.getAttackDamageBonus(e);
        }
        if (this.oneshotmobs) {
            dmg = 9999;
        }
        int i = 0;
        while (i < Skill.skills.size()) {
            if (Skill.skills.get(i).isCompleteDone() && Skill.skills.get(i) instanceof SpecialSkill) {
                dmg += ((SpecialSkill)Skill.skills.get(i)).getAttack();
            }
            ++i;
        }
        return dmg;
    }

    @Override
    public void render(Screen screen) {
        int xt = 0;
        int yt = 14;
        int flip1 = this.walkDist >> 3 & 1;
        int flip2 = this.walkDist >> 3 & 1;
        if (this.dir == 1) {
            xt += 2;
        }
        if (this.dir > 1) {
            flip1 = 0;
            flip2 = this.walkDist >> 4 & 1;
            if (this.dir == 2) {
                flip1 = 1;
            }
            xt += 4 + (this.walkDist >> 3 & 1) * 2;
        }
        int xo = this.x - 8;
        int yo = this.y - 11;
        if (this.isSwimming() && !this.walkoverwater) {
            yo += 4;
            int waterColor = Color.get(-1, -1, 115, 335);
            if (this.tickTime / 8 % 2 == 0) {
                waterColor = Color.get(-1, 335, 5, 115);
            }
            screen.render(xo + 0, yo + 3, 421, waterColor, 0);
            screen.render(xo + 8, yo + 3, 421, waterColor, 1);
        }
        if (this.isrunning && !this.isSwimming()) {
            int runColor = Color.get(-1, 355, -1, -1);
            if (this.tickTime / 8 % 2 == 0) {
                runColor = Color.get(-1, 335, -1, -1);
            }
            screen.render(xo + 0, yo + 8, 421, runColor, 0);
            screen.render(xo + 8, yo + 8, 421, runColor, 1);
        }
        if (this.attackTime > 0 && this.attackDir == 1) {
            screen.render(xo + 0, yo - 4, 422, Color.get(-1, 555, 555, 555), 0);
            screen.render(xo + 8, yo - 4, 422, Color.get(-1, 555, 555, 555), 1);
            if (this.attackItem != null) {
                this.attackItem.renderIcon(screen, xo + 4, yo - 4);
            }
        }
        int col = this.color;
        if (this.hurtTime > 0) {
            col = Color.get(-1, 555, 555, 555);
        }
        if (this.activeItem instanceof FurnitureItem) {
            yt += 2;
        }
        Font.draw(this.username, screen, xo + 8 - this.username.length() / 2 * 8, yo - 8, Color.get(-1, -1, -1, 555));
        screen.render(xo + 8 * flip1, yo + 0, xt + yt * 32, col, flip1);
        screen.render(xo + 8 - 8 * flip1, yo + 0, xt + 1 + yt * 32, col, flip1);
        if (!this.isSwimming() || this.walkoverwater) {
            screen.render(xo + 8 * flip2, yo + 8, xt + (yt + 1) * 32, col, flip2);
            screen.render(xo + 8 - 8 * flip2, yo + 8, xt + 1 + (yt + 1) * 32, col, flip2);
        }
        if (this.attackTime > 0 && this.attackDir == 2) {
            screen.render(xo - 4, yo, 423, Color.get(-1, 555, 555, 555), 1);
            screen.render(xo - 4, yo + 8, 423, Color.get(-1, 555, 555, 555), 3);
            if (this.attackItem != null) {
                this.attackItem.renderIcon(screen, xo - 4, yo + 4);
            }
        }
        if (this.attackTime > 0 && this.attackDir == 3) {
            screen.render(xo + 8 + 4, yo, 423, Color.get(-1, 555, 555, 555), 0);
            screen.render(xo + 8 + 4, yo + 8, 423, Color.get(-1, 555, 555, 555), 2);
            if (this.attackItem != null) {
                this.attackItem.renderIcon(screen, xo + 8 + 4, yo + 4);
            }
        }
        if (this.attackTime > 0 && this.attackDir == 0) {
            screen.render(xo + 0, yo + 8 + 4, 422, Color.get(-1, 555, 555, 555), 2);
            screen.render(xo + 8, yo + 8 + 4, 422, Color.get(-1, 555, 555, 555), 3);
            if (this.attackItem != null) {
                this.attackItem.renderIcon(screen, xo + 4, yo + 8 + 4);
            }
        }
        if (this.activeItem instanceof FurnitureItem) {
            Furniture furniture = ((FurnitureItem)this.activeItem).furniture;
            furniture.x = this.x;
            furniture.y = yo;
            furniture.render(screen);
        }
    }

    @Override
    public void touchItem(ItemEntity itemEntity) {
        itemEntity.take(this);
        this.inventory.add(itemEntity.item);
        ModLoader.PickupItem(this, itemEntity.item);
    }

    @Override
    public boolean canSwim() {
        return true;
    }

    @Override
    public boolean findStartPos(Level level) {
        int x;
        int y;
        while (level.getTile(x = this.random.nextInt(level.w), y = this.random.nextInt(level.h)) != Tile.grass) {
        }
        this.x = x * 16 + 8;
        this.y = y * 16 + 8;
        return true;
    }

    @Override
    public boolean payStamina(int cost) {
        if (cost > this.stamina) {
            return false;
        }
        if (!this.energy) {
            this.stamina -= cost;
        }
        return true;
    }

    @Override
    public void changeLevel(int dir) {
        this.game.scheduleLevelChange(dir);
    }

    @Override
    public int getLightRadius() {
        int rr;
        int r = this.sight;
        if (this.activeItem != null && this.activeItem instanceof FurnitureItem && (rr = ((FurnitureItem)this.activeItem).furniture.getLightRadius()) > r) {
            r = rr;
        }
        return r;
    }

    @Override
    protected void die() {
        super.die();
    }

    @Override
    protected void touchedBy(Entity entity) {
        if (!(entity instanceof MultiPlayer)) {
            entity.touchedBy(this);
        }
    }

    @Override
    protected void doHurt(int damage, int attackDir) {
        if (this.hurtTime > 0 || this.invulnerableTime > 0) {
            return;
        }
        if (this.game.mpstate == 2 && !this.invuln) {
            Sound.playerHurt.play();
            this.level.add(new TextParticle("" + damage, this.x, this.y, Color.get(-1, 504, 504, 504)));
            if (this.level.game.mpstate == 2) {
                this.level.game.server.sendTextParticle(this.x, this.y, Color.get(-1, 500, 500, 500), damage);
            }
            if (!this.unlimitedhealth) {
                this.health -= damage;
            }
            if (!this.nonockback) {
                if (attackDir == 0) {
                    this.yKnockback = 6;
                }
                if (attackDir == 1) {
                    this.yKnockback = -6;
                }
                if (attackDir == 2) {
                    this.xKnockback = -6;
                }
                if (attackDir == 3) {
                    this.xKnockback = 6;
                }
            }
            this.hurtTime = 10;
            this.invulnerableTime = 30;
            this.game.server.multiplayerhpupdated = true;
        }
    }

    @Override
    public void gameWon() {
    }
}

