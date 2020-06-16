package PaooGame.Maps;

import PaooGame.RefLinks;
import PaooGame.States.PlayState;
import PaooGame.States.State;

/*! \class public class FactoryMap
    \brief Implementeaza notiunea de Factory pentru harta jocului
    Se foloseste sablonul de proiectare Factory
 */
public class FactoryMap {
    private static RefLinks refLink;     /*!< O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.*/
    /*! \fn public FactoryMap(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    FactoryMap(RefLinks refLink) {
        /// Retine referinta "shortcut".
        this.refLink = refLink;
    }
    /*! \fn public static int[] generate(int level)
        \brief generarea hartilor pentru fiecare level

        \param level e nivelul la care a ajuns personajul
    */
    public static int[][] generate(int level) {
        if (level == 1) {
            return new Level1().get();
        }
        if (level == 2) {
            return new Level2().get();
        }
        if (level == 3) {
            return new Level3().get();
        }
        if (level == 4) {
            return new Level4().get();
        }
        if (level == 5) {
            return new Level5().get();
        }
        if (level == 6) {
            State.SetState(refLink.GetGame().getEndState(PlayState.getScore(), PlayState.getLife()));
            return new Level1().get();
        }
        else
        {
            throw new IllegalArgumentException("Unknown level type.");
        }
    }
}
