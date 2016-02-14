// 
// Decompiled by Procyon v0.5.30
// 

package de.zelosfan.ld22.server;

import java.io.Serializable;

public class SerializedObject implements Serializable
{
    private int[] array;
    
    public SerializedObject() {
        this.array = null;
    }
    
    public void setArray(final int[] array) {
        this.array = array;
    }
    
    public int[] getArray() {
        return this.array;
    }
}
