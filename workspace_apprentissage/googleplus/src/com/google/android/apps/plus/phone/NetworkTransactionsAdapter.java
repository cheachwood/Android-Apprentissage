package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import java.util.Date;

public final class NetworkTransactionsAdapter extends EsCursorAdapter
{
  public NetworkTransactionsAdapter(Context paramContext, Cursor paramCursor)
  {
    super(paramContext, null);
  }

  public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
    TextView localTextView1 = (TextView)paramView.findViewById(R.id.transaction_time);
    long l = paramCursor.getLong(2);
    localTextView1.setText(DateFormat.format("MM-dd hh:mm:ss", new Date(l)) + "." + l % 1000L);
    ((TextView)paramView.findViewById(R.id.transaction_name)).setText(paramCursor.getString(1));
    ImageView localImageView = (ImageView)paramView.findViewById(16908294);
    TextView localTextView2 = (TextView)paramView.findViewById(R.id.transaction_bytes);
    int j;
    String str2;
    if (paramCursor.isNull(8))
    {
      localImageView.setImageResource(R.drawable.indicator_green);
      j = paramCursor.getInt(7);
      if (j <= 1)
      {
        Resources localResources3 = paramContext.getResources();
        int m = R.string.network_transaction_one_bytes;
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = Long.valueOf(paramCursor.getLong(3));
        arrayOfObject3[1] = Long.valueOf(paramCursor.getLong(4));
        str2 = localResources3.getString(m, arrayOfObject3);
        localTextView2.setText(str2.toString());
      }
    }
    while (true)
    {
      Resources localResources1 = paramContext.getResources();
      int i = R.string.network_transaction_duration;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Long.valueOf(paramCursor.getLong(5));
      String str1 = localResources1.getString(i, arrayOfObject1);
      ((TextView)paramView.findViewById(R.id.transaction_duration)).setText(str1.toString());
      return;
      Resources localResources2 = paramContext.getResources();
      int k = R.string.network_transaction_many_bytes;
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = Long.valueOf(paramCursor.getLong(3));
      arrayOfObject2[1] = Long.valueOf(paramCursor.getLong(4));
      arrayOfObject2[2] = Integer.valueOf(j);
      str2 = localResources2.getString(k, arrayOfObject2);
      break;
      localImageView.setImageResource(R.drawable.indicator_red);
      localTextView2.setText(paramCursor.getString(8));
    }
  }

  public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    return ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(R.layout.network_transaction_row_view, null);
  }

  public static abstract interface NetworkTransactionsQuery
  {
    public static final String[] PROJECTION = { "_id", "name", "time", "sent", "recv", "network_duration", "process_duration", "req_count", "exception" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.NetworkTransactionsAdapter
 * JD-Core Version:    0.6.2
 */