package com.google.android.apps.plus.phone;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsProvider;

public final class RecentImagesLoader extends EsCursorLoader
{
  public RecentImagesLoader(Context paramContext, EsAccount paramEsAccount)
  {
    super(paramContext);
    Uri.Builder localBuilder = EsProvider.PHOTO_BY_STREAM_ID_AND_OWNER_ID_URI.buildUpon().appendPath("camerasync").appendPath(paramEsAccount.getGaiaId());
    EsProvider.appendAccountParameter(localBuilder, paramEsAccount);
    setUri(localBuilder.build());
    setProjection(RecentImagesQuery.PROJECTION);
    setSelectionArgs(null);
    setSortOrder("timestamp DESC LIMIT 10");
  }

  public final Cursor esLoadInBackground()
  {
    long l1 = EsAccountsData.loadRecentImagesTimestamp(getContext());
    long l2 = System.currentTimeMillis();
    if (l2 - l1 >= 86400000L);
    for (long l3 = Math.max(l2 - 432000000L, l1); ; l3 = 9223372036854775807L)
    {
      setSelection("timestamp > " + l3);
      return super.esLoadInBackground();
    }
  }

  public static abstract interface RecentImagesQuery
  {
    public static final String[] PROJECTION = { "photo_id", "url", "timestamp", "video_data" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.RecentImagesLoader
 * JD-Core Version:    0.6.2
 */