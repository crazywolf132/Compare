// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.gfx;

public class Font
{
    public static String chars;
    
    static {
        Font.chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      0123456789.,!?'\"-+=/\\%()<>:;     ";
    }
    
    public static void draw(String msg, final Screen screen, final int x, final int y, final int col) {
        msg = msg.toUpperCase();
        for (int i = 0; i < msg.length(); ++i) {
            final int ix = Font.chars.indexOf(msg.charAt(i));
            if (ix >= 0) {
                screen.render(x + i * 8, y, ix + 960, col, 0);
            }
        }
    }
    
    public static void renderFrame(final Screen screen, final String title, final int x0, final int y0, final int x1, final int y1) {
        int clr = Color.get(-1, 1, 5, 445);
        int clr2 = Color.get(5, 5, 5, 5);
        int clr3 = Color.get(5, 5, 5, 550);
        final int fillclr = 1;
        clr = Color.get(-1, 1, fillclr, -1);
        clr2 = Color.get(fillclr, fillclr, fillclr, fillclr);
        clr3 = Color.get(-1, -1, -1, 550);
        for (int y2 = y0; y2 <= y1; ++y2) {
            for (int x2 = x0; x2 <= x1; ++x2) {
                if (x2 == x0 && y2 == y0) {
                    screen.render(x2 * 8, y2 * 8, 416, clr, 0);
                }
                else if (x2 == x1 && y2 == y0) {
                    screen.render(x2 * 8, y2 * 8, 416, clr, 1);
                }
                else if (x2 == x0 && y2 == y1) {
                    screen.render(x2 * 8, y2 * 8, 416, clr, 2);
                }
                else if (x2 == x1 && y2 == y1) {
                    screen.render(x2 * 8, y2 * 8, 416, clr, 3);
                }
                else if (y2 == y0) {
                    screen.render(x2 * 8, y2 * 8, 417, clr, 0);
                }
                else if (y2 == y1) {
                    screen.render(x2 * 8, y2 * 8, 417, clr, 2);
                }
                else if (x2 == x0) {
                    screen.render(x2 * 8, y2 * 8, 418, clr, 0);
                }
                else if (x2 == x1) {
                    screen.render(x2 * 8, y2 * 8, 418, clr, 1);
                }
                else {
                    screen.render(x2 * 8, y2 * 8, 418, clr2, 1);
                }
            }
        }
        draw(title, screen, x0 * 8 + 8, y0 * 8, clr3);
    }
}
