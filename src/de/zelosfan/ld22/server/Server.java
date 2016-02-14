/*
 * Decompiled with CFR 0_110.
 */
package de.zelosfan.ld22.server;

import com.mojang.ld22.Game;
import de.zelosfan.ld22.server.Connect;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
extends Thread {
    private ServerSocket server = new ServerSocket(4000);
    private Game game;
    public boolean mapupdated = true;
    public boolean positionupdated = true;
    public boolean furnatureupdated = true;
    public boolean zombieposupdated = true;
    public boolean slimeposupdated = true;
    public boolean creeperposupdated = true;
    public boolean newtxp = false;
    public boolean multiplayerhpupdated = true;
    public boolean dropsupdated = true;
    public int tilerefreshcount = 0;
    public int[] xkoord = new int[999];
    public int[] ykoord = new int[999];
    public int txpx;
    public int txpy;
    public int txpcol;
    public int txpmsg;

    public Server(Game game) throws Exception {
        this.game = game;
        System.out.println("[SERVER]Server listening on port 4000.");
        this.start();
    }

    public void sendTextParticle(int x, int y, int col, int msg) {
        this.txpx = x;
        this.txpy = y;
        this.txpcol = col;
        this.txpmsg = msg;
        this.newtxp = true;
    }

    public void updatemap() {
        this.mapupdated = true;
    }

    public void updatetile(int x, int y) {
        if (!this.game.isloadingmap) {
            this.xkoord[this.tilerefreshcount] = x;
            this.ykoord[this.tilerefreshcount] = y;
            ++this.tilerefreshcount;
        }
    }

    public void updateplayerpositions() {
        this.positionupdated = true;
    }

    public void updatefurnature() {
        this.furnatureupdated = true;
    }

    public void updatezombiepos() {
        this.zombieposupdated = true;
    }

    public void updateslimepos() {
        this.slimeposupdated = true;
    }

    public void updatecreeperpos() {
        this.creeperposupdated = true;
    }

    @Override
    public void run() {
        System.out.println("[SERVER]Started CONNECTION THREAD" + Thread.currentThread().getId());
        do {
            try {
                do {
                    Connect a;
                    System.out.println("[CONNECTION]Waiting for client connection.");
                    Socket client = this.server.accept();
                    System.out.println("[CONNECTION]Accepted a connection from: " + client.getInetAddress());
                    if (this.positionupdated) {
                        Connect d = new Connect(client, this.game, 2);
                        this.positionupdated = false;
                        continue;
                    }
                    if (this.multiplayerhpupdated) {
                        Connect g = new Connect(client, this.game, 8);
                        this.multiplayerhpupdated = false;
                        continue;
                    }
                    if (this.mapupdated) {
                        Connect c = new Connect(client, this.game, 1);
                        this.mapupdated = false;
                        continue;
                    }
                    if (this.tilerefreshcount > 0) {
                        Connect i = new Connect(client, this.game, 10);
                        continue;
                    }
                    if (this.newtxp) {
                        Connect f = new Connect(client, this.game, 7);
                        this.newtxp = false;
                        continue;
                    }
                    if (this.dropsupdated) {
                        Connect h = new Connect(client, this.game, 9);
                        this.dropsupdated = false;
                        continue;
                    }
                    if (this.furnatureupdated) {
                        Connect e = new Connect(client, this.game, 3);
                        this.furnatureupdated = false;
                        continue;
                    }
                    if (this.zombieposupdated) {
                        Connect b = new Connect(client, this.game, 5);
                        this.zombieposupdated = false;
                        continue;
                    }
                    if (this.slimeposupdated) {
                        a = new Connect(client, this.game, 4);
                        this.slimeposupdated = false;
                        continue;
                    }
                    if (this.creeperposupdated) {
                        a = new Connect(client, this.game, 6);
                        this.creeperposupdated = false;
                        continue;
                    }
                    a = new Connect(client, this.game, 2);
                    this.updateslimepos();
                    this.updatezombiepos();
                    this.updatecreeperpos();
                } while (true);
            }
            catch (Exception client) {
                continue;
            }
        } while (true);
    }
}

