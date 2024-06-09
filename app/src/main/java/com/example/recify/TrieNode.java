package com.example.recify;

public class TrieNode {
    static final int alphabetSize = 27;
    TrieNode[] children = new TrieNode[alphabetSize];
    boolean wordEnd;
    Ingredient ingredient;
    TrieNode() {
        wordEnd = false;
        for (int i = 0; i < alphabetSize; i++) {
            children[i] = null;
        }
    }
}
