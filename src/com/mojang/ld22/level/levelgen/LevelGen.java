package com.mojang.ld22.level.levelgen;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import com.mojang.ld22.level.tile.Tile;

public class LevelGen {
	private static final Random random = new Random();
	public double[] values;
	private int w, h;

	public LevelGen(int w, int h, int featureSize) {
		this.w = w;
		this.h = h;

		values = new double[w * h]; //setz de l#nge der array values fest

		for (int y = 0; y < w; y += featureSize) {
			for (int x = 0; x < w; x += featureSize) {
				setSample(x, y, random.nextFloat() * 2 - 1);
			}
		}

		int stepSize = featureSize;
		double scale = 1.0 / w;
		double scaleMod = 1;
		do {
			int halfStep = stepSize / 2;
			for (int y = 0; y < w; y += stepSize) {
				for (int x = 0; x < w; x += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + stepSize, y + stepSize);

					double e = (a + b + c + d) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale;
					setSample(x + halfStep, y + halfStep, e);
				}
			}
			for (int y = 0; y < w; y += stepSize) {
				for (int x = 0; x < w; x += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + halfStep, y + halfStep);
					double e = sample(x + halfStep, y - halfStep);
					double f = sample(x - halfStep, y + halfStep);

					double H = (a + b + d + e) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale * 0.5;
					double g = (a + c + d + f) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale * 0.5;
					setSample(x + halfStep, y, H);
					setSample(x, y + halfStep, g);
				}
			}
			stepSize /= 2;
			scale *= (scaleMod + 0.8);
			scaleMod *= 0.3;
		} while (stepSize > 1);
	}

	private double sample(int x, int y) {
		return values[(x & (w - 1)) + (y & (h - 1)) * w];
	}

	private void setSample(int x, int y, double value) {
		values[(x & (w - 1)) + (y & (h - 1)) * w] = value;
	}

	public static byte[][] createAndValidateTopMap(int w, int h, int mapsize,int waterc, int grassc,int rockc,int treec) {
		int attempt = 0;
		double multpl = 0;
		multpl =  mapsize*0.5;
		
		if (multpl == 1.5) {
			multpl = 2;
		}
		
		byte[][] map = createTopMap(w, h);
		byte[] tiles = map[0];
		
		int biomeSize = 16;
		int[][] biomes = new int[w/biomeSize][h/biomeSize];
		
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				setTile(tiles, i, j, w, h, 0);
			}
		}
		
		for(int i = 0; i < w/biomeSize; i++) {
			for(int j = 0; j < h/biomeSize; j++) {
				if(biomes[i][j] == 0) {
					int range = Math.round((float)Math.sqrt((((w/biomeSize)/2-i)*((w/biomeSize)/2-i))+(((h/biomeSize)/2-j)*((h/biomeSize)/2-j))));
					if(range > (w/biomeSize)/(1+(int)(((double)waterc/2)+((double)waterc/2))) && waterc != 1) {
						biomes[i][j] = 5;
					} else
					if(range < (w/biomeSize)/3 && random.nextInt(8) == 0 && rockc != 1) {
						biomes[i][j] = 7;
					} else {
						while(biomes[i][j] == 5 || biomes[i][j] == 0 || biomes[i][j] == 7 ||
							 (biomes[i][j] == 4 && waterc == 1) ||
							 (biomes[i][j] == 1 && grassc == 1) || 
							 (biomes[i][j] == 6 && rockc == 1) || 
							 (biomes[i][j] == 2 && treec == 1)) {
							biomes[i][j] = random.nextInt(7) + 1;
							int rand = random.nextInt(4);
							if((rand == 0) &&
							  ((waterc == 3) && biomes[i][j] != 4)) {
								biomes[i][j] = 4;
							}
							if((rand == 1) &&
							  ((grassc == 3) && biomes[i][j] != 1)) {
								biomes[i][j] = 1;
							}
							if((rand == 0) &&
							  ((rockc == 3) && biomes[i][j] != 6)) {
								biomes[i][j] = 6;
							}
							if((rand == 0) &&
							  ((treec == 3) && biomes[i][j] != 2)) {
								biomes[i][j] = 2;
							}
						}
					}
				}
			}
		}
		
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				int biome = biomes[i/biomeSize][j/biomeSize];
				
				int range = Math.round((float)Math.sqrt(((((i/biomeSize)*biomeSize + biomeSize/2)-i)*(((i/biomeSize)*biomeSize + biomeSize/2)-i))+((((j/biomeSize)*biomeSize + biomeSize/2)-j)*(((j/biomeSize)*biomeSize + biomeSize/2)-j))));
				
				generateBiome(tiles, i, j, w, h, biome, range, biomeSize);
				if((i/biomeSize-1 >= 0 && j/biomeSize-1 >= 0) &&
				   (i/biomeSize+1 < w/biomeSize && j/biomeSize+1 < h/biomeSize)){
					if(biome == 4) {
						if(biomes[i/biomeSize-1][j/biomeSize] == 4 || biomes[i/biomeSize-1][j/biomeSize] == 5) generateBiome(tiles, i-(biomeSize/2), j, w, h, biome, range, biomeSize);
						if(biomes[i/biomeSize+1][j/biomeSize] == 4 || biomes[i/biomeSize+1][j/biomeSize] == 5) generateBiome(tiles, i+(biomeSize/2), j, w, h, biome, range, biomeSize);
						if(biomes[i/biomeSize][j/biomeSize-1] == 4 || biomes[i/biomeSize][j/biomeSize-1] == 5) generateBiome(tiles, i, j-(biomeSize/2), w, h, biome, range, biomeSize);
						if(biomes[i/biomeSize][j/biomeSize+1] == 4 || biomes[i/biomeSize][j/biomeSize+1] == 5) generateBiome(tiles, i, j+(biomeSize/2), w, h, biome, range, biomeSize);
					} else
					if(biome == 5) {
						if(biomes[i/biomeSize-1][j/biomeSize] != 5) generateCoast(tiles, i-biomeSize, j, w, h, biome, range, biomeSize, 1);
						if(biomes[i/biomeSize+1][j/biomeSize] != 5) generateCoast(tiles, i+biomeSize, j, w, h, biome, range, biomeSize, 3);
						if(biomes[i/biomeSize][j/biomeSize-1] != 5) generateCoast(tiles, i, j-biomeSize, w, h, biome, range, biomeSize, 2);
						if(biomes[i/biomeSize][j/biomeSize+1] != 5) generateCoast(tiles, i, j+biomeSize, w, h, biome, range, biomeSize, 0);
					} else
					if(biome == 6 || biome == 7) {
						if(biomes[i/biomeSize-1][j/biomeSize] == 6) generateBiome(tiles, i-(biomeSize/2), j, w, h, biome, range, biomeSize);
						if(biomes[i/biomeSize+1][j/biomeSize] == 6) generateBiome(tiles, i+(biomeSize/2), j, w, h, biome, range, biomeSize);
						if(biomes[i/biomeSize][j/biomeSize-1] == 6) generateBiome(tiles, i, j-(biomeSize/2), w, h, biome, range, biomeSize);
						if(biomes[i/biomeSize][j/biomeSize+1] == 6) generateBiome(tiles, i, j+(biomeSize/2), w, h, biome, range, biomeSize);
						
						if(biome == 7) {
							if(biomes[i/biomeSize-1][j/biomeSize] == 7) generateBiome(tiles, i-(biomeSize/2), j, w, h, biome, range, biomeSize);
							if(biomes[i/biomeSize+1][j/biomeSize] == 7) generateBiome(tiles, i+(biomeSize/2), j, w, h, biome, range, biomeSize);
							if(biomes[i/biomeSize][j/biomeSize-1] == 7) generateBiome(tiles, i, j-(biomeSize/2), w, h, biome, range, biomeSize);
							if(biomes[i/biomeSize][j/biomeSize+1] == 7) generateBiome(tiles, i, j+(biomeSize/2), w, h, biome, range, biomeSize);
						}
					} else {
						if(biomes[i/biomeSize-1][j/biomeSize] == biome) generateBiome(tiles, i-(biomeSize/2), j, w, h, biome, range, biomeSize);
						if(biomes[i/biomeSize+1][j/biomeSize] == biome) generateBiome(tiles, i+(biomeSize/2), j, w, h, biome, range, biomeSize);
						if(biomes[i/biomeSize][j/biomeSize-1] == biome) generateBiome(tiles, i, j-(biomeSize/2), w, h, biome, range, biomeSize);
						if(biomes[i/biomeSize][j/biomeSize+1] == biome) generateBiome(tiles, i, j+(biomeSize/2), w, h, biome, range, biomeSize);
					}
				}
			}
		}
		
		boolean strongholds = false;
		if(strongholds) {
			int many = 3;
			while(many > 0) {
				int x = random.nextInt(w/biomeSize);
				int y = random.nextInt(h/biomeSize);
				if((biomes[x][y] != 4) &&
				   (biomes[x][y] != 5) &&
				   (biomes[x][y] != 7)) {
					for(int i = 0; i < biomeSize; i++) {
						for(int j = 0; j < biomeSize; j++) {
							if((i > biomeSize/2 - 3 && i <= biomeSize/2 + 3) &&
							   (j > biomeSize/2 - 3 && j <= biomeSize/2 + 3)) {
								setTile(tiles, x*biomeSize+i, y*biomeSize+j, w, h, Tile.woodfloor.id);
							} else
							if((i > biomeSize/2 - 4 && i <= biomeSize/2 + 4) &&
							   (j > biomeSize/2 - 4 && j <= biomeSize/2 + 4)) {
								if(j == biomeSize/2 + 4 && i == biomeSize/2) {
									setTile(tiles, x*biomeSize+i, y*biomeSize+j, w, h, Tile.woodendoor.id);
								} else {
									setTile(tiles, x*biomeSize+i, y*biomeSize+j, w, h, Tile.woodplanktile.id);
								}
							}
						}
					}
					many--;
				}
			}
		}
	
        byte[] bio = new byte[(w/biomeSize)*(h/biomeSize)];
		
		for(int i = 0; i < biomes.length; i++) {
			for(int j = 0; j < biomes[i].length; j++) {
				bio[i + j*biomes.length] = (byte) biomes[i][j];
			}
		}
		
		int setsteps = 0;
		while (setsteps < 4) {
		 int x = random.nextInt(w);
		 int y = random.nextInt(h);
		 
		 if (getTile(tiles, x, y, w, h) == Tile.rock.id) {
		  setTile(tiles, x, y, w, h, Tile.stairsDown.id);
		  setsteps++;
		 }
		}
		
		byte[][] result = {tiles, map[1], bio};
		return result;
	}

	public static void generateCoast(byte[] tiles, int i, int j, int w, int h, int biome, int range, int biomeSize, int x) {
		if(range > biomeSize/3) {
			if(x == 0) {
				if(j < (j/biomeSize)*biomeSize+biomeSize/3) {
					setTile(tiles, i, j, w, h, Tile.water.id);
				}
			} else
			if(x == 1) {
				if(i > (i/biomeSize)*biomeSize+biomeSize-biomeSize/3) {
					setTile(tiles, i, j, w, h, Tile.water.id);
				}
			} else
			if(x == 2) {
				if(j > (j/biomeSize)*biomeSize+biomeSize-biomeSize/3) {
					setTile(tiles, i, j, w, h, Tile.water.id);
				}
			} else
			if(x == 3) {
				if(i < (i/biomeSize)*biomeSize+biomeSize/3) {
					setTile(tiles, i, j, w, h, Tile.water.id);
				}
			}
		}
	}

	public static void generateBiome(byte[] tiles, int i, int j, int w, int h, int biome, int range, int biomeSize) {
		if(biome == 1) {
			if(range < biomeSize/2) {
				setTile(tiles, i, j, w, h, Tile.grass.id);
			} else
			if(getTile(tiles, i, j, w, h) != Tile.grass.id &&
			  (random.nextInt(range) == 0)) {
				setTile(tiles, i, j, w, h, Tile.grass.id);
			}
			if(getTile(tiles, i, j, w, h) != Tile.water.id) {
				if(random.nextInt(range+35) == 0) {
					setTile(tiles, i, j, w, h, Tile.tree.id);
				}
			}
		} else
		if(biome == 2) {
			if(range < biomeSize/2) {
				setTile(tiles, i, j, w, h, Tile.grass.id);
			} else
			if(getTile(tiles, i, j, w, h) != Tile.grass.id &&
			  (random.nextInt(range) == 0)) {
				setTile(tiles, i, j, w, h, Tile.grass.id);
			}
			if(getTile(tiles, i, j, w, h) != Tile.water.id) {
				if(random.nextInt(range+1) < 2) {
					setTile(tiles, i, j, w, h, Tile.tree.id);
				}
			}
		} else
		if(biome == 3) {
			if(range < biomeSize/2) {
				setTile(tiles, i, j, w, h, Tile.sand.id);
				if(getTile(tiles, i, j, w, h) == Tile.sand.id) {
					if(random.nextInt(range+32) == 0) {
						setTile(tiles, i, j, w, h, Tile.cactus.id);
					}
				}
			} else
			if(getTile(tiles, i, j, w, h) != Tile.sand.id &&
			  (random.nextInt(range+8) == 0)) {
				setTile(tiles, i, j, w, h, Tile.sand.id);
			}
		} else
		if(biome == 4) {
			if(range < biomeSize/2 + random.nextInt(biomeSize/8)) {
				setTile(tiles, i, j, w, h, Tile.water.id);
			}
		} else
		if(biome == 5) {
			setTile(tiles, i, j, w, h, Tile.water.id);
		} else
		if(biome == 6) {
			if(range < biomeSize/2) {
				if(range < biomeSize/4) {
					setTile(tiles, i, j, w, h, Tile.rock.id);
				} else {
					setTile(tiles, i, j, w, h, Tile.dirt.id);
				}
			} else
			if(getTile(tiles, i, j, w, h) != Tile.dirt.id &&
			  (random.nextInt(range+8) == 0)) {
				setTile(tiles, i, j, w, h, Tile.dirt.id);
			}
			if(getTile(tiles, i, j, w, h) == Tile.dirt.id) {
				if(random.nextInt(range+1) == 0) {
					setTile(tiles, i, j, w, h, Tile.rock.id);
				}
			}
		} else
		if(biome == 7) {
			if(range < biomeSize/4 + random.nextInt(biomeSize/8)) {
				setTile(tiles, i, j, w, h, Tile.hole.id);
			}
			if(range < biomeSize/2) {
				if(range > biomeSize/5 && getTile(tiles, i, j, w, h) != Tile.hole.id) {
					if(random.nextInt(3) == 0) {
						setTile(tiles, i, j, w, h, Tile.rock.id);
					} else {
						setTile(tiles, i, j, w, h, Tile.dirt.id);
					}
				}
			}
		} else {
			setTile(tiles, i, j, w, h, Tile.water.id);
		}
	}

	public static void delete(byte[] map, int x, int y, int w, int h, int... val) {
		for(int i = 0; i < val.length; i++) {
			if(getTile(map, x, y, w, h) == val[i]) {
				setTile(map, x, y, w, h, 0);
			}
		}
	}

	public static int getTile(byte[] map, int x, int y, int w, int h) {
		if (x < 0 || y < 0 || x >= w || y >= h) return 0;
		return map[x + y * w] & 0xff;
	}

	public static void setTile(byte[] map, int x, int y, int w, int h, int val) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		map[x + y * w] = (byte) val;
	}

