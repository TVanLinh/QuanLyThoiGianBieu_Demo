package RangBuoc;

import android.content.Context;
import android.widget.Toast;

import java.util.Date;

import Data.ThoiGianBieu;

/**
 * Created by asus on 17/09/2016.
 */
public class RangBuoc
{
    private ThoiGianBieu thoiGianBieu;
    Context context;
    public  RangBuoc(Context context)
    {
        this.context=context;
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
            Toast.makeText(context, "Phải nhập định dạng ngay theo dd-MM-yyyy", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if(namBDNhuan==0&&kiemTraThang2(ngayBD)==2)
        {
            Toast.makeText(context, "Năm "+getNam(ngayBD)+" tháng 2 chỉ có 28 ngày.!", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if(namKTNhuan==0&&kiemTraThang2(ngayKT)==2)
        {
            Toast.makeText(context, "Năm "+getNam(ngayKT)+" tháng 2 chỉ có 28 ngày.!", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (soSanhSanhNgayThang(ngayBD, ngayKT) == 0) {
            Toast.makeText(context, "Thời gian bắt đầu phải nhỏ hơn hoặc bằng thời gian kết thúc", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (soSanhSanhNgayThang(ngayBD, ngayKT) == 0) {
            Toast.makeText(context, "Thời gian bắt đầu phải nhỏ hơn hoặc bằng thời gian kết thúc", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (thoiGianBieu.getCongViec().equals("")) {
            Toast.makeText(context, "Bạn chưa nhập công việc", Toast.LENGTH_SHORT).show();
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
    public int kiemTraRangBuocKhiNhapNgay(String ngayBD,String ngayKT)
    {
        String rangBuocNgayBD = "^((0[1-9]|(0[1-9]|[1-2][0-9])|3[0-1])-(01|03|05|07|08|10|12)-20[0-9][0-9])|(((0[1-9]|([0-2][1-9])|10|20|30)-(02|04|06|09|11)-20[0-9][0-9]))|((0[1-9]|[0-2][1-9]|20)-(02)-20[0-9][0-9])$";
        String ddMM="^(0[1-9]|1[0-9]|2[0-9]|3[0-1])-(0[1-9]|1[0-2])$";
        if((ngayBD.matches(rangBuocNgayBD)||ngayBD.matches(ddMM))&&(ngayKT.matches(rangBuocNgayBD)||ngayKT.matches(ddMM)))
        {
            return 1;
        }
        return 0;
    }
    public int kiemTraRangBuocGio(ThoiGianBieu thoiGianBieu)
    {
        String str = "^((1[0-9])|(0[0-9])|(2[0-3])):([0-5][0-9])$";
        if (!thoiGianBieu.getGioBD().matches(str) || !thoiGianBieu.getGioKT().matches(str)) {
            Toast.makeText(context, "Bạn phải nhâp giờ bắt đầu và giờ kết thúc theo định dạng hh:mm", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (thoiGianBieu.getGioBD().equals(thoiGianBieu.getGioKT())) {
            Toast.makeText(context, "Giờ bắt đầu và giờ kết thúc không trùng nhau", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (soSanhSanhGio(thoiGianBieu.getGioBD(), thoiGianBieu.getGioKT()) == 0) {
            Toast.makeText(context, "Giờ bắt đầu phải nhỏ hơn giờ kết thúc", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }
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
}
