package Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import NgayThang.NgayThang;

/**
 * Created by asus on 09/09/2016.
 */
public class ThoiGianBieu
{
    private String key="";
    private String gioBD="";
    private String gioKT="";
    private String ngayBD="";
    private String ngayKT="";
    private String congViec="";
    private String luuY="";
    public ThoiGianBieu(ThoiGianBieu t2)
    {
        this.key=t2.key;
        this.gioBD=t2.getGioBD();
        this.gioKT=t2.getGioKT();
        this.ngayBD=t2.getNgayBD();
        this.ngayKT=t2.getNgayKT();
        this.congViec=t2.getCongViec();
        this.luuY=t2.getLuuY();
    }
    private List<ThoiGianBieu> danhSach;

    public List<ThoiGianBieu> getDanhSach() {
        return danhSach;
    }

    public void setDanhSach(List<ThoiGianBieu> danhSach) {
        this.danhSach = danhSach;
    }

    public ThoiGianBieu()
    {

    }
    public void setKey(String key)
    {
        this.key=key;
    }
    public String getKey() {
        return key;
    }

    public void setKey() {
        this.key = gioBD+"-"+ngayBD;
    }

    public String getGioBD() {
        return gioBD;
    }

    public void setGioBD(String gioBD) {
        this.gioBD = gioBD;
    }

    public String getGioKT() {
        return gioKT;
    }

    public void setGioKT(String gioKt) {
        this.gioKT = gioKt;
    }

    public String getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(String ngayBD) {
        this.ngayBD = ngayBD;
    }

    public String getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(String ngayKT) {
        this.ngayKT = ngayKT;
    }

    public String getCongViec() {
        return congViec;
    }

    public void setCongViec(String congViec) {
        this.congViec = congViec;
    }

    public String getLuuY() {
        return luuY;
    }

    public void setLuuY(String luuY) {
        this.luuY = luuY;
    }
    public int layChieuCao ()
    {
        String mangGioB[]=gioBD.split(":");
        String mangGioKT[]=gioKT.split(":");
        int height;
        int gioB=Integer.parseInt(mangGioB[0]);
        int phuB=Integer.parseInt(mangGioB[1]);
        int gioK=Integer.parseInt(mangGioKT[0]);
        int phutK=Integer.parseInt(mangGioKT[1]);
        height=(gioK*240+phutK*4)-(gioB*240+phuB*4);
        return height;
    }
    public int layToaDoY ()
    {
        String mangGioB[]=gioBD.split(":");
        int Y;
        int gioB=Integer.parseInt(mangGioB[0]);
        int phuB=Integer.parseInt(mangGioB[1]);
        return gioB*240+phuB*4;
    }

