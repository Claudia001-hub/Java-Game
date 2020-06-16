package PaooGame.Items;

import PaooGame.Camera.Camera;
import PaooGame.Graphics.Assets;
import PaooGame.Maps.Map;
import PaooGame.RefLinks;
import PaooGame.States.PlayState;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        dreptunghiurile de coliziune
 */

public class Hero extends Character {
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/

    private float gravity = 10.0f;  /*!< Gravitatia (viteza cu care eroul aterizeaza pe sol.*/
    private boolean falling = true; /*!< Flag pentru cadere - true daca personajul e pe sol; false - daca personajul e in cadere.*/
    private boolean jumping = false; /*!< Flag pentru sarire - true daca personajul e in salt; false - daca personajul e pe sol.*/
    public int hero_level;  /*!<auxiliar pentru level */
    public int score;   /*!<auxiliar pentru score */
    public boolean flag = false;    /*!<  Flag care verifica daca eroul a ajuns la stegulet.*/
    public int life;    /*<! Retine viata eroului.*/
    private int moved; /*<! Numarul de pixeli parcursi de erou - pentru animatie.*/
    private int speed; /*<! Viteza personajului. */

    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Hero(RefLinks refLink, float x, float y, boolean reload) throws SQLException {
        ///Apel al constructorului clasei de baza
        super(refLink, x, y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);
        ///Seteaza imaginea de start a eroului
        image = Assets.player[0][0];
        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.x = 16;
        normalBounds.y = 16;
        normalBounds.width = 16;
        normalBounds.height = 32;
        /// initializarea nivelului la care se afla personajul
        if(reload) {
            /// se citeste din baza de date
            hero_level = refLink.GetGame().getDataBase().getLevel();
        }
        else
            hero_level = 0;
        /// initializarea  nivelul vietii personajului
        if(reload) {
            /// se citeste din baza de date
            life = refLink.GetGame().getDataBase().getLife();
        }
        else
            life = 3;
        /// se initializeaza numarul de pixeli parcursi de personaj
        moved = 0;
        /// se initializeza viteza personajului
        speed = 3;
    }
    /*! \fn public void Update(Map map)
        \brief Actualizeaza pozitia si imaginea eroului.
        \verifica coleziunile
        \param map Harta in functie de care se fac actualizarile
     */
    public void Update(Map map) {
        /// Actualizeaza pozitia personajului
        Move();
        /// Verifica daca a fost apasata o tasta
        GetInput();
        /// Actualizeaza imaginea/animatia
        Animation();
        /// Pozitioneaza personajul pe sol
        Gravity();
        /// Verifica coleziunile
        CollisionF(map.solid, map.points, map.bee);
    }
    int i = 0;  /* index pentru gasirea imaginii in vectorul de imagini pentru player*/

    /*! \fn private void Animation()
        \brief Realizeaza animatia personajului pentru mers si salt.
     */
    private void Animation() {
        /// se alege imaginea pentru salt
        if(jumping) {
            image = Assets.player[2][1];
        }
        else
        {
            /// se alege imaginea pentru mers
            if (GetXMove() > 0) {
                image = Assets.player[i][0];
                i = (int) (moved / 5) % 4;
                moved += 1;
            }
            /// se alege imaginea pentru stationare
            else
                image = Assets.player[0][0];
        }
    }
    /*  \fn private void Gravity()
        \brief Functia care da efect de gravitatie jocului
            face ca personajul sa stea mereu pe sol
     */
    private void Gravity() {
        /// functia se activeaza atunci cand unul din flag-urile
        ///     falling sau jumping sunt true
        if (falling || jumping) {
            /// seteaza viteza eroului pe axa y
            SetYMove(gravity + 7*speed * GetYMove());
            /// incetineste eroul cand se apropie de sol pentru un efect vizual frumos
            if (GetYMove() > MAX_SPEED) {
                SetYMove(MAX_SPEED);
            }
        }
    }

