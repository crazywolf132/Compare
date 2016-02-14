// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.gfx;

public class Color
{
    public static int get(final int a, final int b, final int c, final int d) {
        return (get(d) << 24) + (get(c) << 16) + (get(b) << 8) + get(a);
    }
    
    public static int get(final int d) {
        if (d < 0) {
            return 255;
        }
        final int r = d / 100 % 10;
        final int g = d / 10 % 10;
        final int b = d % 10;
        return r * 36 + g * 6 + b;
    }
    
    public static int getR(final int d) {
        return d / 100 % 10;
    }
    
    public static int getG(final int d) {
        return d / 10 % 10;
    }
    
    public static int getB(final int d) {
        return d % 10;
    }
    
    public static int getRGB(final int r, final int g, final int b) {
        int r2 = 0;
        int g2 = 0;
        int b2 = 0;
        if (r > 0 && r < 256) {
            r2 = Math.round((float)(r / 255.0 * 9.0)) * 100;
        }
        if (g > 0 && g < 256) {
            g2 = Math.round((float)(g / 255.0 * 9.0)) * 10;
        }
        if (b > 0 && b < 256) {
            b2 = Math.round((float)(b / 255.0 * 9.0)) * 1;
        }
        return r2 + g2 + b2;
    }
}
