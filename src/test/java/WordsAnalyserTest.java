import java.net.MalformedURLException;
import java.net.URL;

public class WordsAnalyserTest {
    public static void main(String[] args) throws MalformedURLException {
        WordsAnalyser analyser = new WordsAnalyser();
        URL fileUrl = new URL
                ("https://mate-academy.github.io/jv-program-fulltime/test_assigment/words_problem/wordsforproblem.txt");
        analyser.readFromUrl(fileUrl);
        System.out.println("The longest concatenated word is "
                + analyser.getLongestWord() + " characters long");
        System.out.println("The second longest concatenated word is "
                + analyser.getSecondLongestWord() + " characters long");
        System.out.println("Total number of concatenated words is " + analyser.getTotalWords());
    }
}
