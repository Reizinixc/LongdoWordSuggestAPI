package com.github.Reizinixc.wordsuggester;

import java.util.Set;

public interface WordSuggester {
   Set<String> getSuggestWords(String word);
}
