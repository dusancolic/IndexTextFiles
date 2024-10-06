package org.example;

import java.util.Arrays;
import java.util.List;

public class SpaceTokenizer implements Tokenizer {
    @Override
    public List<String> tokenize(String content) {
        return Arrays.asList(content.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+"));    }
}
