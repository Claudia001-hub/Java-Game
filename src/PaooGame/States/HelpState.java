package PaooGame.States;

import PaooGame.RefLinks;

import java.awt.*;

/*! \class public class AboutState extends State
    \brief Implementeaza notiunea de instructiuni (help)
 */
public class HelpState extends State {
    /*! \fn public HelpState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public HelpState(RefLinks refLink) {
        ///Apel al constructorului clasei de baza.
        super(refLink);
    }
    /*! \fn public void Update()
       \brief Actualizeaza starea curenta a meniu about.
    */
    @Override
    public void Update() {
        if(refLink.GetKeyManager().back)
            State.SetState(refLink.GetGame().getMenuState());
    }
    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniu about.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,800,600);
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman", Font.BOLD, 50));
        g.drawString("HELP", 300, 100);
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g.drawString("RIGHT -> Right Key", 100, 200);
        g.drawString("LEFT -> Left Key", 100, 250);
        g.drawString("Jump -> SPACE", 100, 300);
        g.drawString("Back to meniu -> Backspace", 100, 350);
        g.drawString("End game ->Esc", 100, 400);
        g.drawString("Help -> H", 100, 450);
    }
}
