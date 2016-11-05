package com.linhtran.vnua.demothuctap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import Adapter.ListViewAdapter;
import Data.ThoiGianBieu;
import DatabaseHandler.DatabaseHandler;

public class DanhSachCongViecTheoListVew extends AppCompatActivity {
    ListView listView;
    DatabaseHandler databaseHandler;
    List<ThoiGianBieu> listJob;
    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_cong_viec_theo_list_vew);
        databaseHandler=new DatabaseHandler(this);
        listJob= databaseHandler.getAllData();
        ThoiGianBieu job=new ThoiGianBieu();
        job.setDanhSach(listJob);
        listJob=job.sapXepTheoNgayBD();
        listView=(ListView)findViewById(R.id.listView);
        ListViewAdapter adapter=new ListViewAdapter(DanhSachCongViecTheoListVew.this,R.layout.custom_listview,listJob);
        listView.setAdapter(adapter);
        listViewItemOnClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void listViewItemOnClick()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(DanhSachCongViecTheoListVew.this,"Đã hoàn thành "+listJob.get(i).getPhanTram()+"%", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
