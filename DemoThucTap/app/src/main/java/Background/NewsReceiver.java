package Background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by asus on 25/10/2016.
 */
public class NewsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent ) {
        Date date=new Date();
        if(date.getDate()==25)
        {
            Toast.makeText(context, "ngay 25 day roi ", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText( context, "Broadcast recieved! There is news!"+ date.getHours()+ " : "+date.getMinutes(), Toast.LENGTH_SHORT).show();
    }
}