import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

//This class uses the Percolation class to run customized trials and estimate the percolation threshold
//Note: percolation threshold p* is the ratio of open sites to the total number of sites at the time percolation first occurs
public class PercolationStats {
	final private double[] stats;
	final private int t;
	
	//Perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
    	//Throws exception if n<=0 or trials<=0
    	if(n<=0 || trials<=0) {
    		throw new IllegalArgumentException();
    	}
    	this.t = trials;
    	stats = new double[trials];
    	for(int i=0; i<trials; i++) {
    		Percolation p = new Percolation(n);
    		//Repeat the process of opening random sites on the grid until it percolates
    		while(!p.percolates()) {
    			int row = StdRandom.uniform(1, n+1);
    			int col = StdRandom.uniform(1, n+1);    			
    			p.open(row, col);
    		}
    		//Once one trial of percolation is completed, add the percolation threshold to an array
    		stats[i] = p.numberOfOpenSites()/((double) n*n);
    	}
    }

    //Sample mean of percolation threshold
    public double mean() {
    	return StdStats.mean(stats);
    }

    //Sample standard deviation of percolation threshold
    public double stddev() {
    	return StdStats.stddev(stats);    	
    }

    //Low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return mean() - ((1.96*stddev())/(Math.sqrt(t)));
    }

    //High endpoint of 95% confidence interval
    public double confidenceHi() {
    	return mean() + ((1.96*stddev())/(Math.sqrt(t)));    	
    }

   //Takes in 2 command arguments: num (# of rows & columns) and trials (# of times to conduct percolation test on grid num x num)
   public static void main(String[] args) {	   
	   int num = Integer.parseInt(args[0]);	   
	   int trials = Integer.parseInt(args[1]);
	   
	   PercolationStats pStats = new PercolationStats(num, trials);
	   
	   //Provides the sample mean, sample standard deviation, and 95% confidence interval of the percolation thresholds
	   //recorded after each trial is conducted
	   System.out.println("mean = " + pStats.mean());
	   System.out.println("stddev = " + pStats.stddev());
	   System.out.println("95% confidence interval = [" + pStats.confidenceLo() + ", " + pStats.confidenceHi() + "]");
   }

}
