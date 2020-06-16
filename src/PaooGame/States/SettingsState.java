package PaooGame.States;

import PaooGame.RefLinks;

import java.awt.*;

/*! \class public class SettingsState extends State
    \brief Implementeaza notiunea de settings pentru joc.

    Aici setarile vor trebui salvate/incarcate intr-un/dintr-un fisier/baza de date sqlite.
 */
public class SettingsState extends State {
    /*! \fn public SettingsState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public SettingsState(RefLinks refLink) {
        ///Apel al construcotrului clasei de baza.
        super(refLink);
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea setarilor.
     */
    @Override
    public void Update() {
        if(refLink.GetKeyManager().back)
            State.SetState(refLink.GetGame().getMenuState());
        if(refLink.GetMouseInput().x>100 && refLink.GetMouseInput().x<700) {
            if (refLink.GetMouseInput().y > 200 && refLink.GetMouseInput().y < 300) {
                refLink.GetGame().setMusicFlag(true);
                refLink.GetGame().getDataBase().addSettings(1);
            }
            if (refLink.GetMouseInput().y > 300 && refLink.GetMouseInput().y < 400) {
                refLink.GetGame().setMusicFlag(false);
                refLink.GetGame().getDataBase().addSettings(0);
            }
        }

    }
    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran setarile.

        \param g Contextul grafic in care trebuie sa deseneze starea setarilor pe ecran.
     */
    @Override
    public void Draw(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRect(0,0,800,600);

        g.setColor(Color.BLACK);
        g.drawRect(100, 200, 600, 100);
        g.drawRect(100, 300, 600, 100);

        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman", Font.BOLD, 50));
        g.drawString("SETTINGS", 300, 100);
        g.drawString("WITH SOUND", 230, 260);
        g.drawString("WITHOUT SOUND", 200, 360);
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g.drawString("BACK TO MENIU = BACKSPACE",250, 500);
    }
}
