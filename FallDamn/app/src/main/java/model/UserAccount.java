package model;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public final class UserAccount{

    private static UserAccount instance = null;

    private GoogleSignInAccount account;

    /**
     * Constructeur de l'objet.
     */
    private UserAccount() {
        super();
    }

    /**
     * Méthode permettant de renvoyer une instance de la classe Singleton
     * @return Retourne l'instance du singleton.
     */
    public final static UserAccount getInstance() {
        if (UserAccount.instance == null) {
            synchronized(UserAccount.class) {
                if (UserAccount.instance == null) {
                    UserAccount.instance = new UserAccount();
                }
            }
        }
        return UserAccount.instance;
    }

    public GoogleSignInAccount getAccount() {
        return account;
    }

    public void setAccount(GoogleSignInAccount account){
        this.account = account;
    }
}