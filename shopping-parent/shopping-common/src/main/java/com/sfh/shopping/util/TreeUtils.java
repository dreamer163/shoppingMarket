package com.sfh.shopping.util;

import java.util.*;
import java.util.function.Function;

/**
 * 树工具类
 *
 * @author snow1k
 * @version 1.0.0
 * <p>
 * created by snow1k on 2023-03-29
 */
public class TreeUtils {
    public static <T extends Identity<ID>, ID> TreeNode<T, ID> buildTreeByParent(List<T> nodes, Function<T, T> parentLoader) {
        return buildTree(nodes, parentLoader, null);
    }

    public static <T extends Identity<ID>, ID> TreeNode<T, ID> buildTreeByParentId(List<T> nodes, Function<T, ID> parentIdLoader) {
        return buildTree(nodes, null, parentIdLoader);
    }

    /**
     * 构建树
     */
    public static <T extends Identity<ID>, ID> TreeNode<T, ID> buildTree(List<T> nodes, Function<T, T> parentLoader, Function<T, ID> parentIdLoader) {
        TreeNodeImpl<T, ID> root = new TreeNodeImpl<>(null);//根节点
        root.setPhantom(true);//设置为虚节点

        Map<ID, TreeNode<T, ID>> nodeMap = new HashMap<>();//缓存所有树节点
        List<Runnable> lazyTasks = new ArrayList<>();//延迟执行的任务

        for (T node : nodes) {
            TreeNodeImpl<T, ID> thisNode = new TreeNodeImpl<>(node);
            nodeMap.put(thisNode.getId(), thisNode);//存入当前节点

            if (parentLoader != null) {
                T parent = parentLoader.apply(node);
                if (parent != null) {
                    TreeNodeImpl<T, ID> parentNode = new TreeNodeImpl<>(parent);
                    thisNode.setParent(parentNode);

                    TreeNode<T, ID> mapParentNode = nodeMap.get(parentNode.getId());
                    if (mapParentNode != null) {//同编号的父节点已存在
                        thisNode.setParent(mapParentNode);
                    } else {
                        nodeMap.put(parentNode.getId(), parentNode);
                    }
                    continue;
                }
            }

            if (parentIdLoader != null) {
                ID parentId = parentIdLoader.apply(node);
                if (parentId != null) {
                    TreeNode<T, ID> parentNode = nodeMap.get(parentId);
                    if (parentNode != null) {
                        thisNode.setParent(parentNode);
                    } else {//在map中暂时不存在，可能还没遍历到
                        lazyTasks.add(() -> thisNode.setParent(nodeMap.get(parentId)));
                    }
                    continue;
                }
            }

            thisNode.setParent(root);
        }

        //执行延迟任务
        for (Runnable task : lazyTasks) {
            task.run();
        }

        return root;
    }

    public interface Identity<ID> {
        ID getId();
    }

    public interface TreeNode<T extends Identity<ID>, ID> extends Identity<ID> {
        T get();

        default ID getId() {
            return get() == null ? null : get().getId();
        }

        TreeNode<T, ID> getParent();

        List<TreeNode<T, ID>> getChildren();

        /**
         * 是否虚节点
         */
        default boolean isPhantom() {
            return false;
        }

        /**
         * 是否叶子节点
         */
        default boolean isLeaf() {
            return getChildren() == null || getChildren().size() == 0;
        }
    }

    private static class TreeNodeImpl<T extends Identity<ID>, ID> implements TreeNode<T, ID> {
        private final T data;

        private TreeNode<T, ID> parent;

        private final List<TreeNode<T, ID>> children;

        private boolean phantom;

        private TreeNodeImpl(T data) {
            this.data = data;
            this.children = new ArrayList<>();
        }


        public T get() {
            return this.data;
        }

        public void setParent(TreeNode<T, ID> parent) {
            if (parent != this.getParent()) {//设置新的父节点
                if (this.getParent() != null) {
                    this.getParent().getChildren().remove(this);
                }
            }

            this.parent = parent;
            if (parent != null) {
                parent.getChildren().add(this);
            }
        }

        @Override
        public TreeNode<T, ID> getParent() {
            return this.parent;
        }

        @Override
        public List<TreeNode<T, ID>> getChildren() {
            return this.children;
        }


        @Override
        public boolean isPhantom() {
            return phantom;
        }

        public void setPhantom(boolean phantom) {
            this.phantom = phantom;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TreeNodeImpl<?, ?> treeNode = (TreeNodeImpl<?, ?>) o;
            return this.getId().equals(treeNode.getId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getId());
        }
    }
}
