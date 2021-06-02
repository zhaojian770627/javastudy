package com.zj.study.algorithm.tree.avl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class AVLTree {
	class AVLNode {
		int value;
		AVLNode left;
		AVLNode right;
		int height;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public AVLNode getLeft() {
			return left;
		}

		public void setLeft(AVLNode left) {
			this.left = left;
		}

		public AVLNode getRight() {
			return right;
		}

		public void setRight(AVLNode right) {
			this.right = right;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}

	int Height(AVLNode avl) {
		if (avl == null)
			return -1;
		else
			return avl.getHeight();
	}

	AVLNode insert(int x, AVLNode T) {
		if (T == null) {
			T = new AVLNode();
			T.setValue(x);
			T.setLeft(null);
			T.setRight(null);
		} else if (x < T.getValue()) {
			T.setLeft(insert(x, T.getLeft()));
			if (Height(T.getLeft()) - Height(T.getRight()) == 2) {
				if (x < T.getLeft().getValue())
					T = SingleRotateWithLeft(T);
				else
					T = DoubleRotateWithLeft(T);
			}
		} else if (x > T.getValue()) {
			T.setRight(insert(x, T.getRight()));
			if (Height(T.getRight()) - Height(T.getLeft()) == 2)
				if (x > T.getRight().getValue())
					T = SingleRotateWithRight(T);
				else
					T = DoubleRotateWithRight(T);
		}

		T.setHeight(Math.max(Height(T.getLeft()), Height(T.getRight())) + 1);
		return T;
	}

	class AVLTreeNode {
		AVLNode node;
		String title;

		public AVLTreeNode(AVLNode node, String title) {
			this.node = node;
			this.title = title;
		}

		@Override
		public String toString() {
			return title + String.valueOf(node.getValue()) + "|" + node.getHeight();
		}
	}

	private AVLNode SingleRotateWithLeft(AVLNode k2) {
		AVLNode k1;
		k1 = k2.getLeft();
		k2.setLeft(k1.getRight());
		k1.setRight(k2);
		k2.setHeight(Math.max(Height(k2.getLeft()), Height(k2.getRight())) + 1);
		k1.setHeight(Math.max(Height(k1.getLeft()), k2.getHeight()) + 1);
		return k1;
	}

	private AVLNode SingleRotateWithRight(AVLNode k2) {
		AVLNode k1;
		k1 = k2.getRight();
		k2.setRight(k1.getLeft());
		k1.setLeft(k2);
		k2.setHeight(Math.max(Height(k2.getLeft()), Height(k2.getRight())) + 1);
		k1.setHeight(Math.max(Height(k1.getLeft()), k2.getHeight()) + 1);
		return k1;
	}

	private AVLNode DoubleRotateWithLeft(AVLNode k3) {
		k3.setLeft(SingleRotateWithRight(k3.getLeft()));
		return SingleRotateWithLeft(k3);
	}

	private AVLNode DoubleRotateWithRight(AVLNode k3) {
		k3.setRight(SingleRotateWithLeft(k3.getRight()));
		return SingleRotateWithRight(k3);
	}

	/**
	 * 
	 * 1.如果是null节点，则直接返回null 2.如果删除的值<当前节点，则转入left节点进行递归删除
	 * 3.如果删除的值>当前节点，则转入right节点进行递归删除 4.如果删除的值为当前节点，如果当前节点只有一个子树，则直接返回该子树
	 * 5.如果删除的值为当前节点，且当前节点有两个子树，则将当前值更改为右子树中最小的节点值，并递归在右子树中删除该节点值
	 * 6.重新修正该处理节点的height值 7.对处理节点进行重新翻转处理，以修正在删除过程中可能出现的树不平衡情况
	 * 
	 * @param x
	 * @param T
	 * @return
	 */
	private AVLNode remove(int x, AVLNode T) {

		if (T == null)
			return null;
		if (x < T.getValue()) {
			T.setLeft(remove(x, T.getLeft()));

		} else if (x > T.getValue()) {
			T.setRight(remove(x, T.getRight()));

		} else if (T.left != null && T.right != null) {
			T.setValue(findMin(T.getRight()).getValue());
			T.setRight(remove(T.getValue(), T.getRight()));
		} else {
			T = T.getLeft() == null ? T.getRight() : T.getLeft();
		}

		if (T != null)
			T.setHeight(Math.max(Height(T.getLeft()), Height(T.getRight())) + 1);

		T = rotate(T);
		return T;
	}

	private AVLNode rotate(AVLNode T) {
		if (T == null)
			return null;
		if (Height(T.getLeft()) - Height(T.getRight()) == 2) {
			if (Height(T.getLeft().getLeft()) >= Height(T.getLeft().getRight()))
				return SingleRotateWithLeft(T);
			else
				return DoubleRotateWithLeft(T);
		} else if (Height(T.getRight()) - Height(T.getLeft()) == 2) {
			if (Height(T.getRight().getRight()) >= Height(T.getRight().getLeft()))
				return SingleRotateWithRight(T);
			else
				return DoubleRotateWithRight(T);
		}
		return T;
	}

	AVLNode findMin(AVLNode tree) {
		if (tree != null)
			while (tree.getLeft() != null)
				tree = tree.getLeft();
		return tree;
	}

	private AVLNode floor(int num, AVLNode node) {
		if (node == null)
			return null;
		if (num == node.getValue())
			return node;
		if (num < node.getValue())
			return floor(num, node.getLeft());

		AVLNode t = floor(num, node.getRight());
		if (t != null)
			return t;
		return node;
	}

	/**
	 * 
	 * 寻找指定长度的路径的个数
	 * 
	 * 算法就是每个节点都逐层推进，找一遍
	 * 
	 * @param node
	 * @param target
	 * @return
	 */
	private int findPathLength(AVLNode node, int target) {
		if (node != null)
			return innerFindPath(node, target) + findPathLength(node.getLeft(), target)
					+ findPathLength(node.getRight(), target);
		return 0;
	}

	private int innerFindPath(AVLNode node, int target) {
		if (node != null)
			return node.value == target ? 1
					: 0 + innerFindPath(node.getLeft(), target - node.getValue())
							+ innerFindPath(node.getRight(), target - node.getValue());
		return 0;
	}

	/**
	 * 
	 * 判断是否是Bst树
	 * 
	 * @param node
	 * @param minV
	 * @param maxV
	 * @return
	 */
	private boolean isBst(AVLNode node, int minV, int maxV) {
		if (node == null)
			return true;

		if (node.getValue() < minV || node.getValue() > maxV)
			return false;

		return isBst(node.getLeft(), minV, node.getValue()) && isBst(node.getRight(), node.getValue(), maxV);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AVLTree avlTree = new AVLTree();
		avlTree.Show();
	}

	DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Empty");
	AVLNode avlRoot = null;
	JTree tree;

	private void Show() {
		JFrame f = new JFrame("AvlTree");
		f.setLayout(new BorderLayout());
		f.setSize(600, 600);

		tree = new JTree(rootNode);
		f.add(tree, BorderLayout.CENTER);
		JPanel operPnl = new JPanel();
		f.add(operPnl, BorderLayout.SOUTH);
		final JTextField jtNum = new JTextField(20);
		JButton btnPrepare = new JButton("Prepare");
		JButton btnAdd = new JButton("Add");
		JButton btnDel = new JButton("Del");
		JButton btnFloor = new JButton("Floor");
		JButton btnTest = new JButton("Test");
		JButton btnClear = new JButton("Clear");

		operPnl.add(jtNum);
		operPnl.add(btnPrepare);
		operPnl.add(btnAdd);
		operPnl.add(btnDel);
		operPnl.add(btnFloor);
		operPnl.add(btnTest);
		operPnl.add(btnClear);

		btnPrepare.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] data = { 40, 20, 70, 50, 10, 60, 30, 80 };
				for (int i = 0; i < data.length; i++) {
					avlRoot = insert(data[i], avlRoot);
				}
				refreshTree();
			}

		});

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String snum = jtNum.getText();
				if (snum.equals(""))
					return;
				int num = Integer.parseInt(snum);
				AddNum(num);
				jtNum.setText("");
			}

		});

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String snum = jtNum.getText();
				if (snum.equals(""))
					return;
				int num = Integer.parseInt(snum);
				AddNum(num);
				jtNum.setText("");
			}

		});

		btnDel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String snum = jtNum.getText();
				if (snum.equals(""))
					return;
				int num = Integer.parseInt(snum);
				delNum(num);
				jtNum.setText("");
			}

		});

		/**
		 * 寻找等于指定 数字或小于指定数字的最大的数字
		 * 
		 * @param tree
		 * @return
		 */
		btnFloor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String snum = jtNum.getText();
				if (snum.equals(""))
					return;
				int num = Integer.parseInt(snum);

				AVLNode node = floor(num, avlRoot);
				if (node == null)
					JOptionPane.showMessageDialog(null, "未找到");
				else
					JOptionPane.showMessageDialog(null, node.getValue());

				jtNum.setText("");
			}

		});

		btnTest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog(null, isBst(avlRoot, Integer.MIN_VALUE, Integer.MAX_VALUE));
				JOptionPane.showMessageDialog(null, findPathLength(avlRoot, 60));
			}

		});

		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				avlRoot = null;
				DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Empty");
				DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
				tree.setModel(treeModel);
			}
		});
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * AVLNode avlRoot = insert(10, null); insert(11, avlRoot); insert(9, avlRoot);
		 * 
		 * addChildTree(rootNode, avlRoot);
		 */
	}

	private void AddNum(int num) {
		avlRoot = insert(num, avlRoot);

		refreshTree();
	}

	private void refreshTree() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new AVLTreeNode(avlRoot, "Root "));
		addChildTree(rootNode, avlRoot);
		DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		tree.setModel(treeModel);
		expandTree(tree, new TreePath(rootNode));
	}

	private void delNum(int num) {
		avlRoot = remove(num, avlRoot);
		if (avlRoot == null) {
			DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Empty");
			DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
			tree.setModel(treeModel);
			return;
		}
		refreshTree();

	}

	private void addChildTree(DefaultMutableTreeNode root, AVLNode avlRoot) {
		if (avlRoot.getLeft() != null) {
			DefaultMutableTreeNode leftNode = new DefaultMutableTreeNode(new AVLTreeNode(avlRoot.getLeft(), "Left  "));
			addChildTree(leftNode, avlRoot.getLeft());
			root.add(leftNode);
		}
		if (avlRoot.getRight() != null) {
			DefaultMutableTreeNode rightNode = new DefaultMutableTreeNode(
					new AVLTreeNode(avlRoot.getRight(), "Right "));
			addChildTree(rightNode, avlRoot.getRight());
			root.add(rightNode);
		}
	}

	private void expandTree(JTree tree, TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandTree(tree, path);
			}
		}
		tree.expandPath(parent);
	}

}
