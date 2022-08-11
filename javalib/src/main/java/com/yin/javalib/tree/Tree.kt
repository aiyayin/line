package com.yin.javalib.tree

/**
 * Created by ying.fu on 2018/7/16.
 */
class Tree {
    var root: TreeNode? = null

    val level: Int
        get() = if (this.root == null) 0 else getLevel(1, this.root!!)

    private fun isLeaf(node: TreeNode?): Boolean {
        return node != null && node.leftNode == null && node.rightNode == null
    }

    private fun getLevel(level: Int, node: TreeNode?): Int {
        var level = level
        return if (isLeaf(node)) {
            level
        } else {
            level += 1
            level = getLevel(level, node?.leftNode).coerceAtLeast(getLevel(level, node?.rightNode))
            level
        }
    }

    private var s = ""
    fun preTraversal(): String {
        s = ""
        s = if (this.root == null) return s else preTraversal(this.root!!)
        return s
    }

    private fun preTraversal(node: TreeNode): String {
        s = s + node.value + ", "
        if (!isLeaf(node)) {
            preTraversal(node.leftNode!!)
            preTraversal(node.rightNode!!)
        }
        return s
    }

    fun inTraversal(): String {
        s = ""
        s = if (this.root == null) return s else {
            inTraversal(this.root!!)
        }
        return s
    }

    private fun inTraversal(node: TreeNode): String {
        if (!isLeaf(node)) {
            inTraversal(node.leftNode!!)
            s = s + node.value + ", "
            inTraversal(node.rightNode!!)
        } else {
            s = s + node.value + ", "
        }
        return s
    }

    fun postTraversal(): String {
        s = ""
        s = if (this.root == null) return s else postTraversal(this.root!!)
        return s
    }

    private fun postTraversal(node: TreeNode): String {
        s = if (!isLeaf(node)) {
            postTraversal(node.leftNode!!)
            postTraversal(node.rightNode!!)
            s + node.value + ", "
        } else {
            s + node.value + ", "
        }
        return s
    }
}