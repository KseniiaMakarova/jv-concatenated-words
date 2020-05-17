import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WordsAnalyser {
    private static final int CHAR_TO_INT_HELPER = 97;

    public List<String> getConcatenatedWordsFromUrl(URL file) {
        ResultData.initializeData();
        List<String> wordList = new ArrayList<>();
        try {
            wordList = readWordsFromUrl(file);
        } catch (IOException e) {
            System.out.println("There is no such file!");
        }
        return getAllConcatenatedWords(wordList);
    }

    public String getLongestWord() {
        return ResultData.longestWord;
    }

    public String getSecondLongestWord() {
        return ResultData.secondLongestWord;
    }

    private List<String> readWordsFromUrl(URL file) throws IOException {
        List<String> wordList = new ArrayList<>();
        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(file.openStream()))) {
            String word;
            while ((word = reader.readLine()) != null) {
                if (!word.isEmpty()) {
                    wordList.add(word);
                    ResultData.wordLists[findRow(word)][findCol(word)].add(word);
                }
            }
        }
        return wordList;
    }

    private List<String> getAllConcatenatedWords(List<String> wordList) {
        List<String> concatenatedWords = new ArrayList<>();
        for (String word : wordList) {
            if (word.length() > 3 && isConcatenated(word, word)) {
                concatenatedWords.add(word);
                String longestWord = ResultData.longestWord;
                String secondLongestWord = ResultData.secondLongestWord;
                if (word.length() > longestWord.length()) {
                    ResultData.secondLongestWord = longestWord;
                    ResultData.longestWord = word;
                } else if (word.length() > secondLongestWord.length()) {
                    ResultData.secondLongestWord = word;
                }
            }
        }
        return concatenatedWords;
    }

    private boolean isConcatenated(String word, String originalWord) {
        return word.isEmpty()
                || word.length() > 1
                && ResultData.wordLists[findRow(word)][findCol(word)].stream()
                .anyMatch(element -> word.contains(element)
                        && !element.equals(originalWord)
                        && isConcatenated(word.replace(element, ""), originalWord));
    }

    private int findRow(String word) {
        return word.charAt(0) - CHAR_TO_INT_HELPER;
    }

    private int findCol(String word) {
        return word.charAt(1) - CHAR_TO_INT_HELPER;
    }

    private static class ResultData {
        static final int LATIN_ALPHABET_LENGTH = 26;
        static String longestWord;
        static String secondLongestWord;
        static List<String>[][] wordLists;

        static void initializeData() {
            wordLists = new ArrayList[LATIN_ALPHABET_LENGTH][LATIN_ALPHABET_LENGTH];
            for (int i = 0; i < wordLists.length; i++) {
                for (int j = 0; j < wordLists[i].length; j++) {
                    wordLists[i][j] = new ArrayList<>();
                }
            }
            longestWord = secondLongestWord = "";
        }
    }
}
