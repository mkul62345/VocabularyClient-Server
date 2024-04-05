package com.example.wordquizclient;

import com.example.wordquizclient.model.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;

public class Client {
    final Gson gson = new Gson();
    private final String encryptionKey = "119";

    private final SymmetricEncrypter symEnc;

    public Client(){
        symEnc = new SymmetricEncrypter();
    }

    public Request sendToServer(Request argRequest) {
        Type refRequest = new TypeToken<Request>(){}.getType();
        String messageFromServer = "";
        Request returnRequest = null;

        try (Socket myServer = new Socket("127.0.0.1", 12345);
             BufferedReader reader = new BufferedReader(new InputStreamReader(myServer.getInputStream()));
             PrintWriter writer = new PrintWriter(myServer.getOutputStream())
        ){
            //Send GET request to server
            String send = gson.toJson(argRequest);
            writer.println(symEnc.transform(send, encryptionKey));
            writer.flush();

            //Calculating decryption key
            int tempKey = 26 - (Integer.parseInt(encryptionKey) % 26);
            String decryptKey = String.valueOf(tempKey);

            //Await response and decrypting
            messageFromServer = symEnc.transform(reader.readLine(), decryptKey) ;
            returnRequest = gson.fromJson(messageFromServer, refRequest);

        } catch (IOException | ClassCastException e) {
            System.out.println("Send to server failed");
        }

        return returnRequest;
    }

}