    public int getThu(String str) {
        int ngay = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(str);
            ngay = date.getDay();
        } catch (ParseException e) {
            ngay = 0;
            e.printStackTrace();
        }
        return ngay + 1;
    }
    public int sosanhNgayThang(String tg1,String tg2)
    {
        String mang1[]=tg1.split("-");
        int nam1=Integer.parseInt(mang1[0]);
        int thang1=Integer.parseInt(mang1[1]);
        int ngay1=Integer.parseInt(mang1[2]);

        String mang2[]=tg2.split("-");
        int nam2=Integer.parseInt(mang2[0]);
        int thang2=Integer.parseInt(mang2[1]);
        int ngay2=Integer.parseInt(mang2[2]);

        if(nam1>nam2||(nam1==nam2&&thang1>thang2)||(nam1==nam2&&thang1==thang2&&ngay1>ngay2))
        {
            return 1;
        }
        if(nam1==nam2&&thang1==thang2&&ngay1==ngay2)
        {
            return 0;
        }
        return -1;
    }

    public int soSanhSanhGio(String nt1, String nt2)
    {
        String mang1[] = nt1.split(":");
        int gioB = Integer.parseInt(mang1[0]);
        int phuB = Integer.parseInt(mang1[1]);
        String mang[] = nt2.split(":");
        int gioK = Integer.parseInt(mang[0]);
        int phutK = Integer.parseInt(mang[1]);
        if (gioB> gioK||(gioB==gioK&&phuB>phutK)) {
            return 1;
        }
        if(gioB==gioK &&phuB==phutK)
        {
            return 0;
        }
        return -1;
    }
    public  int kiemTraNgayTrungLich(ThoiGianBieu thoiGianBieu)
    {
          if(((sosanhNgayThang(this.ngayKT,thoiGianBieu.ngayBD)<0||sosanhNgayThang(this.ngayBD,thoiGianBieu.ngayKT)>0)&&(getThu(this.getNgayBD())==getThu(thoiGianBieu.getNgayBD())))||(getThu(this.getNgayBD())!=getThu(thoiGianBieu.getNgayBD())))
          {
              return 0;
          }
          return 1;



    }

    public  int kiemTraGioTrungLich(ThoiGianBieu thoiGianBieu)
    {
        if((soSanhSanhGio(this.gioKT,thoiGianBieu.gioBD)<=0||soSanhSanhGio(this.gioBD,thoiGianBieu.gioKT)>=0))
        {
            return 0;
        }
        return 1;
    }

    public  int kiemTraTrungLich(ThoiGianBieu thoiGianBieu)
    {
        if(kiemTraGioTrungLich(thoiGianBieu)==1&&kiemTraNgayTrungLich(thoiGianBieu)==1)
        {
            return 1;
        }
        return 0;
    }
    public int getWeekOfYear()
    {
        Calendar calendar=new GregorianCalendar();
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            date=dateFormat.parse(this.ngayBD);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public int getYear()
    {
        String mang[]=this.ngayBD.split("-");
        return Integer.parseInt(mang[0]);
    }
    public int getMonth() {
        String mang[] = this.ngayBD.split("-");
        return Integer.parseInt(mang[1]);
    }
    public int getDate() {
        String mang[] = this.ngayBD.split("-");
        return Integer.parseInt(mang[2]);
    }
    public List<ThoiGianBieu> sapXepTheoGioBD()
    {
        ThoiGianBieu thoiGianBieus[]=new ThoiGianBieu[this.danhSach.size()];
        for(int i=0;i<danhSach.size();i++)
        {
            thoiGianBieus[i]=danhSach.get(i);
        }
        ThoiGianBieu temp;
        for(int i=0;i<danhSach.size();i++)
            for(int j=danhSach.size()-1;j>i;j--)
                if(soSanhSanhGio(thoiGianBieus[j].getGioBD(),thoiGianBieus[j-1].getGioBD())<0)
                {
                    temp=thoiGianBieus[j];
                    thoiGianBieus[j]=thoiGianBieus[j-1];
                    thoiGianBieus[j-1]=temp;
                }
        for(int i=0;i<danhSach.size();i++)
        {
            danhSach.remove(i);
            danhSach.add(i,thoiGianBieus[i]);
        }
        return danhSach;
    }
    public List<ThoiGianBieu> sapXepTheoNgayBD()
    {
        ThoiGianBieu thoiGianBieus[]=new ThoiGianBieu[this.danhSach.size()];
        for(int i=0;i<danhSach.size();i++)
        {
            thoiGianBieus[i]=danhSach.get(i);
        }
        ThoiGianBieu temp;
        for(int i=0;i<danhSach.size();i++)
            for(int j=danhSach.size()-1;j>i;j--)
                if(sosanhNgayThang(thoiGianBieus[j].getNgayBD(),thoiGianBieus[j-1].getNgayBD())<0)
                {
                    temp=thoiGianBieus[j];
                    thoiGianBieus[j]=thoiGianBieus[j-1];
                    thoiGianBieus[j-1]=temp;
                }
        for(int i=0;i<danhSach.size();i++)
        {
            danhSach.remove(i);
            danhSach.add(i,thoiGianBieus[i]);
        }
        return danhSach;
    }
    public int layToaDoDiemCuoi ()
    {
        String mangGioB[]=gioKT.split(":");
        int Y;
        int gioB=Integer.parseInt(mangGioB[0]);
        int phuB=Integer.parseInt(mangGioB[1]);
        return gioB*240+phuB*4;
    }
    public double getPhanTram()
    {
        Date ngayBD=null,ngayKT=null;
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            ngayBD=simpleDateFormat.parse(this.ngayBD);
            ngayKT=simpleDateFormat.parse(this.ngayKT);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        Date date=new Date();
        double bd= NgayThang.laySoNgay(ngayBD.getYear()+1900,ngayBD.getMonth()+1,ngayBD.getDate());
        double kt=NgayThang.laySoNgay(ngayKT.getYear()+1900,ngayKT.getMonth()+1,ngayKT.getDate());
        double hienTai=NgayThang.laySoNgay(date.getYear()+1900,date.getMonth()+1,date.getDate());
        int a=365;
        if((ngayBD.getYear())%2==0)
        {
            a=366;
        }
        if(ngayKT.getYear()>ngayBD.getYear())
        {
            kt=kt+a;
        }
        if(date.getYear()>ngayBD.getYear())
        {
            hienTai=hienTai+a;
        }
        if(hienTai==kt&&hienTai==bd)
        {
            return getPhanTramTheoPhut();
        }
        if(hienTai>kt)
        {
            return 100;
        }
        if(hienTai<bd)
        {
            return 0;
        }
        double  result= ((hienTai-bd)/(kt-bd))*100;
        return Math.round(result);
    }
    public double getPhanTramTheoPhut()
    {
        Date date=new Date();
        String hienTai=date.getHours()+":"+date.getMinutes();
        if(soSanhSanhGio(hienTai,gioBD)<=0)
        {
            return 0;
        }
        if(soSanhSanhGio(hienTai,gioKT)>0)
        {
            return 100;
        }
        return Math.round(((chuyenDOi(hienTai)-chuyenDOi(gioBD))/(chuyenDOi(gioKT)-chuyenDOi(gioBD)))*100);
    }
    public double chuyenDOi(String str)
    {
        String string[]=str.split(":");
        return Double.parseDouble(string[0])*60+Double.parseDouble(string[1]);
    }
    public     List<ThoiGianBieu> sapXepTheoThu()
    {
        ThoiGianBieu thoiGianBieus[]=new ThoiGianBieu[this.danhSach.size()];
        for(int i=0;i<danhSach.size();i++)
        {
            thoiGianBieus[i]=danhSach.get(i);
        }
        ThoiGianBieu temp;
        for(int i=0;i<danhSach.size();i++)
            for(int j=danhSach.size()-1;j>i;j--)
                if(thoiGianBieus[j].getThu(thoiGianBieus[j].getNgayBD())<thoiGianBieus[j-1].getThu(thoiGianBieus[j-1].getNgayBD()))
                {
                    temp=thoiGianBieus[j];
                    thoiGianBieus[j]=thoiGianBieus[j-1];
                    thoiGianBieus[j-1]=temp;
                }
        for(int i=0;i<danhSach.size();i++)
        {
            danhSach.remove(i);
            danhSach.add(i,thoiGianBieus[i]);
        }
        return danhSach;
    }


}
