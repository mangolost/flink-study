package com.mangolost.flink;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WordWithCount {

    private String word;
    private int count;

    public WordWithCount() {

    }

    public WordWithCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
