package com.example.lg.myrecyclerview.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.lg.myrecyclerview.R;
import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Tomdog on 2018/11/19.
 */

public class SelectableHeaderHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem> {
    private TextView tvValue;
    private PrintView arrowView;
    private CheckBox nodeSelector;

    public SelectableHeaderHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, IconTreeItemHolder.IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_selectable_header,null,false);

        tvValue = view.findViewById(R.id.node_value);
        tvValue.setText(value.text);

        final PrintView iconView = view.findViewById(R.id.icon);
        iconView.setIconText(context.getResources().getString(value.icon));

        arrowView = view.findViewById(R.id.arrow_icon);
        if(node.isLeaf()){
            arrowView.setVisibility(View.GONE);
        }

        nodeSelector = view.findViewById(R.id.node_selector);
        nodeSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                node.setSelectable(isChecked);
                for(TreeNode n : node.getChildren()){
                    getTreeView().selectNode(n,isChecked);
                }
            }
        });
        nodeSelector.setChecked(node.isSelected());
        return view;
    }

    @Override
    public void toggle(boolean active) {
//        super.toggle(active);
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    @Override
    public void toggleSelectionMode(boolean editModeEnabled) {
//        super.toggleSelectionMode(editModeEnabled);
        nodeSelector.setVisibility(editModeEnabled ? View.VISIBLE : View.GONE);
        nodeSelector.setChecked(mNode.isSelected());
    }
}