/*do {
	attempt++;
	byte[][] result = createTopMap(w, h);

	int[] count = new int[256];

	for (int i = 0; i < w * h; i++) {
		count[result[0][i] & 0xff]++;
	}
	
    if (sandc == 1) {
      if (count[Tile.sand.id & 0xff] > 100) continue;
    } else {
	  if (count[Tile.sand.id & 0xff] < sandcount) continue;
    }

	switch (waterc) {
	case 1:
		if (count[Tile.water.id & 0xff] > multpl*6000+attempt*4) continue;				
		break;

	case 2:
		
		break;
	
	case 3:
		
		if (count[Tile.water.id & 0xff] < multpl*10000-attempt*4) continue;
		break;
		
	}


	switch (grassc) {
	case 1:
		if (count[Tile.grass.id & 0xff] > multpl*2500+attempt) continue;				
		break;

	case 2:
		if (count[Tile.grass.id & 0xff] < multpl*100) continue;
		break;
	
	case 3:
		
		if (count[Tile.grass.id & 0xff] < multpl*4000-attempt) continue;
		break;
		
	}
	

	switch (rockc) {
	case 1:
		if (count[Tile.rock.id & 0xff] > multpl*200+attempt) continue;				
		break;

	case 2:
		if (count[Tile.rock.id & 0xff] < multpl*100) continue;
		break;
	
	case 3:
		
		if (count[Tile.rock.id & 0xff] < multpl*1500-attempt) continue;
		break;
		
	}		
	
	switch (treec) {
	case 1:
		if (count[Tile.tree.id & 0xff] > multpl*1200+attempt) continue;				
		break;

	case 2:
		if (count[Tile.tree.id & 0xff] < multpl*100) continue;
		break;
	
	case 3:
		
		if (count[Tile.tree.id & 0xff] < multpl*3000-attempt) continue;
		break;
		
	}				
	
    if (count[Tile.sand.id & 0xff] < multpl*100) continue;
	

	if (count[Tile.stairsDown.id & 0xff] < 2) continue;

	return result;

} while (true);*/
	public static byte[][] createAndValidateUndergroundMap(int w, int h, int depth) {
		int attempt = 0;
		int mapsize = w / 64;
		double multpl = 0;
		multpl =  mapsize*0.5;
		do {
			byte[][] result = createUndergroundMap(w, h, depth);

			int[] count = new int[256];

			for (int i = 0; i < w * h; i++) {
				count[result[0][i] & 0xff]++;
			
			if (count[Tile.rock.id & 0xff] < 100*multpl) continue;
			if (count[Tile.dirt.id & 0xff] < 100*multpl) continue;
			if (count[(Tile.ironOre.id & 0xff) + depth - 1] < 20*multpl) continue;
			if (depth < 3) if (count[Tile.stairsDown.id & 0xff] < 2) continue;
			
			}
			return result;

		} while (true);
	}

	
	
	public static byte[][] createAndValidateHellMap(int w, int h) {
		int attempt = 0;
		int mapsize = w / 64;
		double multpl = 0;
		multpl =  mapsize*0.5;
//		do {
			byte[][] result = createHellMap(w, h);
            return result;
//			int[] count = new int[256];

//			for (int i = 0; i < w * h; i++) {
//				count[result[0][i] & 0xff]++;
			
//			if (count[Tile.rock.id & 0xff] < 100*multpl) continue;
//			if (count[Tile.dirt.id & 0xff] < 100*multpl) continue;
//			if (count[(Tile.ironOre.id & 0xff) + depth - 1] < 20*multpl) continue;
//			if (depth < 3) if (count[Tile.stairsDown.id & 0xff] < 2) continue;
//			
//			}
//			return result;
//
//		} while (true);
	}

	public static byte[][] createAndValidateSkyMap(int w, int h) {
		int attempt = 0;
		int mapsize = w / 64;
		double multpl = 0;
		multpl =  mapsize*0.5;
		do {
			byte[][] result = createSkyMap(w, h);

			int[] count = new int[256];

			for (int i = 0; i < w * h; i++) {
				count[result[0][i] & 0xff]++;
			}
			if (count[Tile.cloud.id & 0xff] < 2000*multpl) continue;
			if (count[Tile.stairsDown.id & 0xff] < 2) continue;

			return result;

		} while (true);
	}

	private static void setID(byte[] map, int i, byte id) {
		map[i] = id;
	}

	private static byte[][] createTopMap(int w, int h) {
		LevelGen mnoise1 = new LevelGen(w, h, 16);
		LevelGen mnoise2 = new LevelGen(w, h, 16);
		LevelGen mnoise3 = new LevelGen(w, h, 16);

		LevelGen noise1 = new LevelGen(w, h, 32);
		LevelGen noise2 = new LevelGen(w, h, 32);

		byte[] map = new byte[w * h];
		byte[] data = new byte[w * h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int i = x + y * w;

				double val = Math.abs(noise1.values[i] - noise2.values[i]) * 3 - 2;
				double mval = Math.abs(mnoise1.values[i] - mnoise2.values[i]);
				mval = Math.abs(mval - mnoise3.values[i]) * 3 - 2;

				double xd = x / (w - 1.0) * 2 - 1;
				double yd = y / (h - 1.0) * 2 - 1;
				if (xd < 0) xd = -xd;
				if (yd < 0) yd = -yd;
				double dist = xd >= yd ? xd : yd;
				dist = dist * dist * dist * dist;
				dist = dist * dist * dist * dist;
				val = val + 1 - dist * 20;

				if (val < -0.5) {
					setID(map, i, Tile.water.id);
				} else if (val > 0.5 && mval < -1.5) {
					setID(map, i, Tile.rock.id);
				} else {
					setID(map, i, Tile.grass.id);
				}
			}
		}

		for (int i = 0; i < w * h / 2800; i++) {
			int xs = random.nextInt(w);
			int ys = random.nextInt(h);
			for (int k = 0; k < 10; k++) {
				int x = xs + random.nextInt(21) - 10;
				int y = ys + random.nextInt(21) - 10;
				for (int j = 0; j < 100; j++) {
					int xo = x + random.nextInt(5) - random.nextInt(5);
					int yo = y + random.nextInt(5) - random.nextInt(5);
					for (int yy = yo - 1; yy <= yo + 1; yy++)
						for (int xx = xo - 1; xx <= xo + 1; xx++)
							if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
								if (map[xx + yy * w] == Tile.grass.id) {
									setID(map, xx + yy * w, Tile.sand.id);
								}
							}
				}
			}
		}

		/*
		 * for (int i = 0; i < w * h / 2800; i++) { int xs = random.nextInt(w); int ys = random.nextInt(h); for (int k = 0; k < 10; k++) { int x = xs + random.nextInt(21) - 10; int y = ys + random.nextInt(21) - 10; for (int j = 0; j < 100; j++) { int xo = x + random.nextInt(5) - random.nextInt(5); int yo = y + random.nextInt(5) - random.nextInt(5); for (int yy = yo - 1; yy <= yo + 1; yy++) for (int xx = xo - 1; xx <= xo + 1; xx++) if (xx >= 0 && yy >= 0 && xx < w && yy < h) { if (map[xx + yy * w] == Tile.grass.id) { map[xx + yy * w] = Tile.dirt.id; } } } } }
		 */

		for (int i = 0; i < w * h / 400; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			for (int j = 0; j < 200; j++) {
				int xx = x + random.nextInt(15) - random.nextInt(15);
				int yy = y + random.nextInt(15) - random.nextInt(15);
				if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
					if (map[xx + yy * w] == Tile.grass.id) {
						setID(map, xx + yy * w, Tile.tree.id);
					}
				}
			}
		}

		for (int i = 0; i < w * h / 400; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int col = random.nextInt(4);
			for (int j = 0; j < 30; j++) {
				int xx = x + random.nextInt(5) - random.nextInt(5);
				int yy = y + random.nextInt(5) - random.nextInt(5);
				if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
					if (map[xx + yy * w] == Tile.grass.id) {
						setID(map, xx + yy * w, Tile.flower.id);
						data[xx + yy * w] = (byte) (col + random.nextInt(4) * 16);
					}
				}
			}
		}

		for (int i = 0; i < w * h / 100; i++) {
			int xx = random.nextInt(w);
			int yy = random.nextInt(h);
			if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
				if (map[xx + yy * w] == Tile.sand.id) {
					setID(map, xx + yy * w, Tile.cactus.id);
				}
			}
		}

		int count = 0;
		stairsLoop: for (int i = 0; i < w * h / 100; i++) {
			int x = random.nextInt(w - 2) + 1;
			int y = random.nextInt(h - 2) + 1;

			for (int yy = y - 1; yy <= y + 1; yy++)
				for (int xx = x - 1; xx <= x + 1; xx++) {
					if (map[xx + yy * w] != Tile.rock.id) continue stairsLoop;
				}

			setID(map, x + y * w, Tile.stairsDown.id);
			count++;
			if (count == 4) break;
		}

		return new byte[][] { map, data };
	}

	private static byte[][] createHellMap(int w, int h) {
		
		LevelGen mnoise1 = new LevelGen(w, h, 16);
		LevelGen mnoise2 = new LevelGen(w, h, 16);
		LevelGen mnoise3 = new LevelGen(w, h, 16);

		LevelGen nnoise1 = new LevelGen(w, h, 16);
		LevelGen nnoise2 = new LevelGen(w, h, 16);
		LevelGen nnoise3 = new LevelGen(w, h, 16);

		LevelGen wnoise1 = new LevelGen(w, h, 16);
		LevelGen wnoise2 = new LevelGen(w, h, 16);
		LevelGen wnoise3 = new LevelGen(w, h, 16);

		LevelGen noise1 = new LevelGen(w, h, 32);
		LevelGen noise2 = new LevelGen(w, h, 32);

		byte[] map = new byte[w * h];
		byte[] data = new byte[w * h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int i = x + y * w;

				double val = Math.abs(noise1.values[i] - noise2.values[i]) * 3 - 2;

				double mval = Math.abs(mnoise1.values[i] - mnoise2.values[i]);
				mval = Math.abs(mval - mnoise3.values[i]) * 3 - 2;

				double nval = Math.abs(nnoise1.values[i] - nnoise2.values[i]);
				nval = Math.abs(nval - nnoise3.values[i]) * 3 - 2;

				double wval = Math.abs(wnoise1.values[i] - wnoise2.values[i]);
				wval = Math.abs(nval - wnoise3.values[i]) * 3 - 2;

				double xd = x / (w - 1.0) * 2 - 1;
				double yd = y / (h - 1.0) * 2 - 1;
				if (xd < 0) xd = -xd;
				if (yd < 0) yd = -yd;
				double dist = xd >= yd ? xd : yd;
				dist = dist * dist * dist * dist;
				dist = dist * dist * dist * dist;
				val = val + 1 - dist * 20;
//				System.out.print("val:"+val);
//                System.out.print("mval:"+mval);
//                System.out.print("wval:"+wval);
//                System.out.print("nval:"+nval);
			    if (val > -2 && wval < -1.8) {
				setID(map, i, Tile.glowstoneOre.id);
			    }
			    else if (val > -2 && wval < 2.5) {
					setID(map, i, Tile.lava.id);
				} else {
					setID(map, i, Tile.dirt.id);
				}
			}
		}

		{
			int r = 2;
			for (int i = 0; i < w * h / 400; i++) {
				int x = random.nextInt(w);
				int y = random.nextInt(h);
				for (int j = 0; j < 30; j++) {
					int xx = x + random.nextInt(5) - random.nextInt(5);
					int yy = y + random.nextInt(5) - random.nextInt(5);
					if (xx >= r && yy >= r && xx < w - r && yy < h - r) {
						if (map[xx + yy * w] == Tile.rock.id) {
							setID(map, xx + yy * w, (byte) ((Tile.ironOre.id & 0xff) + -3 - 1));
						}
					}
				}
			}
		}

		/*if (depth < 3) {
			int count = 0;
			stairsLoop: for (int i = 0; i < w * h / 100; i++) {
				int x = random.nextInt(w - 20) + 10;
				int y = random.nextInt(h - 20) + 10;

				for (int yy = y - 1; yy <= y + 1; yy++)
					for (int xx = x - 1; xx <= x + 1; xx++) {
						if (map[xx + yy * w] != Tile.rock.id) continue stairsLoop;
					}

				setID(map, x + y * w, Tile.stairsDown.id);
				count++;
				if (count == 4) break;
			}*/
