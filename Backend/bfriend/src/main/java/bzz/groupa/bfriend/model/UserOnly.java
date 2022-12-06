package bzz.groupa.bfriend.model;

import java.util.Date;

public interface UserOnly {
    long getId();

    String getUsername();

    Date getAccountCreated();

    Date getLastLogin();
}
