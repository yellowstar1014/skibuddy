package cmpe277.skibuddy.model;

import com.google.gson.annotations.Expose;

/**
 * Created by yellowstar on 11/25/15.
 */
public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(String val) {
        String[] cdt = val.substring(1, val.length() - 1).split(",");
        this.x = Double.valueOf(cdt[0]);
        this.y = Double.valueOf(cdt[1]);
    }

    public double getLat() {
        return x;
    }

    public double getLot() {
        return y;
    }

    public String getValue() {
        return "(" + this.x + "," + this.y + ")";
    }


}
