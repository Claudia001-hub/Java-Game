package PaooGame.Tiles;

import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class Tile
    \brief Retine toate dalele intr-un vector si ofera posibilitatea regasirii dupa un id.
 */
public class Tile {
    private static final int NO_TILES = 50; /*!< Numarul maxim de dale din vector*/
    public static Tile[] tiles = new Tile[NO_TILES];    /*!< Vector de referinte de tipuri de dale.*/

    public static Tile transparent = new PaooGame.Tiles.TransparentTile(0); /*!< Dala de tip transparent */
    public static Tile grassTile = new PaooGame.Tiles.GrassTile(1); /*!< Dala de tip iarba */
    public static Tile backTile = new PaooGame.Tiles.BackTile(2);   /*!< Dala de tip back-ground */
    public static Tile flagTile = new PaooGame.Tiles.FlagTile(3);   /*!< Dala de tip flag */
    public static Tile honeyTile = new PaooGame.Tiles.HoneyTile(4); /*!< Dala de tip miere */

    public static final int TILE_WIDTH  = 50;   /*!< Latimea unei dale.*/
    public static final int TILE_HEIGHT = 50;   /*!< Inaltimea unei dale.*/

    protected BufferedImage img;     /*!< Imaginea aferenta tipului de dala.*/
    protected final int id;     /*!< Id-ul unic aferent tipului de dala.*/

    /*! \fn public Tile()
        \brief Constructorul fara parametri aferent clasei.

     */
    public Tile() {
        id = 0;
    }
    /*! \fn public Tile(BufferedImage texture, int id)
        \brief Constructorul cu parametri aferent clasei.

        \param image Imaginea corespunzatoare dalei.
        \param id Id-ul dalei.
     */
    public Tile(BufferedImage image, int idd) {
        img = image;
        id = idd;

        tiles[id] = this;
    }
    /*! \fn public void Update()
        \brief Actualizeaza proprietatile dalei.
     */
    public void Update() {

    }
    /*! \fn public void Draw(Graphics g, int x, int y)
        \brief Deseneaza in fereastra dala.

        \param g Contextul grafic in care sa se realizeze desenarea
        \param x Coordonata x in cadrul ferestrei unde sa fie desenata dala
        \param y Coordonata y in cadrul ferestrei unde sa fie desenata dala
     */
    public void Draw(Graphics g, int x, int y) {
        /// Desenare dala
        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }
    /*! \fn public void Draw(Graphics g, int x, int y)
        \brief Deseneaza in fereastra fundalul.

        \param g Contextul grafic in care sa se realizeze desenarea
        \param x Coordonata x in cadrul ferestrei unde sa fie desenata dala
        \param y Coordonata y in cadrul ferestrei unde sa fie desenata dala
     */
    public void DrawBackGround(Graphics g, int x, int y) {
        g.drawImage(img, x, y, 1500, 700, null);
    }

    public int GetId() {
        return id;
    }
}
