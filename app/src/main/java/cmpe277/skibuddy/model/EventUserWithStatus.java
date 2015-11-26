package cmpe277.skibuddy.model;

/**
 * Created by yellowstar on 11/25/15.
 */
public class EventUserWithStatus {
    private User user;
    private int status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
