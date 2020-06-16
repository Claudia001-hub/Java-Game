package PaooGame;

import PaooGame.DataBase.DataBase;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Input.KeyManager;
import PaooGame.Input.MouseManager;
import PaooGame.States.*;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.sql.SQLException;

/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)

                ------------
                |           |
                |     ------------
    60 times/s  |     |  Update  |  -->{ actualizeaza variabile, stari, pozitii ale elementelor grafice etc.
        =       |     ------------
     16.7 ms    |           |
                |     ------------
                |     |   Draw   |  -->{ deseneaza totul pe ecran
                |     ------------
                |           |
                -------------
    Implementeaza interfata Runnable:

        public interface Runnable {
            public void run();
        }

    Interfata este utilizata pentru a crea un nou fir de executie avand ca argument clasa Game.
    Clasa Game trebuie sa aiba definita metoda "public void run()", metoda ce va fi apelata
    in noul thread(fir de executie). Mai multe explicatii veti primi la curs.

    In mod obisnuit aceasta clasa trebuie sa contina urmatoarele:
        - public Game();            //constructor
        - private void init();      //metoda privata de initializare
        - private void update();    //metoda privata de actualizare a elementelor jocului
        - private void draw();      //metoda privata de desenare a tablei de joc
        - public run();             //metoda publica ce va fi apelata de noul fir de executie
        - public synchronized void start(); //metoda publica de pornire a jocului
        - public synchronized void stop()   //metoda publica de oprire a jocului
 */
public class Game implements Runnable{
    private GameWindow wnd; /*!< Fereastra in care se va desena tabla jocului*/
    private boolean runState;   /*!< Flag - starea firului de executie.*/
    private Thread gameThread;  /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private BufferStrategy bs;  /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/

    public static int WIDTH, HEIGHT; /*!< dimensiunile - latimea si inaltimea ferestrei*/
    /// Sunt cateva tipuri de "complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    /// flickering (palpaire) a ferestrei.
    /// Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at

    ///                         |------------------------------------------------>|
    ///                         |                                                 |
    ///                 ****************          *****************        ***************
    ///                 *              *   Show   *               *        *             *
    /// [ Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///                 *              *          *               *        *             *
    ///                 ****************          *****************        ***************

    private Graphics g; /*!< Referinta catre un context grafic.*/

    private KeyManager keyManager;  /*!< Referinta catre obiectul care gestioneaza intrarile de la tastatura din partea utilizatorului.*/
    private MouseManager mouseManager;  /*!< Referinta catre obiectul care gestioneaza intrarile de la mouse din partea utilizatorului.*/
    private RefLinks refLink;   /*!< Referinta catre un obiect a carui sarcina este doar de a retine diverse referinte pentru a fi usor accesibile.*/
    private DataBase dataBase;  /*!< Referinta catre baza de date.*/

    private boolean reloadFlag = true;  /*!< Flag pentru reluarea jocului*/
    private boolean musicFlag = true; /*!< Flag pentru redare cu muzica */

    private static Game instance = null; /*!< Tnstanta care ajuta la implementarea Dingleton */
    /*! \fn     public static Game getInstance(String title, int width, int height)
        \brief returneaza instanta pentru clasa Game - Singleton :
            constrange numărul de lansări în execuție simultane ale programului.

        \param title Titlul jocului
        \param width Latimea jocului
        \param height Inaltimea jocului
     */
    public static Game getInstance(String title, int width, int height) throws SQLException, IOException, UnsupportedAudioFileException {
        if(instance == null)
            instance = new Game(title, width, height);
        return instance;
    }
    /*! \fn   public static void Reset()
        \brief Da posibilitatea de resetare a jocului - Singleton
     */
    public static void Reset() {
        instance = null;
    }

    /*! \fn public void setReloadFlag(boolean reloadFlag)
        \brief Seteaza reluarea jocului.
     */
    public void setReloadFlag(boolean reloadFlag) {
        this.reloadFlag = reloadFlag;
    }
    /*! \fn public boolean getReloadFlag()
        \brief Returneaza reluarea jocului.
     */
    public boolean getReloadFlag() {
        return reloadFlag;
    }
    /*! \fn public void setMusicFlag(boolean musicFlag)
        \brief Seteaza redarea cu muzica.
     */
    public void setMusicFlag(boolean musicFlag) {
        this.musicFlag = musicFlag;
    }
    /*! \fn public State getAboutState()
        \brief Returneaza state-ul AboutState.
     */
    public State getAboutState() {
        return new AboutState(refLink);
    }
    /*! \fn public State getPlayState(boolean reloadFlag)
        \brief Returneaza state-ul PlayState.

        \param relodFlag Flag-ul pentru modul de redare al jocului, start new game or reload game
        \param musicFlag Flag-ul pentru redarea jocului cu muzica sau fara
     */
    public State getPlayState(boolean reloadFlag, boolean musicFlag) throws SQLException, IOException, UnsupportedAudioFileException {
        return new PlayState(refLink, reloadFlag, musicFlag);
    }
    /*! \fn public State getSettingsState()
        \brief Returneaza state-ul SattingsState.
     */
    public State getSettingsState() {
        return new SettingsState(refLink);
    }
    /*! \fn public State getMenuState()
        \brief Returneaza state-ul MenuState.
     */
    public State getMenuState() {
        return new MenuState(refLink);
    }
    /*! \fn public State getEndState()
        \brief Returneaza state-ul EndState.
     */
    public State getEndState(int score, int life) {
        return new EndState(refLink, score, life);
    }
    /*! \fn public State getHelpState()
        \brief Returneaza state-ul HelpState.
     */
    public State getHelpState() {
        return new HelpState(refLink);
    }
    /*! \fn public DataBase getDataBase()
        \brief Returneaza baza dedate.
     */
    public DataBase getDataBase() {
        return dataBase;
    }
    /*! \fn public Game(String title, int width, int height)
        \brief Constructor de initializare al clasei Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */
    public Game(String title, int width, int height) throws SQLException, IOException, UnsupportedAudioFileException {
        /// Obiectul GameWindow este creat insa fereastra nu este construita
        /// Acest lucru va fi realizat in metoda init() prin apelul
        /// functiei BuildGameWindow();
        wnd = new GameWindow(title, width, height);
        /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
        ///Construirea obiectului de gestiune a evenimentelor de tastatura
        keyManager = new KeyManager();
        ///Construirea obiectului de gestiune a evenimentelor de la mouse
        mouseManager = new MouseManager();
        ///Construirea obiectului de gestiune a bazei de date
        dataBase = new DataBase();
        /// Initializare parametri latime si inaltime
        WIDTH = width;
        HEIGHT = height;


    }
    /*! \fn private void InitGame()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */
    private void InitGame() {
        /// Este construita fereastra grafica.
        wnd.BuildGameWindow();
        ///Sa ataseaza ferestrei managerul de tastatura pentru a primi evenimentele furnizate de fereastra.
        wnd.GetWndFrame().addKeyListener(keyManager);
        ///Sa ataseaza ferestrei canvas-ului (panzei) de mouse pentru a primi evenimentele furnizate de fereastra.
        wnd.GetCanvas().addMouseListener(mouseManager);
        ///Se incarca toate elementele grafice (dale)
        Assets.Init();
        ///Se construieste obiectul de tip shortcut ce va retine o serie de referinte catre elementele importante din program.
        refLink = new RefLinks(this);
        ///Seteaza starea implicita cu care va fi lansat programul in executie
        State.SetState(getMenuState());
    }
    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run() {
        /// Initializeaza obiectul game
        InitGame();
        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;    /*!< Retine timpul curent de executie.*/

        /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
        /// sau mai bine spus de 60 ori pe secunda.
        final int framesPerSecond = 60;  /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame = 1000000000/framesPerSecond;    /*!< Durata unui frame in nanosecunde.*/

        /// Atat timp timp cat threadul este pornit Update() & Draw()
        while (runState == true)
        {
            /// Se obtine timpul curent
            curentTime = System.nanoTime();

            /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if((curentTime - oldTime)>timeFrame)
            {
                /// Actualizeaza pozitiile elementelor
                try {
                    Update();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /// Deseneaza elementele grafica in fereastra.
                Draw();
                oldTime = curentTime;
            }
        }
    }

    /*! \fn public synchronized void StartGame()
        \brief Creaza si starteaza firul separat de executie (thread).

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StartGame() {
        if (runState == false) {
            /// Se actualizeaza flagul de stare a threadului
            runState = true;
            /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
            /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this);
            /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        } else {
            /// Thread-ul este creat si pornit deja
            return;
        }

    }
    /*! \fn public synchronized void StopGame()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame() throws IOException {
        if(runState == true)
        {
            /// Actualizare stare thread
            runState = false;
            /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
            try {
                /// Metoda join() pune un thread in asteptare panca cand un altul isi termina executie.
                /// Totusi, in situatia de fata efectul apelului este de oprire a threadului.
                gameThread.join();
            }
            catch (InterruptedException ex)
            {
                // In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
                ex.printStackTrace();
            }
        }
        else {
            /// Thread-ul este oprit deja.
            return;
        }

    }

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update () throws SQLException, IOException, UnsupportedAudioFileException {

        ///Determina starea tastelor
        keyManager.Update();
        ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
        if(State.GetState() != null) {
            ///Actualizez starea curenta a jocului daca exista.
            State.GetState().Update();
        }
        /// Setez focus-ul pe Frame pentru a evita blocarea jocului la clik pe mouse
        wnd.GetWndFrame().requestFocus();
    }

    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw () {
        /// Returnez bufferStrategy pentru canvasul existent
        bs = wnd.GetCanvas().getBufferStrategy();
        /// Verific daca buffer strategy a fost construit sau nu
        if (bs == null)
        {
            /// Se executa doar la primul apel al metodei Draw()
            try{
                /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
        /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();
        /// Se sterge ce era
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

        /// operatie de desenare
        ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
        if(State.GetState()!=null)
        {
            ///Actualizez starea curenta a jocului daca exista.
            State.GetState().Draw(g);
        }
        /// end operatie de desenare

        /// Se afiseaza pe ecran
        bs.show();
        /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
        /// elementele grafice ce au fost desenate pe canvas).
        g.dispose();
    }
    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei
     */
    public int GetWidth() {return wnd.GetWndWidth(); }
    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei
     */
    public int GetHeight() {return wnd.GetWndHeight();}
    /*! \fn public KeyManager GetKeyManager()
        \brief Returneaza obiectul care gestioneaza tastatura.
     */
    public KeyManager GetKeyManager() {
        return keyManager;
    }
    /*! \fn public KeyManager GetMouseManager()
        \brief Returneaza obiectul care gestioneaza mouse-ul.
     */
    public MouseManager GetMouseInput() {
        return mouseManager;
    }

    public boolean getMusicFlag() {
        return musicFlag;
    }
}

/*
@startuml
class Game

@enduml
 */