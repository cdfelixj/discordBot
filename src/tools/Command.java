package tools;


import java.util.ArrayList;

/**
 * This class is to store the command sent by the user.
 * It shares the same similarity as a Message object except
 * that a command may also has options.
 * <p>
 * Each option is modeled as a TextPair object. A command
 * may have no option, one option, or multiple options.
 * <p>
 * This class support the following methods:
 * - addOption (to add an option)
 * - getOption (to get the value of an option)
 * - getOptions (to get all options)
 */
public class Command extends Message {
    //TODO - add data members and methods

    private ArrayList<TextPair> options = new ArrayList<>();

    public Command(String name, String id, String content, boolean isPrivate) {
        super(name, id, content, isPrivate);

    }

    public void addOption(String name, String asString) {
        options.add(new TextPair(name, asString));

    }

    public String getOption(String name) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getName().equalsIgnoreCase(name))
                return options.get(i).getValue();
        }
        return null;
    }

    public TextPair[] getOptions() {
        TextPair[] placeholder = new TextPair[options.size()];

        for (int i = 0; i < placeholder.length; i++) {
            placeholder[i] = options.get(i);
        }
        return placeholder;
    }
}
