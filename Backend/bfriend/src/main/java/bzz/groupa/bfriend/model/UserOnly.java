package bzz.groupa.bfriend.model;

import java.util.Date;

public interface UserOnly {
    String getFirstname();

    String getEmail();

    String getLastName();

    Date getAccountCreated();

    Date getLastLogin();
}
