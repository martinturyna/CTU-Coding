package alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Logic {
    int leftI;
    int rightI;
    BinaryTree binaryTree;
    List<Node> subTrees = new ArrayList<>();
    List<Node> finalNodes = new ArrayList<>();
    List<Node> solutionTree = new ArrayList<>();
    List<Integer> parcialSums = new ArrayList<>();
    List<Integer> depthValues = new ArrayList<>();
    Node solutionTreeRoot;

    public Logic(BinaryTree bt, int leftI, int rightI) {
        this.binaryTree = bt;
        this.leftI = leftI;
        this.rightI = rightI;
    }

    public int treeSize(Node root){
        if(root == null){
            return 0;
        }
        return 1+treeSize(root.rightChild) + treeSize(root.leftChild);
    }

    int depthCountVisited = -1;
    void iterateTree(Node root, int depth){
        if(root == null) {
            return;
        }
        if (depthCountVisited <= depth) {
            depthCountVisited = depth;
        }
        if (depthValues.size() == depth) {
            depthValues.add(1);
        }
        else if (depthValues.size() > depth){
            Integer tmp = depthValues.get(depth);
            tmp += 1;
            depthValues.set(depth, tmp);
        }

        iterateTree(root.leftChild,depth+1);
        iterateTree(root.rightChild,depth+1);
    }

    public void solution() { // The whole solution

        for (Node n: binaryTree.tree) {
            if (n.value >= leftI && n.value <= rightI) {
                n.isDeleted = true;
                if (n.leftChild != null) {
                    n.leftChild.parent = null;
                }
                if (n.rightChild != null) {
                    n.rightChild.parent = null;
                }
                if (n.parent != null) {
                    if (n.parent.leftChild == n) {
                        n.parent.leftChild = null;
                    }
                    else {
                        n.parent.rightChild = null;
                    }
                }
            }
            else if (n.value < leftI) {
                if (n.leftChild != null) {
                    if (n.leftChild.value > rightI) {
                        n.leftChild.parent = null;
                        n.leftChild = null;
                    }
                }
                if (n.rightChild != null) {
                    if (n.rightChild.value > rightI) {
                        n.rightChild.parent = null;
                        n.rightChild = null;
                    }
                }
                if (n.parent != null) {
                    if (n.parent.value > rightI) {
                        if (n.parent.leftChild == n) {
                            n.parent.leftChild = null;
                            n.parent = null;
                        }
                        else if (n.parent.rightChild == n) {
                            n.parent.rightChild = null;
                            n.parent = null;
                        }
                    }
                }
            }
            if(!n.isDeleted) { // Create array only with nodes, that are not deleted
                finalNodes.add(n);
            }
        }

        Collections.sort(finalNodes);
        for (Node n: finalNodes) { // Create separate array only with subRoots
            if (n.parent == null) {
                subTrees.add(n);
            }
        }

        int tmp = 0;
        for (Node n : subTrees) {
            tmp += treeSize(n);
            parcialSums.add(tmp);
        }

        solutionTreeRoot = merge(0, subTrees.size()-1);
        //System.out.println(solutionTreeRoot.value);

        /*for (Node n: solutionTree) {

            int l = -1, r = -1, p = -1;
            if (n.leftChild != null) {
                l = n.leftChild.value;
            }
            if (n.rightChild != null) {
                r = n.rightChild.value;
            }
            if (n.parent != null) {
                p = n.parent.value;
            }
            System.out.println("Node: " + n.value + " Left: " + l + " Right: " + r + " Parent: " + p);
        }*/

        iterateTree(solutionTreeRoot, 0);
        /*for (Integer i: depthValues) {
            System.out.println(i);
        }*/
        int lastIndex = depthValues.size()-2;
        System.out.println(depthCountVisited + " " + depthValues.get(lastIndex));


    }



    public Node merge(int l, int r) { // Add interval between left and right
        if (l > r) {
            return null;
        }
        if (l == r) {
            solutionTree.add(subTrees.get(l));
            return subTrees.get(l);
        }

        int tmp = 0;
        if (l > 0) {
            tmp = parcialSums.get(l-1);
        }
        int subSize = parcialSums.get(r) - tmp;
        int middleVal = ((subSize+1)/2) + tmp;
        int index = 0;
        for(int i = l; i <= r; i++) {
            if (parcialSums.get(i) >= middleVal) {
                index = i;
                break;
            }
        }
        Node node = subTrees.get(index);

        // Find and merge leftTree
        Node leftTree = merge(l,index-1);
        Node edgeNode = node; // This node represents the most left and right Node of each SubTree to merge
        while(edgeNode.leftChild != null) {
            edgeNode = edgeNode.leftChild;
        }
        edgeNode.leftChild = leftTree;
        if (leftTree != null) { // In the final solution is this part unnecessary
            leftTree.parent = edgeNode;
        }

        // Find and merge rightTree
        Node rightTree = merge(index+1, r);
        edgeNode = node;
        while(edgeNode.rightChild != null) {
            edgeNode = edgeNode.rightChild;
        }
        edgeNode.rightChild = rightTree;
        if (rightTree != null) { // In the final solution is this part unnecessary
            rightTree.parent = edgeNode;
        }

        solutionTree.add(node);
        return node;
    }
}
