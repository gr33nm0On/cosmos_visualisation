class GameContext {
	private static Screen currentScreen;

	public static Screen getScreen() {
		return currentScreen;
	}

	public static void setScreen(Screen screen) {
		currentScreen = screen;
	}
}


class Screen {
	Point[][] canvas;
	int w, h;

	Screen(int w, int h) {
		this.w = w;
		this.h = h;
		this.canvas = new Point[h][w];
		for (int i = 0; i < this.h; i++) {
			for (int j = 0; j < this.w; j++) {
				this.canvas[i][j] = new Point(j, i);
			}
		}
	}

	public void print() {
		String[] s = new String[this.h];
		for (int i = 0; i < this.h; i++) {
			char[] chl = new char[this.w];
			for (int j = 0; j < this.w; j++) {
				chl[j] = this.canvas[i][j].render();
			}
		 	s[i] = new String(chl);
		}
		System.out.println(String.join("\n", s));
	}
}

class Clock {
	public static void sleep(long time) {
		long time0 = System.currentTimeMillis();
		while (System.currentTimeMillis() - time0 < time) {
		}
	}
}


class GameObject {
	int x, y;

	public void render() {}
}


class GameObjectList {
    private Node head, tail;
    private int size;
    
    private class Node {
        GameObject data;
        Node next, prev;
        
        Node(GameObject data) {
            this.data = data;
        }
    }
    
    public void add(GameObject obj) {
        Node newNode = new Node(obj);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }
    
    public void remove(Node node) {
        if (node.prev != null) node.prev.next = node.next;
        else head = node.next;
        
        if (node.next != null) node.next.prev = node.prev;
        else tail = node.prev;
        
        size--;
    }
    
    public void forEach(Consumer<GameObject> action) {
        Node current = head;
        while (current != null) {
            action.accept(current.data);
            current = current.next;
        }
    }
    
    public int size() { return size; }
}


class Point extends GameObject{
	int l;
	char sprite = ' ';
	boolean hasSprite = false;

	static char[] SPRITES = {' ', '.', '-', '#', '@'};

	Point(int x, int y) {
		this.x = x;
		this.y = y;
		this.l = 0;
	}

	public char render() {
		if (this.l > 100) {
			this.l = 100;
		}
		if (!this.hasSprite){
			this.sprite = SPRITES[(int)(this.l / 100 * (SPRITES.length - 1))];
		}
		return this.sprite;
	}
}

class Particle extends GameObject {
}


public class Main {
	public static void main(String[] args) {
		Screen screen = new Screen(20, 20);
		GameContext.setScreen(screen);

		System.out.print("\033[?1049h");
        System.out.flush();

        int frames = 500;
        int frame = 0;

        while (frames > frame) {
        	System.out.print("\033[0;0H");
            System.out.print("\033[2J"); 

            GameContext.getScreen().print();

        	Clock.sleep(1000);
        	frame++;
        }
	}
}