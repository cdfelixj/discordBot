package bot;

import tools.*;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;


/**
 * This bot will check the seat of a quiz or practical test for a student.
 *
 * This message bot will only work in private message.
 * The user should first type "seat" to start the conversation.
 * The bot will then ask for the student ID. The bot is expecting
 * a 8-digit number as the student ID and ignore any other message.
 * After received the 8-digit number in a private message, the bot
 * will check the seat of the student and return the seat number.
 *
 * The bot allows the user to check seat for other students or check
 * the seat even if the user did not register to UserManagementBot before.
 *
 * We will assume the seat will never change during the execution of the
 * program. Any change of seat will require the program to restart.
 */
public class SeatChecker implements MessageListener {
    //TODO: Add your private data member here
    private boolean seatAsked = false;

    //TODO: Add your methods here

    public String onMessageReceived(Message message) {
        if (message.getIsPrivate()) {
            if (message.getContent().equals("seat")) {
                seatAsked = true;
                return "What is your ID?";
            }
            if (ifMessageValid(message.getContent()) && seatAsked) {
                File filename = new File("seat.csv");
                try {
                    Scanner fin = new Scanner(filename);
                    while (fin.hasNextLine()) {
                        String line = fin.nextLine();
                        String[] entry = line.split(",");
                        if (entry[0].equalsIgnoreCase(message.getContent())) {
                            seatAsked = false;
                            return "Your seat is: " + entry[1];
                        }
                    }
                    seatAsked = false;
                    return "Sorry I cannot find your seat\n";

                } catch (FileNotFoundException e) {
                    System.out.println("seat.csv not found");
                }
            }
        }
        else if (message.getContent().equals("seat") && !message.getIsPrivate() )
            return "Sorry I only work in private messages";
        return null;
    }

    private boolean ifMessageValid(String message){

        if (message.length() != 8)
            return false;

        for(int i = 0 ; i < message.length();i++){
            char temp = message.charAt(i);
            if (!Character.isDigit(temp))
                return false;
        }
        return true;

    }


}

