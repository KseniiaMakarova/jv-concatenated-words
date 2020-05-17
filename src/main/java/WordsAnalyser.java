import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WordsAnalyser {
    public List<String> getConcatenatedWordsFromUrl(URL file) {
        List<String> wordList = new ArrayList<>();
        ResultData resultData = new ResultData();
        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(file.openStream()))) {
            String word;
            while ((word = reader.readLine()) != null) {
                if (!word.isEmpty()) {
                    wordList.add(word);
                    resultData.sortWord(word);
                }
            }
        } catch (IOException e) {
            System.out.println("There is no such file!");
        }
        return resultData.getAllConcatenatedWords(wordList);
    }

    public String getLongestWord() {
        return ResultData.longestWord;
    }

    public String getSecondLongestWord() {
        return ResultData.secondLongestWord;
    }

    private static class ResultData {
        static final int CHAR_TO_INT_HELPER = 97;
        static final int LATIN_ALPHABET_LENGTH = 26;
        static String longestWord;
        static String secondLongestWord;
        List<String>[][] wordLists;

        ResultData() {
            wordLists = new ArrayList[LATIN_ALPHABET_LENGTH][LATIN_ALPHABET_LENGTH];
            for (int i = 0; i < wordLists.length; i++) {
                for (int j = 0; j < wordLists[i].length; j++) {
                    wordLists[i][j] = new ArrayList<>();
                }
            }
            longestWord = secondLongestWord = "";
        }

        void sortWord(String word) {
            wordLists[findRow(word)][findCol(word)].add(word);
        }

        List<String> getAllConcatenatedWords(List<String> wordList) {
            List<String> concatenatedWords = new ArrayList<>();
            for (String word : wordList) {
                if (word.length() > 3 && isConcatenated(word, word)) {
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

        boolean isConcatenated(String word, String originalWord) {
            return word.isEmpty()
                    || word.length() > 1
                    && wordLists[findRow(word)][findCol(word)].stream()
                    .anyMatch(element -> word.contains(element)
                            && !element.equals(originalWord)
                            && isConcatenated(word.replace(element, ""), originalWord));
        }

        int findRow(String word) {
            return word.charAt(0) - CHAR_TO_INT_HELPER;
        }

        int findCol(String word) {
            return word.charAt(1) - CHAR_TO_INT_HELPER;
        }
    }
}
