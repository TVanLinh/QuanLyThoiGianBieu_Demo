package com.linhtran.vnua.demothuctap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Data.ThoiGianBieu;
import DatabaseHandler.DatabaseHandler;
import RangBuoc.RangBuoc;

public class CapNhatThongTin extends AppCompatActivity {
    EditText edtSTDate, edtEDate, edtSTDay, edtEDay, edtCongViec, edtLuuY;
    TextView txtThu;
    Button btnSave, btnThoat;
    DatabaseHandler databaseHandler;
    String str="";
    ThoiGianBieu thoiGianBieuXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_thong_tin);
       // Intent intent=getIntent();
        databaseHandler=new DatabaseHandler(this);

        edtSTDate = (EditText)findViewById(R.id.edtStartDate);
        edtEDate = (EditText) findViewById(R.id.edtEndDate);
        edtSTDay = (EditText) findViewById(R.id.edtStartDay);
        edtEDay = (EditText) findViewById(R.id.edtEndDay);
        edtCongViec = (EditText) findViewById(R.id.edtCongViec);
        edtLuuY = (EditText) findViewById(R.id.edtLuuY);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnThoat = (Button) findViewById(R.id.btnExit);
        txtThu=(TextView)findViewById(R.id.txtThu);
        setDuLieuEditText();
        btnSaveClick();
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(33);
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            item.setIcon(R.drawable.iconback);
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String changeFormatDate1(String str)
    {
        String mang[]=str.split("-");
        return mang[2]+"-"+mang[1]+"-"+mang[0];
    }
    public void setDuLieuEditText()
    {
        Intent intent=getIntent();
        thoiGianBieuXoa=new ThoiGianBieu();
        thoiGianBieuXoa.setGioBD(intent.getStringExtra("gioBD"));
        thoiGianBieuXoa.setNgayBD(intent.getStringExtra("ngayBD"));
        thoiGianBieuXoa.setKey();
        thoiGianBieuXoa.setGioKT(intent.getStringExtra("gioKT"));
        thoiGianBieuXoa.setCongViec(intent.getStringExtra("congViec"));
        thoiGianBieuXoa.setLuuY(intent.getStringExtra("luuY"));
        str=intent.getStringExtra("gioBD")+"-"+intent.getStringExtra("ngayBD");

        edtSTDate.setText(intent.getStringExtra("gioBD"));
        edtEDate.setText(intent.getStringExtra("gioKT"));
        edtSTDay.setText(intent.getStringExtra("ngayBD"));
        txtThu.setText(intent.getStringExtra("thu"));
        if(!intent.getStringExtra("ngayBD").trim().equals(""))
        {
            edtSTDay.setText(changeFormatDate1(intent.getStringExtra("ngayBD")));
        }
        edtEDay.setText(intent.getStringExtra("ngayKT"));
        if(!intent.getStringExtra("ngayKT").trim().equals(""))
        {
            edtEDay.setText(changeFormatDate1(intent.getStringExtra("ngayKT")));
        }
        edtCongViec.setText(intent.getStringExtra("congViec"));
        edtLuuY.setText(intent.getStringExtra("luuY"));

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CapNhatThongTin.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void btnSaveClick()
    {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RangBuoc rangBuoc=new RangBuoc(CapNhatThongTin.this);

                String ngayBD=edtSTDay.getText().toString().trim();
                String ngayKT=edtEDay.getText().toString().trim();
                ThoiGianBieu thoiGianBieu=new ThoiGianBieu();
                thoiGianBieu.setGioBD(edtSTDate.getText().toString());
                thoiGianBieu.setGioKT(edtEDate.getText().toString());

                thoiGianBieu.setCongViec(edtCongViec.getText().toString().trim());
                thoiGianBieu.setLuuY(edtLuuY.getText().toString().trim());

                if(rangBuoc.kiemTraRangBuocGio(thoiGianBieu)==0)
                {
                    return;
                }
                if(rangBuoc.kiemTraRangBuocKhiNhapNgay(ngayBD,ngayKT)==0)
                {
                    Toast.makeText(CapNhatThongTin.this, "Ngày bắt đầu và ngày kết thúc nhập chưa đúng định dạnh dd-MM hoặc dd-MM-YY", Toast.LENGTH_SHORT).show();
                    return;
                }
                String ngayChuoi =rangBuoc.changeFormatDay(edtSTDay.getText().toString());
                String ngayChuoi2 = rangBuoc.changeFormatDay(edtEDay.getText().toString());
                thoiGianBieu.setNgayBD(ngayChuoi);
                thoiGianBieu.setNgayKT(ngayChuoi2);
                thoiGianBieu.setKey();
                if(rangBuoc.kiemTraRangBuoc(thoiGianBieu)==0)
                {
                    return;
                }
                ThoiGianBieu luu=new ThoiGianBieu();
                luu.setGioBD(thoiGianBieu.getGioBD());
                luu.setGioKT(thoiGianBieu.getGioKT());
                luu.setNgayBD(rangBuoc.changeFormatDayRepeat(ngayChuoi));
                luu.setNgayKT(rangBuoc.changeFormatDayRepeat(ngayChuoi2));
                luu.setKey();
                luu.setCongViec(edtCongViec.getText().toString());
                luu.setLuuY(edtLuuY.getText().toString());
                List<ThoiGianBieu> listAll=new ArrayList<ThoiGianBieu>();
                listAll=databaseHandler.getAllData();
                int result=0;

                int i=0;

                ThoiGianBieu tmp=new ThoiGianBieu();
                List<ThoiGianBieu> kt=new ArrayList<ThoiGianBieu>();
                for (int j=0;j<listAll.size();j++)
                {
                    kt.add(listAll.get(j));
                }

                i=0;
                while (i<kt.size())
                {
                    if(thoiGianBieuXoa.getKey().trim().equals(listAll.get(i).getKey().trim()))
                    {
                        kt.remove(i);
                        break;
                    }
                    i++;
                }

                i=0;

                while (i<kt.size())
                {
                    if(luu.kiemTraTrungLich(kt.get(i))==1)
                    {
                        result=1;
                        break;
                    }
                    i++;
                }

                if(result==1)
                {
                    Toast.makeText(CapNhatThongTin.this, "Thời điểm này đã có một việc khác nữa.!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(luu.getKey().trim().equals(str))
                {
                    creatAlertDialog("Thông báo","Bạn có muốn thay đổi không?",luu);
                }
                else
                {
                    creatAlertDialog("Thông báo","Bạn có muốn thay đổi không?",luu);
                }
            }
        });
    }
    public  void creatAlertDialog(String title, String message, final ThoiGianBieu thoiKhoaBieu)
    {
        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(true);
        dialog.setNegativeButton("Không",null);
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseHandler.deleteData(str);
                if (databaseHandler.insertData(thoiKhoaBieu) != -1) {
                    Toast.makeText(CapNhatThongTin.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    setResult(33);
                    finish();
                }
            }
        });
        dialog.show();
    }
}
