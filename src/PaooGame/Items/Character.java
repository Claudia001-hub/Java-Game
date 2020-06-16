package PaooGame.Items;

import PaooGame.RefLinks;

import java.awt.*;

public abstract class Character extends Item {

    public static final float DEFAULT_SPEED = 3.0f; /*!< Viteza implicita a caracterului.*/
    public static final float MAX_SPEED = 5.0f; /*!< Viteaza maxima a caracterului.*/
    public static final int DEFAULT_CREATURE_WIDTH = 100;   /*!< Latimea implicita a imaginii caracterului.*/
    public static final int DEFAULT_CREATURE_HEIGHT = 100;  /*!< Inaltimea implicita a imaginii caracterului.*/

    private float xMove;    /*!< Retine pozitia caracterului pe axa X.*/
    private float yMove;    /*!< Retine pozitia caracterului pe axa Y.*/

    /*! \fn public Character(RefLinks refLink, float x, float y, int width, int height)
        \brief Constructor de initializare al clasei Character

        \param refLink Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
        \param x Pozitia de start pa axa X a caracterului.
        \param y Pozitia de start pa axa Y a caracterului.
        \param width Latimea imaginii caracterului.
        \param height Inaltimea imaginii caracterului.
     */
    public Character (RefLinks refLink, float x, float y, int width, int height) {
        ///Apel constructor la clasei de baza
        super(refLink, x, y, width, height);
        ///Seteaza pe valorile implicite pentru viata, viteza si distantele de deplasare
        //life = DEFAULT_LIFE;
        //speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }
    /*! \fn public void Move()
        \brief Modifica pozitia caracterului
     */
    public void Move() {
        ///Modifica pozitia caracterului pe axa X.
        MoveX();
        ///Modifica pozitia caracterului pe axa Y.
        MoveY();

    }
    /*! \fn public void MoveX()
        \brief Modifica pozitia caracterului pe axa X.
     */
    public void MoveX() {
        ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa X.
        x += xMove;
    }
    /*! \fn public void MoveY()
        \brief Modifica pozitia caracterului pe axa Y.
     */
    public void MoveY() {
        ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa Y.
        y += yMove;
    }
    /*! \fn public int GetLife()
        \brief Returneaza viata caracterului.
     */
    //public int GetLife() { return life; }
     /*! \fn public int GetSpeed()
        \brief Returneaza viteza caracterului.
     */
    //public float GetSpeed() { return speed; }
    /*! \fn public void SetLife(int life)
        \brief Seteaza viata caracterului.
     */
    //public void SetLife(int life) { this.life = life; }
    /*! \fn public void SetSpeed(float speed)
        \brief
     */
    //public void SetSpeed (float speed) { this.speed = speed; }
    /*! \fn public float GetXMove()
        \brief Returneaza distanta in pixeli pe axa X cu care este actualizata pozitia caracterului.
     */
    public float GetXMove() { return xMove; }
    /*! \fn public float GetYMove()
        \brief Returneaza distanta in pixeli pe axa Y cu care este actualizata pozitia caracterului.
     */
    public float GetYMove() { return yMove; }
    /*! \fn public void SetXMove(float xMove)
        \brief Seteaza distanta in pixeli pe axa X cu care va fi actualizata pozitia caracterului.
     */
    public void SetXMove(float xMove) { this.xMove = xMove; }
    /*! \fn public void SetYMove(float yMove)
        \brief Seteaza distanta in pixeli pe axa Y cu care va fi actualizata pozitia caracterului.
     */
    public void SetYMove(float yMove) { this.yMove = yMove; }

    public abstract void Update();

    public abstract void Draw(Graphics g);
}
