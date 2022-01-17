package com.yin.line.javabase.tree

import android.os.Bundle
import android.util.Log
import android.view.View
import com.yin.line.base.BaseActivity
import com.yin.line.base.util.ToolUtil.toast
import com.yin.line.javabase.R
import line.javafoundation.file.file
import line.javafoundation.tree.Tree
import line.javafoundation.tree.TreeNode


class TreeActivity : BaseActivity(), View.OnClickListener {
    private var mTree: Tree? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tree_activity)
        val treeNode3 = TreeNode("3", null, null)
        val treeNode4 = TreeNode("4", null, null)
        val treeNode5 = TreeNode("5", null, null)
        val treeNode6 = TreeNode("6", null, null)
        val treeNode1 = TreeNode("1", treeNode3, treeNode4)
        val treeNode2 = TreeNode("2", treeNode5, treeNode6)
        val root = TreeNode("0", treeNode1, treeNode2)
        mTree = Tree()
        mTree!!.root = root
        findViewById<View>(R.id.level).setOnClickListener(this)
        findViewById<View>(R.id.prePrint).setOnClickListener(this)
        findViewById<View>(R.id.inPrint).setOnClickListener(this)
        findViewById<View>(R.id.postPrint).setOnClickListener(this)
        readFile()
    }

    private fun readFile() {
        val file = file()
        file.readAssetsFile(this)?.let { Log.e("ying>>>>1", it) }
        file.writeFile(this)
        file.readFile(this)?.let { Log.e("ying>>>>22", it) }
        file.writeFile(this)
        file.readFile(this)?.let { Log.e("ying>>>>333", it) }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.level -> {
                toast("level: " + mTree!!.level)
                Log.e("ying>>>", "onClick: level: " + mTree!!.level)
            }
            R.id.prePrint -> {
                toast("prePrint: " + mTree!!.preTraversal())
                Log.e("ying>>>", "onClick: prePrint: " + mTree!!.preTraversal())
            }
            R.id.inPrint -> {
                toast("inPrint: " + mTree!!.inTraversal())
                Log.e("ying>>>", "onClick: inPrint: " + mTree!!.inTraversal())
            }
            R.id.postPrint -> {
                toast("postPrint: " + mTree!!.postTraversal())
                Log.e("ying>>>", "onClick: postPrint: " + mTree!!.postTraversal())
            }
        }
    }
}