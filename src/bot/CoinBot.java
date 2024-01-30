package bot;

import tools.Message;

import java.util.concurrent.ThreadLocalRandom;

public class CoinBot implements MessageListener {

    @Override
    public String onMessageReceived(Message message) {

        int choice = ThreadLocalRandom.current().nextInt(0, 2);
        if (message.getContent().equals("flip coin")) {
            if (choice == 1)
                return "You have received Heads!";
            else
                return "You have received Tails!";
        }
        return null;
    }
}