//		}

		return new byte[][] { map, data };
	}	
	
	
	private static byte[][] createUndergroundMap(int w, int h, int depth) {
		LevelGen mnoise1 = new LevelGen(w, h, 16);
		LevelGen mnoise2 = new LevelGen(w, h, 16);
		LevelGen mnoise3 = new LevelGen(w, h, 16);

		LevelGen nnoise1 = new LevelGen(w, h, 16);
		LevelGen nnoise2 = new LevelGen(w, h, 16);
		LevelGen nnoise3 = new LevelGen(w, h, 16);

		LevelGen wnoise1 = new LevelGen(w, h, 16);
		LevelGen wnoise2 = new LevelGen(w, h, 16);
		LevelGen wnoise3 = new LevelGen(w, h, 16);

		LevelGen noise1 = new LevelGen(w, h, 32);
		LevelGen noise2 = new LevelGen(w, h, 32);

		byte[] map = new byte[w * h];
		byte[] data = new byte[w * h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int i = x + y * w;

				double val = Math.abs(noise1.values[i] - noise2.values[i]) * 3 - 2;

				double mval = Math.abs(mnoise1.values[i] - mnoise2.values[i]);
				mval = Math.abs(mval - mnoise3.values[i]) * 3 - 2;

				double nval = Math.abs(nnoise1.values[i] - nnoise2.values[i]);
				nval = Math.abs(nval - nnoise3.values[i]) * 3 - 2;

				double wval = Math.abs(wnoise1.values[i] - wnoise2.values[i]);
				wval = Math.abs(nval - wnoise3.values[i]) * 3 - 2;

				double xd = x / (w - 1.0) * 2 - 1;
				double yd = y / (h - 1.0) * 2 - 1;
				if (xd < 0) xd = -xd;
				if (yd < 0) yd = -yd;
				double dist = xd >= yd ? xd : yd;
				dist = dist * dist * dist * dist;
				dist = dist * dist * dist * dist;
				val = val + 1 - dist * 20;

				if (val > -2 && wval < -2.0 + (depth) / 2 * 3) {
					if (depth > 2)
						setID(map, i, Tile.lava.id);
					else
						setID(map, i, Tile.water.id);
				} else if (val > -2 && (mval < -1.7 || nval < -1.4)) {
					setID(map, i, Tile.dirt.id);
				} else {
					setID(map, i, Tile.rock.id);
				}
			}
		}

		{
			int r = 2;
			for (int i = 0; i < w * h / 400; i++) {
				int x = random.nextInt(w);
				int y = random.nextInt(h);
				for (int j = 0; j < 30; j++) {
					int xx = x + random.nextInt(5) - random.nextInt(5);
					int yy = y + random.nextInt(5) - random.nextInt(5);
					if (xx >= r && yy >= r && xx < w - r && yy < h - r) {
						if (map[xx + yy * w] == Tile.rock.id) {
							setID(map, xx + yy * w, (byte) ((Tile.ironOre.id & 0xff) + depth - 1));
						}
					}
				}
			}
		}

		if (depth < 3) {
			int count = 0;
			stairsLoop: for (int i = 0; i < w * h / 100; i++) {
				int x = random.nextInt(w - 20) + 10;
				int y = random.nextInt(h - 20) + 10;

				for (int yy = y - 1; yy <= y + 1; yy++)
					for (int xx = x - 1; xx <= x + 1; xx++) {
						if (map[xx + yy * w] != Tile.rock.id) continue stairsLoop;
					}

				setID(map, x + y * w, Tile.stairsDown.id);
				count++;
				if (count == 4) break;
			}
		}

		return new byte[][] { map, data };
	}

	private static byte[][] createSkyMap(int w, int h) {
		LevelGen noise1 = new LevelGen(w, h, 8);
		LevelGen noise2 = new LevelGen(w, h, 8);

		byte[] map = new byte[w * h];
		byte[] data = new byte[w * h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int i = x + y * w;

				double val = Math.abs(noise1.values[i] - noise2.values[i]) * 3 - 2;

				double xd = x / (w - 1.0) * 2 - 1;
				double yd = y / (h - 1.0) * 2 - 1;
				if (xd < 0) xd = -xd;
				if (yd < 0) yd = -yd;
				double dist = xd >= yd ? xd : yd;
				dist = dist * dist * dist * dist;
				dist = dist * dist * dist * dist;
				val = -val * 1 - 2.2;
				val = val + 1 - dist * 20;

				if (val < -0.25) {
					setID(map, i, Tile.infiniteFall.id);
				} else {
					setID(map, i, Tile.cloud.id);
				}
			}
		}

		stairsLoop: for (int i = 0; i < w * h / 50; i++) {
			int x = random.nextInt(w - 2) + 1;
			int y = random.nextInt(h - 2) + 1;

			for (int yy = y - 1; yy <= y + 1; yy++)
				for (int xx = x - 1; xx <= x + 1; xx++) {
					if (map[xx + yy * w] != Tile.cloud.id) continue stairsLoop;
				}

			setID(map, x + y * w, Tile.cloudCactus.id);
		}

		int count = 0;
		stairsLoop: for (int i = 0; i < w * h; i++) {
			int x = random.nextInt(w - 2) + 1;
			int y = random.nextInt(h - 2) + 1;

			for (int yy = y - 1; yy <= y + 1; yy++)
				for (int xx = x - 1; xx <= x + 1; xx++) {
					if (map[xx + yy * w] != Tile.cloud.id) continue stairsLoop;
				}

			setID(map, x + y * w, Tile.stairsDown.id);
			count++;
			if (count == 2) break;
		}

		return new byte[][] { map, data };
	}

	public static void main(String[] args) {
		int d = 0;
		int tick = 0;
		System.out.println("start..");
		if (true) {
			tick++;
			int w = 256;
			int h = 256;

			byte[] map = LevelGen.createAndValidateTopMap(w, h,2,2,2,2,2)[0];
			// byte[] map = LevelGen.createAndValidateUndergroundMap(w, h, (d++ % 3) + 1)[0];
			// byte[] map = LevelGen.createAndValidateSkyMap(w, h)[0];

			BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			int[] pixels = new int[w * h];
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int i = x + y * w;

					if (map[i] == Tile.water.id) pixels[i] = 0x000080;
					if (map[i] == Tile.grass.id) pixels[i] = 0x208020;
					if (map[i] == Tile.rock.id) pixels[i] = 0xa0a0a0;
					if (map[i] == Tile.dirt.id) pixels[i] = 0x604040;
					if (map[i] == Tile.sand.id) pixels[i] = 0xa0a040;
					if (map[i] == Tile.tree.id) pixels[i] = 0x003000;
					if (map[i] == Tile.lava.id) pixels[i] = 0xff2020;
					if (map[i] == Tile.cloud.id) pixels[i] = 0xa0a0a0;
					if (map[i] == Tile.stairsDown.id) pixels[i] = 0xffffff;
					if (map[i] == Tile.stairsUp.id) pixels[i] = 0xffffff;
					if (map[i] == Tile.cloudCactus.id) pixels[i] = 0xff00ff;
				}
			}
			img.setRGB(0, 0, w, h, pixels, 0, w);
			JOptionPane.showMessageDialog(null, null, "Another", JOptionPane.YES_NO_OPTION, new ImageIcon(img.getScaledInstance(w * 4, h * 4, Image.SCALE_AREA_AVERAGING)));
		}
		System.out.println("stop..");
	}
}