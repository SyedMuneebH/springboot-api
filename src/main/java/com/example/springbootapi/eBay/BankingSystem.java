// package com.example.springbootapi.eBay;


// import java.util.*;

// public class BankingSystem {

//     // You can add private fields, maps, helper classes here
//     /*
//     Level 1 (Basic Operations):boolean createAccount(String accountId, int timestamp)
//         Optional<Integer> deposit(String accountId, int timestamp, int amount)
//         Optional<Integer> transfer(String fromId, String toId, int timestamp, int amount)

//     Level 2 
//         (Ranking):List<String> topSpenders(int timestamp, int n) — return top N accounts by outgoing amount (descending), ties broken by accountId (ascending).

//     Level 3+ 
//         (Advanced):Schedule payments with cashback percentages.
//         Handle timestamps, concurrency-like ordering, invalid operations (e.g., overdraft, non-existent accounts).

    
//     */

//     HashMap<String, Account> db;
    
//     public BankingSystem() {
//         this.db = new HashMap<>();
//     }

//     public boolean createAccount(String accountId, int timestamp) {
//         // TODO: Level 1

//         if (db.containsKey(accountId)) {
//             return false;
//         }

//         Account account = new Account(0, timestamp);
//         db.put(accountId, account);

//         return true;
//     }

//     public Optional<Integer> deposit(String accountId, int timestamp, int amount) {
//         // TODO: Leveel 1

//         if (!db.containsKey(accountId)) {
//             return Optional.empty();
//         } else {
//             Account account = db.get(accountId);
//             int balance = account.getBalance();
//             account.setBalance(balance + amount);
//             account.setTimestamp(timestamp);

//             return Optional.of(account.getBalance());
//         }
//     }

//     public Optional<Integer> transfer(String fromId, String toId, int timestamp, int amount) {
//         // TODO: Level 1

//         if (!db.containsKey(fromId) || !db.containsKey(toId)) {
//             return Optional.empty();
//         }

//         Account from = db.get(fromId);
//         Account to = db.get(toId);

//         //set latest timestamps.
//         from.setTimestamp(timestamp);
//         to.setTimestamp(timestamp);
//         //set bank amounts now
//         if (from.getBalance() <= amount) {
//             return Optional.empty();
//         }

//         from.setBalance(from.getBalance() - amount);
//         from.setTotalSpent(from.getTotalSpent() + amount);
//         to.setBalance(to.getBalance() + amount);

        
//         return Optional.of(from.getBalance());
//     }

//     public List<String> topSpenders(int timestamp, int n) {
//         // TODO: Level 2 - Return top N spenders by total outgoing amount
//         // Sorted by spent DESC, then accountId ASC
//         //topSpenders(100, 2) returns 2 of the most top spenders by the timestamp.
//         List<AccountSpenders> spenders = new ArrayList()<>(); // <--- we want to call .get(spend) to get values (account id)

//         for (Map.Entry<String, Account> entry: db.entrySet()) {
//             if (entry.getValue().getTimestamp() <= timestamp) {
//                 spenders.put(entry.getValue().getTotalSpent(), entry.getKey());
//             }
//         }

//         for (int i = 0; i <= n; i++) {
//             //add list items
//         }

//         Collections.sort(result);
//     }

//     // ==================== Level 3+ (if they appear) ====================

//     public void schedulePayment(String accountId, String targetAccId, int timestamp,
//                                 int amount, double cashbackPercentage) {
//         // TODO: Optional higher level
//     }

//     private class Account {
//         int balance;
//         int timestamp;
//         int totalSpent;

//         public Account() {
//             this.balance = 0;
//             this.timestamp = 0;
//             this.totalSpent = 0;
//         }

//         public Account(int balance, int timestamp) {
//             this.balance = balance;
//             this.timestamp = timestamp;
//             this.totalSpent = 0;
//         }

//         public int getBalance() {
//             return balance;
//         }

//         public void setBalance(int balance) {
//             this.balance = balance;
//         }

//         public int getTimestamp() {
//             return timestamp;
//         }

//         public void setTimestamp(int timestamp) {
//             this.timestamp = timestamp;
//         }

//         public void setTotalSpent(int totalSpent){
//             this.totalSpent = totalSpent;
//         }

//         public int getTotalSpent() {
//             return totalSpent;
//         }
//     }

//     private class AccountSpenders {
//         String id;
//         int amtSpent;
//         public AccountSpenders(String id, int amtSpent) {this.id = id; this.amtSpent = amtSpent;}

//         public void setamtSpent(int amtSpent) {this.amtSpent = amtSpent;}

//         public int getamtSpent{return amtSpent;}

        
//     }

//     // You are free to add any private helper methods or inner classes below
// }