package tile;

import Main.gamePanel;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.awt.Graphics2D;
import java.io.InputStream;
import java.io.InputStreamReader;


public class tileManager {
    gamePanel gp;
    tileSprite[] tile;
    int mapTileNum[][];

    public tileManager(gamePanel gp) {
        this.gp = gp;

        tile = new tileSprite[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[0] = new tileSprite();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grassTile2.png"));
            tile[1] = new tileSprite();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/waterTile2.png"));
            tile[2] = new tileSprite();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/brickTile.png"));


        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/Maps/map2.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;

            while (row < gp.maxScreenRow) {

                String line = br.readLine();

                if (line == null || line.trim().isEmpty()) {
                    continue;   // skip empty lines
                }

                String[] numbers = line.trim().split("\\s+");

                for (int col = 0; col < gp.maxScreenCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }

                row++;
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public void draw(Graphics2D g2) {

           int col = 0;
           int row = 0;
           int x = 0;
           int y = 0;

           while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

               int tileNum = mapTileNum[col][row];

               g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
               col++;
               x += gp.tileSize;

               if(col == gp.maxScreenCol){
                   col = 0;
                   x = 0;
                   row++;
                   y += gp.tileSize;
               }
               }
        }
    }

