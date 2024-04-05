package dao;

import java.io.*;
import java.util.*;

public class UserDaoImpl implements IDao<String> {

    public static final String FILE_PATH = ".\\src\\appdata\\user_data.txt";

    HashMap<String, String> users;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    public UserDaoImpl() {
        users = new HashMap<String, String>();

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
                    String userTemp = parts[0].trim();
                    String passTemp = parts[1].trim();
                    //String scoreTemp = parts[2].trim();
                    //User tempUser = new User(userTemp, passTemp);
                    //tempUser.setUserScore(Integer.parseInt(scoreTemp));

                    // put in HashMap if they are not empty
                    if (!userTemp.isEmpty() && !passTemp.isEmpty()) {
                        users.put(userTemp, passTemp);
                    }

                }

            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Init failed");
        } finally {
            try {
                if (bufferedWriter != null){
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                System.out.println("Issue closing writer");
            }
            try {
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("Issue closing reader");
            }

        }

    }

    @Override
    public String find(String userName) {
        return users.get(userName);
    }

    @Override
    public boolean save(String userName, String userPass) {
        //If key doesn't exist, will append new record to end of data file.
        boolean successOp = false;
        if(!users.containsKey(userName)){
            //Add to hashmap
            users.put(userName, userPass);
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH, true));
                bufferedWriter.write((userName + ":" + userPass));
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
                    System.out.println("Saving user failed, IO");
                }

            }

        }

        return successOp;
    }

    private boolean emptyDB() throws IOException, ClassNotFoundException {
        File file = new File(FILE_PATH);

        return !file.exists() || file.length() == 0;
    }


}

