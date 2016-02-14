// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.modloader;

import de.thejackimonster.ld22.vehicles.mod_minecarts;
import de.thejackimonster.ld22.options.mod_options_ingamemenu;
import de.thejackimonster.ld22.matrix.mod_matrix;
import de.thejackimonster.ld22.weapons.mod_weapons;
import de.thejackimonster.ld22.story.dialog.mod_StoryMode;
import de.thejackimonster.ld22.bed.mod_bed;
import de.thejackimonster.ld22.leveltree.mod_leveltree;
import de.thejackimonster.ld22.redstone.mod_redstone;
import de.thejackimonster.ld22.loadandsave.mod_worldoptions;

public class UseMods
{
    private static final BaseMod[] mods;
    
    static {
        mods = new BaseMod[256];
    }
    
    public static void addMods() {
        UseMods.mods[0] = new mod_worldoptions();
        UseMods.mods[1] = new mod_redstone();
        UseMods.mods[2] = new mod_leveltree();
        UseMods.mods[3] = new mod_bed();
        UseMods.mods[4] = new mod_StoryMode();
        UseMods.mods[5] = new mod_weapons();
        UseMods.mods[6] = new mod_matrix();
        UseMods.mods[7] = new mod_options_ingamemenu();
        UseMods.mods[8] = new mod_minecarts();
    }
}
