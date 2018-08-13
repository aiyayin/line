package line.tree;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.yingfu.line.R;

import line.util.ToolUtil;

public class TreeActivity extends AppCompatActivity implements View.OnClickListener {
    private Tree mTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tree_activity);

        TreeNode treeNode3 = new TreeNode("3", null, null);
        TreeNode treeNode4 = new TreeNode("4", null, null);
        TreeNode treeNode5 = new TreeNode("5", null, null);
        TreeNode treeNode6 = new TreeNode("6", null, null);

        TreeNode treeNode1 = new TreeNode("1", treeNode3, treeNode4);
        TreeNode treeNode2 = new TreeNode("2", treeNode5, treeNode6);

        TreeNode root = new TreeNode("0", treeNode1, treeNode2);

        mTree = new Tree();
        mTree.setRoot(root);

        findViewById(R.id.level).setOnClickListener(this);
        findViewById(R.id.prePrint).setOnClickListener(this);
        findViewById(R.id.inPrint).setOnClickListener(this);
        findViewById(R.id.postPrint).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.level:
                ToolUtil.toast("level: " + mTree.getLevel());
                Log.e("ying>>>", "onClick: level: " + mTree.getLevel());
                break;
            case R.id.prePrint:
                ToolUtil.toast("prePrint: " + mTree.preTraversal());
                Log.e("ying>>>", "onClick: prePrint: " + mTree.preTraversal());
                break;
            case R.id.inPrint:
                ToolUtil.toast("inPrint: " + mTree.inTraversal());
                Log.e("ying>>>", "onClick: inPrint: " + mTree.inTraversal());
                break;
            case R.id.postPrint:
                ToolUtil.toast("postPrint: " + mTree.postTraversal());
                Log.e("ying>>>", "onClick: postPrint: " + mTree.postTraversal());
                break;

        }
    }
}
