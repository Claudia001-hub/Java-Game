package PaooGame.Camera;

import PaooGame.Game;
import PaooGame.Items.Hero;

/*! \class public class Camera
    \brief Implementeaza notiunea de camera a jocului
 */
public class Camera {
    private float x, y; /*< Coordonatele camerei*/
    /*  \fn public Camera(float x, float y)
        \brief Constructorul de initializare al clasei.

        \param x Coordonata pe axa X
        \param y Coordonata pe axa Y
     */
    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }
    /*  \fn public void Update(float x)
        \brief Actualizeaz camera in functie de evenimente
     */
    public void Update(float x, float y) {
        this.x = -x + Game.WIDTH/2;
        //this.y = -y + Game.HEIGHT/2;
    }
    /* \fn public float getX()
        \brief Retrneaza parametrul x
     */
    public float getX() {
        return x;
    }
    /* \fn public float getY()
        \brief Retrneaza parametrul y
     */
    public float getY() {
        return y;
    }
    /* \fn public float setX()
        \brief Seteaza parametrul x
     */
    public void setX(float x) {
        this.x = x;
    }
    /* \fn public float setY()
        \brief Retrneaza parametrul y
     */
    public void setY(float y) {
        this.y = y;
    }
}