    /*  \fn private void CollisionF(ArrayList<Rectangle> solid, Map map, ArrayList<Bee> bee)
        \brief Functia care verifica coleziunile din joc

        \param solid Vectorul de dale iarba
        \param points Vectorul de recompense
        \param bee Vectorul de inamici
     */
    private void CollisionF(ArrayList<Rectangle> solid, ArrayList<Rectangle> points, ArrayList<Bee> bee) {
        /// Se verifica coleziunea cu dalele iarba
            for (int i = 0; i < solid.size(); i++) {
                Rectangle aux = solid.get(i);
                    if (getBoundsDown().intersects(new Rectangle(aux.y, aux.x, Tile.TILE_WIDTH, Tile.TILE_HEIGHT))) {
                        y = (int) GetY()-5;
                        SetYMove(0);
                        falling = false;
                        jumping = false;
                    } else
                        falling = true;
                if (getBoundsTop().intersects(new Rectangle(aux.y, aux.x, Tile.TILE_WIDTH, Tile.TILE_HEIGHT))) {
                        y = (int) GetY()+5;
                        SetYMove(0);
                        falling = false;
                        jumping = false;
                } else
                    falling = true;
                if (getBoundsRight().intersects(new Rectangle(aux.y, aux.x, Tile.TILE_WIDTH, Tile.TILE_HEIGHT))) {
                        x = (int)GetX()-5;
                        SetXMove(0);
                    }
                if (getBoundsLeft().intersects(new Rectangle(aux.y, aux.x, Tile.TILE_WIDTH, Tile.TILE_HEIGHT))) {
                        x = (int)GetX()+5;
                        SetXMove(0);
                    }
             }
            /// Se verifica coleziunea cu recompensele
        for (int i = 0; i < points.size(); i++) {
            Rectangle aux = points.get(i);
            if (getBoundsDown().intersects(new Rectangle(aux.x, aux.y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT)) ||
                    getBoundsRight().intersects(new Rectangle(aux.x, aux.y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT)) ||
                    getBoundsTop().intersects(new Rectangle(aux.x, aux.y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT))) {
                score+=1;
                points.remove(points.get(i));
            }
        }
            if(getBoundsDown().intersects(new Rectangle(94*Tile.TILE_WIDTH, 10*Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT))) {
                flag = true;
            }

            /// Se verifica coleziunea cu inamicii
            for(int i=0; i<bee.size(); i++)
        if (getBoundsRight().intersects(bee.get(i).getBounds()) || getBoundsDown().intersects(bee.get(i).getBounds()) || getBoundsLeft().intersects(bee.get(i).getBounds()) || getBoundsTop().intersects(bee.get(i).getBounds())) {
            bee.remove(i);
            life--;
        }
    }
    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */
    public void GetInput() {
        ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        SetXMove(0);
        SetYMove(0);
        ///Verificare apasare tasta "dreapta"
        if(refLink.GetKeyManager().right)
        {
            SetXMove(speed);
        }
        ///Verificare apasare tasta "stanga"
        if(refLink.GetKeyManager().left)
        {
            SetXMove(-speed);
        }
        ///Verificare apasare tasta "space"
        if(refLink.GetKeyManager().jump && jumping == false)
        {
            jumping = true;
            SetYMove(-5*speed);
        }
    }

    ///
    @Override
    public void Update() {
    }

    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafic in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g) {
        g.drawImage(image, (int)x, (int)y, width, height, null);
        /// urmatoarele linii de cod sunt pentru debug
        //g.setColor(Color.red);
        //Graphics2D g2d = (Graphics2D) g;
        //g2d.draw(getBoundsDown());
        //g2d.draw(getBoundsRight());
        //g2d.draw(getBoundsLeft());
        //g2d.draw(getBoundsTop());
    }

    /// Pozitia pe axa x a caracterului
    public float GetX() {
        return x;
    }
    /// Pozitia pe axa y a caracterului
    public float GetY() {
        return y;
    }

    /// Dreptungiul de coleziune din partea de jos a eroului
    public Rectangle getBoundsDown() {
        return new Rectangle((int)(x+width/2-width/4), (int)(y+height/2)+10, (int)width/2, (int)height/2-20);
    }
    /// Dreptungiul de coleziune din partea dreapa a eroului
    public Rectangle getBoundsRight() {
        return new Rectangle((int)(x+width-width/4), (int)y+10, (int)width/4, (int)height-30);
    }
    /// Dreptungiul de coleziune din partea stanga a eroului
    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x, (int)y+10, (int)width/4, (int)height-30);
    }
    /// Dreptungiul de coleziune din partea de sus a eroului
    public Rectangle getBoundsTop() {
        return new Rectangle((int)(x+width/2-width/4), (int)y-10, (int)width/2, (int)height/2-20);
    }
}
