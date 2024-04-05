package dao;

import java.io.*;
import java.util.*;

public class QuestionDaoImpl implements IDao<String> {

    public static final String FILE_PATH = ".\\src\\appdata\\question_data.txt";

    List<String> keysAsArray;
    HashMap<String, String> words;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    public QuestionDaoImpl() {
        words = new HashMap<String, String>();

        try {
            boolean emptyData = emptyDB();
            if(emptyData) {
                bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH));
                bufferedWriter.write("");
                bufferedWriter.flush();
            }
            else
            {
                bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
                String line;
                // read file line by line
                while ((line = bufferedReader.readLine()) != null) {
                    // split the line by :
                    String[] parts = line.split(":");

                    // first part is name, second is number
                    String wordTemp = parts[0].trim();
                    String descriptionTemp = parts[1].trim();

                    // put in HashMap if they are not empty
                    if (!wordTemp.isEmpty() && !descriptionTemp.isEmpty()) {
                        words.put(wordTemp, descriptionTemp);
                    }

                }

            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Init failed");
        } finally {
            keysAsArray = new ArrayList<String>(words.keySet());
            try {
                if (bufferedWriter != null){
                    bufferedWriter.close(); //close BufferedOutputStream
                    //it will close FileOutputStream
                }
            } catch (IOException e) {
                System.out.println("Issue closing writer");
            }
            try {
                if (bufferedReader != null){
                    bufferedReader.close(); //close BufferedOutputStream
                    //it will close FileOutputStream
                }
            } catch (IOException e) {
                System.out.println("Issue closing reader");
            }

        }

    }

    @Override
    public String find(String word) {
         return words.get(word);
    }

    @Override
    public boolean save(String word, String description) {
        //If key doesn't exist, will append new record to end of data file.
        boolean successOp = words.containsKey(word);
        if(!successOp) {
            //Add to hashmap and to keyarray
            words.put(word, description);
            keysAsArray.add(word);
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH, true));
                bufferedWriter.write((word + ":" + description));
                bufferedWriter.newLine();
                bufferedWriter.flush();
                successOp = true;

            } catch (IOException e) {
                System.out.println("Save to file failed");
            }finally {
                try {
                    if (bufferedWriter != null){
                        bufferedWriter.close();
                    }
                } catch (IOException e) {
                    System.out.println("Saving word failed, IO");
                }

            }

        }

        return successOp;
    }

    private boolean emptyDB() throws IOException, ClassNotFoundException {
        File file = new File(FILE_PATH);

        return !file.exists() || file.length() == 0;
    }

    public Map<String , String> getDaoMap(){
        return this.words;
    }

    public List<String> getKeysAsArray(){
        return this.keysAsArray;
    }


}

