package cmpe277.skibuddy.model;

import com.google.gson.annotations.Expose;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yellowstar on 11/25/15.
 */
public class Path {
    private List<Point> points;
    public Path() {
        this.points = new LinkedList<>();
    }

    public Path(String val) {
        this.points = new LinkedList<>();
        String[] strs = val.trim().substring(0, val.length() - 1).split(",");
        for (String str : strs) {
            points.add(new Point(str.trim()));
        }
    }

    public void add(Point point) {
        this.points.add(point);
    }

    public String getValue() {
        StringBuffer b = new StringBuffer("[");
        for(int p = 0; p < this.points.size(); ++p) {
            if(p > 0) {
                b.append(",");
            }

            b.append(this.points.get(p).getValue());
        }
        b.append("]");
        return b.toString();
    }
}
