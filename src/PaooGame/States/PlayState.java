package PaooGame.States;

import PaooGame.Camera.Camera;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Items.Hero;
import PaooGame.Maps.Map;
import PaooGame.Music.Music;
import PaooGame.RefLinks;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State {
    private static int score;   /*!< Referinta catre scorul jocului */
    private static int life; /*!< Referinta catre viata playerului */
    private Hero hero;  /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/
    private Map map;    /*!< Referinta catre harta curenta.*/
    private Camera camera;  /*!< Referinta catre camera jocului. */
    public int level; /*!< Level-ul */
    public boolean reload;  /*!< Flag pentru metoda de redare a jocului - joc nou sau reluarea jocului anterior*/
    private Music music; /*!< Muzica jocului */
    public boolean musicFlag; /*!< Flag pentru redare cu muzica sau fara */
    /*! \fn public PlayState(RefLinks refLink)
                \brief Constructorul de initializare al clasei

                \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
                \param reload Flag pentru modul de redare al jocului
                \param musicFlag pentru redarea jocului cu muzica sau fara
             */
    public  PlayState (RefLinks refLink, boolean reload, boolean musicFlag) throws SQLException, IOException, UnsupportedAudioFileException {
        ///Apel al constructorului clasei de baza
        super(refLink);
        /// Initializare reload - se stabileste modul de redare al jocului
        this.reload = reload;
        /// Instantiaza music - se adauga muzica
        music = new Music("/music/Music.wav");
        this.musicFlag = musicFlag;

        /// Se seteaza nivelul de inceput al jocului
        if(reload) {
            /// Se citeste din baza de date
            level = refLink.GetGame().getDataBase().getLevel();
        }
        else
            level = 1;
        ///Construieste harta jocului
        map = new Map(refLink, level);
        ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(map);
        ///Construieste eroul
        hero = new Hero(refLink, 50, 300, reload);
        /// Construieste camera jocului
        camera = new Camera(0,0);
        /// In functie de tipul de rulare - start sau reload
        /// Se seteaza scorul initial
        if(reload) {
            /// Se citeste din baza de date
            score = refLink.GetGame().getDataBase().getScore();
        }
        else
            score = hero.score;
        /// Se seteaza viata initiala
        if(reload) {
            /// Se citeste din baza de date
            life = refLink.GetGame().getDataBase().getLife();
        }
        else
            life = hero.life;
    }
    /* \fn public static int getScore()
       \brief Returneaza scorul
     */
    public static int getScore() {
        return score;
    }
    /* \fn public static int getLife()
       \brief Returneaza viata
     */
    public static int getLife() {
        return life;
    }
    /*! \fn public void Update()
       \brief Actualizeaza starea curenta a jocului.
    */

    public void Update() throws SQLException, IOException {
        /// Conditie de revenire la meniu si salvare date
        if(refLink.GetKeyManager().back) {
            refLink.GetGame().getDataBase().add(score + hero.score, life, level);
            State.SetState(refLink.GetGame().getMenuState());
        }
        /// Conditie de oprire a jocului si salvare date
        if(refLink.GetKeyManager().exit) {
            refLink.GetGame().getDataBase().add(score+hero.score, life, level);
            System.exit(0);
        }
        /// Conditie de intrare in meniul Help
        if(refLink.GetKeyManager().help) {
            State.SetState(refLink.GetGame().getHelpState());
        }
        if(hero.flag == false) {
            map.Update();
            hero.Update(map);
            life = hero.life;
            camera.Update(hero.GetX(), hero.GetY());
            map.Update();
        }
            /// Actualizarea jocului - erou, viata, scor, nivel, sfarsitul jocului
        if(hero.flag == true || hero.GetY() > 600) {
            if(hero.GetY()>600)
                life-=1;
            hero.flag = false;
            level += 1;
            if(hero.score!=0)
                score += hero.score;
            map = new Map(refLink, level);
            refLink.SetMap(map);
            hero = new Hero(refLink, 50, 300, false);
            hero.life = life;
            camera = new Camera(0, 0);
        }
        if(life < 0)
        {
            State.SetState(refLink.GetGame().getEndState(score, life));
        }
        if(musicFlag == true && reload==false) {
            /// reda background-ul muzical al jocului
            music.PlayMusic();
            musicFlag = false;
        }

    }
    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    public void Draw(Graphics g) {
        // Desenarea cu ajutorul camerei
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(camera.getX()-350, camera.getY());
        map.Draw(g2d);
        hero.Draw(g2d);
        /// Afisarea testului pe ecran: Scor + viata
        g.setFont(new Font("Times New Roman", Font.BOLD, 25));
        g.drawString("Score:  " + (score+hero.score), -(int)camera.getX()+1000, 50);
        g.drawString("Lives:  "+life, -(int)camera.getX()+1000, 100);
    }
}