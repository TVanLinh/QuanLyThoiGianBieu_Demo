package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linhtran.vnua.demothuctap.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Data.ThoiGianBieu;
import NgayThang.NgayThang;

/**
 * Created by asus on 24/09/2016.
 */
public class ListViewAdapter extends ArrayAdapter<ThoiGianBieu>
{
    Context context;
    int resource;
    List<ThoiGianBieu> listJob;

    public ListViewAdapter(Context context, int resource, List<ThoiGianBieu> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.listJob=objects;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.custom_listview, null);
        }
        TextView txtCV,txtNgay,txtPhanTram,txtGio;
        ProgressBar progressBar;
        txtCV=(TextView)view.findViewById(R.id.txtCongViec);
        txtNgay=(TextView)view.findViewById(R.id.txtNgay);
        txtPhanTram=(TextView)view.findViewById(R.id.txtPhanTram);
        txtGio=(TextView)view.findViewById(R.id.txtGio);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        ThoiGianBieu thoiGianBieu=listJob.get(position);

        Date ngayBD=new Date(),ngayKT=new Date();
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
        try
        {
                ngayBD=simpleDateFormat.parse(thoiGianBieu.getNgayBD());
                ngayKT=simpleDateFormat.parse(thoiGianBieu.getNgayKT());
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        String gio=thoiGianBieu.getGioBD()+" đến "+thoiGianBieu.getGioKT();
        txtGio.setText(gio);
        txtCV.setText(thoiGianBieu.getCongViec());
        String ngay=NgayThang.formatDDMMYYYY(ngayBD)+" đến "+NgayThang.formatDDMMYYYY(ngayKT);
        txtNgay.setText(ngay);
        progressBar.setMax(100);
        progressBar.setProgress((int)thoiGianBieu.getPhanTram());
        if((int)thoiGianBieu.getPhanTram()==100)
        {
            progressBar.setIndeterminate(true);
        }
        String pT=thoiGianBieu.getPhanTram()+"%";
        txtPhanTram.setText(pT);
        return view;
    }
}