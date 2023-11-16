package com.rag.rxjavatest2.pojo;

public class CountryStat {
    private String country;
    private int population;
    private Stat cases;
    private Stat deaths;
    private Stat tests;

    public String getCountry() {
        return country;
    }

    public int getPopulation() {
        return population;
    }

    public Stat getCases() {
        return cases;
    }

    public Stat getDeaths() {
        return deaths;
    }

    public Stat getTests() {
        return tests;
    }

    public static class Stat {
        private int total;

        public Stat(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }
    }


}
