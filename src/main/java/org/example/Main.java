package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    /*
       Please write a Java application that provides a service for indexing text files.
       The console interface should allow for (i) specifying the indexed files and directories and (ii) querying files containing a given word.
       The library should be extensible by the tokenization algorithm (simple splitting by words/support lexers/etc.).
       State persistence between running sessions is not needed, but the application should be able to serve consecutive requests.
       Providing some tests and usage examples is advised. Usage of external libraries is discouraged.

       The result should be provided as a link to the repository with code on GitHub.*/


    /* usage example:
    Options: [1] Index files, [2] Search word, [0] Exit
    1

    Enter file/s (if you want to enter multiple files, split them with ',') or directory path:
       C:\Users\User\Desktop\IndexTextFiles\src\main\resources\1.txt

    Indexing file: 1.txt

    Options: [1] Index files, [2] Search word, [0] Exit
    1

    Enter file/s (if you want to enter multiple files, split them with ',') or directory path:
       C:\Users\User\Desktop\IndexTextFiles\src\main\resources\2.txt, C:\Users\User\Desktop\IndexTextFiles\src\main\resources\3.txt

    Indexing file: 2.txt
    Indexing file: 3.txt

    Options: [1] Index files, [2] Search word, [0] Exit
    2

    Enter word to search:
    word

    Files containing "words":
    C:\Users\User\Desktop\IndexTextFiles\src\main\resources\2.txt

    Options: [1] Index files, [2] Search word, [0] Exit
    0
     */



    private final FileIndexer fileIndexer;

    public Main() {
        this.fileIndexer = new FileIndexer(new SpaceTokenizer());
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Options: [1] Index files, [2] Search word, [0] Exit");
            String option = scanner.nextLine().trim();

            switch(option)
            {
                case "1":
                    System.out.println("Enter file/s (if you want to enter multiple files, split them with ',') or directory path:");
                    String input = scanner.nextLine().trim();

                    //
                    List<String> paths = Arrays.stream(input.split(","))
                            .map(String::trim)
                            .filter(path -> !path.isEmpty())  // Filter out empty entries (in case of ", ,")
                            .toList();

                    // Check if at least one valid path was provided
                    if (paths.isEmpty()) {
                        System.out.println("Please provide valid paths separated by commas.");
                        break;
                    }
                    try {
                        for(String path : paths)
                             fileIndexer.indexFile(new File(path));
                    } catch (IOException e) {
                        System.out.println("Error indexing file: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.println("Enter word to search:");
                    String word = scanner.nextLine().trim();
                    Set<String> files = fileIndexer.search(word);
                    if(files.isEmpty())
                        System.out.println("No files found.");

                    else{
                        System.out.println("Files containing \"" + word + "\":");
                        for (String filePath : files) {
                            System.out.println(filePath);
                        }
                    }
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }
}