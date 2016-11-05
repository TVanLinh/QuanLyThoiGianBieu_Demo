package com.linhtran.vnua.demothuctap;

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
import java.util.Date;
import java.util.List;

import Data.ThoiGianBieu;
import DatabaseHandler.DatabaseHandler;
import RangBuoc.RangBuoc;

public class TaoMoiThongTin extends AppCompatActivity {
    EditText edtSTDate, edtEDate, edtSTDay, edtEDay, edtCongViec, edtLuuY;
    TextView txtThu;
    DatabaseHandler databaseHandler;
    Button btnSave, btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler=new DatabaseHandler(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.layout_nhhapcongviec);

        edtSTDate = (EditText)findViewById(R.id.edtStartDate);
        edtEDate = (EditText) findViewById(R.id.edtEndDate);
        edtSTDay = (EditText) findViewById(R.id.edtStartDay);
        edtEDay = (EditText) findViewById(R.id.edtEndDay);
        edtCongViec = (EditText) findViewById(R.id.edtCongViec);
        edtLuuY = (EditText) findViewById(R.id.edtLuuY);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnThoat = (Button) findViewById(R.id.btnExit);

        setDuLieuEditText();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RangBuoc rangBuoc=new RangBuoc(TaoMoiThongTin.this);

//                String rangBuocNgayBD = "^((0[1-9]|(0[1-9]|[1-2][0-9])|3[0-1])-(01|03|05|07|08|10|12)-20[0-9][0-9])|(((0[1-9]|([0-2][1-9])|10|20|30)-(02|04|06|09|11)-20[0-9][0-9]))|((0[1-9]|[0-2][1-9]|20)-(02)-20[0-9][0-9])$";
//                String ddMM="^(0[1-9]|1[0-9]|2[0-9]|3[0-1])-(0[1-9]|1[0-2]$";
   //             String gioBD = edtSTDate.getText().toString();
//                String gioKetThuc = edtEDate.getText().toString();
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
                    Toast.makeText(TaoMoiThongTin.this, "Ngày bắt đầu và ngày kết thúc nhập chưa đúng định dạnh dd-MM hoặc dd-MM-YY", Toast.LENGTH_SHORT).show();
                    return;
                }
                String ngayChuoi = rangBuoc.changeFormatDay(edtSTDay.getText().toString());
                String ngayChuoi2 =rangBuoc.changeFormatDay(edtEDay.getText().toString());
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
                luu.setNgayBD(changeFormatDayRepeat(ngayChuoi));
                luu.setNgayKT(changeFormatDayRepeat(ngayChuoi2));
                luu.setKey();
                luu.setCongViec(edtCongViec.getText().toString());
                luu.setLuuY(edtLuuY.getText().toString());
                List<ThoiGianBieu> listAll=new ArrayList<ThoiGianBieu>();
                listAll=databaseHandler.getAllData();
                int result=0;
                int i=0;
                while (i<listAll.size())
                {
                    if(luu.kiemTraTrungLich(listAll.get(i))==1)
                    {
                        result=1;
                        break;
                    }
                    i++;
                }
                if(result==1)
                {
                    Toast.makeText(TaoMoiThongTin.this, "Thời điểm này đã có việc.!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(databaseHandler.insertData(luu)!=-1)
                {
                    Toast.makeText(TaoMoiThongTin.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(TaoMoiThongTin.this,MainActivity.class);
                    setResult(33);
                    finish();
                   // startActivity(intent);
                }else
                {
                    Toast.makeText(TaoMoiThongTin.this, "Đã có công việc vào thời điểm này", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TaoMoiThongTin.this,MainActivity.class);
                setResult(33);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                setResult(33);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //chuyen theo dinh dang 20-10-2016
    public String changeFormatDay(String str) {
        String ngay = "";
        Date date = new java.util.Date();
        String mang[] = str.split("-");
        if(mang.length==2)
        {
            ngay = mang[0] + "-" + mang[1]+"-"+(date.getYear() + 1900);
        }else {
            ngay=mang[0]+"-"+mang[1]+"-"+mang[2];
        }
        return ngay;
    }

    //chuyen theo dinh dang 2016-10-20
    public String changeFormatDayRepeat(String str) {
        String ngay = "";
        Date date = new java.util.Date();
        String mang[] = str.split("-");
        ngay=mang[2]+"-"+mang[1]+"-"+mang[0];
        return ngay;
    }
    public int kiemTraRangBuoc( ThoiGianBieu thoiGianBieu) {
        String str = "^((1[0-9])|(0[0-9])|(2[0-3])):([0-5][0-9])$";
        String ngaythang = "^(([0-2][1-9])|([1-9])|(3[0-1])):([1-9]|(1[0-2]))$";
        Date date = new java.util.Date();
        String ngayBD =thoiGianBieu.getNgayBD();
        String ngayKT = thoiGianBieu.getNgayKT();
        String rangBuocNgayBD;
        int namBDNhuan=1;
        if (((getNam(thoiGianBieu.getNgayBD()) % 4) == 0)) {
                rangBuocNgayBD = "^((0[1-9]|(0[1-9]|[1-2][0-9])|3[0-1])-(01|03|05|07|08|10|12)-20[0-9][0-9])|(((0[1-9]|([0-2][1-9])|10|20|30)-(02|04|06|09|11)-20[0-9][0-9]))|((0[1-9]|[0-2][1-9]|20)-(02)-20[0-9][0-9])$";
            } else {
                rangBuocNgayBD = "^((0[1-9]|(0[1-9]|[1-2][0-9])|3[0-1])-(01|03|05|07|08|10|12)-20[0-9][0-9])|(((0[1-9]|([0-2][1-9])|10|20|30)-(04|06|09|11)-20[0-9][0-9]))|((0[1-9]|[0-2][1-8]|20|09|19)-(02)-20[0-9][0-9])$";
                namBDNhuan = 0;
            }
        String rangBuocNgayKT;
        int namKTNhuan=1;
        if ((getNam(thoiGianBieu.getNgayKT()) % 4) == 0) {
                rangBuocNgayKT = "^((0[1-9]|(0[1-9]|[1-2][0-9])|3[0-1])-(01|03|05|07|08|10|12)-20[0-9][0-9])|(((0[1-9]|([0-2][1-9])|10|20|30)-(02|04|06|09|11)-20[0-9][0-9]))|((0[1-9]|[0-2][1-9])-(02)-20[0-9][0-9])$";
            } else {
                rangBuocNgayKT = "^((0[1-9]|(0[1-9]|[1-2][0-9])|3[0-1])-(01|03|05|07|08|10|12)-20[0-9][0-9])|(((0[1-9]|([0-2][1-9])|10|20|30)-(04|06|09|11)-20[0-9][0-9]))|((0[1-9]|[0-2][1-8]|20|09|19)-(02)-20[0-9][0-9])$";
                namKTNhuan = 0;
        }

        if (!ngayBD.matches(rangBuocNgayBD) || !ngayKT.matches(rangBuocNgayKT)) {
            Toast.makeText(TaoMoiThongTin.this, "Phải nhập định dạng ngay theo dd-MM-yyyy", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if(namBDNhuan==0&&kiemTraThang2(ngayBD)==2)
        {
            Toast.makeText(TaoMoiThongTin.this, "Năm "+getNam(ngayBD)+" tháng 2 chỉ có 28 ngày.!", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if(namKTNhuan==0&&kiemTraThang2(ngayKT)==2)
        {
            Toast.makeText(TaoMoiThongTin.this, "Năm "+getNam(ngayKT)+" tháng 2 chỉ có 28 ngày.!", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (soSanhSanhNgayThang(ngayBD, ngayKT) == 0) {
            Toast.makeText(TaoMoiThongTin.this, "Thời gian bắt đầu phải nhỏ hơn hoặc bằng thời gian kết thúc", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (soSanhSanhNgayThang(ngayBD, ngayKT) == 0) {
            Toast.makeText(TaoMoiThongTin.this, "Thời gian bắt đầu phải nhỏ hơn hoặc bằng thời gian kết thúc", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (thoiGianBieu.getCongViec().equals("")) {
            Toast.makeText(TaoMoiThongTin.this, "Bạn chưa nhập công việc", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }

    public int soSanhSanhNgayThang(String nt1, String nt2) {
        String mang1[] = nt1.split("-");
        int namBD=Integer.parseInt(mang1[2]);
        int ngayBD = Integer.parseInt(mang1[0]);
        int thangBD = Integer.parseInt(mang1[1]);
        String mang[] = nt2.split("-");
        int ngayKT = Integer.parseInt(mang[0]);
        int thangKT = Integer.parseInt(mang[1]);
        int namKT=Integer.parseInt(mang[2]);

        if(namBD>namKT||(namBD==namKT&&thangBD>thangKT)||(namBD==namKT&&thangBD==thangKT&&ngayBD>ngayKT))
            return 0;
        return 1;
    }
    public int soSanhSanhGio(String nt1, String nt2)
    {
        String mang1[] = nt1.split(":");
        int gioB = Integer.parseInt(mang1[0]);
        int phuB = Integer.parseInt(mang1[1]);
        String mang[] = nt2.split(":");
        int gioK = Integer.parseInt(mang[0]);
        int phutK = Integer.parseInt(mang[1]);
        if (gioB > gioK) {
            return 0;
        }
        if(gioB==gioK &&phuB>phutK)
        {
            return 0;
        }
        return 1;
    }
    public int getNam(String str)
    {
        String mang[]=str.split("-");
        return  Integer.parseInt(mang[2]);
    }
    public int kiemTraThang2(String str)
    {
        String mang[]=str.split("-");
        return  Integer.parseInt(mang[1]);
    }
    public int kiemTraRangBuocGio(ThoiGianBieu thoiGianBieu)
    {
        String str = "^((1[0-9])|(0[0-9])|(2[0-3])):([0-5][0-9])$";
        if (!thoiGianBieu.getGioBD().matches(str) || !thoiGianBieu.getGioKT().matches(str)) {
            Toast.makeText(TaoMoiThongTin.this, "Bạn phải nhâp giờ bắt đầu và giờ kết thúc theo định dạng hh:mm", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (thoiGianBieu.getGioBD().equals(thoiGianBieu.getGioKT())) {
            Toast.makeText(TaoMoiThongTin.this, "Giờ bắt đầu và giờ kết thúc không trùng nhau", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (soSanhSanhGio(thoiGianBieu.getGioBD(), thoiGianBieu.getGioKT()) == 0) {
            Toast.makeText(TaoMoiThongTin.this, "Giờ bắt đầu phải nhỏ hơn giờ kết thúc", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }
    public int kiemTraRangBuocKhiNhapNgay()
    {
        String rangBuocNgayBD = "^((0[1-9]|(0[1-9]|[1-2][0-9])|3[0-1])-(01|03|05|07|08|10|12)-20[0-9][0-9])|(((0[1-9]|([0-2][1-9])|10|20|30)-(02|04|06|09|11)-20[0-9][0-9]))|((0[1-9]|[0-2][1-9]|20)-(02)-20[0-9][0-9])$";
        String ddMM="^(0[1-9]|1[0-9]|2[0-9]|3[0-1])-(0[1-9]|1[0-2])$";
        String ngayBD=edtSTDay.getText().toString().trim();
        String ngayKT=edtEDay.getText().toString().trim();
        if((ngayBD.matches(rangBuocNgayBD)||ngayBD.matches(ddMM))&&(ngayKT.matches(rangBuocNgayBD)||ngayKT.matches(ddMM)))
        {
            return 1;
        }
        return 0;
    }
    public void setDuLieuEditText()
    {
        Intent intent=getIntent();
        edtSTDate.setText(intent.getStringExtra("gioBD"));
        edtEDate.setText(intent.getStringExtra("gioKT"));
        edtSTDay.setText(intent.getStringExtra("ngayBD"));
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
                Intent intent=new Intent(TaoMoiThongTin.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public String changeFormatDate1(String str)
    {
        String mang[]=str.split("-");
        return mang[2]+"-"+mang[1]+"-"+mang[0];
    }
}
