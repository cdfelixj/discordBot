package bot;

import net.dv8tion.jda.api.hooks.ListenerAdapter;



public abstract class AbstractBot extends ListenerAdapter {
    /**
     * This method is used to perform some actions.
     */
    public abstract void actionPerform();
}
