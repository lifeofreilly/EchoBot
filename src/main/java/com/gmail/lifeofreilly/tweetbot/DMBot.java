package com.gmail.lifeofreilly.tweetbot;

import java.lang.Runnable;
import java.util.List;
import org.apache.log4j.Logger;
import twitter4j.*;

/**
 * Defines the DMBot.
 * This bot polls twitter for new direct messages, and throttles the requests based on rate limits.
 * When a direct message is reveived the bot will reply to the sender with a direct message and echo the content.
 * Upon processing the message, the bot will destroy the orignial direct message.
 * If using or modifying this bot you must adhere to:
 * Automation rules and best practices
 * https://support.twitter.com/articles/76915-automation-rules-and-best-practices
 *
 * @author Seth Reilly
 * @version 1.0, May 2013
 */
public class DMBot extends TwitterBot {

    private String screenName;
    private final static Logger log = Logger.getLogger(DMBot.class);

     /**
     * Sole constructor.
     */
    public DMBot() {
        screenName = TwitterBot.getBotScreenName();
        log.info("ReplyBot is using the screen name: " + screenName);
    }

    /**
     * Polls for new direct message and responds.
     * Requests are throttled based on rate limit information.
     */
    @Override
    public void run() {
        Twitter twitter = new TwitterFactory().getInstance();
        RateLimitStatusListener rateListener = new RateLimitStatusListenerImpl();
        twitter.addRateLimitStatusListener(rateListener);
        Paging paging = new Paging(1, 10);
        while(true) {
            try {
                List<DirectMessage> messages = twitter.getDirectMessages(paging);
                log.info("Processing: " + messages.size() + " Direct Messages.");
                for (DirectMessage message : messages) {
                    log.info("Direct Message received: " + message.getSenderScreenName() + ": " + message.getText());
                    String dmText = "echo: " + message.getText();
                    if(!screenName.equalsIgnoreCase(message.getSenderScreenName())) {
                        log.info("Sending DM: " + dmText);
                        twitter.sendDirectMessage(message.getSenderScreenName(), dmText);
                        twitter.destroyDirectMessage(message.getId());
                    }
                    else {
                        log.info("Automation Safety Check: Never respond to self to avoid recursive behavior.");
                    }
                }
            } catch (TwitterException ex) {
                log.error("Exception occured while sending Direct Messages: ", ex);
            }

            try {
                log.info("Sleep for 90 seconds between calls to avoid hitting rate limits.");
                Thread.sleep(90000);
            } catch (InterruptedException ex) {
                log.error("Exception occured while sleeping: ", ex);
                Thread.currentThread().interrupt();
            }
        }
    }
}