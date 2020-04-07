import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{

	final private int num; //length & width of grid
	private int count = 0; //number of open sites
	private boolean[][] grid; //holds true/false for each site; true->open; false -> blocked
	final private WeightedQuickUnionUF uf; 
	final private WeightedQuickUnionUF ufIsFull; //accounts for backwash; no bottom virtual site
	
	//Creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){    	
    	//Throws exception if n<=0
    	if(n<=0){
    		throw new IllegalArgumentException();
    	}
    	this.num = n;
    	//Creates a union find data type for an n x n grid
    	uf = new WeightedQuickUnionUF(n*n+2);
    	ufIsFull = new WeightedQuickUnionUF(n*n+1);
    	grid = new boolean[n+2][n];
    }

    //Opens the site (row, col) if it is not open already
    public void open(int row, int col){    	
    	int newCol = col - 1;
    	
    	//Throws exception if the parameters reference a site not on the grid
    	if(row<1 || row>num || col<1 || col>num) {
    		throw new IllegalArgumentException();
    	}    
    	
    	//Opens the site if not already open
    	if(!grid[row][newCol]) {
    		grid[row][newCol] = true;
    		count++;
    	}
    	
		//Creates connection to bottom source site if the newly opened site is on the bottom row of the grid
    	if(row==num) {
    		uf.union(index(row, col), index(num+1, 1));
    	}
    	
		//Creates connection to top source site if the newly opened site is on the top row of the grid
    	if(row == 1) {
    		uf.union(index(row, col), 0);
    		ufIsFull.union(index(row, col), 0);    		
    	}
    	
    	//Creates connection to already opened neighboring sites
    	if(row!=1 && isOpen(row-1, col)) {
    		if(grid[row-1][newCol]) {
    			uf.union(index(row, col), index(row-1, col));
    			ufIsFull.union(index(row, col), index(row-1, col));

    		}
    	}    	
    	if(row!=num && isOpen(row+1, col)) {
    		if(grid[row+1][newCol]) {
    			uf.union(index(row, col), index(row+1, col));   
    			ufIsFull.union(index(row, col), index(row+1, col));    		
    		}
    	}    	
    	if(col!=1 && isOpen(row, col-1)) {
    		if(grid[row][newCol-1]) {
    			uf.union(index(row, col), index(row, col-1));    
    			ufIsFull.union(index(row, col), index(row, col-1));    		
    		}
    	}    	
    	if(col!=num && isOpen(row, col+1)) {
    		if(grid[row][newCol+1]) {
    			uf.union(index(row, col), index(row, col+1));   
    			ufIsFull.union(index(row, col), index(row, col+1));    		
    		}
    	}
    }

    //Is the site (row, col) open?
    public boolean isOpen(int row, int col){
    	//Throws exception if the parameters reference a site not on the grid
    	if(row<1 || row>num || col<1 || col>num) {
    		throw new IllegalArgumentException();
    	}   	
    	return grid[row][col - 1];
    }

    //Is the site (row, col) full?
    public boolean isFull(int row, int col){
    	//Throws exception if the parameters reference a site not on the grid
    	if(row<1 || row>num || col<1 || col>num) {
    		throw new IllegalArgumentException();
    	}
    	if(ufIsFull.find(0) == ufIsFull.find(index(row, col)) && isOpen(row, col)) {
    			return true;
    	}    	
    	return false;
    }
    
    private int index(int row, int col) {
    	return (row-1)*num + col;
    }

    //Returns the number of open sites
    public int numberOfOpenSites() {
    	return count;
    }

    //Does the system percolate?
    public boolean percolates() {
    	//Checks whether the sites in the bottom test row are connected to the sites in the top test row
    	if(uf.find(index(num+1,1)) == uf.find(0)){
    		return true;
    	}
    	return false;
    }

    public static void main(String[] args) {
    	//Test client
    }
}