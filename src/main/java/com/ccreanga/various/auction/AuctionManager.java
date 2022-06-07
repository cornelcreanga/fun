package com.ccreanga.various.auction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuctionManager {

    private final List<Auction> auctions = new ArrayList<>();

    /**
     * in which auctions they participated, their current bids, number of times they placed a bid on an auction,
     * which auctions they won, how much they spent, how many times they’ve withdrawn.
     */

    public void registerAuction(Auction auction) {
        auctions.add(auction);
    }

    public UserStats getUserAuctionStats(User user) {
        List<Auction> userAuctions = new ArrayList<>();
        List<Bid> userBids = new ArrayList<>();
        int wonCount = 0;
        int amountSpent = 0;
        int withdrawnCount = 0;

        for (Auction auction : auctions) {
            Optional<Bid> optional = auction.getCurrentUserBid(user);
            if (optional.isPresent()) {
                userAuctions.add(auction);
                userBids.add(optional.get());
                if (auction.isClosed()) {
                    Optional<Bid> winner = auction.getWinner();
                    if (winner.isPresent() && winner.get().getUser().equals(user)) {
                        wonCount++;
                        wonCount += winner.get().getAmount();
                    }
                }
                if (auction.userWithdrawn(user)) {
                    withdrawnCount++;
                }
            }
        }
        return new UserStats(user, userAuctions, userBids, wonCount, amountSpent, withdrawnCount);
    }

}
