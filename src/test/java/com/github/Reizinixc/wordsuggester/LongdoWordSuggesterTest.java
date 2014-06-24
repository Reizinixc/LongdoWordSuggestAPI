package com.github.Reizinixc.wordsuggester;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class LongdoWordSuggesterTest {
   private WordSuggester wordSuggester;
   private static final Comparator<String> SUGGEST_WORD_SET_POLICY = new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
         return o1.compareToIgnoreCase(o2);
      }
   };

   @Before
   public void setUp() throws Exception {
      wordSuggester = new LongdoWordSuggester(20);
   }

   @Test
   public void testGetSuggestWords() throws Exception {
      Set<String> actualSuggestSet = new TreeSet<String>(SUGGEST_WORD_SET_POLICY);
      actualSuggestSet.add("Type");
      actualSuggestSet.add("Type and type-founding");
      actualSuggestSet.add("type d'appareil");
      actualSuggestSet.add("type d'avion");
      actualSuggestSet.add("type de billet");
      actualSuggestSet.add("type de donn\u00E9es");
      actualSuggestSet.add("type de voiture");
      actualSuggestSet.add("Type specimens");
      actualSuggestSet.add("Type specimens (Natural history)");

      Set<String> expectedSuggestSet = wordSuggester.getSuggestWords("type");

      assertEquals(expectedSuggestSet, actualSuggestSet);
   }

}
