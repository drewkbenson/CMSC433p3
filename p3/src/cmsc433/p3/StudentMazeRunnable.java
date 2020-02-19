package cmsc433.p3;

import java.util.LinkedList;
import java.util.concurrent.RecursiveTask;

public class StudentMazeRunnable extends RecursiveTask<Boolean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Maze maze;
	private Position pos;
	private Direction prev;

	public StudentMazeRunnable(Maze mazeIn, Position posIn, Direction prevIn) {
		maze = mazeIn;
		pos = posIn;
		prev = prevIn;
	}

	public void run(){

	}

	@Override
	protected Boolean compute(){
		try {

			if (pos.equals(maze.getEnd())) {
				StudentMTMazeSolver.soln.add(prev);
				return true;
			} else {
				LinkedList<Direction> next = maze.getMoves(pos);
				if (prev == null) {
					StudentMazeRunnable[] nexts = new StudentMazeRunnable[next.size()];
					for(int i = 0; i < next.size(); i++) {
						nexts[i] = new StudentMazeRunnable(maze, pos.move(next.get(i)),next.get(i));
						nexts[i].fork();
					}

					for(int i = 0; i < next.size(); i++) {
						if (nexts[i].get() == true) {
							StudentMTMazeSolver.solved = true;
							return true;
						}

					}
					return false;
				} else {

					for (int i = 0; i < next.size(); i++) {
						if(prev.reverse().equals(next.get(i)))
							next.remove(i);
					}

					
					//long a = System.nanoTime();

					StudentMazeRunnable[] nexts = new StudentMazeRunnable[next.size()];
					for(int i = 0; i < next.size(); i++) {
						nexts[i] = new StudentMazeRunnable(maze, pos.move(next.get(i)),next.get(i));
						nexts[i].fork();
					}

					//long b = System.nanoTime() - a;
					//System.out.println("Taken: " + b);

					for(int i =0 ; i < next.size(); i++) {
						if (nexts[i].get() == true) {

							StudentMTMazeSolver.soln.addFirst(prev);
							return true;
						}
					}

					return false;
				}
			}
		}catch (Exception e) {
			return false;
		}

	}
}