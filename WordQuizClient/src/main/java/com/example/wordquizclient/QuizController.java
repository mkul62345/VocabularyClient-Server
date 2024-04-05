package com.example.wordquizclient;
import com.example.wordquizclient.model.Request;
import com.example.wordquizclient.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import java.util.Map;

public class QuizController {

    @FXML
    private Label wordDescription;

    @FXML
    private RadioButton radio1;

    @FXML
    private RadioButton radio2;

    @FXML
    private RadioButton radio3;

    @FXML
    private RadioButton radio4;

    @FXML
    private RadioButton radio5;

    private Client client;
    private Request currentResponse;

    private User currentUser;

    @FXML
    protected void initialize(User argUser) {
        client = new Client();
        currentUser = argUser;

        /////Form request
        String action = "GET";
        Map<String, Object> head = Map.ofEntries(
                Map.entry("UserName", currentUser.getUserName()),
                Map.entry("UserPass", currentUser.getUserPass())
        );
        Request request = new Request(action, head, head);

        //Request poll and display it
        currentResponse = client.sendToServer(request);
        DisplayPoll();

    }

    private void DisplayPoll(){
        try {
            ToggleGroup answerGroup = new ToggleGroup();
            RadioButton[] radioArr = {radio1, radio2, radio3, radio4, radio5};
            int index = 0;

            //Displaying poll response data in page.
            wordDescription.setText( (String) currentResponse.getBody().get("PollText"));
            String temp = currentResponse.getBody().get("Options").toString();
            String[] arrOfOptions = temp.substring(1, temp.length() - 1).split(", ", 5);
            for (RadioButton rad : radioArr) {
                rad.setText(arrOfOptions[index]);
                rad.setToggleGroup(answerGroup);
                index++;
            }

        } catch (Exception e){
            System.out.println("Loading poll Failed");
        }

    }

    private Map<String , Object> generateAuthHeader(){
        Map<String , Object> authHeader = Map.ofEntries(
                Map.entry("UserName", currentUser.getUserName()),
                Map.entry("UserPass", currentUser.getUserPass())
        );

        return authHeader;
    }

    @FXML
    protected void onSubmitButtonClick(){
        try {
            int index = 0;
            RadioButton[] radioArr = { radio1, radio2, radio3, radio4, radio5 };
            Request response = new Request("CHECK", generateAuthHeader(), currentResponse.getBody());

            //Get selected option
            for(RadioButton rad : radioArr) {
                if (rad.isSelected()){
                    response.getBody().replace("SelectedAnswer", index);
                    break;
                }

                index++;
            }

            //Send Check request
            currentResponse = client.sendToServer(response);
            if(currentResponse.getAction().equals("ALLOW")){
                DisplayPoll();
            }
            else{
                wordDescription.setText((String) currentResponse.getBody().get("PollText") + " | Incorrect, try again");
            }


        } catch (Exception e) {
            System.out.println("Submit Failed");
        }

    }




}
