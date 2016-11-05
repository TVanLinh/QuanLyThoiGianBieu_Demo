package com.linhtran.vnua.demothuctap;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Adapter.TimKiemAdapter;
import Data.ThoiGianBieu;
import DatabaseHandler.DatabaseHandler;

public class TimKiemActivity extends AppCompatActivity {
    List<ThoiGianBieu> listJob;
    DatabaseHandler databaseHandler;
    EditText edtCV;
    TextView txtNoiDung;
    ListView listView;
    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        databaseHandler=new DatabaseHandler(this);
        edtCV =(EditText)findViewById(R.id.edtCV);
        txtNoiDung=(TextView)findViewById(R.id.txtNoiDung);
        listView=(ListView)findViewById(R.id.listiViewTK);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(TimKiemActivity.this, ""+listJob.get(i).getCongViec(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timkiem,menu);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.timKiem);
        View view= MenuItemCompat.getActionView(item);
        ImageButton ibtnSearch=(ImageButton)view.findViewById(R.id.ibtnTimKiem);
        ibtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=edtCV.getText().toString().trim();
                if(str.equals(""))
                {
                    Toast.makeText(TimKiemActivity.this, "Bạn chưa nhập công viêc.!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listJob=databaseHandler.searchData(str);
                if (listJob.size()==0)
                {
                    Toast.makeText(TimKiemActivity.this, "Công việc không được tìm thấy", Toast.LENGTH_SHORT).show();
                    String cv="Không có công việc nào";
                  // txtNoiDung.setText(cv);
                    return;
                }
                Toast.makeText(TimKiemActivity.this, listJob.size()+" công việc được tìm thấy", Toast.LENGTH_SHORT).show();
                loadJob();
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }
    public void loadJob()
    {
        ThoiGianBieu thoiGian=new ThoiGianBieu();
        thoiGian.setDanhSach(listJob);
        listJob=thoiGian.sapXepTheoThu();
        TimKiemAdapter adapter=new TimKiemAdapter(TimKiemActivity.this,R.layout.custom_layout_timkiem,listJob);
        listView.setAdapter(adapter);
    }
}
