package bzu.androidstudioproject.loggedinuser;

import bzu.androidstudioproject.registration.User;

public class LoggedInUser {
    public static User loggedIn ;

    private LoggedInUser() {  }

    public static User getLoggedInUser()
    {
        return loggedIn;
    }
    public static void setLoggedInUser(User user)
    {
        loggedIn = user;
    }

}
