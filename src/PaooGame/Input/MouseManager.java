package PaooGame.Input;

import PaooGame.Game;
import PaooGame.States.State;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/* \class public class MouseManager implements MouseListener
    \brief Gestioneaza intrarea (input-ul) de la mouse.

    Clasa citeste daca a fost actionat mouse-ul si verifica
    coordonatele punctului unde a fost facut click-ul.
 */
public class MouseManager implements MouseListener {
    public int x;   /*!< x e coordonata pe axa x din fereastra*/
    public int y;   /*!< y e coordonata pe axa y din fereastra*/

    /*! \fn public void mouseClicked(KeyEvent e)

       Momentan aceasta functie nu este utila in program.
    */
    public void mouseClicked(MouseEvent e) {
    }

    /*! \fn public void mousePressed(MouseEvent e)
       \brief Functie ce va fi apelata atunci cand un un eveniment de mouse apasat este generat.

        \param e obiectul eveniment de mouse.
    */
    public void mousePressed(MouseEvent e) {
        /// Se retin coordonatele punctului in care a fost actionat mouse-ul
        x = e.getX();
        y = e.getY();
    }
    /*! \fn public void mousePressed(MouseEvent e)
       \brief Functie ce va fi apelata atunci cand un un eveniment de mouse eliberat este generat.

        \param e obiectul eveniment de mouse.
    */
    public void mouseReleased(MouseEvent e) {
        /// Se retine ca mouse-ul a fost eliberat
        x = 0;
        y = 0;
    }
    /*! \fn public void mouseClicked(KeyEvent e)

      Momentan aceasta functie nu este utila in program.
   */
    public void mouseEntered(MouseEvent e) {
    }

    /*! \fn public void mouseClicked(KeyEvent e)

      Momentan aceasta functie nu este utila in program.
   */
    public void mouseExited(MouseEvent e) {

    }

}
