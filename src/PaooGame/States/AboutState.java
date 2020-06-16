package PaooGame.States;

import PaooGame.Game;
import PaooGame.RefLinks;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/*! \class public class AboutState extends State
    \brief Implementeaza notiunea de credentiale (about)
 */
public class AboutState extends State {
    /*! \fn public AboutState(RefLinks refLink)
       \brief Constructorul de initializare al clasei.

       \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
    */
    public AboutState(RefLinks refLink) {
        ///Apel al constructorului clasei de baza.
        super(refLink);
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniu about.
     */
    @Override
    public void Update() throws SQLException, IOException, UnsupportedAudioFileException {
        if(refLink.GetKeyManager().back)
            State.SetState(refLink.GetGame().getMenuState());
        if(refLink.GetKeyManager().enter) {
            refLink.GetGame().getPlayState(refLink.GetGame().getReloadFlag(), refLink.GetGame().getMusicFlag());
            State.SetState(refLink.GetGame().getPlayState(refLink.GetGame().getReloadFlag(), refLink.GetGame().getMusicFlag()));
        }
    }
    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniu about.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,800, 600);
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g.drawString("GAME STORY - Teddy", 200, 100);
        g.setFont(new Font("Times New Roman", Font.BOLD, 15));

        g.drawString("Actiunea jocului de desfasoara intr-o zona de dealuri, la poalele muntelui, unde, ursuletul Teddy,", 100, 150);
        g.drawString("flamand, iesit din hibernare, incearca sa adune cat mai multe borcanele cu miere de albine.",50, 170);
        g.drawString("El stie ca aici e locul unde albinutele isi depozitiaza mierea.", 50, 200);
        g.drawString("Aceasta este mancarea lui preferata inca de cand era un mic si mama lui, ursoaica ii aducea miere de albine ", 50, 220);
        g.drawString("la desert.", 50, 240);
        g.drawString("Deseori o manca cu zmeura, mure, afine sau merisoare. ", 50, 260);
        g.drawString("Pentru ca el si fratii lui imparteau mereu mancarea, acum vrea cat mai multa miere doar pentru el.", 50, 280);
        g.drawString("Drumul catre aceste borcanele e plin de pericole: gropi si albinute paznic care au rolul de a pazi atent", 50, 300);
        g.drawString("borcanelele cu miere de albine.", 50, 320);
        g.drawString("Daca va reusi sa se fereasca de obstacole, ursuletul  va avea miere in camara lui pentru mult timp.", 50, 340);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Vrei sa il ajuti sa culeaga borcanase cu miere?", 100, 450);
        g.drawString( "PRESS ENTER", 300, 500);
    }
}
