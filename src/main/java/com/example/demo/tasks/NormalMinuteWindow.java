package com.example.demo.tasks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

public class NormalMinuteWindow {

    public static void main(String[] args) {
        final int WINDOW = 60000;
        List<TradeEntry> tradeInfo = new ArrayList<>();
        int bestResultV = 0, bestResultD = 0, localResultV, localResultD;
        NormalMinuteWindow app = new NormalMinuteWindow();
        app.readFile(tradeInfo);

        for (int i = 0; i < tradeInfo.size(); i++) {
            localResultD = 0;
            localResultV = 0;
            TradeEntry next = tradeInfo.get(i);
            int counter = i;
            long win = tradeInfo.get(i).getDate().getTime() + WINDOW;
            while (win > next.getDate().getTime()) {
                if (next.getExchange().equals(true)) {
                    localResultV++;
                } else {
                    localResultD++;
                }
                if (counter < tradeInfo.size() - 1) {
                    counter++;
                    next = tradeInfo.get(counter);
                } else break;
            }

            bestResultV = Math.max(localResultV, bestResultV);
            bestResultD = Math.max(localResultD, bestResultD);

        }

        System.out.println(bestResultV);
        System.out.println(bestResultD);

    }

//    static int windowApproach(List<TradeEntry> tradeInfo, int window) {
//        int n = tradeInfo.size();
//
//        if (n < window) {
//            System.out.println("Invalid");
//            return -1;
//        }
//        int bestResultV = 0, bestResultD = 0, localResultV, localResultD;
//        //counting the first window
//        for (int i = 0; i < window; i++) {
//            if (tradeInfo.get(i).getExchange().equals(true)) {
//                bestResultV++;
//            } else {
//                bestResultD++;
//            }
//        }
//
//        localResultD = bestResultD;
//        localResultV = bestResultV;
//
//            for (int i = window; i < n; i++) {
//                if (tradeInfo.get(i).getExchange().equals(true)) {
//                    localResultV++;
//                } else {
//                    localResultD++;
//                }
//                window_sum += arr[i] - arr[i - window];
//
//                bestResultD = Math.max(bestResultD, localResultD);
//                bestResultV = Math.max(bestResultV, localResultV);
//            }
//
//        return max_sum;
//    }

    class TradeEntry {
        private Timestamp date;
        private Float price;
        private Short size;
        private Boolean exchange;

        public TradeEntry(Timestamp date, Float price, Short size, Boolean exchange) {
            this.date = date;
            this.price = price;
            this.size = size;
            this.exchange = exchange;
        }

        public Timestamp getDate() {
            return date;
        }

        public void setDate(Timestamp date) {
            this.date = date;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }

        public Short getSize() {
            return size;
        }

        public void setSize(Short size) {
            this.size = size;
        }

        public Boolean getExchange() {
            return exchange;
        }

        public void setExchange(Boolean exchange) {
            this.exchange = exchange;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TradeEntry that = (TradeEntry) o;
            return date.equals(that.date) &&
                    price.equals(that.price) &&
                    size.equals(that.size) &&
                    exchange.equals(that.exchange);
        }

        @Override
        public int hashCode() {
            return Objects.hash(date, price, size, exchange);
        }
    }

    private void readFile(List<TradeEntry> list) {
        String csvFile = "/home/vaclav/Documents/trades.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                String[] tradeInfo = line.split(cvsSplitBy);
                boolean exch;
                if (tradeInfo[3].toUpperCase().equals("V"))
                    exch = true;
                else exch = false;

                list.add(new TradeEntry(Timestamp.valueOf("2000-03-01 ".concat(tradeInfo[0])),
                        Float.parseFloat(tradeInfo[1]),
                        Short.parseShort(tradeInfo[2]),
                        exch));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
