package com.example.zyandeep.wordlistsqlite;

public class WordItem {

    private int id;
    private String word;
    private String desc;

    public WordItem() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "WordItem{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}