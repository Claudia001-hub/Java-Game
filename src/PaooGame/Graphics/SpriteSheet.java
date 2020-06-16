package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class SpriteSheet
    \brief Clasa retine o referinta catre o imagine formata din dale (sprite sheet)

    Metoda crop() returneaza o dala de dimensiunile precizate (o subimagine) din sprite sheet
    de la adresa (x * latimeDala, y * inaltimeDala)

    Metode cropPlayer() este o metda asemanatoare cu metoda crop() dar returneaza un tablou
    bidimensional

    Metode cropBee() este o metda asemanatoare cu metoda crop() dar returneaza un tablou
    unidimensional
 */

public class SpriteSheet {
    private BufferedImage spriteSheet;  /*!< Referinta catre obiectul BufferdImage ce contine sprite-sheetul.*/

    /*! \fn public SpriteSheet(BufferedImage sheet)
        \brief Constructor, initializeaza spriteSheet.

        \param buffImg Un obiect BufferedImage valid.
     */
    public SpriteSheet(BufferedImage spriteSheet) {
        /// Retine referinta catre BufferedImage object.
        this.spriteSheet = spriteSheet;
    }
    /*! \fn public BufferedImage crop(int x, int y, int widht, int height)
        \brief Returneaza un obiect BufferedImage ce contine o subimage (dala).

        Subimaginea este localizata avand ca referinta punctul din stanga sus.

        \param x numarul dalei din sprite sheet pe axa x.
        \param y numarul dalei din sprite sheet pe axa y.
     */
    public BufferedImage crop(int x, int y, int width, int height) {
        /// Subimaginea (dala) este regasita in sprite sheet specificad coltul stanga sus
        /// al imaginii si apoi latimea si inaltimea (totul in pixeli). Coltul din stanga sus al imaginii
        /// se obtine inmultind numarul de ordine al dalei cu dimensiunea in pixeli a unei dale.
        return spriteSheet.getSubimage(x*width, y*height, width, height);
    }
     /*! \fn public BufferedImage cropPlayer(int x, int y, int widht, int height)
          \brief Returneaza un tablou bidimensional de obiecte BufferedImage ce contin o subimage (dala).
    */
    public BufferedImage[][] cropPlayer(int x, int y) {
        BufferedImage[][] p = new BufferedImage[4][2];
        p[0][0] = spriteSheet.getSubimage(x*65, y*65, 65, 65);
        p[1][0] = spriteSheet.getSubimage(1*65, 0, 65, 65);
        p[3][0] = spriteSheet.getSubimage(2*65+5, 0, 65, 65);
        p[2][0] = p[0][0];
        p[0][1] = spriteSheet.getSubimage(0*65, 1*65+30, 65, 65);
        p[1][1] = spriteSheet.getSubimage(1*65, 1*65, 70, 85);
        p[2][1] = spriteSheet.getSubimage(2*65+90, 1*65+20, 65, 65);
        p[3][1] = spriteSheet.getSubimage(3*65+78, 1*65+20, 65, 65);
        return p;
    }
    /*! \fn public BufferedImage cropPlayer(int x, int y, int widht, int height)
          \brief Returneaza un tablou unidimensional de obiecte BufferedImage ce contin o subimage (dala).
    */
    public BufferedImage[] cropBee(int x, int y) {
        BufferedImage[] b = new BufferedImage[5];
        ImageLoader loader = new ImageLoader();
        b[0] = loader.LoadImage("/textures/Bee_0000_Capa-1.png");
        b[1] = loader.LoadImage("/textures/Bee_0001_Capa-2.png");
        b[2] = loader.LoadImage("/textures/Bee_0002_Capa-3.png");
        b[3] = loader.LoadImage("/textures/Bee_0003_Capa-4.png");
        b[4] = loader.LoadImage("/textures/Bee_0004_Capa-5.png");
        return b;
    }
}
