package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.Maps.Map;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class public class Bee extends Character
    \brief Implementeaza notiunea de inamic(albina) al eroului

 Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea
        deplasarea
        dreptunghiul de coliziune
 */
public class Bee extends Character {
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a inamicului.*/
    float tempx, tempy;  /*!< auxiliare pentru pozitiile inamicilor */
    float speed; /*!< Vizeza albinutei*/
    int i; /*!< Index care ajuta la animatie - gasirea imaginilor in vectorul de imagini */
    int moved;  /*!< Numarul de pixeli parcursi*/

    /*! \fn public Bee(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Bee

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a inamicului.
        \param y Pozitia initiala pe axa Y a inamicului.
     */
    public Bee(RefLinks refLink, float x, float y) {
        ///Apel al constructorului clasei de baza
        super(refLink, x, y, 50, 50);
        /// Seteaza imaginea de start a inamicului
        image = Assets.bee[0];
        /// Seteaza pozitile temporare initiale
        tempx = x;
        tempy = y;
        /// Initializare viteza albinute
        speed = 1.0f;
    }
    /*! \fn public void Update()
       \brief Actualizeaza pozitia si imaginea inamicilor.
    */
    public void Update() {
        /// Actualizeaza pozitia in functie de viteza
        MoveX();
        if (x <= tempx + 400)
            SetXMove(speed);
        else
            x = tempx - 200;
        /// Creaza animatia
        image = Assets.bee[i];
        i = (int) (moved / 5) % 5;
        moved += 1;
    }

    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafic in care trebuie efectuata desenarea eroului.
     */
    public void Draw(Graphics g) {
        g.drawImage(image, (int)x, (int)y, width, height, null);
        /// urmatoarele linii de cod sunt pentru debug
        //g.setColor(Color.red);
        //Graphics2D g2d = (Graphics2D) g;
        //g2d.draw(getBounds());
    }

    /// Se creaza dreptunghiul de coleziune
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }
}
