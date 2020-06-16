package PaooGame.DataBase;

import java.sql.*;
import java.util.Scanner;
/*! \class public class DataBase
    \brief Clasa ce contine baza de date si legatura jocului cu baza de date

 */
public class DataBase {
    private Connection connection = null;
    private Statement statement = null;
    /* \fn public DataBase()
        \brief Constructorul de initializare al clasei.
     */
    public DataBase() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:GameDataBase.db");
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            /// se creaza tabela Save la prima rulare a jocului
            //String rs = "CREATE TABLE Save (Score INTEGER, Life INTEGER, Level Integer);";
            //statement.executeUpdate(rs);
            /// se creaza tabela Settings la prima rulare a jocului
            //String rs = "CREATE TABLE Settings (Sound INTEGER);";
            //statement.executeUpdate(rs);
            connection.commit();
        }
        catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    /*  \fn void addSettings(int music)
        \brief se adauga setarea de joc cu muzica sau nu
     */
    public void addSettings(int music) {
        try {
            String rs = "INSERT INTO Settings (Sound)"+
                    "VALUES("+music+");";
            statement.executeUpdate(rs);
            connection.commit();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    /* \fn public void add(int score, int life, int level)
        \brief Functie de adaugare inregistrare in baza de date

        \param score Scorul la care s-a ajuns in joc
        \param life Viata la care s-a ajung in joc
        \param level Nivelul la care s-a ajuns in joc
     */
    public void add(int score, int life, int level){
        try{
            String rs = "INSERT INTO Save (Score, Life, Level) " +
                    "VALUES (" + score + ", " +life + ", "+level+");";
            statement.executeUpdate(rs);
            connection.commit();
        }
        catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    /*  \fn public int getScore()
        \brief Returneaza ultime valoare stocata in baza de date la coloana Score
     */
    public int getScore() throws SQLException {
        try {
            ResultSet rs = statement.executeQuery("SELECT Score FROM Save order by ROWID DESC");
            int score = rs.getInt("Score");
            return score;
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return 0;
    }
    /*  \fn public int getLife()
       \brief Returneaza ultime valoare stocata in baza de date la coloana Life
    */
    public int getLife() throws SQLException {
        try {
            ResultSet rs = statement.executeQuery("SELECT Life FROM Save order by ROWID DESC");
            int life = rs.getInt("Life");
            return life;
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return 0;
    }
    /*  \fn public int getLevel()
       \brief Returneaza ultime valoare stocata in baza de date la coloana Level
    */
    public int getLevel() throws SQLException {
        try {
            ResultSet rs = statement.executeQuery("SELECT Level FROM Save order by ROWID DESC");
            int level = rs.getInt("Level");
            return level;
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return 0;
    }
}
