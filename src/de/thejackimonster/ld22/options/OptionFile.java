/*
 * Decompiled with CFR 0_110.
 */
package de.thejackimonster.ld22.options;

import com.mojang.ld22.Game;
import com.mojang.ld22.gfx.Color;
import de.thejackimonster.ld22.file.SystemFile;

public class OptionFile {
    public static boolean created = false;
    public static Game g;
    public static int difficulty;
    public static String name;
    public static int color;
    public static int a;
    public static int b;
    public static int c;
    public static int d;
    public static int[] keys;
    public static int[] bkeys;

    static {
        difficulty = 1;
        name = "Player";
        color = Color.get(-1, 100, 220, 532);
        a = -1;
        b = 100;
        c = 220;
        d = 532;
        keys = new int[]{87, 65, 83, 68, 81, 86, 27, 67, 88, 89, 69, 82};
        bkeys = new int[]{87, 65, 83, 68, 81, 86, 27, 67, 88, 89, 69, 82};
    }

    public static void create(Game game) {
        if (game == null || game.isApplet) {
            return;
        }
        created = true;
        g = game;
        SystemFile sf = new SystemFile("options.txt", game);
        sf.Create();
        sf.Close();
        OptionFile.readOpt();
        int i = 0;
        while (i < keys.length) {
            if (keys[i] == 0) {
                OptionFile.keys[i] = bkeys[i];
            }
            ++i;
        }
        OptionFile.writeOpt();
    }

    public static void readOpt() {
        if (!created) {
            return;
        }
        SystemFile sf = new SystemFile("options.txt", g);
        sf.Create();
        sf.Reset();
        sf.Read();
        int i = 0;
        while (!sf.EndOfFile()) {
            String s = sf.ReadLn();
            if (i == 0) {
                difficulty = Integer.parseInt(s);
            }
            if (i == 1) {
                name = s;
            }
            if (i == 2) {
                Game.CLIENTCONNECTHOST = s;
            }
            if (i >= 3 && i <= 6) {
                if (i == 3) {
                    a = (int)Double.parseDouble(s);
                }
                if (i == 4) {
                    b = (int)Double.parseDouble(s);
                }
                if (i == 5) {
                    c = (int)Double.parseDouble(s);
                }
                if (i == 6) {
                    d = (int)Double.parseDouble(s);
                    if (b < 1) {
                        b = 555;
                    }
                    if (c < 1) {
                        c = 0;
                    }
                    if (d < 1) {
                        d = 0;
                    }
                    color = Color.get(a, b, c, d);
                }
            }
            if (i > 6) {
                OptionFile.keys[i - 7] = Integer.parseInt(s);
            }
            ++i;
        }
        sf.Close();
    }

    public static void writeOpt() {
        if (!created) {
            return;
        }
        SystemFile sf = new SystemFile("options.txt", g);
        sf.Create();
        sf.Rewrite();
        sf.WriteLn(String.valueOf(difficulty));
        sf.WriteLn(name);
        sf.WriteLn(Game.CLIENTCONNECTHOST);
        sf.WriteLn(String.valueOf((double)a));
        sf.WriteLn(String.valueOf((double)b));
        sf.WriteLn(String.valueOf((double)c));
        sf.WriteLn(String.valueOf((double)d));
        int i = 0;
        while (i < keys.length) {
            sf.WriteLn(String.valueOf(keys[i]));
            ++i;
        }
        sf.Write();
        sf.Close();
    }
}

