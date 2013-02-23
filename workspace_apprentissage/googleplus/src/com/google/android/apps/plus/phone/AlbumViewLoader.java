package com.google.android.apps.plus.phone;

import android.content.Context;
import android.database.Cursor;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.EsAccount;
import java.util.ArrayList;

public final class AlbumViewLoader extends PhotoCursorLoader
{
  private static final String[] COUNT_PROJECTION = { "_count" };
  private int mExcludedCount;
  private MediaRef[] mExcludedMedia;

  public AlbumViewLoader(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, MediaRef[] paramArrayOfMediaRef)
  {
    super(paramContext, paramEsAccount, paramString1, paramString2, paramString3, paramString4, paramString5, null, true, 2, paramString6);
    this.mExcludedMedia = paramArrayOfMediaRef;
  }

  public final Cursor esLoadInBackground()
  {
    setUri(getLoaderUri());
    int i = -1;
    if (this.mExcludedMedia != null)
    {
      Cursor localCursor2 = null;
      StringBuilder localStringBuilder;
      ArrayList localArrayList;
      String str1;
      try
      {
        setProjection(COUNT_PROJECTION);
        localCursor2 = super.esLoadInBackground();
        if ((localCursor2 != null) && (localCursor2.moveToNext()))
        {
          int n = localCursor2.getInt(0);
          i = n;
        }
        if (localCursor2 != null)
          localCursor2.close();
        localStringBuilder = new StringBuilder();
        localArrayList = new ArrayList(2 * this.mExcludedMedia.length);
        int k = 0;
        int m = 0;
        if (m < this.mExcludedMedia.length)
        {
          MediaRef localMediaRef = this.mExcludedMedia[m];
          String str2 = localMediaRef.getOwnerGaiaId();
          if ((str2 != null) && (localMediaRef.hasPhotoId()))
          {
            String str3 = String.valueOf(localMediaRef.getPhotoId());
            if (k != 0)
              localStringBuilder.append(" OR ");
            localArrayList.add(str2);
            localArrayList.add(str3);
            localStringBuilder.append("(owner_id = ? AND photo_id = ?)");
            k = 1;
          }
          m++;
        }
      }
      finally
      {
        if (localCursor2 != null)
          localCursor2.close();
      }
      setSelection("photo_id NOT IN (" + str1 + ")");
      setSelectionArgs((String[])localArrayList.toArray(new String[localArrayList.size()]));
    }
    setProjection(PhotoQuery.PROJECTION);
    Cursor localCursor1 = super.esLoadInBackground();
    if (i < 0);
    for (int j = 0; ; j = localCursor1.getCount() - i)
    {
      this.mExcludedCount = j;
      return localCursor1;
    }
  }

  public final int getExcludedCount()
  {
    return this.mExcludedCount;
  }

  public static abstract interface PhotoQuery
  {
    public static final String[] PROJECTION = { "_id", "action_state", "comment_count", "plusone_by_me", "plusone_count", "owner_id", "fingerprint", "title", "photo_id", "url", "pending_status", "video_data", "is_panorama", "timestamp", "width" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.AlbumViewLoader
 * JD-Core Version:    0.6.2
 */