package PaooGame.States;

import PaooGame.Game;
import PaooGame.RefLinks;

import java.awt.*;
/*! \class public class MenuState extends State
    \brief Implementeaza notiunea de menu pentru joc.
 */
public class MenuState extends State {
    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public MenuState (RefLinks refLink) {
        ///Apel al constructorului clasei de baza.
        super(refLink);
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    public void Update() {
        if(refLink.GetMouseInput().x>200 && refLink.GetMouseInput().x<600) {
            if(refLink.GetMouseInput().y >130 && refLink.GetMouseInput().y<200){
                refLink.GetGame().setReloadFlag(false);
                State.SetState(refLink.GetGame().getAboutState());
            }
            if(refLink.GetMouseInput().y > 200 && refLink.GetMouseInput().y<270) {
                refLink.GetGame().setReloadFlag(true);
                State.SetState(refLink.GetGame().getAboutState());
            }
            if(refLink.GetMouseInput().y > 270 && refLink.GetMouseInput().y <340)
                State.SetState(refLink.GetGame().getEndState(PlayState.getScore(), PlayState.getLife()));
            if(refLink.GetMouseInput().y > 340 && refLink.GetMouseInput().y <410)
                State.SetState(refLink.GetGame().getHelpState());
            if(refLink.GetMouseInput().y > 410 && refLink.GetMouseInput().y <480)
                State.SetState(refLink.GetGame().getSettingsState());
        }

        if(refLink.GetKeyManager().help)
            State.SetState((refLink.GetGame().getHelpState()));
        if(refLink.GetKeyManager().exit) {
            System.exit(0);
        }
    }
    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    public void Draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,800, 600);
        g.setColor(Color.BLACK);
        g.drawRect(200,130,400,70);
        g.drawRect(200, 200, 400, 70);
        g.drawRect(200, 270, 400, 70);
        g.drawRect(200, 340, 400, 70);
        g.drawRect(200, 410, 400, 70);
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman", Font.BOLD, 50));
        g.drawString("MENU", 310, 100);
        g.drawString("START", 310, 190);
        g.drawString("RELOAD", 280, 260);
        g.drawString("END", 330, 330);
        g.drawString("HELP", 320, 400);
        g.drawString("SETTINGS", 280, 470);

        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g.drawString("END = ESC     HELP = H", 300, 580);
    }
}
