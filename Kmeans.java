import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by grewn0uille on 08/12/15.
 */
public class Kmeans {

	//each point is associated with the nearest cluster
    public static void classification(ArrayList<Point> tab_points, ArrayList<Point> tab_cluster_t, int nb_points, int nb_clusters){
        for(int i = 0; i<nb_points; ++i){
            double min = Double.POSITIVE_INFINITY;
            for(int j = 0; j<nb_clusters; ++j){
                double distance = tab_points.get(i).distance(tab_cluster_t.get(j));
                if(distance < min){
                    min = distance;
                    tab_points.get(i).setCluster(j);
                }
            }
            tab_points.get(i).afficher();
        }
    }

	//check if we obtain the needed convergence
    public static boolean check_classif(ArrayList<Point> tab_t, ArrayList<Point> tab_tp1, int nb_clusters, double conv){
        boolean res = true;
        int i = 0;
        while(res && i<nb_clusters){
            double d = tab_t.get(i).distance(tab_tp1.get(i));
            if(d > conv){
                res = false;
            }
            i++;
        }
        return res;
    }

	//calculate the centroid of the array of points
    public static void calcul_bary(ArrayList<Point> tab_cluster_tp1, int i, ArrayList<Point> tab_tmp, double borneInf, double borneSup){
        double centroidX =0;
        double centroidY = 0;

        for(Point p : tab_tmp) {
            centroidX += p.getX();
            centroidY += p.getY();
        }
        Point centroid = new Point(centroidX / tab_tmp.size(), centroidY / tab_tmp.size());
        if(centroid.x>borneInf && centroid.x<borneSup && centroid.y>borneInf && centroid.y<borneSup){ //pour que ca reste dans l'espace de travail
            tab_cluster_tp1.set(i, centroid) ;
        }
    }

	//find the new clusters
    public static void nouveaux_clusters(ArrayList<Point>tab_cluster_tp1, ArrayList<Point>tab_points,
                                         int nb_clusters, int nb_points, double borneInf, double borneSup){
        for(int i = 0; i<nb_clusters; ++i){
            ArrayList<Point> tab_tmp = new ArrayList<>();
            for(int j = 0; j<nb_points; ++j){
                if(tab_points.get(j).cluster==i){
                    tab_tmp.add(tab_points.get(j));
                }
            }
            calcul_bary(tab_cluster_tp1, i, tab_tmp, borneInf, borneSup);
        }
    }

	//display array of points
    public static void affiche_tab(ArrayList<Point> tab, int length, String msg) {
        System.out.println(msg);
        for(int i = 0; i< length ; ++i){
            System.out.print(i+" : ");
            tab.get(i).afficher();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        //parameters
        int nb_clusters = 4; //number of clusters
        int nb_points = 10; //number of points
        double convergence = 0.5; //convergence
        double borneInf = 0; //lower bound of the working area
        double borneSup = 10; //upper bound of the working area

        ArrayList<Point> tab_points = new ArrayList<>(); //array of points
        ArrayList<Point> tab_cluster_t = new ArrayList<>(); //aray of cluster at a t moment
        int nb_it = 1; //iteration counter
        boolean check = false; //to check the convergence

        //initialisation clusters
        for(int i =0 ; i< nb_clusters; ++i){
            double x = borneInf + Math.random()*borneSup;
            double y = borneInf + Math.random()*borneSup;
            Point p = new Point(x, y);
            tab_cluster_t.add(p);
        }

        ArrayList<Point> tab_cluster_tp1 = new ArrayList<>(tab_cluster_t);

        //initialisation points
        for(int i =0 ; i< nb_points; ++i){
            double x = borneInf + Math.random()*borneSup;
            double y = borneInf + Math.random()*borneSup;
            Point p = new Point(x, y);
            tab_points.add(p);
        }


        System.out.println();
        System.out.println("1e itération :");
        affiche_tab(tab_cluster_t, nb_clusters, "Clusters : ");
        classification(tab_points, tab_cluster_t, nb_points, nb_clusters);
        nouveaux_clusters(tab_cluster_tp1, tab_points, nb_clusters, nb_points, borneInf, borneSup);

        while(!check){ //while we doesn't obtain the needed convergence
            check = check_classif(tab_cluster_t, tab_cluster_tp1, nb_clusters, convergence);
            nb_it++;
            if(!check){
                tab_cluster_t = new ArrayList<>(tab_cluster_tp1);
                System.out.println();
                System.out.println(nb_it + "e itération");
                affiche_tab(tab_cluster_t, nb_clusters, "Clusters : ");
                classification(tab_points, tab_cluster_tp1, nb_points, nb_clusters);
                nouveaux_clusters(tab_cluster_tp1, tab_points, nb_clusters, nb_points, borneInf, borneSup);
            } else {
                System.out.println("Clustering terminé");
            }
        }
    }

}
