package com.softdesign.devintensive.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.ui.activities.BaseActivity;

import java.util.List;

public class RepositoriesAdapter extends BaseAdapter {
    private List<String> mRepoList;
    private Context mContext;
    private LayoutInflater mInflater;

    public RepositoriesAdapter(Context context, List<String> repoList) {
        mRepoList = repoList;
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mRepoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRepoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;

        if (itemView == null){
            itemView = mInflater.inflate(R.layout.item_repositories_list, parent, false);
        }

        TextView repoName = (TextView) itemView.findViewById(R.id.gitrepo_et);
        repoName.setText(mRepoList.get(position));

        return itemView;
    }
}
