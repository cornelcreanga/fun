package com.ccreanga.various.auction;

import java.util.List;

public class UserStats {

    private User user;
    private List<Auction> auctions;
    private List<Bid> bids;
    private int wonCount;
    private long amountSpent;
    private int withdrawnCount;

    public UserStats(User user, List<Auction> auctions, List<Bid> bids, int wonCount, long amountSpent, int withdrawnCount) {
        this.user = user;
        this.auctions = auctions;
        this.bids = bids;
        this.wonCount = wonCount;
        this.amountSpent = amountSpent;
        this.withdrawnCount = withdrawnCount;
    }

    public User getUser() {
        return user;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public int getWonCount() {
        return wonCount;
    }

    public long getAmountSpent() {
        return amountSpent;
    }

    public int getWithdrawnCount() {
        return withdrawnCount;
    }
}
