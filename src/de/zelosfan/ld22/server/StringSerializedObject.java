// 
// Decompiled by Procyon v0.5.30
// 

package de.zelosfan.ld22.server;

import java.io.Serializable;

public class StringSerializedObject implements Serializable
{
    private String[] array;
    
    public StringSerializedObject() {
        this.array = null;
    }
    
    public void setArray(final String[] array) {
        this.array = array;
    }
    
    public String[] getArray() {
        return this.array;
    }
}
