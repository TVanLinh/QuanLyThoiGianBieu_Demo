package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.linhtran.vnua.demothuctap.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Data.ThoiGianBieu;
import NgayThang.NgayThang;

/**
 * Created by asus on 26/09/2016.
 */
public class TimKiemAdapter extends ArrayAdapter<ThoiGianBieu>
{
    Context context;
    int resource;
    List<ThoiGianBieu> listJob;

    public TimKiemAdapter(Context context, int resource, List<ThoiGianBieu> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.listJob=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.custom_layout_timkiem, null);
        }
        TextView txtCongViec,txtGio,txtThu,txtNgay;
        txtCongViec=(TextView)view.findViewById(R.id.txtCongViec);
        txtGio=(TextView)view.findViewById(R.id.txtGio);
        txtThu=(TextView)view.findViewById(R.id.txtThu);
        txtNgay=(TextView)view.findViewById(R.id.txtNgay);

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
        txtCongViec.setText(thoiGianBieu.getCongViec());
        String ngay= NgayThang.formatDDMMYYYY(ngayBD)+" đến "+NgayThang.formatDDMMYYYY(ngayKT);
        txtNgay.setText(ngay);
        int thu=thoiGianBieu.getThu(thoiGianBieu.getNgayBD());
        String strThu;
        strThu="Thứ "+thu;
        if(thu==1)
        {
            strThu="Chủ nhật";
        }
        txtThu.setText(strThu);
        return view;
    }
}
