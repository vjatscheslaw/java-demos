package com.example.demo.potroshitel;

public class TerminatorQuoter implements Quoter {

    private String quote;

    @InjectRandomInt(min = 1, max = 10)
    private int repeat;

    public void init() {
        System.out.println("Phase 2");
    }

    public TerminatorQuoter() {
        System.out.println("Phase 1");
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public void sayQuote() {
        System.out.println("Says: " + quote + " ");
    }

    @Override
    public String returnQuote() {
        return quote + " " + repeat;
    }
}
