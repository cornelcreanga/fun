package com.ccreanga.various.auction;

public class Bid {

    private User user;
    private Auction auction;
    private long timestamp;
    private long amount;

    public Bid(User user, Auction auction, long timestamp, long amount) {
        this.user = user;
        this.auction = auction;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public Auction getAuction() {
        return auction;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "user=" + user +
                ", auction=" + auction +
                ", timestamp=" + timestamp +
                ", amount=" + amount +
                '}';
    }
}
