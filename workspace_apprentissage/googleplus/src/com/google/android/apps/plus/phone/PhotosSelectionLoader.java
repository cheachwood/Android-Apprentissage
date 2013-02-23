package com.google.android.apps.plus.phone;

import android.content.Context;
import android.database.Cursor;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.EsAccount;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class PhotosSelectionLoader extends PhotoCursorLoader
{
  private List<MediaRef> mMediaRefsToLoad;

  public PhotosSelectionLoader(Context paramContext, EsAccount paramEsAccount, String paramString, List<MediaRef> paramList)
  {
    super(paramContext, paramEsAccount, paramString, null, null, "camerasync", null, null, false, 0, null);
    this.mMediaRefsToLoad = paramList;
  }

  public final Cursor esLoadInBackground()
  {
    setUri(getLoaderUri());
    if ((this.mMediaRefsToLoad != null) && (!this.mMediaRefsToLoad.isEmpty()))
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("( CASE photo_id ");
      StringBuilder localStringBuilder2 = new StringBuilder();
      ArrayList localArrayList = new ArrayList(2 * this.mMediaRefsToLoad.size());
      int i = 0;
      int j = 0;
      Iterator localIterator = this.mMediaRefsToLoad.iterator();
      while (localIterator.hasNext())
      {
        String str = String.valueOf(((MediaRef)localIterator.next()).getPhotoId());
        if (i != 0)
          localStringBuilder2.append(" OR ");
        localArrayList.add(str);
        localStringBuilder2.append("(photo_id == ?)");
        i = 1;
        StringBuilder localStringBuilder3 = new StringBuilder(" WHEN '").append(str).append("' THEN ");
        int k = j + 1;
        localStringBuilder1.append(j);
        j = k;
      }
      localStringBuilder1.append(" END )");
      setSortOrder(localStringBuilder1.toString());
      setSelection(localStringBuilder2.toString());
      setSelectionArgs((String[])localArrayList.toArray(new String[localArrayList.size()]));
    }
    setProjection(AlbumViewLoader.PhotoQuery.PROJECTION);
    return super.esLoadInBackground();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PhotosSelectionLoader
 * JD-Core Version:    0.6.2
 */