package DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Data.ThoiGianBieu;
import NgayThang.NgayThang;

/**
 * Created by asus on 09/09/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper  {
    public static final String  DATABASE_NAME="database";
    public static final int  DATABASE_VERSION=1;
    public static final String TABLE_NAME="thoigianbieu";
    public static final String  MA="khoa";
    public static final String  GIOBD="giobatdau";
    public static final String  GIOKT="gioketthuc";
    public static final String  NGAYBD="ngaybatdau";
    public static final String  NGAYKT="ngayketthuc";
    public static final String  CONGVIEC="congviec";
    public static final String  LUY="luuy";
    Context context;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table " +TABLE_NAME+" ( "+
                    MA+ " text primary key ,"+
                    GIOBD+ " text,"+
                    GIOKT+ " text,"+
                    NGAYBD+ " text,"+
                    NGAYKT+ " text,"+
                    CONGVIEC+ " text,"+
                    LUY+ " text"+
                " )";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public int  insertData(ThoiGianBieu thoiGianBieu) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MA, thoiGianBieu.getKey());
        values.put(GIOBD, thoiGianBieu.getGioBD());
        values.put(GIOKT, thoiGianBieu.getGioKT());
        values.put(NGAYBD, thoiGianBieu.getNgayBD());
        values.put(NGAYKT, thoiGianBieu.getNgayKT());
        values.put(CONGVIEC, thoiGianBieu.getCongViec());
        values.put(LUY, thoiGianBieu.getLuuY());
        int i = (int) sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return i;
    }
     public List<ThoiGianBieu> getThongTinThu2(List<ThoiGianBieu> listJob)
     {
         List<ThoiGianBieu> bieuList=new ArrayList<ThoiGianBieu>();
         for(int i=0;i<listJob.size();i++)
             if(NgayThang.getThu(listJob.get(i).getNgayBD())==2)
                 bieuList.add(listJob.get(i));
         return bieuList;
     }
    public List<ThoiGianBieu> getThongTinThu3(List<ThoiGianBieu> listJob)
    {
        List<ThoiGianBieu> bieuList=new ArrayList<ThoiGianBieu>();
        for(int i=0;i<listJob.size();i++)
            if(NgayThang.getThu(listJob.get(i).getNgayBD())==3)
                bieuList.add(listJob.get(i));
        return bieuList;
    }
    public List<ThoiGianBieu> getThongTinThu4(List<ThoiGianBieu> listJob)
    {
        List<ThoiGianBieu> bieuList=new ArrayList<ThoiGianBieu>();
        for(int i=0;i<listJob.size();i++)
            if(NgayThang.getThu(listJob.get(i).getNgayBD())==4)
                bieuList.add(listJob.get(i));
        return bieuList;
    }
    public List<ThoiGianBieu> getThongTinThu5(List<ThoiGianBieu> listJob)
    {
        List<ThoiGianBieu> bieuList=new ArrayList<ThoiGianBieu>();
        for(int i=0;i<listJob.size();i++)
            if(NgayThang.getThu(listJob.get(i).getNgayBD())==5)
                bieuList.add(listJob.get(i));
        return bieuList;
    }
    public List<ThoiGianBieu> getThongTinThu6(List<ThoiGianBieu> listJob)
    {
        List<ThoiGianBieu> bieuList=new ArrayList<ThoiGianBieu>();
        for(int i=0;i<listJob.size();i++)
            if(NgayThang.getThu(listJob.get(i).getNgayBD())==6)
                bieuList.add(listJob.get(i));
        return bieuList;
    }
    public List<ThoiGianBieu> getThongTinThu7(List<ThoiGianBieu> listJob)
    {
        List<ThoiGianBieu> bieuList=new ArrayList<ThoiGianBieu>();
        for(int i=0;i<listJob.size();i++)
            if(NgayThang.getThu(listJob.get(i).getNgayBD())==7)
                bieuList.add(listJob.get(i));
        return bieuList;
    }
    public List<ThoiGianBieu> getThongTinCN(List<ThoiGianBieu> listJob)
    {
        List<ThoiGianBieu> bieuList=new ArrayList<ThoiGianBieu>();
        for(int i=0;i<listJob.size();i++)
            if(NgayThang.getThu(listJob.get(i).getNgayBD())==1)
                bieuList.add(listJob.get(i));
        return bieuList;
    }
    public List<ThoiGianBieu> getThongTin()
    {
        List<ThoiGianBieu> bieuList=new ArrayList<ThoiGianBieu>();
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Date date=new Date();
        String string= NgayThang.toString(date);
        String sql="select * from "+TABLE_NAME +"  where   date("+string+") <= date("+NGAYKT+")";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToFirst();
        ThoiGianBieu thoiGianBieu;
        while (!cursor.isAfterLast())
        {
            thoiGianBieu=new ThoiGianBieu();
            thoiGianBieu.setGioBD(cursor.getString(cursor.getColumnIndex(GIOBD)));
            thoiGianBieu.setGioKT(cursor.getString(cursor.getColumnIndex(GIOKT)));
            thoiGianBieu.setNgayBD(cursor.getString(cursor.getColumnIndex(NGAYBD)));
            thoiGianBieu.setNgayKT(cursor.getString(cursor.getColumnIndex(NGAYKT)));
            thoiGianBieu.setCongViec(cursor.getString(cursor.getColumnIndex(CONGVIEC)));
            thoiGianBieu.setLuuY(cursor.getString(cursor.getColumnIndex(LUY)));
            thoiGianBieu.setKey();
            bieuList.add(thoiGianBieu);
            cursor.moveToNext();
        }
        sqLiteDatabase.close();
        return bieuList;
    }
    public  List<ThoiGianBieu>getAllData()
    {
        List<ThoiGianBieu> bieuList=new ArrayList<ThoiGianBieu>();
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String sql="select * from "+TABLE_NAME;
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToFirst();
        ThoiGianBieu thoiGianBieu;
        while (!cursor.isAfterLast())
        {
            thoiGianBieu=new ThoiGianBieu();
            thoiGianBieu.setGioBD(cursor.getString(cursor.getColumnIndex(GIOBD)));
            thoiGianBieu.setGioKT(cursor.getString(cursor.getColumnIndex(GIOKT)));
            thoiGianBieu.setNgayBD(cursor.getString(cursor.getColumnIndex(NGAYBD)));
            thoiGianBieu.setNgayKT(cursor.getString(cursor.getColumnIndex(NGAYKT)));
            thoiGianBieu.setCongViec(cursor.getString(cursor.getColumnIndex(CONGVIEC)));
            thoiGianBieu.setLuuY(cursor.getString(cursor.getColumnIndex(LUY)));
            thoiGianBieu.setKey();
            bieuList.add(thoiGianBieu);
            cursor.moveToNext();
        }
        sqLiteDatabase.close();
        return bieuList;
    }
    public  int updateData(ThoiGianBieu thoiGianBieu)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(MA, thoiGianBieu.getKey());
        values.put(GIOBD, thoiGianBieu.getGioBD());
        values.put(GIOKT, thoiGianBieu.getGioKT());
        values.put(NGAYBD, thoiGianBieu.getNgayBD());
        values.put(NGAYKT, thoiGianBieu.getNgayKT());
        values.put(CONGVIEC, thoiGianBieu.getCongViec());
        values.put(LUY, thoiGianBieu.getLuuY());
        int i=sqLiteDatabase.update(TABLE_NAME,values,MA+ " =?",new String[]{thoiGianBieu.getKey()});
        sqLiteDatabase.close();
        return i;
    }
    public int kiemTraTonTai(ThoiGianBieu thoiGianBieu)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String sql="select * from "+TABLE_NAME+" where "+MA+" = "+"'"+thoiGianBieu.getKey()+"'";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
      //  cursor.moveToFirst();
        return cursor.getCount();
    }
    public  int deleteData(String str)
    {
        int a=-1;
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        a=sqLiteDatabase.delete(TABLE_NAME,MA+" =? ",new String[]{str});
        sqLiteDatabase.close();
        return a;
    }

    //lay thong tin trong mot tuan co ngay la date
    public  List<ThoiGianBieu> getThongTin(Date date)
    {
        List<ThoiGianBieu> list=new ArrayList<ThoiGianBieu>();

        Date startWeek=NgayThang.getDateStartOfWeek(date);
        Date endWeek=NgayThang.getDateEndOfWeek(date);

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String sql="select * from "+TABLE_NAME  +" where  date("+NGAYBD+") <= date('"+NgayThang.toString(endWeek)+"')  AND date("+NGAYKT+") >= date('"+NgayThang.toString(startWeek)+"')";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToFirst();
        Toast.makeText(context, ""+"Từ :"+NgayThang.formatDDMMYYYY(startWeek)+" đến :"+NgayThang.formatDDMMYYYY(endWeek), Toast.LENGTH_SHORT).show();

        ThoiGianBieu thoiGianBieu;
        while(!cursor.isAfterLast())
        {
            thoiGianBieu=new ThoiGianBieu();
            thoiGianBieu.setGioBD(cursor.getString(cursor.getColumnIndex(GIOBD)));
            thoiGianBieu.setGioKT(cursor.getString(cursor.getColumnIndex(GIOKT)));
            thoiGianBieu.setNgayBD(cursor.getString(cursor.getColumnIndex(NGAYBD)));
            thoiGianBieu.setNgayKT(cursor.getString(cursor.getColumnIndex(NGAYKT)));
            thoiGianBieu.setCongViec(cursor.getString(cursor.getColumnIndex(CONGVIEC)));
            thoiGianBieu.setLuuY(cursor.getString(cursor.getColumnIndex(LUY)));
            thoiGianBieu.setKey();
            list.add(thoiGianBieu);
            cursor.moveToNext();
        }
        sqLiteDatabase.close();
        return list;
    }
    public List<ThoiGianBieu>  searchData(String str)
    {
        List<ThoiGianBieu> listJob=new ArrayList<ThoiGianBieu>();
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String sql= "select * from "+TABLE_NAME +" where "+CONGVIEC.toUpperCase() +" LIKE '%"+str.toUpperCase()+"%'";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToFirst();
        ThoiGianBieu thoiGianBieu;
        while (!cursor.isAfterLast())
        {
            thoiGianBieu=new ThoiGianBieu();
            thoiGianBieu.setGioBD(cursor.getString(cursor.getColumnIndex(GIOBD)));
            thoiGianBieu.setGioKT(cursor.getString(cursor.getColumnIndex(GIOKT)));
            thoiGianBieu.setNgayBD(cursor.getString(cursor.getColumnIndex(NGAYBD)));
            thoiGianBieu.setNgayKT(cursor.getString(cursor.getColumnIndex(NGAYKT)));
            thoiGianBieu.setCongViec(cursor.getString(cursor.getColumnIndex((CONGVIEC))));
            thoiGianBieu.setLuuY(cursor.getString(cursor.getColumnIndex(LUY)));
            listJob.add(thoiGianBieu);
            cursor.moveToNext();
        }
        return listJob;
    }
}
