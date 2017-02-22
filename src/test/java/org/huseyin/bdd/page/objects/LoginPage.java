package org.huseyin.bdd.page.objects;

public class LoginPage extends AbstractPage {

    public static final String PATH = "/login";

    public LoginPage() {
        super(PATH);
    }

    public void login() {
        //TODO
    }

    public void login(String username, String secret) {
        //TODO
    }

    public boolean isNotLoggedIn() {
        //TODO
        return true;
    }

    public boolean isLoginFailed() {
        //TODO
        return true;
    }

    public boolean isAuthorised() {
        //TODO
        return true;
    }
}
