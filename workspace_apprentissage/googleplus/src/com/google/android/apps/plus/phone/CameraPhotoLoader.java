package com.google.android.apps.plus.phone;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.google.android.apps.plus.util.MediaStoreUtils;

public final class CameraPhotoLoader extends CameraAlbumLoader
{
  public CameraPhotoLoader(Context paramContext)
  {
    super(paramContext, null);
  }

  protected final Cursor buildMatrixCursor(Context paramContext, Cursor[] paramArrayOfCursor, Uri[] paramArrayOfUri)
  {
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(PhotoPagerLoader.PhotoQuery.PROJECTION);
    while (true)
    {
      long l1 = -1L;
      int i = -1;
      int j = 0;
      if (j < paramArrayOfCursor.length)
      {
        Cursor localCursor2 = paramArrayOfCursor[j];
        if ((localCursor2 != null) && (!localCursor2.isAfterLast()))
          if (!localCursor2.isNull(1))
            break label87;
        label87: for (long l3 = 0L; ; l3 = localCursor2.getLong(1))
        {
          if (l3 > l1)
          {
            l1 = l3;
            i = j;
          }
          j++;
          break;
        }
      }
      if (i != -1)
      {
        Cursor localCursor1 = paramArrayOfCursor[i];
        try
        {
          long l2 = localCursor1.getLong(0);
          String str = localCursor1.getString(2);
          Uri localUri = ContentUris.withAppendedId(paramArrayOfUri[i], l2);
          byte[] arrayOfByte = MediaStoreUtils.toVideoDataBytes(paramContext, localUri);
          localEsMatrixCursor.newRow().add(Long.valueOf(l2)).add(Long.valueOf(0L)).add(localUri.toString()).add(null).add(str).add(arrayOfByte).add(null);
          localCursor1.moveToNext();
        }
        finally
        {
          localCursor1.moveToNext();
        }
      }
    }
    return localEsMatrixCursor;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.CameraPhotoLoader
 * JD-Core Version:    0.6.2
 */