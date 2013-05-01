package com.gmail.lifeofreilly.tweetbot;

import org.apache.log4j.Logger;

/**
 * Defines the BotRunner which starts various twitter bots.
 * If using or modifying this bot you must adhere to:
 * Automation rules and best practices
 * https://support.twitter.com/articles/76915-automation-rules-and-best-practices
 *
 * @author Seth Reilly
 * @version 1.0, May 2013
 */
public class BotRunner {

    private final static Logger log = Logger.getLogger(BotRunner.class);

    /**
     * Starts ReplyBot and DMBot threads.
     *
     * @param args no arguments required.
     */
    public static void main(String[] args) {
        Thread replyBotThread = new Thread(new ReplyBot());
        Thread dmBotThread = new Thread(new DMBot());
        replyBotThread.start();
        dmBotThread.start();
    }
}