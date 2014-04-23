package th.ac.ku.ndsrl.kunemata.api.wordsuggest.longdo;

import java.util.Set;

public interface WordSuggester {
   Set<String> getSuggestWords(String word);
}
