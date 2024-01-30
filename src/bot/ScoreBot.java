package bot;

import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import tools.*;

/**
 * This bot handle the command '/score'.
 * When a user ask about his score, this bot will check the score of the user and return the score.
 * To know which student ID that the user has been registered, this bot will ask the UserManagementBot.
 * Prior to using this bot, you need to register this bot to the UserManagementBot using the command '/registration'.
 */
public class ScoreBot extends CommandBot {
    public static final String DEFAULT_FILE = "dictation.csv";
    //TODO: Add your private data member here

    ArrayList<String> scores = new ArrayList<String>();

    private UserManagementBot bot;
    private String fileName = "dictation.csv";
    private String name;

    /**
     * The constructor of the bot, require a UserManagementBot object.
     * Since the filename of the score is not given, the default file name is used.
     */
    public ScoreBot(UserManagementBot r) {
        //TODO
        bot = r;

    }

    /**
     * The constructor of the bot, require a UserManagementBot object and the filename of the score.
     */
    public ScoreBot(UserManagementBot r, String filename) {
        //TODO
        bot = r;
        fileName = filename;
    }

    /**
     * Which score that the object is listening to.
     */
    @Override
    public String getCommand() {
        //TODO
        return "score";
    }

    /**
     * The short description for this command.
     */
    @Override
    public String getCommandHelp() {
        //TODO
        return "Displays the user's score";
    }

    /**
     * This method is used to respond to a command.
     */
    @Override
    public String respond(Command command) {
        //TODO
        System.out.println("Score Bot received a slash command '/score' from user: " + name);

        try {
            String studentId = bot.getStudentID(command.getSenderID());
            name = command.getName();

            File fileName = new File("dictation.csv");
            Scanner fin = new Scanner(fileName);
            while (fin.hasNextLine()) {
                String line = fin.nextLine();
                String[] entry = line.split(",");
                String[] scoresString = new String[entry.length - 1];
                double[] scores = new double[scoresString.length];
                if (entry[0].equalsIgnoreCase(studentId)) {


                    for (int i = 1; i < entry.length; i++) {
                        scoresString[i - 1] = entry[i];
                    }


                    String output = "Your scores are: ";
                    double sum = 0;
                    double count = 0;
                    for (int i = 0; i < scoresString.length; i++) {
                        if (!scoresString[i].equalsIgnoreCase("-")) {

                            scores[i] = Double.parseDouble(scoresString[i]);
                            sum += scores[i];
                            count++;
                            output += scores[i] + ", ";

                        } else
                            output += "N/A, ";
                    }
                    output += "and your average score is: ";


                    double average = sum / count;

                    output += average;


                    return output;
                }
            }

        } catch (IDNotFoundException e) {
            return "You are not registered";
        } catch (FileNotFoundException e) {
            return "File not found";
        }
        return "You are not registered";
    }

    /**
     * Print the last user who queried the score bot service to console.
     */
    @Override
    public void actionPerform() {
        //TODO
        if (name != null)
            System.out.println("The last user queried ScoreBot is: " + name);
    }
}
