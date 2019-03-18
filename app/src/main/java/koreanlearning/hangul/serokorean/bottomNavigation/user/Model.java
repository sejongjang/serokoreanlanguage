package koreanlearning.hangul.serokorean.bottomNavigation.user;

public class Model {

    private static Model model;
    private String authtoken;

    // constructor is private
    private Model() {

    }

    //returns the instance of the Model
    public static Model getModel() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
