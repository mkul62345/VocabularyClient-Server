package com.example.wordquizclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WordsApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoaderLogin = new FXMLLoader(WordsApplication.class.getResource("login-view.fxml"));
            Parent root = fxmlLoaderLogin.load();
            Scene sceneLogin = new Scene(root, 300, 300);
            stage.setTitle("Quiz!");
            stage.setScene(sceneLogin);
            stage.show();
        } catch (Exception e){
            System.out.println("Failed to init app");
        }

    }

    public static void main(String[] args) {
        launch();
    }
}