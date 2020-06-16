package PaooGame.Graphics;

import java.awt.image.BufferedImage;
/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets {
    /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage grass;
    public static BufferedImage backGround;
    public static BufferedImage[][] player;
    public static BufferedImage transparent;
    public static BufferedImage flag;
    public static BufferedImage honey;
    public static BufferedImage[] BackGround;
    public static BufferedImage[] bee;
    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
        /// Se creaza un obiect temporat ImageLoader care ajuta la incarcarea imaginilor pentru dale
        ImageLoader loader = new ImageLoader();
        /// Se incarca imaginile corespunzatoare elementelor necesare
        backGround = loader.LoadImage("/textures/Full-background.png");
        grass = loader.LoadImage("/textures/Tile.png");
        honey = loader.LoadImage("/textures/honey3.png");
        flag = loader.LoadImage("/textures/flag.png");
        /// Se creaza un tablou de BufferedImage pentru a incarca fundalul
        BackGround = new BufferedImage[3];
        /// Se incarca imaginile corespunzatoare elementelor necesare
        BackGround[0] = loader.LoadImage("/textures/layer-1.png");
        BackGround[1] = loader.LoadImage("/textures/layer-2.png");
        BackGround[2] = loader.LoadImage("/textures/layer-3.png");
        /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet s = new SpriteSheet(ImageLoader.LoadImage("/textures/miks2.png"));
        /// Se obtin imaginile corespunzatoare elementelor necesare
        player = s.cropPlayer(0,0);
        transparent = s.crop(0, 0, 10, 10);
        /// Se foloseste acelasi obiect SpriteSheet auxiliar pentru a incarca
        /// alta imagine
        s = new SpriteSheet(ImageLoader.LoadImage("/textures/Bee_0000_Capa-1.png"));
        bee = s.cropBee(0,0);
    }
}
