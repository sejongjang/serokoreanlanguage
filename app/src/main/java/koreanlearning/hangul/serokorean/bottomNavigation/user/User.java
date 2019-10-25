package koreanlearning.hangul.serokorean.bottomNavigation.user;

public class User {

    private static User user;
    private String authtoken;

    // constructor is private
    private User() {

    }

    //returns the instance of the User
    public static User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
