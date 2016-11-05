package NgayThang;

/**
 * Created by asus on 20/09/2016.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NgayThang
{
    public  NgayThang()
    {

    }
    public static Date getDateStartOfWeek(Date date)
    {
        Date date1=new Date();
        int thu=date.getDay()+1;
        if(thu==1)
        {
            thu=8;
        }
        date1.setYear(date.getYear());
        date1.setMonth(date.getMonth());
        date1.setDate(date.getDate()-(thu-2));
        return date1;
    }
    public static Date getDateEndOfWeek(Date date)
    {
        Date date1=new Date();
        int thu=date.getDay()+1;
        if(thu==1)
        {
            thu=8;
        }
        date1.setYear(date.getYear());
        date1.setMonth(date.getMonth());
        date1.setDate(date.getDate()+(8-thu));
        return date1;
    }
    public static  int soSanhNgayThang(Date date1,Date date2)
    {
        Calendar cal1=Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2=Calendar.getInstance();
        cal2.setTime(date2);
        if (cal1.get(Calendar.YEAR)>cal2.get(Calendar.YEAR))
        {
            return 1;
        }
        if (cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR)&&cal1.get(Calendar.MONTH)>cal2.get(Calendar.MONTH))
        {
            return  1;
        }
        if (cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR)&&cal1.get(Calendar.MONTH)==cal2.get(Calendar.MONTH)&&cal1.get(Calendar.DATE)>cal2.get(Calendar.DATE))
        {
            return  1;
        }
        if (cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR)&&cal1.get(Calendar.MONTH)==cal2.get(Calendar.MONTH)&&cal1.get(Calendar.DATE)==cal2.get(Calendar.DATE))
        {
            return 0;
        }
        return -1;
    }
    public static int  soSanhSanhGio(String nt1, String nt2)
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
    public  static  String toString(Date date)
    {
        Calendar cal1=Calendar.getInstance();
        cal1.setTime(date);
        String thang="";
        if(cal1.get(Calendar.MONTH)+1>=10)
        {
            thang=""+(cal1.get(Calendar.MONTH)+1);

        }else {
            thang="0"+(cal1.get(Calendar.MONTH)+1);
        }
        String ngay="";
        if(cal1.get(Calendar.DATE)>=10)
        {
            ngay=""+cal1.get(Calendar.DATE);
        }
        else
        {
            ngay="0"+cal1.get(Calendar.DATE);
        }
        String string=cal1.get(Calendar.YEAR)+"-"+thang+"-"+ngay;
        return string.trim();
    }
    public static int getThu(String str) {
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
    public  static  String formatDDMMYYYY(Date date)
    {
        Calendar cal1=Calendar.getInstance();
        cal1.setTime(date);
        String thang="";
        if(cal1.get(Calendar.MONTH)+1>=10)
        {
            thang=""+(cal1.get(Calendar.MONTH)+1);

        }else {
            thang="0"+(cal1.get(Calendar.MONTH)+1);
        }
        String ngay="";
        if(cal1.get(Calendar.DATE)>=10)
        {
            ngay=""+cal1.get(Calendar.DATE);
        }
        else
        {
            ngay="0"+cal1.get(Calendar.DATE);
        }
        String string=ngay+"-"+thang+"-"+cal1.get(Calendar.YEAR);
        return string.trim();
    }
    public static int laySoNgay(int nam,int thang,int ngay)
    {
        int result=0;
        int thang2=28;
        if(nam%4==0)
        {
            thang2=29;
        }
        switch(thang)
        {
            case 1:
            {
                result=ngay;
                break;
            }
            case 2:
            {
                result=31+ngay;
                break;
            }
            case 3:
            {
                result=31+thang2+ngay;
                break;
            }
            case 4:
            {
                result=31+thang2+31+ngay;
                break;
            }
            case 5:
            {
                result=31+thang2+31+30+ngay;
                break;
            }
            case 6:
            {
                result=31+thang2+31+30+31+ngay;
                break;
            }
            case 7:
            {
                result=31+thang2+31+30+31+30+ngay;
                break;
            }
            case 8:
            {
                result=31+thang2+31+30+31+30+31+ngay;
                break;
            }
            case 9:
            {
                result=31+thang2+31+30+31+30+31+31+ngay;
                break;
            }
            case 10:
            {
                result=31+thang2+31+30+31+30+31+31+30+ngay;
                break;
            }
            case 11:
            {
                result=31+thang2+31+30+31+30+31+31+30+31+ngay;
                break;
            }
            case 12:
            {
                result=31+thang2+31+30+31+30+31+31+30+31+30+ngay;
            }
        }
        return result;
    }
    public static String changeFormatDay(String str) {
        Date date = new java.util.Date();
        String mang[] = str.split("-");
        return mang[2]+"-"+mang[1]+"-"+mang[0];
    }
}
