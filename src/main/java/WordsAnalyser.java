import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class WordsAnalyser {
    private static final int CHAR_TO_INT_HELPER = 97;
    private static final int LATIN_ALPHABET_LENGTH = 26;
    private int longestWord;
    private int secondLongestWord;
    private int totalWords;
    private ArrayList<String>[][] buckets;
    private ArrayList<String> wordList;
    private String originalWord;

    public WordsAnalyser() {
        buckets = new ArrayList[LATIN_ALPHABET_LENGTH][LATIN_ALPHABET_LENGTH];
        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < buckets[i].length; j++) {
                buckets[i][j] = new ArrayList<>();
            }
        }
        wordList = new ArrayList<>();
    }

    public void readFromUrl(URL file) {
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(file.openStream()))) {
            String word;
            while ((word = reader.readLine()) != null) {
                if (!word.isEmpty()) {
                    wordList.add(word);
                    buckets[findRow(word)][findCol(word)].add(word);
                }
            }
            analyseWords();
        } catch (IOException e) {
            System.out.println("There is no such file!");
        }
    }

    public int getLongestWord() {
        return longestWord;
    }

    public int getSecondLongestWord() {
        return secondLongestWord;
    }

    public int getTotalWords() {
        return totalWords;
    }

    private void analyseWords() {
        for (String word : wordList) {
            originalWord = word;
            if (word.length() > 3 && isConcatenated(word)) {
                totalWords++;
                if (word.length() > longestWord) {
                    secondLongestWord = longestWord;
                    longestWord = word.length();
                } else if (word.length() > secondLongestWord) {
                    secondLongestWord = word.length();
                }
            }
        }
    }

    private boolean isConcatenated(String word) {
        if (word.length() == 1) {
            return false;
        }
        if (word.isEmpty()) {
            return true;
        }
        ArrayList<String> bucket = buckets[findRow(word)][findCol(word)];
        for (String element : bucket) {
            if (word.contains(element) && !element.equals(originalWord)
                    && isConcatenated(word.replace(element, ""))) {
                return true;
            }
        }
        return false;
    }

    private int findRow(String word) {
        return word.charAt(0) - CHAR_TO_INT_HELPER;
    }

    private int findCol(String word) {
        return word.charAt(1) - CHAR_TO_INT_HELPER;
    }
}
