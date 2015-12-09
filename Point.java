import java.util.ArrayList;

/**
 * Created by grewn0uille on 08/12/15.
 */


public class Point {

    public double x;
    public double y;
    public int cluster;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.cluster = -1;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public double distance(Point p2){
        double res;
        res = Math.sqrt(Math.pow(this.x - p2.x, 2) + Math.pow(this.y - p2.y, 2));
        return res;
    }

    public void afficher(){
        if(this.cluster!=-1){
            System.out.println("("+this.x+", "+this.y+") : cluster "+this.cluster);
        } else {
            System.out.println("("+this.x+", "+this.y+")");
        }
    }
}
