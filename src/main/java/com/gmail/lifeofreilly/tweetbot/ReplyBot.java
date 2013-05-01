package com.gmail.lifeofreilly.tweetbot;

import org.apache.log4j.Logger;
import twitter4j.*;

/**
 * Defines the ReplyBot.
 * This bot leverages the streaming api to listen for direct replies.
 * Direct replies are tweets that begin with @[bot's name].
 * When a direct reply is reveived the bot will reply to the sender and echo the tweet text.
 * If using or modifying this bot you must adhere to:
 * Automation rules and best practices
 * https://support.twitter.com/articles/76915-automation-rules-and-best-practices
 *
 * @author Seth Reilly
 * @version 1.0, May 2013
 */
public class ReplyBot extends TwitterBot {

    private TwitterStream twitterStream;
    private String[] terms = {};
    private long[] ids = new long[1];
    private String screenName;
    private final static Logger log = Logger.getLogger(ReplyBot.class);

    /**
     * Sole constructor.
     */
    public ReplyBot() {
        screenName = TwitterBot.getBotScreenName();
        ids[0] = TwitterBot.getBotId();
        log.info("ReplyBot is using the screen name: " + screenName);
        log.info("ReplyBot is following: " + ids[0]);
    }

    /**
     * Defines the stream filter and listener.
     * Starts listening to the stream.
     */
    @Override
    public void run() {
        twitterStream = new TwitterStreamFactory().getInstance();
        TwitterBotListener listener = new TwitterBotListener();
        twitterStream.addListener(listener);
        FilterQuery fq = new FilterQuery(0, ids, terms);
        twitterStream.filter(fq);
    }

    /**
     * Implements the twitter4j StatusListener.
     */
    public class TwitterBotListener implements StatusListener {

        /**
         * Determine if status is a direct reply and echo it to sender.
         */
        @Override
        public void onStatus(Status status) {
            log.info("Received onStatus: " + status.getText());
            log.info("getInReplyToUserId: " + status.getInReplyToUserId());
            try {
                if (status.getInReplyToUserId() == ids[0]) {
                    if (!screenName.equalsIgnoreCase(status.getUser().getScreenName())) {
                        String tweetText = ("@" + status.getUser().getScreenName() + " echo:" + status.getText().replace("@" + screenName, ""));
                        if (tweetText.length() > 140) {
                            tweetText = tweetText.substring(0, 139);
                        }
                        StatusUpdate update = new StatusUpdate(tweetText);
                        update.setInReplyToStatusId(status.getId());
                        Twitter twitter = new TwitterFactory().getInstance();
                        log.info("Sending Tweet: " + tweetText);
                        Status response = twitter.updateStatus(update);
                    }
                }
            } catch (TwitterException ex) {
                log.error("Exception occured while sending the response: ", ex);
            }
        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            log.info("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
        }

        @Override
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            log.info("Got track limitation notice:" + numberOfLimitedStatuses);
        }

        @Override
        public void onScrubGeo(long userId, long upToStatusId) {
            log.info("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
        }

        @Override
        public void onStallWarning(StallWarning warning) {
            log.info("Got stall warning:" + warning);
        }

        @Override
        public void onException(Exception ex) {
            log.error("TwitterBotListener onException: ", ex);
        }
    }
}