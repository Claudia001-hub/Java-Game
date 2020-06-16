package PaooGame.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*! \class public class KeyManager implements KeyListener
    \brief Gestioneaza intrarea (input-ul) de tastatura.

    Clasa citeste daca au fost apasata o tasta, stabiliteste ce tasta a fost actionata si seteaza corespunzator un flag.
    In program trebuie sa se tina cont de flagul aferent tastei de interes. Daca flagul respectiv este true inseamna
    ca tasta respectiva a fost apasata si false nu a fost apasata.
 */

public class KeyManager implements KeyListener {

    private boolean[] keys; /*!< Vector de flaguri pentru toate tastele. Tastele vor fi regasite dupa cod [0 - 255]*/
    public boolean right;   /*!< Flag pentru tasta "dreapta" apasata.*/
    public boolean left;   /*!< Flag pentru tasta "stanga" apasata.*/
    public boolean jump;    /*!< Flag pentru tasta "space" apasata*/
    public boolean enter;   /*!< Flag pentru tasta "enter" apasata.*/
    public boolean exit;    /*!< Flag pentru tasta "escape" apasata.*/
    public boolean back;    /*!< Flag pentru tasta "backspace" apasata.*/
    public boolean help;    /*< Flag pentru tasta "H" apasata.*/

    /*! \fn public KeyManager()
        \brief Constructorul clasei.
     */
    public KeyManager() {
        ///Creare vector de flaguri aferente tastelor.
        keys = new boolean[256];
    }

    /*  \fn public void Update()
        \brief Functia seteaza variabilele in functie de tasta apasata.
        \KeyEvent e evenimentul de tastatura.
    */
    public void Update() {
        right = keys[KeyEvent.VK_RIGHT];
        left = keys[KeyEvent.VK_LEFT];
        jump = keys[KeyEvent.VK_SPACE];
        enter = keys[KeyEvent.VK_ENTER];
        exit = keys[KeyEvent.VK_ESCAPE];
        back = keys[KeyEvent.VK_BACK_SPACE];
        help = keys[KeyEvent.VK_H];
    }

    /*! \fn public void keyPressed(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand un un eveniment de tasta apasata este generat.

         \param e obiectul eveniment de tastatura.
     */
    public void keyPressed(KeyEvent e) {
        /// se retin in vectorul de flaguri ca o tasta a fost apasata.
        keys[e.getKeyCode()] = true;
    }
    /*! \fn public void keyReleased(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand un un eveniment de tasta eliberata este generat.

         \param e obiectul eveniment de tastatura.
     */
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    /*! \fn public void keyTyped(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand o tasta a fost apasata si eliberata.
        Momentan aceasta functie nu este utila in program.
     */
    public void keyTyped(KeyEvent e) {

    }
}
