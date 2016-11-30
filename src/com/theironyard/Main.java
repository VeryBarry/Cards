package com.theironyard;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static HashSet<Card> createDeck() {
        HashSet<Card> deck = new HashSet<>();
        for (Card.Suit suit : Card.Suit.values()){
            for (Card.Rank rank : Card.Rank.values()) {
                Card c = new Card(suit, rank);
                deck.add(c);
            }
        }
        return deck;
    }

    public static HashSet<HashSet<Card>> createHands(HashSet<Card> deck) {
        HashSet<HashSet<Card>> hands = new HashSet<>();
        for(Card c1 : deck) {
            HashSet<Card> deck2 = (HashSet<Card>) deck.clone();
            deck2.remove(c1);
            for(Card c2 : deck2) {
                HashSet<Card> deck3 = (HashSet<Card>) deck2.clone();
                deck3.remove(c2);
                for(Card c3 : deck3) {
                    HashSet<Card> deck4 = (HashSet<Card>) deck3.clone();
                    deck4.remove(c3);
                    for(Card c4 : deck4) {
                        HashSet<Card> hand = new HashSet<>();
                        hand.add(c1);
                        hand.add(c2);
                        hand.add(c3);
                        hand.add(c4);
                        hands.add(hand);
                    }
                }
            }
        }
        return hands;
    }

    public static boolean isFlush(HashSet<Card> hand) {
        HashSet<Card.Suit> suits =
                hand.stream()
                        .map(c -> c.suit)
                        .collect(Collectors.toCollection(HashSet::new));
        return suits.size() == 1;
    }

    public static boolean isPair (HashSet<Card> hand) {
        HashMap<Card.Rank, Integer> ranks = new HashMap<>();
        for (Card c : hand) {
            if (!ranks.containsKey(c.rank)) {
                ranks.put(c.rank, 1);
            }
            else {
                int tally = ranks.get(c.rank);
                int newTally = tally + 1;
                ranks.put(c.rank, newTally);
            }
        }
        return ranks.containsValue(2);
    }

    public static boolean isStraight(HashSet<Card> hand) {
        HashSet<Card.Rank> ranks = new HashSet<>();
        ArrayList<Integer> rankOrdinalArray = new ArrayList<>();
        for (Card c : hand) {
            ranks.add(c.rank);
            rankOrdinalArray.add(c.rank.ordinal());
        }
        Collections.sort(rankOrdinalArray);
        int consecutive = 0;
        ArrayList<Integer> aceHighStraightArray = new ArrayList<Integer> (Arrays.asList(10, 11, 12, 0));
        for (int i = 1; i < ranks.size(); i++) {
            boolean straight = false;
            boolean aceHighStraight = false;
            if (rankOrdinalArray.get(i) - rankOrdinalArray.get(i - 1) <= 1) {
                consecutive++;
                if (consecutive >= 3) {
                    straight = true;
                }
            }
            if (rankOrdinalArray.containsAll(aceHighStraightArray)) {
                aceHighStraight = true;
            }
            if (straight || aceHighStraight) {
                if(!isFlush(hand)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isStraightFlush(HashSet<Card> hand) {
        HashSet<Card.Rank> ranks = new HashSet<>();
        ArrayList<Integer> rankOrdinalArray = new ArrayList<>();
        for (Card c : hand) {
            ranks.add(c.rank);
            rankOrdinalArray.add(c.rank.ordinal());
        }
        Collections.sort(rankOrdinalArray);
        int consecutive = 0;
        ArrayList<Integer> aceHighStraightArray = new ArrayList<Integer>(Arrays.asList(10, 11, 12, 0));
        for (int i = 1; i < ranks.size(); i++) {
            boolean straight = false;
            boolean aceHighStraight = false;
            if (rankOrdinalArray.get(i) - rankOrdinalArray.get(i - 1) <= 1) {
                consecutive++;
                if (consecutive >= 3) {
                    straight = true;
                }
            }
            if (rankOrdinalArray.containsAll(aceHighStraightArray)) {
                aceHighStraight = true;
            }
            if (straight || aceHighStraight) {
                if (isFlush(hand)) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
        return ranks.size() == 1;
    }

    public static boolean threeOfAKind (HashSet<Card> hand) {
        HashMap<Card.Rank, Integer> ranks = new HashMap<>();
        for (Card c : hand) {
            if (!ranks.containsKey(c.rank)) {
                ranks.put(c.rank, 1);
            }
            else {
                int tally = ranks.get(c.rank);
                int newTally = tally + 1;
                ranks.put(c.rank, newTally);
            }
        }
        return ranks.containsValue(3);
    }

    public static boolean fourOfAKind (HashSet<Card> hand) {
        HashSet<Card.Rank> ranks = new HashSet<>();
        for (Card c : hand) {
            ranks.add(c.rank);
        }
        return ranks.size() == 1;
    }

    public static void main(String[] args) {
        HashSet<Card> deck = createDeck();
        HashSet<HashSet<Card>> hands = createHands(deck);

        HashSet<HashSet<Card>> flushes = hands.stream().filter(Main::isFlush).collect(Collectors.toCollection(HashSet::new));
        System.out.println(flushes.size());

        HashSet<HashSet<Card>> straights = hands.stream().filter(Main::isStraight).collect(Collectors.toCollection(HashSet::new));
        System.out.println(straights.size());

        HashSet<HashSet<Card>> fours = hands.stream().filter(Main::fourOfAKind).collect(Collectors.toCollection(HashSet::new));
        System.out.println(fours.size());

        HashSet<HashSet<Card>> threes = hands.stream().filter(Main::threeOfAKind).collect(Collectors.toCollection(HashSet::new));
        System.out.println(threes.size());

        HashSet<HashSet<Card>> pairs = hands.stream().filter(Main::isPair).collect(Collectors.toCollection(HashSet::new));
        System.out.println(pairs.size());

        HashSet<HashSet<Card>> straightFlush = hands.stream().filter(Main::isStraightFlush).collect(Collectors.toCollection(HashSet::new));
        System.out.println(straightFlush.size());
    }
}