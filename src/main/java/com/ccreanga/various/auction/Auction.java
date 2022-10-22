package com.ccreanga.various.auction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Auction {

    private final String NO_WINNER = "no_winner";
    private String name;
    private long minValue;
    private Bid winner;
    private ConcurrentMap<User, PriorityQueue<Bid>> map = new ConcurrentHashMap<>();
    private Set<User> withdrawnUsers = new HashSet<>();
    boolean open = true;

    private Auction(String name, long minValue, Bid winner, ConcurrentMap<User, PriorityQueue<Bid>> map, Set<User> withdrawnUsers, boolean open) {
        this.name = name;
        this.minValue = minValue;
        this.winner = winner;
        this.map = map;
        this.withdrawnUsers = withdrawnUsers;
        this.open = open;
    }

    public Auction(String name, long minValue) {
        this.name = name;
        this.minValue = minValue;
    }

    public synchronized Optional<Bid> getWinner() {
        if (winner.getUser().getUsername().equals(NO_WINNER))
            return Optional.empty();
        return Optional.of(winner);
    }

    private synchronized Bid createNoWinner() {
        return new Bid(new User(NO_WINNER), this, System.currentTimeMillis(), Long.MIN_VALUE);
    }

    public synchronized Optional<Bid> getCurrentUserBid(User user) {
        PriorityQueue<Bid> bids = map.get(user);
        if (bids == null || bids.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(bids.peek());
        }
    }

    public synchronized int countUserBids(User user) {
        PriorityQueue<Bid> bids = map.get(user);
        if (bids == null)
            return 0;
        return bids.size();
    }

    public synchronized boolean userWithdrawn(User user) {
        return withdrawnUsers.contains(user);
    }


    public synchronized void withdrawAllBids(User user) {
        checkOpen();
        withdrawnUsers.add(user);
        map.put(user, new PriorityQueue<>(Comparator.comparingLong(Bid::getAmount)));
        if (winner.getUser().equals(user)) {
            winner = createNoWinner();
            Set<User> keys = map.keySet();
            for (User next : keys) {
                PriorityQueue<Bid> bids = map.get(next);
                Bid max = bids.peek();
                if (max == null)
                    continue;
                if (winner.getAmount() < max.getAmount()) {
                    winner = max;
                }
            }
        }
    }

    public synchronized void addBid(Bid bid) {
        checkOpen();
        if (withdrawnUsers.contains(bid.getUser())) {
            throw new UserWithdrawnException();
        }

        if (bid.getAmount() < minValue) {
            throw new BidTooLowException();
        }
        map.putIfAbsent(bid.getUser(), new PriorityQueue<>(Comparator.comparingLong(Bid::getAmount)));
        PriorityQueue<Bid> bids = map.get(bid.getUser());
        if ((bids.size() > 0) && (bids.peek().getAmount() > bid.getAmount())) {
            throw new BidTooLowException();
        }
        bids.add(bid);
        if (winner == null) {
            winner = bid;
        } else {
            if (winner.getAmount() < bid.getAmount()) {
                winner = bid;
            }
        }
    }

    public synchronized void close() {
        open = false;
    }

    public synchronized boolean isOpen() {
        return open;
    }

    public synchronized boolean isClosed() {
        return !open;
    }

    public synchronized void checkOpen() {
        if (!open)
            throw new ClosedAuctionException();
    }


    public static void main(String[] args) {
        Auction auction = new Auction("test", 10);
        User cucu = new User("cucu");
        User vasi = new User("vasi");
        auction.addBid(new Bid(cucu, auction, System.currentTimeMillis(), 100));
        auction.addBid(new Bid(cucu, auction, System.currentTimeMillis(), 110));
        auction.addBid(new Bid(vasi, auction, System.currentTimeMillis(), 100));
        auction.withdrawAllBids(cucu);
        System.out.println(auction.getWinner());
    }

}


