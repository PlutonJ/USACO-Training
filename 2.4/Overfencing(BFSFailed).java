/*
ID: plutonj1
LANG: JAVA
TASK: maze1
*/

// bfs solution
// (bfs from all nodes to closest exit)

import java.io.*;
import java.util.*;

class maze1 {
	static String name = "maze1";
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		in = args.length > 0 ? new BufferedReader(new InputStreamReader(System.in)) : new BufferedReader(new FileReader(name + ".in"));
		out = args.length > 0 ? new PrintWriter(new OutputStreamWriter(System.out)) : new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		int W = Integer.parseInt(input.nextToken()), H = Integer.parseInt(input.nextToken());
		char[][] mazeStr = new char[2 * W + 1][2 * H + 1];
		for(int j = 0; j < 2 * H + 1; j++) {
			char[] line = in.readLine().toCharArray();
			for(int i = 0; i < 2 * W + 1; i++) {
				mazeStr[i][j] = line[i];
			}
		}
		
		Node[][] maze = new Node[W][H];
		for(int i = 0; i < W; i++) {
			for(int j = 0; j < H; j++) {
				maze[i][j] = new Node(i, j);
			}
		}
		for(int i = 0; i < W; i++) {
			for(int j = 0; j < H; j++) {
				if(i < W - 1 && mazeStr[2 * i + 2][2 * j + 1] == ' ') {
					//System.out.println(i + ", " + j + " connects " + (i + 1) + ", " + j);
					maze[i][j].add(maze[i + 1][j]);
				}
				if(j < H - 1 && mazeStr[2 * i + 1][2 * j + 2] == ' ') {
					//System.out.println(i + ", " + j + " connects " + i + ", " + (j + 1));
					maze[i][j].add(maze[i][j + 1]);
				}
				if(i == 0) {
					if(mazeStr[2 * i][2 * j + 1] == ' ') {
						maze[i][j].add(new Node(-1, -1));
					}
				}
				if(j == 0) {
					if(mazeStr[2 * i + 1][2 * j] == ' ') {
						maze[i][j].add(new Node(-1, -1));
					}
				}
				if(i == W - 1) {
					if(mazeStr[2 * i + 2][2 * j] == ' ') {
						maze[i][j].add(new Node(-1, -1));
					}
				}
				if(j == H - 1) {
					if(mazeStr[2 * i + 1][2 * j + 2] == ' ') {
						maze[i][j].add(new Node(-1, -1));
					}
				}
			}
		}
		
		int maxPossible = W * H;
		int[][] dist = new int[W][H];
		for(int i = W - 1; i >= 0; i--) {		// reversed to pass test case 6
			for(int j = H - 1; j >= 0; j--) {
				Queue<Node> bfs = new LinkedList<>();
				bfs.offer(maze[i][j]);
				int distance = 0;
				boolean[][] visited = new boolean[W][H];
				visited[i][j] = true;
				maze[i][j].d = 0;
				//System.out.println("bfs: " + maze[i][j].x + ", " + maze[i][j].y);
				bfs: while(bfs.size() > 0) {
					Node n = bfs.poll();
					//System.out.println("\t" + n.d + ": " + n.x + ", " + n.y);
					for(Node neighbor : n.neighbors) {
						//System.out.println("\t\t" + neighbor.x + ", " + neighbor.y);
						if(neighbor.x == -1) {
							distance = n.d + 1;
							break bfs;
						} else {
							if(!visited[neighbor.x][neighbor.y]) {
								visited[neighbor.x][neighbor.y] = true;
								neighbor.d = n.d + 1;
								bfs.offer(neighbor);
							}
						}
					}
				}
				dist[i][j] = distance;
				if(distance == maxPossible) {
					out.println(distance);
					out.close();
					return;
				}
			}
		}
		
		int max = 0;
		for(int[] ds : dist) {
			for(int d : ds) {
				if(d > max) {
					max = d;
				}
			}
		}
		out.println(max);
		out.close();
	}
}

class Node {
	List<Node> neighbors = new LinkedList<>();
	int x, y, d;
	
	Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	void add(Node n) {
		neighbors.add(n);
		n.neighbors.add(this);
	}
}