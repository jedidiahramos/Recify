package com.example.recify;

public class Trie {
    TrieNode root;
    void insert(Ingredient ingredient) {
        int level;
        String word = ingredient.ingredientName;
        int length = word.length();
        int letter;
        TrieNode currNode = root;
        for (level = 0; level < length; level++) {
            letter = word.charAt(level);
            if (letter == ' ') {
                letter = 26;
            } else {
                letter -= 'a';
            }
            if (currNode.children[letter] == null) {
                currNode.children[letter] = new TrieNode();
            }
            currNode = currNode.children[letter];
        }
        currNode.wordEnd = true;
        currNode.ingredient = ingredient;
    }
    Ingredient search(String word) {
        int level;
        int length = word.length();
        int letter;
        TrieNode currNode = root;
        for (level = 0; level < length; level++) {
            letter = word.charAt(level);
            if (letter == ' ') {
                letter = 26;
            } else {
                letter -= 'a';
            }
            if (currNode.children[letter] == null) {
                return null;
            }
            currNode = currNode.children[letter];
        }
        if (currNode.wordEnd) {
            return currNode.ingredient;
        } else {
            for (int i = 0; i < 27; i++) {
                if (currNode.children[i] != null) {
                    currNode = currNode.children[i];
                    if (currNode.wordEnd) {
                        return currNode.ingredient;
                    }
                }
            }
            return null;
        }
    }
}
