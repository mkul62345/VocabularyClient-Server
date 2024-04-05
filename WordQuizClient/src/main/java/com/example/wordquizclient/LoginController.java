package com.example.wordquizclient;

import com.example.wordquizclient.model.Request;
import com.example.wordquizclient.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Map;

public class LoginController {

    @FXML
    private Label logInTitle;

    @FXML
    public TextField userBox;

    @FXML
    public TextField passBox;

    private Client client;

    @FXML
    protected void initialize() {
        client = new Client();

    }


    //Change to legitimately authenticate from textboxes
    private boolean sendRequest(String argAction) {
        Map<String, Object> head = Map.ofEntries(
                Map.entry("UserName", userBox.getText()),
                Map.entry("UserPass", passBox.getText())
        );
        Request request = new Request(argAction, head, head);

        //Open connection and send request
        Request currentResponse = client.sendToServer(request);

        return currentResponse.getAction().equals("BAD");
    }


    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException{
        if(sendRequest("AUTH")){
            logInTitle.setText("Login Failed, try again!");
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("quiz-view.fxml"));
            Parent root = loader.load();

            QuizController quizController = loader.getController();
            quizController.initialize(new User(userBox.getText(), passBox.getText()));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 300, 300);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    protected void onSignupButtonClick() {
        if(sendRequest("SIGNUP")){
            logInTitle.setText("Signup Failed, try again!");
        }
        else{
            logInTitle.setText("Signup success, try logging in!");
        }

    }

}
