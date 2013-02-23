package com.google.android.apps.plus.phone;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.DbLocation;

public final class PlacesAdapter extends EsCursorAdapter
{
  public PlacesAdapter(Context paramContext)
  {
    super(paramContext, null);
  }

  public static DbLocation getLocation(Cursor paramCursor)
  {
    return DbLocation.deserialize(paramCursor.getBlob(2));
  }

  public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(16908294);
    TextView localTextView1 = (TextView)paramView.findViewById(16908310);
    TextView localTextView2 = (TextView)paramView.findViewById(16908293);
    DbLocation localDbLocation = getLocation(paramCursor);
    String str;
    if (localDbLocation != null)
    {
      if (!localDbLocation.isPrecise())
        break label83;
      localImageView.setImageResource(R.drawable.list_current);
      localTextView1.setText(R.string.my_location);
      str = localDbLocation.getLocationName();
    }
    while (true)
    {
      localTextView2.setText(str);
      return;
      label83: if (localDbLocation.isCoarse())
      {
        localImageView.setImageResource(R.drawable.ic_location_city);
        localTextView1.setText(R.string.my_city);
        str = localDbLocation.getLocationName();
      }
      else
      {
        localImageView.setImageResource(R.drawable.ic_location_grey);
        localTextView1.setText(localDbLocation.getName());
        str = localDbLocation.getBestAddress();
      }
    }
  }

  public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    return LayoutInflater.from(paramContext).inflate(R.layout.location_row_layout, paramViewGroup, false);
  }

  public static abstract interface LocationQuery
  {
    public static final String[] PROJECTION = { "_id", "name", "location" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PlacesAdapter
 * JD-Core Version:    0.6.2
 */