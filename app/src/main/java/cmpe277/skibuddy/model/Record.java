package cmpe277.skibuddy.model;

import java.util.Date;

/**
 * Created by yellowstar on 11/25/15.
 */
public class Record {
    private Date startTime;
    private Date endTime;
    private double distance;
    private Path path;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = new Date(startTime);
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = new Date(endTime);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
