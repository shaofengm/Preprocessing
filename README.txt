#BETA TESTING...
To use the library, download all the files and follow the instructions below. You may want to follow "2. case study" to try how to run it on a sample dataset.
1. how to run preprocessing
(1) go to config/config.txt, change the two paths;
(2) cd to Preprocessing folder(where you can ls and see Preprocess.java in it), compile the java code with this command:
    javac Preprocess.java
(2) run preprocessing with this command:
    java Preprocess config_file_path dataset_path
  
2. case study: preprocess the folder data/SAMPLE
(1) leave config/config.txt unchanged;
(2) compile the java code:
    javac Preprocess.java
(3) run preprocessing with the command:
    java Preprocess config/config.txt data/SAMPLE/
(4) check ./data to see the results

3. how to change stoplist
if you want to add more words to stoplist, just put the words in a text file, then put the text file under Preprocessing/flpfiles/stoplist

4. how to change stemming
if you want to add more word pairs to stemming table, just put the word pairs in a text file, then put the text file under Preprocessing/flpfiles/stemming
