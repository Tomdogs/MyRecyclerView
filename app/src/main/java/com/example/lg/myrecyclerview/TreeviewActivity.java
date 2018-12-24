package com.example.lg.myrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lg.myrecyclerview.holder.IconTreeItemHolder;
import com.example.lg.myrecyclerview.holder.SelectableHeaderHolder;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

public class TreeviewActivity extends AppCompatActivity {
    private static final String NAME = "行政区划";
    private AndroidTreeView tView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treeview);

        ViewGroup containerView = (ViewGroup) findViewById(R.id.container);

        TreeNode root = TreeNode.root();

        TreeNode s1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"北京"))
                .setViewHolder(new SelectableHeaderHolder(this));
        TreeNode s2 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"河南"))
                .setViewHolder(new SelectableHeaderHolder(this));

        fillFolder(s1);
        fillFolder(s2);
        root.addChildren(s1,s2);
        for(int i =0;i<34;i++){
            TreeNode s3 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,"河南 "+(1+i)))
                    .setViewHolder(new SelectableHeaderHolder(this));
            fillFolder(s3);
            root.addChildren(s3);
        }


        tView = new AndroidTreeView(this,root);
        tView.setDefaultAnimation(true);//默认动画
        tView.setUse2dScroll(true);//上下左右可以滑动
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);//，默认样式
        tView.setDefaultNodeClickListener(nodeClickListener);

        containerView.addView(tView.getView());

//        tView.expandAll();

        if(savedInstanceState != null){
            String state = savedInstanceState.getString("tState");
            if(!TextUtils.isEmpty(state)){
                tView.restoreState(state);
            }
        }
    }


    private void fillFolder(TreeNode folder){
        TreeNode currentNode = folder;
        for (int i= 0;i < 5;i++){
            TreeNode file = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, NAME+" "+i))
                    .setViewHolder(new SelectableHeaderHolder(this));
            currentNode.addChild(file);
            currentNode = file;
        }
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            Toast.makeText(TreeviewActivity.this,"点击了 "+node.getId()+" "+item.text,Toast.LENGTH_LONG).show();
        }
    };
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState",tView.getSaveState());
    }
}
