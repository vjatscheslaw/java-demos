package com.example.demo.datastructures;

import java.util.Stack;

public class BinarySearchTree {

    public static void main(String[] args) {
        Tree tree = new Tree();

        for (int i = 0; i < 122; i++) {
            tree.insert((int) Math.round(Math.random() * 10000L), Math.random() + 1);
        }

        System.out.println(tree.displayTree());

//        Node found = tree.find(30);
//        if (found != null) System.out.println("Found the node with key=" + found.iData);
//        else System.out.println("failed to find");
    }


}

class Node {
    int iData;
    double dData;
    Node leftChild, rightChild;


    @Override
    public String toString() {
        return "Node{" +
                "iData=" + iData +
                ", fData=" + dData +
                '}';
    }
}

class Tree {
    private Node root;

    public Tree() {
    }

    //O(logN)
    public Node find(int key) {
        Node current = root;
        while (current.iData != key) {
            if (key < current.iData)
                current = current.leftChild;
            else
                current = current.rightChild;
            if (current == null) return null;
        }
        return current;
    }

    public void insert(int id, double dd) {
        Node newNode = new Node();
        newNode.iData = id;
        newNode.dData = dd;
        if (root == null)
            root = newNode;
        else {
            Node current = root;
            Node parent;
            while (true) {
                parent = current;
                if (id < current.iData) {
                    current = current.leftChild;
                    if (current == null) {
                        parent.leftChild = newNode;
                        return;
                    }
                } else {
                    current = current.rightChild;
                    if (current == null) {
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    }

    public boolean delete(int key) {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        while (current.iData != key) {
            parent = current;
            if (key < current.iData) {
                isLeftChild = true;
                current = current.leftChild;
            } else {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null) return false;
        }
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) root = null;
            else if (isLeftChild) parent.leftChild = null;
            else parent.rightChild = null;
        } else if (current.rightChild == null) {
            if (current == root) root = current.leftChild;
            else if (isLeftChild) parent.leftChild = current.leftChild;
            else parent.rightChild = current.leftChild;
        } else if (current.leftChild == null) {
            if (current == root) root = current.rightChild;
            else if (isLeftChild) parent.leftChild = current.rightChild;
            else parent.rightChild = current.rightChild;
        } else {
            Node successor = getSuccessor(current);
            if (current == root) root = successor;
            else if (isLeftChild) parent.leftChild = successor;
            else parent.rightChild = successor;
        }
        return true;
    }

    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

    public void traverse(int traverseType) {
        switch (traverseType) {
            case 1:
                System.out.println("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2:
                System.out.println("\nInorder traversal: ");
                inOrder(root);
                break;
            case 3:
                System.out.println("\nPostorder traversal: ");
                postOrder(root);
                break;
        }
        System.out.println();
    }

    private void preOrder(Node localRoot) {
        if (localRoot != null) {
            System.out.print(localRoot.iData + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }

    //Симметричный обход - с ним можно сделать из дерева отсортированный список
    private void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.iData + " ");
            inOrder(localRoot.rightChild);
        }
    }

    private void postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.iData + " ");
        }
    }


    public String displayTree() {
        StringBuffer sb = new StringBuffer();
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        sb.append(".......................................\n");
        while (!isRowEmpty) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int j = 0; j < nBlanks; j++) sb.append(" ");
            while (!globalStack.isEmpty()) {
                Node temp = (Node) globalStack.pop();
                if (temp != null) {
                    sb.append(temp.iData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);
                    if (temp.leftChild != null || temp.rightChild != null) {
                        isRowEmpty = false;
                    }
                } else {
                    sb.append("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
            nBlanks /= 2;
            while (!localStack.isEmpty()) {
                globalStack.push(localStack.pop());
            }
        }
        sb.append("..........................................\n");
        return sb.toString();
    }
}

