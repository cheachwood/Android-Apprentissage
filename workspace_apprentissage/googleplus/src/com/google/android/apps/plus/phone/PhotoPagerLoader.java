package com.google.android.apps.plus.phone;

import android.content.Context;
import android.database.Cursor;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.EsAccount;

public final class PhotoPagerLoader extends PhotoCursorLoader
{
  private final MediaRef[] mMediaRefs;
  private final String mOwnerGaiaId;
  private final String mPhotoUrl;

  public PhotoPagerLoader(Context paramContext, EsAccount paramEsAccount, String paramString1, MediaRef[] paramArrayOfMediaRef, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt, String paramString7)
  {
  }

  public final Cursor esLoadInBackground()
  {
    int i = 0;
    Object localObject;
    if (this.mMediaRefs != null)
    {
      MediaRef[] arrayOfMediaRef = this.mMediaRefs;
      localObject = new EsMatrixCursor(PhotoQuery.PROJECTION);
      if (i < arrayOfMediaRef.length)
      {
        if (arrayOfMediaRef[i].getLocalUri() != null)
          ((EsMatrixCursor)localObject).newRow().add(Integer.valueOf(i)).add(null).add(arrayOfMediaRef[i].getLocalUri()).add(arrayOfMediaRef[i].getOwnerGaiaId());
        while (true)
        {
          i++;
          break;
          ((EsMatrixCursor)localObject).newRow().add(Integer.valueOf(i)).add(Long.valueOf(arrayOfMediaRef[i].getPhotoId())).add(arrayOfMediaRef[i].getUrl()).add(arrayOfMediaRef[i].getOwnerGaiaId());
        }
      }
    }
    else
    {
      if (this.mPhotoUrl == null)
        break label185;
      String str = this.mPhotoUrl;
      localObject = new EsMatrixCursor(PhotoQuery.PROJECTION);
      ((EsMatrixCursor)localObject).newRow().add(Integer.valueOf(0)).add(null).add(str).add(this.mOwnerGaiaId);
    }
    while (true)
    {
      return localObject;
      label185: setUri(getLoaderUri());
      setProjection(PhotoQuery.PROJECTION);
      localObject = super.esLoadInBackground();
    }
  }

  public static abstract interface PhotoQuery
  {
    public static final String[] PROJECTION = { "_id", "photo_id", "url", "owner_id", "title", "video_data", "is_panorama", "album_name", "upload_status" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PhotoPagerLoader
 * JD-Core Version:    0.6.2
 */