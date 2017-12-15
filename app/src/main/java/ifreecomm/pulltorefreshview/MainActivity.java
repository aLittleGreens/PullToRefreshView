package ifreecomm.pulltorefreshview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ifreecomm.pulltorefreshview.adapter.RecyclerAdapter;
import ifreecomm.pulltorefreshview.pullrefresh.PullToRefreshView;

public class MainActivity extends AppCompatActivity {

    private PullToRefreshView refreshView;
    private RecyclerView recyclerView;
    private List<String> mList;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.clear();
        for (int i = 0; i < 15; i++) {
            mList.add("item:" + i);
        }
    }

    private void initView() {
        refreshView = (PullToRefreshView) findViewById(R.id.refreshView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linerLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        adapter = new RecyclerAdapter(this, mList);
        recyclerView.setAdapter(adapter);
        refreshView.setPullDownEnable(true);
        refreshView.setPullUpEnable(true);
    }

    private void initListener() {
        refreshView.setListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refleshData();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
    }

    private void refleshData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                adapter.notifyDataSetChanged();
                refreshView.onFinishLoading();
            }
        }, 1500);
    }

    private void loadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mList.add("item:" + mList.size());
                adapter.notifyItemInserted(mList.size());
                refreshView.onFinishLoading();
            }
        }, 1500);


    }


}
