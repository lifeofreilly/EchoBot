package com.gmail.lifeofreilly.tweetbot;

import org.apache.log4j.Logger;
import twitter4j.RateLimitStatusEvent;
import twitter4j.RateLimitStatusListener;

/**
 * Implements twitter4j RateLimitStatusListener.
 * When rate limit is reached the thread will be put to sleep until reset.
 * If using or modifying this bot you must adhere to:
 * Automation rules and best practices
 * https://support.twitter.com/articles/76915-automation-rules-and-best-practices
 *
 * @author Seth Reilly
 * @version 1.0, May 2013
 */
public class RateLimitStatusListenerImpl implements RateLimitStatusListener {

    private final static Logger log = Logger.getLogger(RateLimitStatusListenerImpl.class);

    /**
     * Sleep if rate limit is reached.
     */
    @Override
    public void onRateLimitReached(RateLimitStatusEvent event) {

        log.warn("Rate limit hit. Seconds until reset: " + event.getRateLimitStatus().getSecondsUntilReset() + ". Going to sleep zzz...");

        try {
            Thread.sleep((event.getRateLimitStatus().getSecondsUntilReset() * 1000) + 15000);
        } catch (InterruptedException ex) {
            log.error("Exception occured while sleeping: ", ex);
            Thread.currentThread().interrupt();
        }

    }

    /**
     * Log rate limit status information.
     */
    @Override
    public void onRateLimitStatus(RateLimitStatusEvent event) {
        log.info("Remaining number of API requests available: " + event.getRateLimitStatus().getRemaining());
        log.info("Remaining seconds until reset: " + event.getRateLimitStatus().getSecondsUntilReset());
    }

}