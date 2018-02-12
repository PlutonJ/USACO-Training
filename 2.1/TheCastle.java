/*
ID: plutonj1
LANG: JAVA
TASK: castle
*/

import java.io.*;
import java.util.*;

class castle {
	static String name = "castle";
	
	static PrintWriter out;
	
	static int M, N;
	static Module[][] castle;
	static boolean[][] checked;
	static List<Room> rooms;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
		StringTokenizer input;
		
		input = new StringTokenizer(in.readLine());
		M = Integer.parseInt(input.nextToken());
		N = Integer.parseInt(input.nextToken());
		castle = new Module[N][M];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				castle[i][j] = new Module(j, i);
			}
		}
		for(int i = 0; i < N; i++) {
			input = new StringTokenizer(in.readLine());
			for(int j = 0; j < M; j++) {
				int walls = Integer.parseInt(input.nextToken());
				if((walls & 1) == 0) castle[i][j].w = castle[i][j - 1];	// if no wall
				if((walls & 2) == 0) castle[i][j].n = castle[i - 1][j];
				if((walls & 4) == 0) castle[i][j].e = castle[i][j + 1];
				if((walls & 8) == 0) castle[i][j].s = castle[i + 1][j];
			}
		}
		
		rooms = new LinkedList<Room>();
		checked = new boolean[N][M];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(!checked[i][j]) {									// if not in a previously checked room, castle[i][j] belongs to a new room
					rooms.add(new Room(castle[i][j]));
				}
			}
		}
		out.println(rooms.size());
		Room largest = null;
		for(Room r : rooms) {
			if(largest == null || r.size > largest.size) largest = r;
		}
		out.println(largest.size);
		
//		int max = -1;		// misunderstanding: not necessarily removing a wall from the largest room
//		String wall = null;
//		for(Module m : largest.modules) {
//			if(m.w == null) {
//				if(m.x - 1 >= 0) {
//					if(castle[m.y][m.x - 1].room != largest && castle[m.y][m.x - 1].room.size > max) {
//						max = castle[m.y][m.x - 1].room.size;
//						wall = (m.y + 1) + " " + m.x + " E";
//					}
//				}
//			}
//			if(m.n == null) {
//				if(m.y - 1 >= 0) {
//					if(castle[m.y - 1][m.x].room != largest && castle[m.y - 1][m.x].room.size > max) {
//						max = castle[m.y - 1][m.x].room.size;
//						wall = (m.y + 1) + " " + (m.x + 1) + " N";
//					}
//				}
//			}
//			if(m.e == null) {
//				if(m.x + 1 < M) {
//					if(castle[m.y][m.x + 1].room != largest && castle[m.y][m.x + 1].room.size > max) {
//						max = castle[m.y][m.x + 1].room.size;
//						wall = (m.y + 1) + " " + (m.x + 1) + " E";
//					}
//				}
//			}
//			if(m.s == null) {
//				if(m.y + 1 < N) {
//					if(castle[m.y + 1][m.x].room != largest && castle[m.y + 1][m.x].room.size > max) {
//						max = castle[m.y + 1][m.x].room.size;
//						wall = (m.y + 2) + " " + (m.x + 1) + " N";
//					}
//				}
//			}
//		}
		int max = -1;
		int wallX = M, wallY = -1;
		boolean north = false;
		for(int i = N - 1; i >= 0; i--) {		// iterate from furthest west and south
			for(int j = 0; j < M; j++) {
				Module m = castle[i][j].n;
				if(m == null && i > 0) m = castle[i - 1][j];
				if(m != null && castle[i][j].room != m.room && (castle[i][j].room.size + m.room.size == max ? (j == wallX ? i > wallY : j < wallX) : castle[i][j].room.size + m.room.size > max)) {
					max = castle[i][j].room.size + m.room.size;
					wallX = j;
					wallY = i;
					north = true;
				}
				m = castle[i][j].e;
				if(m == null && j < M - 1) m = castle[i][j + 1];
				if(m != null && castle[i][j].room != m.room && (castle[i][j].room.size + m.room.size == max ? (j == wallX ? i > wallY : j < wallX) : castle[i][j].room.size + m.room.size > max)) {
					max = castle[i][j].room.size + m.room.size;
					wallX = j;
					wallY = i;
					north = false;
				}
			}
		}
		out.println(max);
		out.println((wallY + 1) + " " + (wallX + 1) + " " + (north ? "N" : "E"));
		
		out.close();
	}
}

class Module {
	int x, y;
	Module w, n, e, s;
	Room room;
	
	Module(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Room {
	Set<Module> modules;
	int size;
	
	Room(Module head) {
		//modules = new TreeSet<Module>((a, b) -> a.x - b.x == 0 ? b.y - a.y : a.x - b.x);	// since output #4 prioriy: furthest wet -> furthest south
																							// does not apply anymore since not necessarily deleting a wall form the largest room
		modules = new HashSet<Module>();
		Queue<Module> q = new LinkedList<Module>();			// dfs to find all modules in the same room as head
		q.offer(head);
		while(q.size() > 0) {
			Module m = q.poll();
			m.room = this;
			modules.add(m);
			if(m.w != null && !castle.checked[m.w.y][m.w.x]) {
				castle.checked[m.w.y][m.w.x] = true;
				q.offer(m.w);
			}
			if(m.n != null && !castle.checked[m.n.y][m.n.x]) {
				castle.checked[m.n.y][m.n.x] = true;
				q.offer(m.n);
			}
			if(m.e != null && !castle.checked[m.e.y][m.e.x]) {
				castle.checked[m.e.y][m.e.x] = true;
				q.offer(m.e);
			}
			if(m.s != null && !castle.checked[m.s.y][m.s.x]) {
				castle.checked[m.s.y][m.s.x] = true;
				q.offer(m.s);
			}
		}
		size = modules.size();
		//System.out.println(size);
	}
}