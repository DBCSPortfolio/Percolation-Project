/************************************************
 * Name: Damon Black
 * Course: Coursera Princeton Algorithms I
 * Assignment: Week 1 - Percolation
 ***********************************************/

// class PercolationStats API is as follows:

/* public class PercolationStats {
 *    // perform T independent experiments on an N-by-N grid
 *    public PercolationStats(int N, int T)
 *    // sample mean of percolation threshold
 *    public double mean()
 *    // sample standard deviation of percolation threshold
 *    public double stddev()
 *    // low endpoint of 95% confidence interval
 *    public double confidenceLow()
 *    // high endpoint of 95% confidence interval
 *    public double confidenceHigh()
 * }
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;

public class PercolationStats
{
   private double[] thresholdArray;
   private int numTrials;
   
   public static void main(String[] args)
   {
      int testDimension = StdIn.readInt();
      int testTrials = StdIn.readInt();
      Stopwatch stopwatch = new Stopwatch();
      PercolationStats percTestStats = new PercolationStats(testDimension,
            testTrials);
      StdOut.println("\nmean                   : " + percTestStats.mean());
      StdOut.println("standard deviation     : " + percTestStats.stddev());
      StdOut.println("95% confidence interval: [" 
            + percTestStats.confidenceLo() + ", "
            + percTestStats.confidenceHi() + "]");
      double time = stopwatch.elapsedTime();
      StdOut.println("\nTime elapsed in seconds: " + time);
   }
   
   // perform T independent experiments on an N-by-N grid
   public PercolationStats(int n, int trials)
   {
      if (n <= 0)
      {
         throw new IllegalArgumentException("The grid size is too small.");
      }
      if (trials <= 0)
      {
         throw new IllegalArgumentException("The number of trials "
               + "is too small.");
      }
      
      thresholdArray = new double[trials];
      numTrials = trials;
      
      for (int i = 0; i < trials; i++)
      {
         Percolation tempPercSim = new Percolation(n);
         while (tempPercSim.percolates() == false)
         {
            tempPercSim.open(StdRandom.uniform(1, n + 1), 
                  StdRandom.uniform(1, n + 1));
         }
         thresholdArray[i] = tempPercSim.numberOfOpenSites()
               / (double) (n * n);
      }
   }
   
   // sample mean of percolation threshold
   public double mean()
   {  
      return StdStats.mean(thresholdArray);
   }
   
   // sample standard deviation of percolation threshold
   public double stddev()
   {
      if (numTrials == 1)
      {
         return Double.NaN;
      }
      else
      {
         return StdStats.stddev(thresholdArray);
      }
   }
   
   // low endpoint of 95% confidence interval
   public double confidenceLo()
   {
      return (mean() - ((1.96 * stddev()) / Math.sqrt(numTrials)));
   }
   
   // high endpoint of 95% confidence interval
   public double confidenceHi()
   {
      return (mean() + ((1.96 * stddev()) / Math.sqrt(numTrials)));
   }
}