package org.example;

import java.io.*;
import java.util.*;

public class FileIndexer {
    private final Tokenizer tokenizer;
    private final Map<String, Set<String>> wordIndex = new HashMap<>();

    public FileIndexer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void indexFile(File file) throws FileNotFoundException, IOException{

            if(file.isDirectory())
            {
                for(File f : Objects.requireNonNull(file.listFiles()))
                {
                    indexFile(f);
                }
            }

            else if (file.isFile() && file.getName().endsWith(".txt"))
            {
                System.out.println("Indexing file: " + file.getName());
                try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder content = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    List<String> words = tokenizer.tokenize(content.toString());
                    for (String word : words) {
                        wordIndex.computeIfAbsent(word, k -> new HashSet<>()).add(file.getAbsolutePath());
                    }
                }
            }
    }

    public Set<String> search(String word) {
        return wordIndex.getOrDefault(word.toLowerCase(), Collections.emptySet());
    }
}
