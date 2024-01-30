package bot;


import tools.Command;
import tools.IDNotFoundException;
import tools.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class UserManagementBot extends CommandBot {
    //Add your data member here
    private static ArrayList<User> peopleList = new ArrayList<>();


    //Constructor
    public UserManagementBot(String filename) {
        //TODO
        addOption("regcode", "Registers user", true);
        File fileName = new File(filename);
        try {
            Scanner fin = new Scanner(fileName);
            int i = 0;
            while (fin.hasNextLine()) {
                String[] entry = fin.nextLine().split(",");
                if (entry.length == 3) {
                    peopleList.add(new User(entry[0], entry[1]));
                    peopleList.get(i).setStudentName(entry[2]);

                } else if (entry.length == 5) {
                    peopleList.add(new User());
                    peopleList.get(i).setStudentID(entry[0]);
                    peopleList.get(i).setStudentName(entry[2]);
                    peopleList.get(i).setID(entry[3]);
                    peopleList.get(i).setUsername(entry[4]);
                    peopleList.get(i).setRegistrationCode("-");
                    peopleList.get(i).setRegistered();

                }
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

    }


    /**
     * Written for you. No need to change it
     */
    @Override
    public String getCommand() {
        return "registration";
    }

    /**
     * Written for you. No need to change it
     */
    @Override
    public String getCommandHelp() {
        return "Registers the user";
    }


    /**
     * to check if a user has been registered
     */
    boolean isRegistered(String id) {
        //TODO
        for (int i = 0; i < peopleList.size(); i++) {
            if (peopleList.get(i).getID() != null && peopleList.get(i).getID().equalsIgnoreCase(id))
                return true;

        }
        return false;
    }


    /**
     * To respond to the command '/registration'.
     * <p>
     * If the user has already registered, return "You are already registered!"
     * If the registration code is correct, register the user and return "You are registered!".
     * If the registration code is incorrect, return "Invalid registration code!"
     * <p>
     * To avoid data lost, remember to save the data to the file after each user's registration.
     */
    @Override
    public String respond(Command command) {
        //TODO

        if (isRegistered(command.getSenderID()))
            return "You are already registered!";
        else {
            for (int i = 0; i < peopleList.size(); i++) {

                if ( !command.getOption("regcode").equalsIgnoreCase("-") && command.getOption("regcode").equalsIgnoreCase(peopleList.get(i).getRegistrationCode())) {
                    peopleList.get(i).setRegistered();
                    peopleList.get(i).setRegistrationCode("-");
                    peopleList.get(i).setID(command.getSenderID());
                    peopleList.get(i).setUsername(command.getName());
                    rewriteFile();
                    return "You are registered!";
                }
            }

        }

        return "Invalid registration code!";
    }

    private void rewriteFile() {
        File fileName = new File("users.csv");
        try {
            reorderPeopleList();
            PrintWriter out = new PrintWriter(fileName);
            for (int i = 0; i < peopleList.size(); i++) {
                if (isRegistered(peopleList.get(i).getID())) {
                    out.print(peopleList.get(i).getStudentID() + ",");
                    out.print(peopleList.get(i).getRegistrationCode() + ",");
                    out.print(peopleList.get(i).getStudentName() + ",");
                    out.print(peopleList.get(i).getID() + ",");
                    out.print(peopleList.get(i).getUsername());
                    out.println();

                } else {
                    out.print(peopleList.get(i).getStudentID() + ",");
                    out.print(peopleList.get(i).getRegistrationCode() + ",");
                    out.print(peopleList.get(i).getStudentName() + ",");
                    out.println();
                }


            }
            out.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    private void reorderPeopleList() {
        for (int j = 0; j < peopleList.size(); j++) {
            for (int i = 0; i < peopleList.size() - 1; i++) {
                if (peopleList.get(i).getStudentID().compareTo(peopleList.get(i + 1).getStudentID()) > 0) {
                    User temp = peopleList.get(i);
                    peopleList.set(i, peopleList.get(i + 1));
                    peopleList.set(i + 1, temp);
                }
            }
        }
    }

    /**
     * return the student ID of the user with the given discord ID
     * <p>
     * throws an IDNotFoundException if the discord ID cannot be found
     */
    public String getStudentID(String id) throws IDNotFoundException {
        //TODO
        for (int i = 0; i < peopleList.size(); i++) {
            if (peopleList.get(i).getID() != null && peopleList.get(i).getID().equalsIgnoreCase(id))
                return peopleList.get(i).getStudentID();
        }
        throw new IDNotFoundException("ID not found: " + id);
    }

    /**
     * return the user object with the given discord ID
     * <p>
     * throws an IDNotFoundException if the discord ID cannot be found
     */
    public User getStudent(String id) throws IDNotFoundException {
        //TODO

        for (int i = 0; i < peopleList.size(); i++) {
            if (peopleList.get(i).getID() != null && peopleList.get(i).getID().equalsIgnoreCase(id))
                return peopleList.get(i);
        }
        throw new IDNotFoundException("ID not found: " + id);

    }


    /**
     * Output how many number of users have registered.
     */
    @Override
    public void actionPerform() {
        int numOfRegistered = 0;
        for (int i = 0; i < peopleList.size(); i++) {
            if (peopleList.get(i).isRegistered()) {
                numOfRegistered++;
            }
        }
        System.out.println("Registration bot: ");
        System.out.println("There are " + numOfRegistered + "/" + peopleList.size() + " users has registered the system");
    }
}
