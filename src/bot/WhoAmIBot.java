package bot;

import tools.Command;
import tools.IDNotFoundException;


public class WhoAmIBot extends CommandBot {
    private final UserManagementBot userManagementBot;
    private int count = 0;

    /**
     * The constructor of the bot, require a UserManagementBot object.
     */
    public WhoAmIBot(UserManagementBot userManagementBot) {
        this.userManagementBot = userManagementBot;
    }
    /**
     * The command of the bot.
     */
    @Override
    protected String getCommand() {
        return "whoami";
    }
    /**
     * The short description for this command.
     */
    @Override
    protected String getCommandHelp() { return "A simple who am i bot.";  }

    /**
     * This method is used to respond to a command.
     */
    @Override
    protected String respond(Command command) {
        try {
            return "You are " + userManagementBot.getStudent(command.getSenderID()).getStudentName();
        } catch (IDNotFoundException e) {
            return "You have not registered yet";
        }
    }


    @Override
    public void actionPerform() {
        count++;
        count %= 5;
        if (count == 0)
            System.out.println("WhoAmIBot is still running!");
    }
}
