package com.gmail.lifeofreilly.tweetbot;

import org.apache.log4j.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * Defines the abstract TwitterBot class.
 * If using or modifying this bot you must adhere to:
 * Automation rules and best practices
 * https://support.twitter.com/articles/76915-automation-rules-and-best-practices
 *
 * @author Seth Reilly
 * @version 1.0, May 2013
 */
public abstract class TwitterBot implements Runnable {

    private final static Logger log = Logger.getLogger(TwitterBot.class);

    /**
     * Gets the screen name of the twitter bot.
     *
     * @return the screen name.
     */
    public static String getBotScreenName() {
        String screenName = new String();
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            screenName = twitter.getScreenName();
        } catch (TwitterException ex) {
            log.error("Exception occured while getting bot's screen name. Forcing exit: ", ex);
            System.exit(0);
        }
        return screenName;
    }

    /**
     * Gets the id of the twitter bot.
     *
     * @return the id.
     */
    public static long getBotId() {
        long id = 0;
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            id = twitter.getId();
            log.info("ID is: " + id);
        } catch (TwitterException ex) {
            log.error("Exception occured while getting bot's screen name. Forcing exit: ", ex);
            System.exit(0);
        }
        return id;
    }
}