/************************************************
 * Name: Damon Black
 * Course: Coursera Princeton Algorithms I
 * Assignment: Week 1 - Percolation
 ***********************************************/

// class Percolation API is as follows:

/* public class Percolation {
 *    // create N-by-N grid, with all sites blocked
 *    public Percolation(int N)
 *    // open site (row i, column j) if it is not open already
 *    public void open (int i, int j)
 *    // is site (row i, column j) open?
 *    public boolean isOpen(int i, int j)
 *    // is site (row i, column j) full?
 *    public boolean isFull(int i, int j)
 *    // number of open sites
 *    public int numberOfOpenSites()
 *    // does the system percolate?
 *    public boolean percolates()
 * }
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// these 3 are not included in assignment submission
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stopwatch;

public class Percolation
{
   private boolean[] siteArray;
   private int gridSize;
   private int gridDimension;
   private int topVirtualSite;
   private int bottomVirtualSite;
   private int openSites;
   private WeightedQuickUnionUF percoOpen;
   private WeightedQuickUnionUF percoFull;
   
   // not included in actual assignment submission - just a test client
   public static void main(String[] args)
   {
      Stopwatch stopwatch = new Stopwatch();
      
      StdDraw.enableDoubleBuffering();
      
      Percolation percoNine = new Percolation(9);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(2, 5);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(7, 8);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(6, 9);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(1, 4);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(8, 1);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(9, 2);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(9, 1);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(1, 7);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(1, 1);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(1, 9);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(3, 5);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(6, 4);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(9, 5);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(9, 6);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(7, 9);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(5, 3);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(7, 4);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(8, 4);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(9, 4);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(2, 6);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(3, 4);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(5, 4);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(2, 4);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      percoNine.open(4, 4);
      PercolationVisualizer.draw(percoNine, 9);
      StdDraw.show();
      StdDraw.pause(100);
      
      if (percoNine.percolates())
      {
         StdOut.println("The system percolates successfully.");
      }
      
      double time = stopwatch.elapsedTime();
      StdOut.println("Time elapsed in seconds: " + time);
   }
   
   // create N-by-N grid, with all sites blocked
   public Percolation(int n)
   {
      if (n <= 0)
      {
         throw new IllegalArgumentException("Invalid grid size.");
      }
      gridDimension = n; // height/length of grid
      gridSize = gridDimension * gridDimension; // # of elements in grid
      siteArray = new boolean[gridSize];
      percoOpen = new WeightedQuickUnionUF(gridSize + 2); // n^2+top+bottom
      percoFull = new WeightedQuickUnionUF(gridSize + 2);
      openSites = 0; // counter variable to be incremented when sites open
      
      topVirtualSite = 0;
      bottomVirtualSite = gridSize + 1;
      
      // initializing each site to be blocked
      for (int i = 0; i < gridSize; i++)
      {
         siteArray[i] = false;
      }
   }
   
   // private method for validating passed indices
   private boolean indexValidator(int passedIndex)
   {
      if ((passedIndex < 1) || (passedIndex > gridDimension))
      {
         throw new IllegalArgumentException("Invalid site coordinate.");
      }
      else
      {
         return true;
      }
   }
   
   // private method for changing 2D array parameter to 1D to fit WQUUF
   private int twoDConvertToSingle(int row, int col)
   {
      if (indexValidator(row) == true && indexValidator(col) == true)
      {
         return ((row - 1) * gridDimension) + (col - 1);
      }
      else
      {
         return -1;
      }
   }
   
   // open site (row i, column j) if it is not open already
   public void open(int row, int col)
   {
      int currentSite = twoDConvertToSingle(row, col);
      indexValidator(row);
      indexValidator(col);
      if (siteArray[currentSite] == false)
      {
         siteArray[currentSite] = true;
         openSites++;
         
         // left
         if (col != 1)
         {
            if (isOpen(row, col - 1))
            {
               percoOpen.union(currentSite, currentSite - 1);
               percoFull.union(currentSite, currentSite - 1);
            }
         }
         
         // right
         if (col != gridDimension)
         {
            if (isOpen(row, col + 1))
            {
               percoOpen.union(currentSite, currentSite + 1);
               percoFull.union(currentSite, currentSite + 1);
            }
         }
         
         // above
         if (row != 1)
         {
            if (isOpen(row - 1, col))
            {
               percoOpen.union(currentSite, currentSite - gridDimension);
               percoFull.union(currentSite, currentSite - gridDimension);
            }
         }
         else
         {
            percoOpen.union(currentSite, topVirtualSite);
            percoFull.union(currentSite, topVirtualSite);
         }
         
         // below
         if (row != gridDimension)
         {
            if (isOpen(row + 1, col))
            {
               percoOpen.union(currentSite, currentSite + gridDimension);
               percoFull.union(currentSite, currentSite + gridDimension);
            }
         }
         else
         {
            percoOpen.union(currentSite, bottomVirtualSite);
         }
      }
   }
   
   // is site (row i, column j) open?
   public boolean isOpen(int row, int col)
   {
      indexValidator(row);
      indexValidator(col);
      return siteArray[twoDConvertToSingle(row, col)];
   }
   
   // is site (row i, column j) full (connected to an open top site)?
   // is also used by the assignment's client to determine correctness
   public boolean isFull(int row, int col)
   {
      indexValidator(row);
      indexValidator(col);
      return isOpen(row, col)
            && percoFull.connected(twoDConvertToSingle(row, col),
                  topVirtualSite);
   }
   
   // number of open sites
   public int numberOfOpenSites()
   {
      return openSites;
   }
   
   // does the system percolate?
   public boolean percolates()
   {
      if (percoOpen.connected(topVirtualSite, bottomVirtualSite))
      {
         return true;
      }
      else
      {
         return false;
      }
   }
}