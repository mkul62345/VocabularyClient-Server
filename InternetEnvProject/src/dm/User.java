package dm;

public class User {
    private String UserName;
    private String UserPass;
    private int UserScore;
    public String getUserName()
    {
        return UserName;
    }

    public void setUserName(String input)
    {
        this.UserName = input;
    }

    public String getUserPass()
    {
        return UserPass;
    }

    public void setUserScore(int argScore)
    {
        this.UserScore++;
    }

    public int getUserScore()
    {
        return UserScore;
    }

    public void setUserPass(String input)
    {
        this.UserPass = input;
    }

    public User(String userName, String userPass)
    {
        this.UserName = userName;
        this.UserPass = userPass;
        this.UserScore = 0;
    }

}
