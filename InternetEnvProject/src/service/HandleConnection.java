package service;

import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;
import com.google.gson.Gson;
import server.Request;
import service.QuestionService;


public class HandleConnection {

    private final Gson g = new GsonBuilder().create();
    private final Socket someClient;

    public HandleConnection(Socket someClient){
        this.someClient = someClient;
    }

    public boolean process(QuestionService service) throws IOException {
        boolean ret = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.someClient.getInputStream()));

        //Calculating decryption key and decrypting
        int tempKey = 26 - (Integer.parseInt(service.getEncryptionKey()) % 26);
        String decryptKey = String.valueOf(tempKey);
        String line = service.getSymEnc().transform(reader.readLine(), decryptKey) ;
        Request request = g.fromJson(line, Request.class);
        String actionName = request.getAction();

        //authenticate here
        if(service.authenticate(request.getHead())){
            switch (actionName){
                case "AUTH":
                    service.sendEmptyResponse("ALLOW", someClient);
                    break;

                case "GET":
                    service.handleGet(someClient);
                    break;

                case "CHECK":
                    service.handleCheck(request, someClient);
                    break;

                case "SHUTDOWN":
                    ret = true;
                    break;

                default:
                    System.out.println("Bad action");
                    service.sendEmptyResponse("BAD", someClient);
                    break;

            }
        }
        else if(request.getAction().equals("SIGNUP")){
            service.handleSignup(request, someClient);
        }
        else{
            System.out.println("Bad-Auth-Request");
            service.sendEmptyResponse("BAD", someClient);
        }


        return ret;
    }

}

