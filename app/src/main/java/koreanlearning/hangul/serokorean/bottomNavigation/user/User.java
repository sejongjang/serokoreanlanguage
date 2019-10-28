package koreanlearning.hangul.serokorean.bottomNavigation.user;

public class User {

    private static User user;
    private String authtoken;
    private String firstname;
    private String lastname;
    private String email;
    private String userId;
    private String photoURL;

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

    public static void setUser(User user) {
        User.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
