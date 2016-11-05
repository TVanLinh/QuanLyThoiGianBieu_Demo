package com.linhtran.vnua.demothuctap;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import Background.NewsReceiver;
import Background.NewsService;
import Data.ThoiGianBieu;
import DatabaseHandler.DatabaseHandler;
import NgayThang.NgayThang;

public class MainActivity extends AppCompatActivity {
    TextView cv, vn;
    AbsoluteLayout lineThu2, lineThu3, lineThu4, lineThu5, lineThu6, lineThu7, lineThuCN,lauoutXoa;
    DatabaseHandler databaseHandler;
    List<ThoiGianBieu> thoiGianBieus;

    ThoiGianBieu thoiGianBieu;
    Date ngayXoa;

    CheckBox xoaToDay, xoaALl;
    int indexWeek=0;

    Date tuanTruocNgayXoa,tuanSauNgayXoa;

    int i;

    BroadcastReceiver receiver;
    @Override
    protected void onStart() {
        super.onStart();
        Notification.Builder buider=new Notification.Builder(this);
        buider.setContentTitle("Thông báo");
        notification();
      //  Toast.makeText(MainActivity.this, "Trần Văn Linh\n01644952648", Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_menu_next_tuan);
      //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        startService(new Intent(MainActivity.this, NewsService.class));
        databaseHandler=new DatabaseHandler(this);
        receiver=new NewsReceiver();
        lineThu2 = (AbsoluteLayout) findViewById(R.id.lineThu2);
        lineThu3 = (AbsoluteLayout) findViewById(R.id.lineThu3);
        lineThu4 = (AbsoluteLayout) findViewById(R.id.lineThu4);
        lineThu5 = (AbsoluteLayout) findViewById(R.id.lineThu5);
        lineThu6 = (AbsoluteLayout) findViewById(R.id.lineThu6);
        lineThu7 = (AbsoluteLayout) findViewById(R.id.lineThu7);
        lineThuCN = (AbsoluteLayout) findViewById(R.id.lineCN);
        thoiGianBieus=databaseHandler.getThongTin(new Date());
        loadDATA(databaseHandler.getThongTinThu2(thoiGianBieus),databaseHandler.getThongTinThu3(thoiGianBieus),databaseHandler.getThongTinThu4(thoiGianBieus),databaseHandler.getThongTinThu5(thoiGianBieus),databaseHandler.getThongTinThu6(thoiGianBieus),databaseHandler.getThongTinThu7(thoiGianBieus),databaseHandler.getThongTinCN(thoiGianBieus));
        lineThu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvClick();
            }
        });
        lineThu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvClick();
            }
        });
        lineThu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvClick();
            }
        });
        lineThu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvClick();
            }
        });
        lineThu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvClick();
            }
        });
        lineThu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvClick();
            }
        });
        lineThuCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvClick();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        registerReceiver(receiver,new IntentFilter("com.linhtran.vnua.demothucta"));
    }

    @Override
    protected void onResume() {
        super.onResume();
       }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home)
        {
            previousWeek(--indexWeek);

            return true;
        }
        if (id == R.id.taoMoi) {
            Toast.makeText(MainActivity.this, "Tạo mới", Toast.LENGTH_SHORT).show();
            truyenDuLieuTaoMoi(new ThoiGianBieu());

        }
        if(id==R.id.hoanThanh)
        {
            Intent intent=new Intent(MainActivity.this,DanhSachCongViecTheoListVew.class);
            startActivity(intent);
        }
        if(id==R.id.timKiem)
        {
            Intent intent=new Intent(MainActivity.this,TimKiemActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem item=menu.findItem(R.id.nextWeek);
        View view = MenuItemCompat.getActionView(item);
        ImageButton btnNext=(ImageButton)view.findViewById(R.id.ibtnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 previousWeek(++indexWeek);
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }
    public List<ThoiGianBieu> resetDataView(List<ThoiGianBieu> tg)
    {
        List<ThoiGianBieu> result;
        result=tg;
        thoiGianBieu=new ThoiGianBieu();
        thoiGianBieu.setDanhSach(tg);
        thoiGianBieu.sapXepTheoGioBD();
        List<ThoiGianBieu> ochuaCoViec=taoOchuaCoViec(thoiGianBieu.sapXepTheoGioBD());

        for(int i=0;i<ochuaCoViec.size();i++)
        {
            result.add(ochuaCoViec.get(i));
        }
         thoiGianBieu.setDanhSach(result);
        return thoiGianBieu.sapXepTheoGioBD();
    }
    public  void loadDataThu2(List<ThoiGianBieu> listJob)
    {
        thoiGianBieus=listJob;//databaseHandler.getThongTinThu2(databaseHandler.getThongTin());
        if (thoiGianBieus.size()==0)
        {
            return;
        }
        thoiGianBieus=resetDataView(thoiGianBieus);
        lauoutXoa=new AbsoluteLayout(this);
        lauoutXoa=lineThu2;
        TextView textView;
        ActionBar.LayoutParams layoutParams;
        for( i=0;i<thoiGianBieus.size();i++)
        {
            layoutParams=new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,thoiGianBieus.get(i).layChieuCao());
            textView=new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setBackground(getResources().getDrawable(R.drawable.boder));
            textView.setTranslationY(thoiGianBieus.get(i).layToaDoY());
            setAligmentTextView(textView,thoiGianBieus.get(i));
            if(!thoiGianBieus.get(i).getCongViec().equals(""))
            {
                //  textView.setBackgroundColor(setMauSac());
                textView.setBackgroundResource(setMauSac());
                String str=thoiGianBieus.get(i).getGioBD()+"-"+thoiGianBieus.get(i).getGioKT()+"\n\n"+thoiGianBieus.get(i).getCongViec();
                textView.setText(str);
               // textViewLongClick(textView,thoiGianBieus.get(i),2);
                texViewSetLongClick3(textView,2,thoiGianBieus.get(i));
            }
            //setMauSac());
            final List<ThoiGianBieu> finalThoiGianBieus = thoiGianBieus;
            final int finalI = i;
            textViewSetClick2(textView,finalThoiGianBieus.get(finalI),2,indexWeek);
            lineThu2.addView(textView);
        }
    }
    public  void loadDataThu3(List<ThoiGianBieu> listJob)
    {
        List<ThoiGianBieu> thoiGianBieus;
        thoiGianBieus=listJob;
        if (thoiGianBieus.size()==0)
        {
            return;
        }
        thoiGianBieus=resetDataView(thoiGianBieus);
        lauoutXoa=new AbsoluteLayout(this);
        lauoutXoa=lineThu3;
        TextView textView;
        ActionBar.LayoutParams layoutParams;
        for( i=0;i<thoiGianBieus.size();i++)
        {
            layoutParams=new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,thoiGianBieus.get(i).layChieuCao());
            textView=new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setBackground(getResources().getDrawable(R.drawable.boder));
            textView.setText(thoiGianBieus.get(i).getCongViec());
            textView.setTranslationY(thoiGianBieus.get(i).layToaDoY());
            setAligmentTextView(textView,thoiGianBieus.get(i));
            if(!thoiGianBieus.get(i).getCongViec().equals(""))
            {
                //  textView.setBackgroundColor(setMauSac());
                textView.setBackgroundResource(setMauSac2());
                String str=thoiGianBieus.get(i).getGioBD()+"-"+thoiGianBieus.get(i).getGioKT()+"\n\n"+thoiGianBieus.get(i).getCongViec();
                textView.setText(str);
               // textViewLongClick(textView,thoiGianBieus.get(i),3);
                texViewSetLongClick3(textView,3,thoiGianBieus.get(i));
            }
            //setMauSac());
            final List<ThoiGianBieu> finalThoiGianBieus = thoiGianBieus;
            final int finalI = i;
            textViewSetClick2(textView,finalThoiGianBieus.get(finalI),3,indexWeek);
            lineThu3.addView(textView);
        }
    }
    public  void loadDataThu4(List<ThoiGianBieu> listJob)
    {
        List<ThoiGianBieu> thoiGianBieus;
        thoiGianBieus=listJob;
        if (thoiGianBieus.size()==0)
        {
            return;
        }
        thoiGianBieus=resetDataView(thoiGianBieus);
        lauoutXoa=new AbsoluteLayout(this);
        lauoutXoa=lineThu4;

        TextView textView;
        ActionBar.LayoutParams layoutParams;
        for(int i=0;i<thoiGianBieus.size();i++)
        {
            layoutParams=new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,thoiGianBieus.get(i).layChieuCao());
            textView=new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setBackground(getResources().getDrawable(R.drawable.boder));
            textView.setText(thoiGianBieus.get(i).getCongViec());
            textView.setTranslationY(thoiGianBieus.get(i).layToaDoY());
            setAligmentTextView(textView,thoiGianBieus.get(i));
            if(!thoiGianBieus.get(i).getCongViec().trim().equals(""))
            {
                // textView.setBackgroundColor(setMauSac());
                textView.setBackgroundResource(setMauSac());
                String str=thoiGianBieus.get(i).getGioBD()+"-"+thoiGianBieus.get(i).getGioKT()+"\n\n"+thoiGianBieus.get(i).getCongViec();
                textView.setText(str);
              //  textViewLongClick(textView,thoiGianBieus.get(i),4);
                texViewSetLongClick3(textView,4,thoiGianBieus.get(i));
            }
            textViewSetClick2(textView,thoiGianBieus.get(i),4,indexWeek);
            lineThu4.addView(textView);
        }
    }
    public  void loadDataThu5(List<ThoiGianBieu> listJob)
    {
        List<ThoiGianBieu> thoiGianBieus;
        thoiGianBieus=listJob;
        if (thoiGianBieus.size()==0)
        {
            return;
        }
        thoiGianBieus=resetDataView(thoiGianBieus);
        lauoutXoa=new AbsoluteLayout(this);
        lauoutXoa=lineThu5;

        TextView textView;
        ActionBar.LayoutParams layoutParams;
        for(int i=0;i<thoiGianBieus.size();i++)
        {
            layoutParams=new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,thoiGianBieus.get(i).layChieuCao());
            textView=new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setBackground(getResources().getDrawable(R.drawable.boder));
            textView.setText(thoiGianBieus.get(i).getCongViec());
            textView.setTranslationY(thoiGianBieus.get(i).layToaDoY());
            setAligmentTextView(textView,thoiGianBieus.get(i));
            if(!thoiGianBieus.get(i).getCongViec().trim().equals(""))
            {
                //   textView.setBackgroundColor(setMauSac());
                textView.setBackgroundResource(setMauSac2());
                String str=thoiGianBieus.get(i).getGioBD()+"-"+thoiGianBieus.get(i).getGioKT()+"\n\n"+thoiGianBieus.get(i).getCongViec();
                textView.setText(str);
             //   textViewLongClick(textView,thoiGianBieus.get(i),5);
                texViewSetLongClick3(textView,5,thoiGianBieus.get(i));
            }
            final int finalI = i;
            textViewSetClick2(textView,thoiGianBieus.get(finalI),5,indexWeek);
            lineThu5.addView(textView);
        }
    }
    public  void loadDataThu6(List<ThoiGianBieu> listJob)
    {
        List<ThoiGianBieu> thoiGianBieus;
        thoiGianBieus=listJob;
        if (thoiGianBieus.size()==0)
        {
            return;
        }
        thoiGianBieus=resetDataView(thoiGianBieus);
        lauoutXoa=new AbsoluteLayout(this);
        lauoutXoa=lineThu6;

        TextView textView;
        ActionBar.LayoutParams layoutParams;
        for(int i=0;i<thoiGianBieus.size();i++)
        {
            layoutParams=new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,thoiGianBieus.get(i).layChieuCao());
            textView=new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setBackground(getResources().getDrawable(R.drawable.boder));
            textView.setText(thoiGianBieus.get(i).getCongViec());
            textView.setTranslationY(thoiGianBieus.get(i).layToaDoY());
            setAligmentTextView(textView,thoiGianBieus.get(i));
            if(!thoiGianBieus.get(i).getCongViec().trim().equals(""))
            {
                //   textView.setBackgroundColor(setMauSac());
                textView.setBackgroundResource(setMauSac());
                String str=thoiGianBieus.get(i).getGioBD()+"-"+thoiGianBieus.get(i).getGioKT()+"\n\n"+thoiGianBieus.get(i).getCongViec();
                textView.setText(str);
               // textViewLongClick(textView,thoiGianBieus.get(i),6);
                texViewSetLongClick3(textView,6,thoiGianBieus.get(i));
            }
            final int finalI = i;
            textViewSetClick2(textView,thoiGianBieus.get(finalI),6,indexWeek);
            lineThu6.addView(textView);
        }
    }
    public  void loadDataThu7(List<ThoiGianBieu> listJob)
    {
        List<ThoiGianBieu> thoiGianBieus;
        thoiGianBieus=listJob;
        if (thoiGianBieus.size()==0)
        {
            return;
        }
        thoiGianBieus=resetDataView(thoiGianBieus);
        lauoutXoa=new AbsoluteLayout(this);
        lauoutXoa=lineThu7;

        TextView textView;
        ActionBar.LayoutParams layoutParams;
        for(int i=0;i<thoiGianBieus.size();i++)
        {
            layoutParams=new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,thoiGianBieus.get(i).layChieuCao());
            textView=new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setBackground(getResources().getDrawable(R.drawable.boder));
            textView.setText(thoiGianBieus.get(i).getCongViec());
            textView.setTranslationY(thoiGianBieus.get(i).layToaDoY());
            setAligmentTextView(textView,thoiGianBieus.get(i));
            if(!thoiGianBieus.get(i).getCongViec().trim().equals(""))
            {
                //  textView.setBackgroundColor(setMauSac());
                textView.setBackgroundResource(setMauSac2());
                String str=thoiGianBieus.get(i).getGioBD()+"-"+thoiGianBieus.get(i).getGioKT()+"\n\n"+thoiGianBieus.get(i).getCongViec();
                textView.setText(str);
              //  textViewLongClick(textView,thoiGianBieus.get(i),7);
                texViewSetLongClick3(textView,7,thoiGianBieus.get(i));
            }
            final int finalI = i;
            textViewSetClick2(textView,thoiGianBieus.get(finalI),7,indexWeek);
            lineThu7.addView(textView);
        }
    }
    public  void loadDataThuCN(List<ThoiGianBieu> listJob)
    {
        List<ThoiGianBieu> thoiGianBieus;
        thoiGianBieus=listJob;
        if (thoiGianBieus.size()==0)
        {
            return;
        }
        thoiGianBieus=resetDataView(thoiGianBieus);
        lauoutXoa=new AbsoluteLayout(this);
        lauoutXoa=lineThuCN;

        TextView textView;
        ActionBar.LayoutParams layoutParams;

        for( int i=0;i<thoiGianBieus.size();i++)
        {
            layoutParams=new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,thoiGianBieus.get(i).layChieuCao());
            textView=new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setBackground(getResources().getDrawable(R.drawable.boder));
            textView.setText(thoiGianBieus.get(i).getCongViec());
            textView.setTranslationY(thoiGianBieus.get(i).layToaDoY());
            setAligmentTextView(textView,thoiGianBieus.get(i));
            if(!thoiGianBieus.get(i).getCongViec().trim().equals(""))
            {
                // textView.setBackgroundColor(setMauSac());
                textView.setBackgroundResource(setMauSac3());
                String str=thoiGianBieus.get(i).getGioBD()+"-"+thoiGianBieus.get(i).getGioKT()+"\n\n"+thoiGianBieus.get(i).getCongViec();
                textView.setText(str);
              //  textViewLongClick(textView,thoiGianBieus.get(i),8);
                texViewSetLongClick3(textView,8,thoiGianBieus.get(i));
            }
            final int finalI = i;
            lineThuCN.addView(textView);
            textViewSetClick2(textView,thoiGianBieus.get(finalI),8,indexWeek);
        }
    }
    public void loadDATA(List<ThoiGianBieu> hai,List<ThoiGianBieu> ba,List<ThoiGianBieu> tu,List<ThoiGianBieu> nam,List<ThoiGianBieu> sau,List<ThoiGianBieu> bay,List<ThoiGianBieu> cn)
    {
        loadDataThu2(hai);
        loadDataThu3(ba);
        loadDataThu4(tu);
        loadDataThu5(nam);
        loadDataThu6(sau);
        loadDataThu7(bay);
        loadDataThuCN(cn);
    }
    public int  setMauSac()
    {
        Random r=new Random();
        int a=r.nextInt(10);

        switch (a)
        {
            case 0:{
                a=R.color.mau1;
                break;
            }
            case 1:{
                a=R.color.colorAccent;
                break;
            }
            case 2:{
                a=R.color.mau2;
                break;
            }
            case 3:{
                a=R.color.mau3;

                break;
            }
            case 4:{
                a=R.color.mau4;

                break;
            }
            case 5:{
                a=R.color.mau5;
                break;
            }
            case 6:{
                a=R.color.mau1a;
                break;
            }
            case 7:{
                a=R.color.mau2a;
                break;
            }
            case 8:{
                a=R.color.mau3a;

                break;
            }
            case 9:{
                a=R.color.mau4a;

                break;
            }
            case 10:{
                a=R.color.mau5a;
                break;
            }
        }
        return  a;
    }
    public int  setMauSac2()
    {
        Random r=new Random();
        int a=r.nextInt(10);

        switch (a)
        {
            case 0:{
                a=R.color.mau6;
                break;
            }
            case 1:{
                a=R.color.mau7;
                break;
            }
            case 2:{
                a=R.color.mau8;
                break;
            }
            case 3:{
                a=R.color.mau10;

                break;
            }
            case 4:{
                a=R.color.mau11;

                break;
            }
            case 5:{
                a=R.color.mau12;
                break;
            }
            case 6:{
                a=R.color.mau7a;
                break;
            }
            case 7:{
                a=R.color.mau8a;
                break;
            }
            case 8:{
                a=R.color.mau10a;

                break;
            }
            case 9:{
                a=R.color.mau11a;

                break;
            }
            case 10:{
                a=R.color.mau12a;
                break;
            }
        }
        return  a;
    }
    public int  setMauSac3()
    {
        Random r=new Random();
        int a=r.nextInt(10);

        switch (a)
        {
            case 0:{
                a=R.color.mau13;
                break;
            }
            case 1:{
                a=R.color.mau14;
                break;
            }
            case 2:{
                a=R.color.mau15;
                break;
            }
            case 3:{
                a=R.color.mau16;

                break;
            }
            case 4:{
                a=R.color.mau17;

                break;
            }
            case 5:{
                a=R.color.mau18;
                break;
            }
            case 7:{
                a=R.color.mau14a;
                break;
            }
            case 8:{
                a=R.color.mau15a;
                break;
            }
            case 9:{
                a=R.color.mau16a;

                break;
            }
            case 10:{
                a=R.color.mau17a;

                break;
            }
            case 6:{
                a=R.color.mau13a;
                break;
            }
        }
        return  a;
    }
    public void tvClick()
    {
        ThoiGianBieu thoiGianBieu=new ThoiGianBieu();
        truyenDuLieuTaoMoi(thoiGianBieu);
    }
    public   List<ThoiGianBieu>  taoOchuaCoViec(List<ThoiGianBieu> thoGianBieus)
    {
        int vitri=0,chieuCao=0;
        List<ThoiGianBieu> result=new ArrayList<ThoiGianBieu>();

        ThoiGianBieu thoiGianBieu;
        for(int i=0;i<thoGianBieus.size();i++)
        {
            if(i==0) {
                thoiGianBieu = new ThoiGianBieu();
                thoiGianBieu.setGioBD("00:00");
                thoiGianBieu.setGioKT(thoGianBieus.get(i).getGioBD());
                thoiGianBieu.setKey();
                result.add(thoiGianBieu);
            }
            if(i<thoGianBieus.size()-1)
            {
                if ((vitri <= thoGianBieus.get(i + 1).layToaDoY() && chieuCao <= (thoGianBieus.get(i + 1).layToaDoY() - thoGianBieus.get(i).layToaDoDiemCuoi())))
                {
                    thoiGianBieu = new ThoiGianBieu();
                    vitri = thoGianBieus.get(i).layToaDoDiemCuoi();
                    chieuCao = thoGianBieus.get(i + 1).layToaDoY() - thoGianBieus.get(i).layToaDoDiemCuoi();
                    thoiGianBieu.setGioBD(thoGianBieus.get(i).getGioKT());
                    thoiGianBieu.setGioKT(thoGianBieus.get(i + 1).getGioBD());
                    thoiGianBieu.setKey();
                    result.add(thoiGianBieu);
                }
            }
            if(i==thoGianBieus.size()-1)
            {
                thoiGianBieu = new ThoiGianBieu();
                thoiGianBieu.setGioBD(thoGianBieus.get(thoGianBieus.size() - 1).getGioKT());
                thoiGianBieu.setGioKT("23:59");
                result.add(thoiGianBieu);
            }

        }
        return result;
    }
    public void truyenDuLieuCapNhat(ThoiGianBieu tg)
    {
        Intent intent=new Intent(MainActivity.this,CapNhatThongTin.class);
        intent.putExtra("gioBD",tg.getGioBD());
        intent.putExtra("gioKT",tg.getGioKT());
        intent.putExtra("ngayBD",tg.getNgayBD());
        intent.putExtra("ngayKT",tg.getNgayKT());
        intent.putExtra("congViec",tg.getCongViec());
        intent.putExtra("luuY",tg.getLuuY());
        if(tg.getThu(tg.getNgayBD())==1)
        {
            intent.putExtra("thu","Chủ Nhật");
        }
        else
        {
            intent.putExtra("thu","Thứ "+tg.getThu(tg.getNgayBD()));
        }
        startActivityForResult(intent,99);
    }
    public void truyenDuLieuTaoMoi(ThoiGianBieu tg)
    {

        Intent intent=new Intent(MainActivity.this,TaoMoiThongTin.class);
        intent.putExtra("gioBD",tg.getGioBD());
        intent.putExtra("gioKT",tg.getGioKT());
        intent.putExtra("ngayBD",tg.getNgayBD());
        intent.putExtra("ngayKT",tg.getNgayKT());
        intent.putExtra("congViec",tg.getCongViec());
        intent.putExtra("luuY",tg.getLuuY());
        startActivityForResult(intent,99);
    }
    public void truyenDuLieuTaoMoi2(ThoiGianBieu tg,int thu,int tuan)
    {
        Date date=new Date();
        date.setDate(date.getDate()+tuan*7);
        Date startWeek=NgayThang.getDateStartOfWeek(date);
        Date ngayClick=startWeek;
        ngayClick.setDate(startWeek.getDate()+(thu-2));
        Intent intent=new Intent(MainActivity.this,TaoMoiThongTin.class);
        intent.putExtra("gioBD",tg.getGioBD());
        intent.putExtra("gioKT",tg.getGioKT());
        intent.putExtra("ngayBD",NgayThang.toString(ngayClick));
        intent.putExtra("ngayKT",tg.getNgayKT());
        intent.putExtra("congViec",tg.getCongViec());
        intent.putExtra("luuY",tg.getLuuY());
        startActivityForResult(intent,99);
    }
    public void textViewSetClick2(TextView textView, final ThoiGianBieu thoiGianBieu, final int thu, final int tuan)
    {
        if(thoiGianBieu.getCongViec().trim().equals(""))
        {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    truyenDuLieuTaoMoi2(thoiGianBieu,thu,tuan);
                }
            });

        }else {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    truyenDuLieuCapNhat(thoiGianBieu);
                }
            });
        }

    }
    public void setAligmentTextView(TextView textView, ThoiGianBieu thoiGianBieu1)
    {
        if(!thoiGianBieu1.getGioBD().trim().equals("")&&!thoiGianBieu1.getGioKT().trim().equals(""))
        {
                textView.setGravity(Gravity.CENTER);

        }
    }

    public void notification()
    {
        Notification.Builder builder;
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            builder=new Notification.Builder(this);
            builder.setSmallIcon(R.drawable.icon_notification);
            notificationManager.notify(1,builder.build());
            builder.setAutoCancel(true);
            builder.setStyle(new Notification.BigTextStyle().bigText("Bạn vừa bật ứng dụng quản lý thời gian biểu \n Trần Văn Linh  01644952648"));
            Intent  inten =new Intent(this.getApplicationContext(),MainActivity.class);
            PendingIntent pintent=PendingIntent.getActivities(this.getApplicationContext(),1, new Intent[]{inten},0);
            builder.setContentIntent(pintent);
            notificationManager.notify(1,builder.build());
        }
    }
    public void removeAllView()
    {
        lineThu2.removeAllViews();
        lineThu2.requestLayout();

        lineThu3.removeAllViews();
        lineThu3.requestLayout();

        lineThu4.removeAllViews();
        lineThu4.requestLayout();

        lineThu5.removeAllViews();
        lineThu5.requestLayout();

        lineThu6.removeAllViews();
        lineThu6.requestLayout();

        lineThu7.removeAllViews();
        lineThu7.requestLayout();

        lineThuCN.removeAllViews();
        lineThuCN.requestLayout();
    }
    public void previousWeek(int previousWeek)
    {
        Date date=new Date();
        int ngay=date.getDate()+previousWeek*7;
        date.setDate(ngay);
        List<ThoiGianBieu> listJob=databaseHandler.getThongTin(date);
        List<ThoiGianBieu> thu2=databaseHandler.getThongTinThu2(listJob);
        List<ThoiGianBieu> thu3=databaseHandler.getThongTinThu3(listJob);
        List<ThoiGianBieu> thu4=databaseHandler.getThongTinThu4(listJob);
        List<ThoiGianBieu> thu5=databaseHandler.getThongTinThu5(listJob);
        List<ThoiGianBieu> thu6=databaseHandler.getThongTinThu6(listJob);
        List<ThoiGianBieu> thu7=databaseHandler.getThongTinThu7(listJob);
        List<ThoiGianBieu> cn=databaseHandler.getThongTinCN(listJob);
        removeAllView();
        loadDATA(thu2,thu3,thu4,thu5,thu6,thu7,cn);
    }

    public void   texViewSetLongClick3(final TextView textView, final int thu, final ThoiGianBieu jobXoa)
    {
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

           // Date date =new Date();
            Date tuanXoa=new Date();
            tuanXoa.setDate(tuanXoa.getDate()+indexWeek*7);

            //Ngay bat dau va ket thuc cua tuan can xoa
            Date ngayBDTuanXoa=NgayThang.getDateStartOfWeek(tuanXoa);
            Date ngayKTTuanXoa=NgayThang.getDateEndOfWeek(tuanXoa);

            //Ngay can xoa trong tuan
             ngayXoa = new Date();
            ngayXoa.setYear(ngayBDTuanXoa.getYear());
            ngayXoa.setMonth(ngayBDTuanXoa.getMonth());
            ngayXoa.setDate(ngayBDTuanXoa.getDate()+(thu-2));

            tuanTruocNgayXoa=new Date();
            tuanSauNgayXoa=new Date();
            //tuan truoc cua ngay xoa
            tuanTruocNgayXoa.setYear(ngayXoa.getYear());
            tuanTruocNgayXoa.setMonth(ngayXoa.getMonth());
            tuanTruocNgayXoa.setDate(ngayXoa.getDate()-7);
            //tuan sau cua ngay xoa
            tuanSauNgayXoa.setYear(ngayXoa.getYear());
            tuanSauNgayXoa.setMonth(ngayXoa.getMonth());
            tuanSauNgayXoa.setDate(ngayXoa.getDate()+7);
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
            //NgayBat dau va ngay ket thuc cua jobXoa
            Date ngayBDJobXoa=null,ngayKTJobXoa=null;
            try {
                ngayBDJobXoa=simpleDateFormat.parse(jobXoa.getNgayBD());
                ngayKTJobXoa=simpleDateFormat.parse(jobXoa.getNgayKT());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //TH1 Ngayxoa,ngay bat dau va ngay ket thuc trung nhau
            if(NgayThang.soSanhNgayThang(ngayBDJobXoa,ngayXoa)==0&& NgayThang.soSanhNgayThang(ngayKTJobXoa,ngayXoa)==0||NgayThang.soSanhNgayThang(ngayKTJobXoa,tuanSauNgayXoa)<0)
            {
                final AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setTitle("Xóa công việc ");
                dialog.setMessage("Bạn có muốn xóa công việc này không?");
                dialog.setNegativeButton("Không",null);
                dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lauoutXoa.removeView(textView);
                        if( databaseHandler.deleteData(jobXoa.getKey())!=-1)
                        {
                            Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                        thoiGianBieus=databaseHandler.getThongTin(ngayXoa);
                        loadDATA(databaseHandler.getThongTinThu2(thoiGianBieus),databaseHandler.getThongTinThu3(thoiGianBieus),databaseHandler.getThongTinThu4(thoiGianBieus),databaseHandler.getThongTinThu5(thoiGianBieus),databaseHandler.getThongTinThu6(thoiGianBieus),databaseHandler.getThongTinThu7(thoiGianBieus),databaseHandler.getThongTinCN(thoiGianBieus));
                        finish();
                    }
                });
                dialog.show();
                return false;
            }
            if(NgayThang.soSanhNgayThang(ngayXoa,ngayBDJobXoa)==0)
            {
                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.layout_xoa);
                dialog.setTitle("Xóa công việc lúc  "+jobXoa.getGioBD());
                dialog.setCancelable(true);

                Button btnXoa, btnThoat;
                xoaToDay = (CheckBox) dialog.findViewById(R.id.xoaToDay);
                xoaALl = (CheckBox) dialog.findViewById(R.id.xoaALl);
                btnXoa = (Button) dialog.findViewById(R.id.btnXoa);
                btnThoat = (Button) dialog.findViewById(R.id.btnThoat);

                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ThoiGianBieu job=new ThoiGianBieu(jobXoa);
                        job.setNgayBD(NgayThang.toString(tuanSauNgayXoa));
                        job.setKey();
                        if(xoaALl.isChecked()&&xoaToDay.isChecked()||xoaALl.isChecked())
                        {
                            lauoutXoa.removeAllViews();
                            if( databaseHandler.deleteData(jobXoa.getKey())!=-1)
                            {
                               Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                            }
                            previousWeek(indexWeek);
                        }
                        if (xoaToDay.isChecked())
                        {
                            lauoutXoa.removeAllViews();
                            databaseHandler.insertData(job);
                            if(databaseHandler.deleteData(jobXoa.getKey())!=-1)
                            {
                                Toast.makeText(MainActivity.this, "Xóa thàng công", Toast.LENGTH_SHORT).show();
                                previousWeek(indexWeek);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                            }

                       }
                      //  thoiGianBieus=databaseHandler.getThongTin(ngayXoa);
                      //  loadDATA(databaseHandler.getThongTinThu2(thoiGianBieus),databaseHandler.getThongTinThu3(thoiGianBieus),databaseHandler.getThongTinThu4(thoiGianBieus),databaseHandler.getThongTinThu5(thoiGianBieus),databaseHandler.getThongTinThu6(thoiGianBieus),databaseHandler.getThongTinThu7(thoiGianBieus),databaseHandler.getThongTinCN(thoiGianBieus));
                       // previousWeek(indexWeek);
                        dialog.dismiss();
                    }

                });
                dialog.show();
                thoatClick(btnThoat,dialog);
                return false;


            }
            if(NgayThang.soSanhNgayThang(ngayKTJobXoa,ngayXoa)==0)
            {

                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.layout_xoa);
                dialog.setTitle("Xóa công việc lúc  "+jobXoa.getGioBD());
                dialog.setCancelable(true);

                Button btnXoa, btnThoat;
                xoaToDay = (CheckBox) dialog.findViewById(R.id.xoaToDay);
                xoaALl = (CheckBox) dialog.findViewById(R.id.xoaALl);
                btnXoa = (Button) dialog.findViewById(R.id.btnXoa);
                btnThoat = (Button) dialog.findViewById(R.id.btnThoat);

                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ThoiGianBieu job=new ThoiGianBieu(jobXoa);
                        job.setNgayKT(NgayThang.toString(tuanTruocNgayXoa));
                        if(xoaALl.isChecked()&&xoaToDay.isChecked()||xoaALl.isChecked())
                        {
                            lauoutXoa.removeView(textView);
                            if( databaseHandler.deleteData(jobXoa.getKey())!=-1)
                            {
                                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                            previousWeek(indexWeek);
                            return;
                        }
                        if (xoaToDay.isChecked())
                        {
                            lauoutXoa.removeView(textView);
                            if(databaseHandler.updateData(job)!=-1)
                            {
                                Toast.makeText(MainActivity.this, "Xóa thàng công", Toast.LENGTH_SHORT).show();
                                previousWeek(indexWeek);
                                return;
                             }
                            else {
                                Toast.makeText(MainActivity.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
                thoatClick(btnThoat,dialog);
                return false;
             //   return ;
            }
            if(NgayThang.soSanhNgayThang(ngayKTJobXoa,tuanSauNgayXoa)>=0&& NgayThang.soSanhNgayThang(ngayBDJobXoa,tuanTruocNgayXoa)<=0)
            {

                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.layout_xoa);
                dialog.setTitle("Xóa công việc lúc  "+jobXoa.getGioBD());
                dialog.setCancelable(true);

                Button btnXoa, btnThoat;
                xoaToDay = (CheckBox) dialog.findViewById(R.id.xoaToDay);
                xoaALl = (CheckBox) dialog.findViewById(R.id.xoaALl);
                btnXoa = (Button) dialog.findViewById(R.id.btnXoa);
                btnThoat = (Button) dialog.findViewById(R.id.btnThoat);

                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ThoiGianBieu job1=new ThoiGianBieu(jobXoa);
                        job1.setNgayKT(NgayThang.toString(tuanTruocNgayXoa));

                        ThoiGianBieu job2=new ThoiGianBieu(jobXoa);
                        job2.setNgayBD(NgayThang.toString(tuanSauNgayXoa));
                        job2.setKey();

                        if(xoaALl.isChecked()&&xoaToDay.isChecked()||xoaALl.isChecked())
                        {
                            lauoutXoa.removeView(textView);

                            if( databaseHandler.deleteData(jobXoa.getKey())!=-1)
                            {
                                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                previousWeek(indexWeek);
                                return;
                            }
                        }

                        if (xoaToDay.isChecked())
                        {
                            lauoutXoa.removeView(view);
                            if(databaseHandler.updateData(job1)!=-1&&databaseHandler.insertData(job2)>0)
                            {
                                Toast.makeText(MainActivity.this, "Xóa thàng công", Toast.LENGTH_SHORT).show();
                                previousWeek(indexWeek);
                                return;
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
                thoatClick(btnThoat,dialog);
                return false;
                }

             return false;
            }
        });
    }


    public void thoatClick(Button btn, final Dialog dig)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           dig.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode==33))
        {
            previousWeek(indexWeek);
        }
    }
}
