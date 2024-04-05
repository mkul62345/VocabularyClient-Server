package service;

import CryptoAlgos.SymmetricEncrypter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dao.QuestionDaoImpl;
import dao.UserDaoImpl;
import dm.Poll;
import server.Request;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

public class QuestionService {

    private final String encryptionKey = "119";
    private SymmetricEncrypter symEnc;

    Gson g = new GsonBuilder().create();

    QuestionDaoImpl questionDao;
    UserDaoImpl userDao;

    public SymmetricEncrypter getSymEnc(){
        return symEnc;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public QuestionService(QuestionDaoImpl questionDao, UserDaoImpl userDao) {
        this.questionDao = questionDao;
        this.userDao = userDao;
        symEnc = new SymmetricEncrypter();
    }

    public String getWordDescription(String word) {
        return questionDao.find(word);
    }

    public String retrieveUserPass(String argUsername){
        return userDao.find(argUsername);
    }

    public Poll MakePoll() {
        Random rng = new Random();
        HashSet<String> selectedWords = new HashSet<String>();
        while (selectedWords.size() < 5) {
            //Select random key and get random word
            selectedWords.add(questionDao.getKeysAsArray().get(rng.nextInt(questionDao.getKeysAsArray().size())));

        }
        int correctNumber = rng.nextInt(5);
        String[] wordArray = selectedWords.toArray(new String[5]);

        return new Poll(wordArray, correctNumber, questionDao.getDaoMap().get(wordArray[correctNumber]));
    }

    public boolean authenticate(Map<String, Object> userAsMap) {
        boolean authSuccess = false;
        String userName = (String) userAsMap.get("UserName");
        String userPass = retrieveUserPass(userName);
        if(userPass != null && userPass.equals( (String) userAsMap.get("UserPass"))){
            authSuccess = true;
        }

        return authSuccess;
    }

    public void handleGet(Socket myClient) throws IOException{
        String response = makePollResponse(MakePoll());
        sendResponse(response, myClient);
    }

    public void handleCheck(Request argRequest, Socket myClient) throws IOException{
        //Check if answer in received request is correct
        double correct = (double) argRequest.getBody().get("CorrectAnswer");
        double selected = (double) argRequest.getBody().get("SelectedAnswer");
        if (correct == selected){
            handleGet(myClient);
            System.out.println("Good job");
        }
        else{
            argRequest.setAction("BAD");
            sendResponse(g.toJson(argRequest, argRequest.getClass()), myClient);
            System.out.println("Incorrect :(");
        }

    }

    public void handleSignup(Request argRequest, Socket myClient) throws IOException {
        //Check if answer in received request is correct
        String userName = (String) argRequest.getHead().get("UserName");
        String userPass = (String) argRequest.getHead().get("UserPass");

        if (userDao.save(userName, userPass)){
            argRequest.setAction("GOOD");
            System.out.println("User added");
        }
        else{
            argRequest.setAction("BAD");
            System.out.println("User exists :(");
        }

        sendResponse(g.toJson(argRequest, argRequest.getClass()), myClient);
    }

    public void sendEmptyResponse(String argAction, Socket myClient) throws IOException{
        Type refRequest = new TypeToken<Request>() {}.getType();
        Map<String, Object> emptyBody = Map.ofEntries(
                Map.entry("UserName", "None"),
                Map.entry("UserPass", "None")
        );
        Request response = new Request(argAction, emptyBody, emptyBody);
        String js = g.toJson(response, refRequest);
        sendResponse(js, myClient);
    }

    private String makePollResponse(Poll argPoll){
        Type refMap = new TypeToken<Map<String, Object>>() {}.getType();
        Type refRequest = new TypeToken<Request>() {}.getType();
        Type refPoll = new TypeToken<Poll>(){}.getType();
        String js = g.toJson(argPoll, refPoll);
        Map<String, Object> emptyHead = Map.ofEntries(
                Map.entry("UserName", "None"),
                Map.entry("UserPass", "None")
        );
        Request response = new Request("ALLOW", emptyHead, g.fromJson(js, refMap));
        js = g.toJson(response, refRequest);

        return js;
    }

    private void sendResponse(String responseString, Socket myClient) throws IOException{
        PrintWriter writer = new PrintWriter(myClient.getOutputStream());
        symEnc = new SymmetricEncrypter();
        writer.println(symEnc.transform(responseString, encryptionKey));
        writer.flush();
        writer.close();
    }

}


