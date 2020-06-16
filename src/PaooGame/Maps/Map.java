package PaooGame.Maps;

import PaooGame.Graphics.ImageLoader;
import PaooGame.Items.Bee;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;
import PaooGame.Tiles.TransparentTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*! \class public class Map
    \brief Implementeaza notiunea de harta a jocului.
 */
public class Map {
    private RefLinks refLink;   /*!< O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.*/
    private int width;    /*!< Latimea hartii in numar de dale.*/
    private int height;    /*!< Inaltimea hartii in numar de dale.*/
    protected int[][] tiles;    /*!< Referinta catre o matrice cu codurile dalelor ce vor construi harta.*/
    public int[][] map;     /*!< Matricea cu tipul dalelor */

    public ArrayList<Rectangle> solid;  /*!< Vectorul cu dalele iarba. */
    public ArrayList<Rectangle> points;     /*!< Vectorul cu dalele recompense(puncte). */
    public ArrayList<Bee> bee;      /*!< Vectorul cu dalele inamici. */
    public BufferedImage[] image;
    /*! \fn public Map(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
        \param level Nivelul in functie de care se actualizeaza harta
    */
    public Map(RefLinks refLink, int level) {
        /// Retine referinta "shortcut".
        this.refLink = refLink;
        /// Latimea hartii in numarul de dale
        width = 100;
        /// Inaltimea hartii in numarul de dale
        height = 12;
        /// Generarea hartii prin metoda Factory
        map = new FactoryMap(refLink).generate(level);
        ///incarca harta de start. Functia poate primi ca argument id-ul hartii ce poate fi incarcat.
        LoadWorld();
        /// Construirea vectorului de dale iarba
        solid = new ArrayList<Rectangle>();
        /// Construirea vectorul puncte
        points = new ArrayList<Rectangle>();
        /// Construirea vectorului de inamici
        bee = new ArrayList<Bee>();

        ImageLoader loader = new ImageLoader();
        image = new BufferedImage[3];
        image[0] = loader.LoadImage("/textures/layer-1.png");
        image[1] = loader.LoadImage("/textures/layer-2.png");
        image[2] = loader.LoadImage("/textures/layer-3.png");

        /// Separarea dalelor iarba de restul dalelor jocului
        for(int x = 0; x <height; x++) {
            for (int y = 0; y < width; y++) {
                if (map[x][y] == 1) {
                    solid.add(new Rectangle(x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT));
                }
            }
        }
        /// Adaugarea punctelor(recompenselor) pe harta la anumite cooronate
        points.add(new Rectangle(550, 300, Tile.TILE_WIDTH, Tile.TILE_HEIGHT));
        points.add(new Rectangle(1200, 500, Tile.TILE_WIDTH, Tile.TILE_HEIGHT));
        points.add(new Rectangle(1600, 200, Tile.TILE_WIDTH, Tile.TILE_HEIGHT));
        points.add(new Rectangle(2700, 200, Tile.TILE_WIDTH, Tile.TILE_HEIGHT));
        points.add(new Rectangle(3800, 250, Tile.TILE_WIDTH, Tile.TILE_HEIGHT));
        /// Adaugarea inamicilor pe harta
        for(int i=0; i<points.size(); i++)
            bee.add(new Bee(refLink, points.get(i).x-200, points.get(i).y));
    }
    /*! \fn public  void Update()
        \brief Actualizarea hartii in functie de evenimente (un copac a fost taiat)
     */
    public void Update() {
        /// Update pentru fiecare inamic
        for(int i = 0; i<bee.size(); i++)
            bee.get(i).Update();
    }
    /*! \fn public void Draw(Graphics g)
        \brief Functia de desenare a hartii.

        \param g Contextul grafic in care se realizeaza desenarea.
     */
    public void Draw(Graphics g) {
        /// Se deseneaza BackGround-ul
        for(int i = 0; i < 1500*5; i+=1500) {
            //Tile.backTile.DrawBackGround(g, i, 0);
            g.drawImage(image[0], i, 0, 1500, 700, null);
            g.drawImage(image[1], i, 0, 1500, 700, null);
            g.drawImage(image[2], i, 0, 1500, 700, null);
        }
        /// Se parcurge matricea de dale (codurile aferente) si se deseneaza harta respectiva
        /// Si se contureaza toate elementele in patrate pentru debug
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //g.setColor(Color.WHITE);
                GetTile(x, y).Draw(g, x * Tile.TILE_HEIGHT, y * Tile.TILE_WIDTH);
                //g.drawRect(x*Tile.TILE_HEIGHT, y*Tile.TILE_WIDTH, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
                //if(map[y][x] == 3)
                //    g.setColor(Color.RED);
                //    g.drawRect(x*Tile.TILE_HEIGHT, y*Tile.TILE_WIDTH, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
            }
        }
        //g.setColor(Color.GREEN);
        //for (Rectangle rect:solid
        //     ) {
        //    g.drawRect(rect.y, rect.x, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        //}
        //g.setColor(Color.BLUE);
        //for (Rectangle rect:points) {
        //    Tile.honeyTile.Draw(g, rect.x, rect.y);
        //    g.drawRect(rect.x, rect.y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        //}
        // se deseneaza bonusurile - borcanele cu miere
        for (Rectangle rect:points) {
            Tile.honeyTile.Draw(g, rect.x, rect.y);
        }
        /// Se deseneaza inamicii
        for(int i=0; i<bee.size(); i++)
            bee.get(i).Draw(g);
    }
    /*! \fn public Tile GetTile(int x, int y)
       \brief Intoarce o referinta catre dala aferenta codului din matrice de dale.

       In situatia in care dala nu este gasita datorita unei erori ce tine de cod dala, coordonate gresite etc se
       intoarce o dala predefinita (ex. grassTile, mountainTile)
    */
    public Tile GetTile(int x, int y) {
        if(x < 0 || y < 0 || x >= width || y >= height)
        {
            return Tile.transparent;
        }
        Tile t = Tile.tiles[tiles[x][y]];
        if(t == null)
        {
            return Tile.transparent;
        }
        return t;
    }
    /*! \fn private void LoadWorld()
        \brief Functie de incarcare a hartii jocului.
        Aici se poate genera sau incarca din fisier harta. Momentan este incarcata static.
     */
    private void LoadWorld() {
        ///Se construieste matricea de coduri de dale
        tiles = new int[width][height];
        //Se incarca matricea cu coduri
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if(map!=null)
                    tiles[y][x] = map[x][y];
            }
        }
    }
}
