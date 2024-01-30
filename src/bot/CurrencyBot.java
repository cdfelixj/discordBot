package bot;


import tools.Command;
import tools.IDNotFoundException;
import tools.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This bot is the currency bot in which it will calculate the HKD currency to any of
 * 4 registered currencies (USD,EUR,JPY,RMB)
 * You can command it to only exchange 2 currencies, or 3, or 4 at a time.
 */
public class CurrencyBot extends CommandBot {
    //Add your data member here

    public static int countUsed = 0;

    //Constructor
    public CurrencyBot() {

        addOption("usd", "exchange to USD", false);
        addOption("eur", "exchange to EUR", false);
        addOption("jpy", "exchange to JPY", false);
        addOption("rmb", "exchange to RMB", false);
    }


    @Override
    public String getCommand() {
        return "exchange";
    }


    @Override
    public String getCommandHelp() {
        return "Exchange currency";
    }


    @Override
    public String respond(Command command) {
        countUsed++;
        double currencyToUSD = 0;
        double currencyToEUR = 0;
        double currencyToJPY = 0;
        double currencyToRMB = 0;
        String output = "Currency Exchange rates as of 3/12/2023 \n";

        //Changing the inputs to doubles
        try {
            if (command.getOption("usd") != null)
                currencyToUSD = Double.parseDouble(command.getOption("usd"));
            if (command.getOption("eur") != null)
                currencyToEUR = Double.parseDouble(command.getOption("eur"));
            if (command.getOption("jpy") != null)
                currencyToJPY = Double.parseDouble(command.getOption("jpy"));
            if (command.getOption("rmb") != null)
                currencyToRMB = Double.parseDouble(command.getOption("rmb"));
        } catch (NullPointerException e) {

        } finally {
            if (currencyToUSD != 0) {
                double usd = currencyToUSD * 0.12801;
                output += "HKD: " + command.getOption("usd") + " to USD: " + usd + "\n";
            }
            if (currencyToEUR != 0) {
                double eur = currencyToEUR * 0.11758;
                output += "HKD: " + command.getOption("eur") + " to EUR: " + eur + "\n";
            }
            if (currencyToJPY != 0) {
                double jpy = currencyToJPY * 18.79492;
                output += "HKD: " + command.getOption("jpy") + " to JPY: " + jpy + "\n";
            }
            if (currencyToRMB != 0) {
                double rmb = currencyToRMB * 0.90525;
                output += "HKD: " + command.getOption("rmb") + " to RMB: " + rmb + "\n";
            }
        }

        return output;
    }

    //Output how many times it has been used
    @Override
    public void actionPerform() {

        if (countUsed > 0)
            System.out.println(countUsed + " times currency bot has been used");
    }
}
