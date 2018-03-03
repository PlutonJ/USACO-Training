/*
ID: plutonj1
LANG: JAVA
TASK: maze1
*/

// thought process: 
// 		bfs from 1st exit, set d for all nodes to distance from 1st exit
//		bfs from 2nd exit, set d for all nodes to min(dist from 2nd exit, d)
//		find maximum d

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
		Node e0 = null, e1 = null;	// exits
		for(int i = 0; i < W; i++) {
			for(int j = 0; j < H; j++) {
				maze[i][j] = new Node(i, j);
			}
		}
		for(int i = 0; i < W; i++) {
			for(int j = 0; j < H; j++) {
				if(i < W - 1 && mazeStr[2 * i + 2][2 * j + 1] == ' ') {
					maze[i][j].add(maze[i + 1][j]);
				}
				if(j < H - 1 && mazeStr[2 * i + 1][2 * j + 2] == ' ') {
					maze[i][j].add(maze[i][j + 1]);
				}
				if(i == 0) {
					if(mazeStr[2 * i][2 * j + 1] == ' ') {
						maze[i][j].add(e0 == null ? (e0 = new Node(-1, -1)) : (e1 = new Node(-1, -1)));
					}
				}
				if(j == 0) {
					if(mazeStr[2 * i + 1][2 * j] == ' ') {
						maze[i][j].add(e0 == null ? (e0 = new Node(-1, -1)) : (e1 = new Node(-1, -1)));
					}
				}
				if(i == W - 1) {
					if(mazeStr[2 * i + 2][2 * j + 1] == ' ') {
						maze[i][j].add(e0 == null ? (e0 = new Node(-1, -1)) : (e1 = new Node(-1, -1)));
					}
				}
				if(j == H - 1) {
					if(mazeStr[2 * i + 1][2 * j + 2] == ' ') {
						maze[i][j].add(e0 == null ? (e0 = new Node(-1, -1)) : (e1 = new Node(-1, -1)));
					}
				}
			}
		}
		
		boolean[][] visited = new boolean[W][H];
		Queue<Node> bfs = new LinkedList<>();
		bfs.offer(e0.neighbors.get(0));
		bfs.peek().d = 1;
		visited[bfs.peek().x][bfs.peek().y] = true;
		while(bfs.size() > 0) {
			Node n = bfs.poll();
			for(Node neighbor : n.neighbors) {
				if(neighbor.x != -1 && !visited[neighbor.x][neighbor.y]) {
					neighbor.d = n.d + 1;
					visited[neighbor.x][neighbor.y] = true;
					bfs.offer(neighbor);
				}
			}
		}
		visited = new boolean[W][H];
		bfs = new LinkedList<>();
		bfs.offer(e1.neighbors.get(0));
		bfs.peek().d = 1;
		visited[bfs.peek().x][bfs.peek().y] = true;
		while(bfs.size() > 0) {
			Node n = bfs.poll();
			for(Node neighbor : n.neighbors) {
				if(neighbor.x != -1 && !visited[neighbor.x][neighbor.y]) {
					neighbor.d = Math.min(n.d + 1, neighbor.d);
					visited[neighbor.x][neighbor.y] = true;
					bfs.offer(neighbor);
				}
			}
		}
		
		int max = 0;
		for(Node[] ns : maze) {
			for(Node n : ns) {
				if(n.d > max) {
					max = n.d;
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