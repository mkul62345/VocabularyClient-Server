package com.example.wordquizclient.model;

public class Poll implements java.io.Serializable {

    private final String PollText;
    private final int CorrectAnswer;
    private int SelectedAnswer;
    private final String[] Options;

    public Poll(String[] wordArgs, int correctAnswerArgs, String textArgs){
        PollText = textArgs;
        CorrectAnswer = correctAnswerArgs;
        Options = wordArgs;
    }


    public String getPollText() {
        return PollText;
    }

    public String[] getPollWords() {
        return Options;
    }

    public String getOption(int index)
    {
        return Options[index];
    }

    public int getCorrectAnswer()
    {
        return CorrectAnswer;
    }

    public int getSelectedAnswer()
    {
        return SelectedAnswer;
    }

    public void setSelectedAnswer(int input)
    {
        this.SelectedAnswer = input;
    }



}


