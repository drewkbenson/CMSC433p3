package cmsc433.p3;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * This file needs to hold your solver to be tested. 
 * You can alter the class to extend any class that extends MazeSolver.
 * It must have a constructor that takes in a Maze.
 * It must have a solve() method that returns the datatype List<Direction>
 *   which will either be a reference to a list of steps to take or will
 *   be null if the maze cannot be solved.
 */
public class StudentMTMazeSolver extends SkippingMazeSolver
{
	public static boolean solved = false;
	public static LinkedList<Direction> soln;
	//public static ForkJoinPool pool;

	public StudentMTMazeSolver(Maze maze)
	{
		super(maze);
	}

	public List<Direction> solve()
	{

		ForkJoinPool pool = new ForkJoinPool(1);//2*(Math.max(maze.getHeight(), maze.getWidth())));


		soln = new LinkedList<Direction>();
		StudentMazeRunnable ran = new StudentMazeRunnable(maze, maze.getStart(), null);
		pool.execute(ran);
		while(!ran.isDone() && !solved) {
			try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
			}

		if(soln.size() != 0) {
			return soln;
		}
		return null;

	}


}
