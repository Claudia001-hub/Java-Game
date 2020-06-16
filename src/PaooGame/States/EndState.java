package PaooGame.States;

import PaooGame.RefLinks;

import java.awt.*;
import java.io.IOException;

/*! \class public class EndState extends State
    \brief Implementeaza notiunea de sfarsit de joc
 */
public class EndState extends State{
    /*! \fn public EndState(RefLinks refLink)
       \brief Constructorul de initializare al clasei.

       \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
    */
    private int score;
    private int life;
    public EndState(RefLinks refLink, int score, int life) {
        ///Apel al constructorului clasei de baza.
        super(refLink);
        this.score = score; //initializare scor la sfarsitul jocului
        this.life = life; //initializare viata la sfarsitul jocului
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniu about.
     */
    @Override
    public void Update() throws IOException {
        if(refLink.GetKeyManager().exit) {
            System.exit(0);
            refLink.GetGame().StopGame();
        }
        if(refLink.GetKeyManager().back)
            State.SetState(refLink.GetGame().getMenuState());
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniu about.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */

    public void Draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0, 800, 600);
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman", Font.BOLD, 50));
        g.drawString("END GAME ", 250, 300);
        g.drawString("SCORE :  "+score, 260, 350);
        if(life>=0) {
            g.setFont(new Font("Times New Roman", Font.BOLD, 70));
            g.drawString("YOU WIN!!!", 200, 400);
        }
    }
}
