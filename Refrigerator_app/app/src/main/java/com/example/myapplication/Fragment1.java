package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment{

    // List item
    public List<PhGridItem> mItemList;
    // Grid view
    public GridView mGridView;
    // GridView adapter
    public PhGridArrayAdapter mGridAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);

        mGridView = (GridView) view.findViewById(R.id.gridview);

        // Grid 설정
        bindGrid();
        return view;
    }

    public void bindGrid() {

        Cal_dday cd = new Cal_dday();

        // Grid item 생성
        List<String> item = new ArrayList<String>();
        List<String> date = new ArrayList<String>();
        List<Integer> d_day = new ArrayList<Integer>();
        DatabaseHelper db = new DatabaseHelper(getActivity());
        Cursor res = db.readInfo("냉장");

        while(res.moveToNext()){
            item.add(res.getString(1));
            date.add(res.getString(4));

            String intStr = res.getString(4).replaceAll("[^0-9]", "");
            if(intStr.isEmpty() || intStr == null) {
                d_day.add(0);
            }
            else{
                String year = "20";
                int myear = Integer.parseInt(year.concat(intStr.substring(0, 2)));
                int mmonth = Integer.parseInt(intStr.substring(2, 4));
                int mday = Integer.parseInt(intStr.substring(4, 6));

                d_day.add(cd.countdday(myear, mmonth, mday));
            }
        }
        mItemList = new ArrayList<>();
        for(int i=0; i<item.size(); i++){
            int id;
            ProductIcon pd = new ProductIcon();
            id = pd.p_icon(item.get(i));
            mItemList.add(new PhGridItem(id, item.get(i), date.get(i), d_day.get(i)));
        }

        // Grid adapter
        mGridAdapter = new PhGridArrayAdapter(getActivity(), mItemList);
        mGridView.setAdapter(mGridAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final PhGridItem item = (PhGridItem) mGridAdapter.getItem(i);
                bindGrid();
                Intent intent = new Intent(getActivity(), UpdateInfo.class);
                intent.putExtra("p_name", item.getName());
                intent.putExtra("p_date", item.getDate());
                startActivity(intent);
            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final PhGridItem item = (PhGridItem) mGridAdapter.getItem(i);

                PhGridItemViewHolder viewHolder = new PhGridItemViewHolder(view);
                viewHolder.delete.setVisibility(View.VISIBLE);
                viewHolder.delete.setEnabled(true);

                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseHelper db = new DatabaseHelper(getActivity());
                        db.DeleteInfo(item.getName(), item.getDate());
                        bindGrid();
                        Toast.makeText(getActivity(),"삭제가 완료되었습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });
    }
}
