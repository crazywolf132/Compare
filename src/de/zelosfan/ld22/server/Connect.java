package de.zelosfan.ld22.server;

import com.mojang.ld22.Game;
import com.mojang.ld22.entity.Creeper;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.Furniture;
import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.MultiPlayer;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.Slime;
import com.mojang.ld22.entity.Zombie;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.item.ResourceItem;
import de.thejackimonster.ld22.story.dialog.NPC;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Connect extends Thread {

   private Socket client = null;
   private ObjectInputStream ois = null;
   private ObjectOutputStream oos = null;
   private Game game = null;
   private int updatetype;


   public Connect() {}

   public Connect(Socket clientSocket, Game game, int updatetype) {
      this.client = clientSocket;
      this.game = game;
      this.updatetype = updatetype;

      try {
         this.ois = new ObjectInputStream(this.client.getInputStream());
         this.oos = new ObjectOutputStream(this.client.getOutputStream());
      } catch (Exception var7) {
         try {
            this.client.close();
         } catch (Exception var6) {
            System.out.println(var6.getMessage());
         }

         return;
      }

      this.start();
   }

   public void run() {
      Object lvl = null;
      boolean updatestat = false;
      boolean playerid = false;

      try {
         int z;
         int ex;
         int ey;
         int z2;
         int ex2;
         int ey2;
         int etyp2;
         int elvl2;
         int z3;
         int ex3;
         int ey3;
         int ewalkdist3;
         int elvl3;
         int edir3;
         int z4;
         int ex4;
         int ey4;
         int var41;
         int var42;
         DataInputStream dis = new DataInputStream(this.ois);
         int var38 = dis.readInt();
         int var37 = dis.readInt();
         System.out.println("[CONNECTION]Starting Client to Server update with ID:" + var37);
         label283:
         switch(var37) {
         case 0:
         default:
            break;
         case 1:
            int dos = dis.readInt();
            byte[] len = new byte[dos];
            if(dos > 0) {
               dis.readFully(len);
            }

            this.game.level.tiles = len;
            break;
         case 2:
            this.game.level.remove(this.game.player2);
            this.game.player2.x = dis.readInt();
            this.game.player2.y = dis.readInt();
            this.game.player2.dir = dis.readInt();
            this.game.player2.walkDist = dis.readInt();
            this.game.player2.color = dis.readInt();
            z = dis.readInt();
            this.game.player2.username = "";

            for(ex = 0; ex < z; ++ex) {
               this.game.player2.username = this.game.player2.username + (char)dis.readInt();
            }

            ((MultiPlayer)this.game.player2).invuln = false;
            ex = dis.readInt();
            if(ex != -1) {
               this.game.player2.health = ex;
               this.game.server.multiplayerhpupdated = true;
            }

            this.game.level.add(this.game.player2);
            break;
         case 3:
            for(ey = 0; ey < this.game.level.entities.size(); ++ey) {
               if(this.game.level.entities.get(ey) instanceof Mob && !(this.game.level.entities.get(ey) instanceof Player) && !(this.game.level.entities.get(ey) instanceof MultiPlayer)) {
                  Mob etyp = (Mob)this.game.level.entities.get(ey);
                  etyp.remove();
               }
            }

            ey = dis.readInt();
            elvl2 = 0;

            while(true) {
               if(elvl2 >= ey) {
                  break label283;
               }

               var41 = dis.readInt();
               z2 = dis.readInt();
               ex2 = dis.readInt();
               ey2 = dis.readInt();
               etyp2 = dis.readInt();
               Mob var43 = (Mob)((Entity)Entity.entities.get(ex2)).getClass().newInstance();
               var43.x = var41;
               var43.y = z2;
               var43.health = etyp2;
               this.game.level.add(var43);
               var43.render(this.game.screen);
               ++elvl2;
            }
         case 4:
            for(elvl2 = 0; elvl2 < this.game.level.entities.size(); ++elvl2) {
               if(this.game.level.entities.get(elvl2) instanceof ItemEntity) {
                  ItemEntity ejumptime2 = (ItemEntity)this.game.level.entities.get(elvl2);
                  ejumptime2.remove();
               }
            }

            elvl2 = dis.readInt();
            ey3 = 0;

            while(true) {
               if(ey3 >= elvl2) {
                  break label283;
               }

               var42 = dis.readInt();
               z3 = dis.readInt();
               ex3 = dis.readInt();
               Item var45 = (Item)Item.items.get(ex3);
               if(var45 instanceof ResourceItem) {
                  ResourceItem var46 = new ResourceItem(((ResourceItem)var45).resource);
                  this.game.level.add(new ItemEntity(var46, var42, z3));
               }

               ++ey3;
            }
         case 5:
            this.updatetype = dis.readInt();
            System.out.println("[CONNECTION]Skipping " + this.updatetype + " because of CLIENTREQUEST(" + var37 + ") with ID:" + this.updatetype);
            break;
         case 6:
            ey3 = dis.readInt();

            for(ey4 = 0; ey4 < ey3; ++ey4) {
               ewalkdist3 = dis.readInt();
               elvl3 = dis.readInt();
               edir3 = dis.readInt();
               z4 = dis.readInt();
               ex4 = dis.readInt();
               this.game.player2.hurt(ewalkdist3, elvl3, edir3, z4, ex4);
            }
         }

         System.out.println("[CONNECTION]Initiating update from SERVER to CLIENT with ID:" + String.valueOf(this.updatetype));
         int ewalkdist4;
         int edir4;
         int z5;
         int z11;
         DataOutputStream var39;
         label246:
         switch(this.updatetype) {
         case 0:
         default:
            break;
         case 1:
            var39 = new DataOutputStream(this.oos);
            var39.writeInt(this.updatetype);
            int var40 = this.game.level.tiles.length;
            var39.writeInt(this.game.lightlvl);
            var39.writeInt(var40);
            if(var40 > 0) {
               var39.write(this.game.level.tiles, 0, var40);
            }
            break;
         case 2:
            var39 = new DataOutputStream(this.oos);
            var39.writeInt(this.updatetype);
            var39.writeInt(this.game.player.x);
            var39.writeInt(this.game.player.y);
            var39.writeInt(this.game.player.dir);
            var39.writeInt(this.game.player.walkDist);
            var39.writeInt(this.game.player.color);
            var39.writeInt(this.game.player.username.length());

            for(z = 0; z < this.game.player.username.length(); ++z) {
               var39.writeInt(this.game.player.username.charAt(z));
            }

            if(this.game.player.activeItem != null) {
               z = Item.getIDOfName(this.game.player.activeItem.getName());
               var39.writeInt(z);
            } else {
               var39.writeInt(-1);
            }
            break;
         case 3:
            var39 = new DataOutputStream(this.oos);
            var39.writeInt(this.updatetype);
            z = 0;

            for(ex = 0; ex < this.game.level.entities.size(); ++ex) {
               if(this.game.level.entities.get(ex) instanceof Furniture) {
                  ++z;
               }
            }

            var39.writeInt(z);
            z2 = 0;

            while(true) {
               if(z2 >= this.game.level.entities.size()) {
                  break label246;
               }

               if(this.game.level.entities.get(z2) instanceof Furniture) {
                  ex = ((Entity)this.game.level.entities.get(z2)).x;
                  ey = ((Entity)this.game.level.entities.get(z2)).y;
                  var41 = Entity.getIDOfName(((Entity)this.game.level.entities.get(z2)).name);
                  var39.writeInt(ex);
                  var39.writeInt(ey);
                  var39.writeInt(var41);
               }

               ++z2;
            }
         case 4:
            var39 = new DataOutputStream(this.oos);
            var39.writeInt(this.updatetype);
            z2 = 0;

            for(ex2 = 0; ex2 < this.game.level.entities.size(); ++ex2) {
               if(this.game.level.entities.get(ex2) instanceof Slime) {
                  ++z2;
               }
            }

            var39.writeInt(z2);
            z3 = 0;

            while(true) {
               if(z3 >= this.game.level.entities.size()) {
                  break label246;
               }

               if(this.game.level.entities.get(z3) instanceof Slime) {
                  Slime var44 = (Slime)this.game.level.entities.get(z3);
                  ex2 = ((Entity)this.game.level.entities.get(z3)).x;
                  ey2 = ((Entity)this.game.level.entities.get(z3)).y;
                  etyp2 = Entity.getIDOfName(((Entity)this.game.level.entities.get(z3)).name);
                  elvl2 = var44.lvl;
                  var42 = var44.jumpTime;
                  var39.writeInt(ex2);
                  var39.writeInt(ey2);
                  var39.writeInt(etyp2);
                  var39.writeInt(elvl2);
                  var39.writeInt(var42);
               }

               ++z3;
            }
         case 5:
            var39 = new DataOutputStream(this.oos);
            var39.writeInt(this.updatetype);
            z3 = 0;

            for(ex3 = 0; ex3 < this.game.level.entities.size(); ++ex3) {
               if(this.game.level.entities.get(ex3) instanceof Zombie) {
                  ++z3;
               }
            }

            var39.writeInt(z3);
            z4 = 0;

            while(true) {
               if(z4 >= this.game.level.entities.size()) {
                  break label246;
               }

               if(this.game.level.entities.get(z4) instanceof Zombie) {
                  Zombie var47 = (Zombie)this.game.level.entities.get(z4);
                  ex3 = ((Entity)this.game.level.entities.get(z4)).x;
                  ey3 = ((Entity)this.game.level.entities.get(z4)).y;
                  ewalkdist3 = var47.walkDist;
                  elvl3 = var47.lvl;
                  edir3 = var47.dir;
                  var39.writeInt(ex3);
                  var39.writeInt(ey3);
                  var39.writeInt(ewalkdist3);
                  var39.writeInt(elvl3);
                  var39.writeInt(edir3);
               }

               ++z4;
            }
         case 6:
            var39 = new DataOutputStream(this.oos);
            var39.writeInt(this.updatetype);
            z4 = 0;

            for(ex4 = 0; ex4 < this.game.level.entities.size(); ++ex4) {
               if(this.game.level.entities.get(ex4) instanceof Creeper) {
                  ++z4;
               }
            }

            var39.writeInt(z4);
            z5 = 0;

            while(true) {
               if(z5 >= this.game.level.entities.size()) {
                  break label246;
               }

               if(this.game.level.entities.get(z5) instanceof Creeper) {
                  Creeper var48 = (Creeper)this.game.level.entities.get(z5);
                  ex4 = ((Entity)this.game.level.entities.get(z5)).x;
                  ey4 = ((Entity)this.game.level.entities.get(z5)).y;
                  ewalkdist4 = var48.walkDist;
                  int elvl4 = var48.lvl;
                  edir4 = var48.dir;
                  var39.writeInt(ex4);
                  var39.writeInt(ey4);
                  var39.writeInt(ewalkdist4);
                  var39.writeInt(elvl4);
                  var39.writeInt(edir4);
               }

               ++z5;
            }
         case 7:
            var39 = new DataOutputStream(this.oos);
            var39.writeInt(this.updatetype);
            var39.writeInt(this.game.server.txpx);
            var39.writeInt(this.game.server.txpy);
            var39.writeInt(this.game.server.txpmsg);
            var39.writeInt(this.game.server.txpcol);
            break;
         case 8:
            var39 = new DataOutputStream(this.oos);
            var39.writeInt(this.updatetype);
            var39.writeInt(this.game.player2.health);
            break;
         case 9:
            var39 = new DataOutputStream(this.oos);
            var39.writeInt(this.updatetype);
            z5 = 0;

            int ex5;
            for(ex5 = 0; ex5 < this.game.level.entities.size(); ++ex5) {
               if(this.game.level.entities.get(ex5) instanceof ItemEntity) {
                  ++z5;
               }
            }

            var39.writeInt(z5);

            for(z11 = 0; z11 < this.game.level.entities.size(); ++z11) {
               if(this.game.level.entities.get(z11) instanceof ItemEntity) {
                  this.game.newdrophelper = false;
                  ItemEntity var49 = (ItemEntity)this.game.level.entities.get(z11);
                  ex5 = var49.x;
                  int ey5 = var49.y;
                  int etyp5 = Item.getIDOfName(var49.name);
                  var39.writeInt(ex5);
                  var39.writeInt(ey5);
                  var39.writeInt(etyp5);
               }
            }

            this.game.newdrophelper = false;
            break;
         case 10:
            var39 = new DataOutputStream(this.oos);
            var39.writeInt(this.updatetype);
            var39.writeInt(this.game.server.tilerefreshcount);

            for(z11 = 0; z11 < this.game.server.tilerefreshcount; ++z11) {
               var39.writeInt(this.game.server.xkoord[z11]);
               var39.writeInt(this.game.server.ykoord[z11]);
               var39.writeInt(this.game.level.getTile(this.game.server.xkoord[z11], this.game.server.ykoord[z11]).id);
            }

            --this.game.server.tilerefreshcount;
            break;
         case 11:
            var39 = new DataOutputStream(this.oos);
            var39.writeInt(this.updatetype);
            z11 = 0;

            int i;
            for(i = 0; i < this.game.level.entities.size(); ++i) {
               if(this.game.level.entities.get(i) instanceof NPC) {
                  ++z11;
               }
            }

            var39.writeInt(z11);

            for(i = 0; i < this.game.level.entities.size(); ++i) {
               if(this.game.level.entities.get(i) instanceof NPC) {
                  NPC zomb = (NPC)this.game.level.entities.get(i);
                  ex4 = ((Entity)this.game.level.entities.get(i)).x;
                  ey4 = ((Entity)this.game.level.entities.get(i)).y;
                  ewalkdist4 = zomb.walkDist;
                  edir4 = zomb.dir;
                  var39.writeInt(ex4);
                  var39.writeInt(ey4);
                  var39.writeInt(ewalkdist4);
                  var39.writeInt(edir4);
               }
            }
         }

         System.out.println("[CONNECTION]Finished SERVER to CLIENT update");
         System.out.println("[<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<]");
         this.oos.flush();
         this.ois.close();
         this.oos.close();
         this.client.close();
      } catch (Exception var36) {
         ;
      }

   }
}
