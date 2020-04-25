import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WordsAnalyser {
    private static final int CHAR_TO_INT_HELPER = 97;
    private static final int LATIN_ALPHABET_LENGTH = 26;
    private final List<String>[][] buckets;
    private String originalWord;
    private String longestWord;
    private String secondLongestWord;

    public WordsAnalyser() {
        buckets = new ArrayList[LATIN_ALPHABET_LENGTH][LATIN_ALPHABET_LENGTH];
        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < buckets[i].length; j++) {
                buckets[i][j] = new ArrayList<>();
            }
        }
        longestWord = secondLongestWord = "";
    }

    public List<String> examineUrl(URL file) {
        List<String> wordList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(file.openStream()))) {
            String word;
            while ((word = reader.readLine()) != null) {
                if (!word.isEmpty()) {
                    wordList.add(word);
                    buckets[findRow(word)][findCol(word)].add(word);
                }
            }
        } catch (IOException e) {
            System.out.println("There is no such file!");
        }
        return getAllConcatenatedWords(wordList);
    }

    public String getLongestWord() {
        return longestWord;
    }

    public String getSecondLongestWord() {
        return secondLongestWord;
    }

    private List<String> getAllConcatenatedWords(List<String> wordList) {
        List<String> concatenatedWords = new ArrayList<>();
        for (String word : wordList) {
            originalWord = word;
            if (word.length() > 3 && isConcatenated(word)) {
                concatenatedWords.add(word);
                if (word.length() > longestWord.length()) {
                    secondLongestWord = longestWord;
                    longestWord = word;
                } else if (word.length() > secondLongestWord.length()) {
                    secondLongestWord = word;
                }
            }
        }
        return concatenatedWords;
    }

    private boolean isConcatenated(String word) {
        return word.isEmpty()
                || word.length() > 1
                && buckets[findRow(word)][findCol(word)].stream()
                    .anyMatch(element -> word.contains(element)
                        && !element.equals(originalWord)
                        && isConcatenated(word.replace(element, "")));
    }

    private int findRow(String word) {
        return word.charAt(0) - CHAR_TO_INT_HELPER;
    }

    private int findCol(String word) {
        return word.charAt(1) - CHAR_TO_INT_HELPER;
    }
}
