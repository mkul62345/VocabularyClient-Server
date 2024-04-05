package server;

import dao.QuestionDaoImpl;
import dao.UserDaoImpl;
import service.HandleConnection;
import service.QuestionService;

import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public void start(){
        QuestionService questionService = new QuestionService(new QuestionDaoImpl(), new UserDaoImpl());
        Socket someClient;
        ServerSocket server = null;

        try {
            server = new ServerSocket(12345);
            while(true){
                someClient = server.accept();
                HandleConnection clientConnection = new HandleConnection(someClient);

                //On exit command, break out of loop and finish execution
                if(clientConnection.process(questionService)){
                    break;
                }

            }

        } catch (Exception e) {
            System.out.println("Connection Failed");
        } finally {
            try{
                if(server != null) {
                    server.close();
                }
            } catch (Exception e) {
                System.out.println("Failed to close server socket");
            }

        }

    }

}
