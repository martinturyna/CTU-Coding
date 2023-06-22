import java.util.Stack;

/*
interface CustomIterator {
    boolean hasNext();
    int next();
}*/

class Node {
    final int contents;
    final Node left, right;
    Node parent = null;

    Node(int contents, Node left, Node right) {
        this.contents = contents;
        this.left = left;
        if (left != null) left.parent = this;
        this.right = right;
        if (right != null) right.parent = this;
    }

    CustomIterator preorderIterator() { return new PreorderIterator(this); }
    CustomIterator inorderIterator() { return new InorderIterator(this); }
    CustomIterator postorderIterator() { return new PostorderIterator(this); }
}

class PreorderIterator implements CustomIterator {
    private Node currNode = null;
    private Node prevNode = null;
    private Node rootNode = null;

    public PreorderIterator(Node currNode) {
        this.currNode = currNode;
        this.rootNode = currNode;
    }

    @Override
    public boolean hasNext() {
        return (currNode != null);
    }

    @Override
    public int next() {
        Node retNode = currNode;
        if (currNode.left != null) {
            prevNode = currNode;
            currNode = currNode.left;

        }
        else if (currNode.right != null) {
            prevNode = currNode;
            currNode = currNode.right;
        }
        else {
            while (currNode != rootNode) {
                prevNode = currNode;
                currNode = currNode.parent;
                if (prevNode == currNode.left) {
                    if (currNode.right != null) {
                        prevNode = currNode;
                        currNode = currNode.right;
                        break;
                    }
                }
            }
            if (currNode == rootNode) {
                currNode = null;
            }
        }
        return retNode.contents;
    }
}

class InorderIterator implements CustomIterator{
    Stack<Node> stack = new Stack<Node>();

    public InorderIterator(Node rootNode) {
        findNext(rootNode);
    }

    @Override
    public boolean hasNext() {
        return (!stack.isEmpty());
    }

    public void findNext(Node currNode) {
        while (currNode != null) {
            stack.push(currNode);
            currNode = currNode.left;
        }
    }

    @Override
    public int next() {
        Node resNode = stack.pop();
        findNext(resNode.right);

        return resNode.contents;
    }
}

class PostorderIterator implements CustomIterator {
    Stack<Node> stack = new Stack<Node>();

    public PostorderIterator(Node root) {
        findNext(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public void findNext(Node currNode) {
        while (currNode != null) {
            stack.push(currNode);
            if (currNode.left != null) {
                currNode = currNode.left;
            }
            else {
                currNode = currNode.right;
            }
        }
    }

    @Override
    public int next() {

        Node resNode = stack.pop();
        if (!stack.isEmpty()) {
            Node top = stack.peek();
            if (resNode == top.left) {
                findNext(top.right);
            }

        }
        return resNode.contents;
    }
}
/*
class Main {
    public static void main(String[] args) {

        //ukazkovy strom podle http://cs.wikipedia.org/wiki/Strom_(datová_struktura)
        Node a = new Node(1, null, null);
        Node c = new Node(3, null, null);
        Node e = new Node(5, null, null);
        Node h = new Node(8, null, null);
        Node d = new Node(4, c, e);
        c.parent = d;
        e.parent = d;
        Node i = new Node(9, h, null);
        h.parent = i;
        Node b = new Node(2, a, d);
        a.parent = b;
        d.parent = b;
        Node g = new Node(7, null, i);
        i.parent = g;
        Node f = new Node(6, b, g);
        b.parent = f;
        g.parent = f;

        CustomIterator it = f.preorderIterator();
        while (it.hasNext()) {
            System.out.print((char) (64 + it.next()) + " ");
        }
        System.out.println();

        it = f.inorderIterator();
        while (it.hasNext()) {
            System.out.print((char) (64 + it.next()) + " ");

        }
        System.out.println();

        it = f.postorderIterator();
        while (it.hasNext()) {
            System.out.print((char) (64 + it.next()) + " ");
        }

        System.out.println();

    }
}*/