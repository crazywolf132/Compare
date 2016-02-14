package de.zelosfan.ld22.server;

import java.io.*;
import java.net.*;
import com.mojang.ld22.Game;
import com.mojang.ld22.item.FurnitureItem;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.screen.TitleMenu;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.Furniture;
import com.mojang.ld22.entity.ItemEntity;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.entity.MultiPlayer;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Slime;
import com.mojang.ld22.entity.Zombie;
import com.mojang.ld22.entity.Creeper;
import com.mojang.ld22.entity.particle.TextParticle;



public class Client implements Runnable{
   private Game game;

   
    public Client (Game game,boolean setpos) {
    System.out.println("[CLIENT]Starting client");
	this.game = game;
	if (!game.setpos) {
	 game.setpos = setpos;
	}
	
	this.addClientRequest(2); //request map
	this.addClientRequest(1);
	this.run();
   }

   public void addClientRequest(int reqid) {
	   game.clientRequests[game.requestcount] = reqid;
	   game.requestcount++;
   }
    
    
   public void run() {
	   
	  try { 
      game.packagecps++;
      ObjectOutputStream oos = null;
      ObjectInputStream ois = null;
      Socket socket = null;
      DataOutputStream dos;
      int updatestat = 0;
      try {
        // open a socket connection
        System.out.println("[CONNECTION]Open socket to "+game.CLIENTCONNECTHOST+" on port: "+game.SERVERSOCKET);
        socket = new Socket(game.CLIENTCONNECTHOST, game.SERVERSOCKET);

        // open I/O streams for objects
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        // read an object from the server
        
        
        
         if (game.requestcount > 0) {
          updatestat = 5;
         } else if (game.mapupdated) {
          game.mapupdated = false;
          updatestat = 1;
          
         } else if (game.positionupdated || game.multiplayerrespawned) {
          updatestat = 2;	
          game.positionupdated = false;
         } else if (game.tookdrops){
          updatestat = 4;
          game.tookdrops = false;
         } else if (game.mobdied) {
          updatestat = 3;
          game.mobdied = false;
   //      } else if (game.atkcount > 0){
   //       updatestat = 6;
   //       game.atkrefresh = false;
         } else {
          updatestat = 2;
          game.positionupdated = true;
         }
       System.out.println("[CONNECTION]Starting CLIENT to SERVER update with ID:"+updatestat);
	     dos = new DataOutputStream(oos);
	     dos.writeInt(game.playerid);	 
  	     dos.writeInt(updatestat);    
  	     
       switch (updatestat) { 
       case 0:
	   
    	   break;
	    case 1:

  	     int len = game.level.tiles.length; 
      	 dos.writeInt(len);
      	 if (len > 0) {
      	        dos.write(game.level.tiles, 0, len);
      	 }    	 
      	 break;
       
       
	    case 2:

  	     dos.writeInt(game.player.x);
  	     dos.writeInt(game.player.y);  
  	     dos.writeInt(game.player.dir);
  	     dos.writeInt(game.player.walkDist); 
	     dos.writeInt(game.player.color);
	     dos.writeInt(game.player.username.length());
	     for (int i = 0;i<game.player.username.length();i++) {
	    	 dos.writeInt((int)game.player.username.charAt(i));
	    	 
	     }
  	     
  	     
  	     
  	     if (game.multiplayerrespawned) {
  	    	 dos.writeInt(game.player.maxHealth);
  	    	 game.multiplayerrespawned = false;
  	     } else {
  	    	 dos.writeInt(-1);
  	     }
  	     break;
  	     
  	     
	    case 3:

         	 
         	int z2 = 0;
 		    for (int i=0;i<game.level.entities.size();i++) {
 	   		   if (game.level.entities.get(i) instanceof Mob && (!(game.level.entities.get(i) instanceof Player))&& (!(game.level.entities.get(i) instanceof MultiPlayer))) {
 	   		     z2++;
 	   		   }
 	   		 }
 		     dos.writeInt(z2); 
         	 
      		int ex2;
      		int ey2;
      		int etyp2;
      		int elvl2;
      		//int ejumptime2;
 		     int mobhp;
 		     for (int i=0;i<game.level.entities.size();i++) {
 		 	   if (game.level.entities.get(i) instanceof Mob && (!(game.level.entities.get(i) instanceof Player))&& (!(game.level.entities.get(i) instanceof MultiPlayer))) {
 		 		 Mob mon = (Mob) game.level.entities.get(i);
 		 		 mobhp = mon.health;
 		    	 ex2 = game.level.entities.get(i).x;
 		    	 ey2 = game.level.entities.get(i).y;
 		    	 etyp2 = Entity.getIDOfName(game.level.entities.get(i).name);
 		    	 elvl2 = 1;
 		    //	 ejumptime2 = slime.jumpTime;
     		     dos.writeInt(ex2);
     		     dos.writeInt(ey2);
     		     dos.writeInt(etyp2);
     		     dos.writeInt(elvl2);
     		     dos.writeInt(mobhp);
     		//     dos.writeInt(ejumptime2);
 		 		 
 		       }
     		 }
     		break;
     		
	    case 4:

          	 
          	int z5 = 0;
  		    for (int i=0;i<game.level.entities.size();i++) {
  	   		   if (game.level.entities.get(i) instanceof ItemEntity) {
  	   		     z5++;
  	   		   }
  	   		 }
  		     dos.writeInt(z5); 
          	 
       		int ex5;
       		int ey5;
       	    int etyp5;
  		     for (int i=0;i<game.level.entities.size();i++) {
  		       if (game.level.entities.get(i) instanceof ItemEntity) {
  		    	 ItemEntity item = (ItemEntity) game.level.entities.get(i);
  		    	 ex5 = item.x;
  		    	 ey5 = item.y;
  		    	 etyp5 = Item.getIDOfName(item.name);

  		    	
      		     dos.writeInt(ex5);
      		     dos.writeInt(ey5);
      		     dos.writeInt(etyp5);

  		       }
      		 }
  		     break;
  		     
	    case 5:

       	 dos.writeInt(game.clientRequests[game.requestcount-1]);
       	 game.requestcount--;
       	 System.out.println("[CONNECTION]Send CLIENTREQUEST for update with ID:"+game.clientRequests[game.requestcount-1]);
       	 break;
		   
	    case 6:
 	
	      // 	 dos.writeInt(game.atkcount);
	       	dos.writeInt(1);
	      // 	 for (int i=0;i<game.atkcount;i++) {
	       	int i = game.atkcount - 1;
	       		dos.writeInt(game.atkxcoord[i]); 
	       		dos.writeInt(game.atkx2coord[i]); 
	       		dos.writeInt(game.atkycoord[i]); 
	       		dos.writeInt(game.atky2coord[i]); 
	       		dos.writeInt(game.atkdmg[i]); 
	       	    game.atkxcoord[i] = 0;
	       	    game.atkx2coord[i] = 0;
	       	    game.atkycoord[i] = 0;
	            game.atky2coord[i] = 0;
	            game.atkdmg[i] = 0;
	      // 	 }
	       	 game.atkcount -= 1;
	         break;
       } 
       
       oos.flush();
        
        DataInputStream dis = new DataInputStream(ois);
        int statusupdated = dis.readInt();
        System.out.println("[CONNECTION]Initiating update from SERVER to CLIENT with ID:"+String.valueOf(statusupdated));

        switch(statusupdated) {
        	case 1:
         //client       System.out.println("Downloading level");
                game.lightlvl = dis.readInt();
                int len = dis.readInt();
                byte[] data = new byte[len];
                if (len > 0) {
                    dis.readFully(data);
                }

       //client       System.out.println("The Level is downloaded");
                game.level.tiles = data;
                break;
                
        	case 2:
        //client     System.out.println("Going to set new pos");
              
      
                	game.level.remove(game.player2);
                    game.player2.x = dis.readInt();
                    game.player2.y = dis.readInt();
                    
                    if (game.setpos) {
                    	game.player.x = game.player2.x;
                    	game.player.y = game.player2.y;
                    	game.positionupdated = true;
                    	game.setpos = false;
                    	game.respawnx = game.player.x;
                    	game.respawny = game.player.y;
                    }
        
                    game.player2.dir = dis.readInt();
                    game.player2.walkDist = dis.readInt();
                    game.player2.color = dis.readInt();
                    int len2 = dis.readInt();
                    game.player2.username = "";
                    for (int i=0;i<len2;i++) {
                    	game.player2.username = game.player2.username+((char)dis.readInt());

                    }
          //          game.player2.username = "";
         //           for (int i=0;i<dis.readInt();i++) {
         //           	game.player2.username.concat(String.valueOf(dis.readChar()));	
         //           }

                   int actitem = dis.readInt();
                  if (actitem > -1) {

               	
                   
                     
             //       Entity entity = Entity.entities.get(actitem).getClass().newInstance(); 				      
 
                    Item item =  Item.items.get(actitem); 
          		
          			FurnitureItem resItem = new FurnitureItem(((FurnitureItem) item).furniture);
          			ItemEntity itx = new ItemEntity(resItem, 0, 0);
              		//game.level.add(itx);      
          			game.player2.activeItem = itx.item;
          			game.player2.render(game.screen);
                    
                     
             /*        FurnitureItem fitem = new FurnitureItem(new Workbench());
                     game.player2.activeItem = fitem;
                     game.player2.attackTime = 1;
                     game.player2.attackDir = game.player2.dir;
                     game.player2.render(game.screen);
             		 if (game.player2.activeItem instanceof FurnitureItem) {
            			Furniture furniture = ((FurnitureItem) game.player2.activeItem).furniture;
            			furniture.x = game.player2.x;
            			furniture.y = yo;
            			furniture.render(game.screen);

            		 }
                    }
                    */
                  }
                    game.level.add(game.player2);
                  
                  //client System.out.println("Set new position");
                break;
             
        	case 3:
        		
   		        for (int i=0;i<game.level.entities.size();i++) {
   		           if (game.level.entities.get(i) instanceof Furniture) {
   		             Furniture furniture = (Furniture) game.level.entities.get(i);
   		             furniture.remove();
   		           }
   		        }
   		   //client	System.out.println("Downloading new entities:furniture");
        		int togo = dis.readInt();
        		int ex;
        		int ey;
        		int etyp;
        		for (int i=0;i<togo;i++) {
                    ex = dis.readInt();
                    ey = dis.readInt();
                    etyp = dis.readInt();
                    Entity entity = Entity.entities.get(etyp).getClass().newInstance(); 				      
				    entity.x = ex;
					entity.y = ey;
					game.level.add(entity);                    
                    
                    
        		}
        		
        		//client	System.out.println("finished new entities:furniture");
        		break;
        		
        	case 4:
        		
   		        for (int i=0;i<game.level.entities.size();i++) {
   		           if (game.level.entities.get(i) instanceof Slime) {
   		             Slime slime = (Slime) game.level.entities.get(i);
   	   		     //    game.level.removeEntity(slime.x, slime.y, slime);
   		             slime.remove();
   		           }
   		        }
   		/*        game.level.entitiesInTiles = new ArrayList[game.level.w * game.level.h];
   				for (int i = 0; i < game.level.w * game.level.h; i++) {
   					game.level.entitiesInTiles[i] = new ArrayList<Entity>();
   				}
         */
   		   //client		System.out.println("Downloading new entities:slime");
        		int togo2 = dis.readInt();
        		int ex2;
        		int ey2;
        		int etyp2;
        		int elvl2;
        		int ejumptime;

        		for (int i=0;i<togo2;i++) {
                    ex2 = dis.readInt();
                    ey2 = dis.readInt();
                    etyp2 = dis.readInt();
                    elvl2 = dis.readInt();
                    ejumptime = dis.readInt();
                  
                    Slime slime = new Slime(elvl2);
 				      
				    slime.x = ex2;
					slime.y = ey2;
					slime.jumpTime = ejumptime;
			
					game.level.add(slime);  
				//	game.level.insertEntity(ex2/16, ey2/16, slime);
                    slime.render(game.screen);
                    
        		}
        		
        		//client	System.out.println("finished new entities:slime");
        		break;
 
        	case 5:
        		
   		        for (int i=0;i<game.level.entities.size();i++) {
   		           if (game.level.entities.get(i) instanceof Zombie) {
   		             Zombie zomb = (Zombie) game.level.entities.get(i);
   		             zomb.remove();
   		           }
   		        }
   		   //client	System.out.println("Downloading new entities:zombie");
        		int togo3 = dis.readInt();
        		int ex3;
        		int ey3;
                int ewalkdist3;
        		int elvl3;
        		int edir3;

        		for (int i=0;i<togo3;i++) {
                    ex3 = dis.readInt();
                    ey3 = dis.readInt();
                    ewalkdist3 = dis.readInt();
                    elvl3 = dis.readInt();
                    edir3 = dis.readInt();
                  
                    Zombie zomb = new Zombie(elvl3);
 				      
				    zomb.x = ex3;
					zomb.y = ey3;
					zomb.walkDist = ewalkdist3;
			        zomb.dir = edir3;
					game.level.add(zomb);                    
                    zomb.render(game.screen);              
        		}
        		
        		//client	System.out.println("finished new entities:zombie");
        		break;
        		
        	case 6:
        		
   		        for (int i=0;i<game.level.entities.size();i++) {
   		           if (game.level.entities.get(i) instanceof Creeper) {
   		             Creeper zomb = (Creeper) game.level.entities.get(i);
   		             zomb.remove();
   		           }
   		        }
   		   //client	System.out.println("Downloading new entities:creeper");
        		int togo4 = dis.readInt();
        		int ex4;
        		int ey4;
                int ewalkdist4;
        		int elvl4;
        		int edir4;

        		for (int i=0;i<togo4;i++) {
                    ex4 = dis.readInt();
                    ey4 = dis.readInt();
                    ewalkdist4 = dis.readInt();
                    elvl4 = dis.readInt();
                    edir4 = dis.readInt();
                  
                    Creeper zomb = new Creeper(elvl4);
 				      
				    zomb.x = ex4;
					zomb.y = ey4;
					zomb.walkDist = ewalkdist4;
			        zomb.dir = edir4;
					game.level.add(zomb);                    
                    zomb.render(game.screen);              
        		}
        		
        		//client	System.out.println("finished new entities:creepr");
        		break;
        		
        	case 7:
        	//client	System.out.println("start txp");
        		int x1 = dis.readInt();
        		int y1 = dis.readInt();
        		int s1 = dis.readInt();
        		int col1 = dis.readInt();
 
        		
        		TextParticle txp = new TextParticle(""+s1, x1, y1, col1);
        	    game.level.add(txp);
        	    txp.render(game.screen);
        	    
        	  //client	System.out.println("drawed txp");
        		break;
        		
        	case 8:
        		//client	System.out.println("Update player HP");
        		
        		game.player.health = dis.readInt();
        		
        		break;
        	case 9:
        		
   		        for (int i=0;i<game.level.entities.size();i++) {
   		           if (game.level.entities.get(i) instanceof ItemEntity && game.level.entities.get(i) != null) {
   		             ItemEntity drop = (ItemEntity) game.level.entities.get(i);
   		           //  System.out.println("KIlldrop"+i);
   		             drop.remove();
   		           }
   		        }
   		   //client	System.out.println("Downloading new entities:drops");
        		int togo5 = dis.readInt();
        		int ex5;
        		int ey5;
        		int etyp5;
        		for (int i=0;i<togo5;i++) {
        			game.newdrophelper = false;
                    ex5 = dis.readInt();
                    ey5 = dis.readInt();
                    etyp5 = dis.readInt();
                   
                    Item item =  Item.items.get(etyp5); 
        			if (item instanceof ResourceItem) {
        				ResourceItem resItem = new ResourceItem(((ResourceItem) item).resource);
                  		game.level.add(new ItemEntity(resItem, ex5, ey5));   
        			}   
                    
        		}
        		game.newdrophelper = false;
        		//client	System.out.println("finished new entities:drops");        		
        		break;
        		
        	case 10:
    	    	int togo10 = dis.readInt();
        		for (int i=0;i<togo10;i++) {
                    int ex10 = dis.readInt();
                    int ey10 = dis.readInt();
                    int typ10 = dis.readInt();
                    game.level.setTile(ex10, ey10, Tile.tiles[typ10], 0);
        		}        		
        		break;
        		
        		
        }
   
        System.out.println("[CONNECTION]Finished SERVER to CLIENT update with ID:"+statusupdated);
        System.out.println("[<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<]"); 
        
        oos.close();
        ois.close();
        game.dcimage = false;
        if (game.menu instanceof TitleMenu) {
        	game.setMenu(null);
        }
      } catch(Exception e) {
        System.out.println(e.getMessage());
     //   System.exit(0);
        game.dcimage = true;

        if (game.menu == null) {
         game.setMenu(new TitleMenu());
        }
        
      }
      
   } catch (Exception e) {
	   
   }
	  
   }


   
}