package com.google.android.apps.plus.phone;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video.Media;
import android.util.Log;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.MediaStoreUtils;
import com.google.android.apps.plus.util.StopWatch;
import java.util.HashSet;

public class CameraAlbumLoader extends EsCursorLoader
  implements Pageable
{
  protected static final Uri[] sMediaStoreUri = arrayOfUri;
  private int mExcludedCount;
  private HashSet<String> mExcludedUris;
  private boolean mHasMore;
  private int mLoadLimit;
  private boolean mPageable;

  static
  {
    Uri[] arrayOfUri = new Uri[4];
    arrayOfUri[0] = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    arrayOfUri[1] = MediaStoreUtils.PHONE_STORAGE_IMAGES_URI;
    arrayOfUri[2] = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    arrayOfUri[3] = MediaStoreUtils.PHONE_STORAGE_VIDEO_URI;
  }

  public CameraAlbumLoader(Context paramContext, EsAccount paramEsAccount)
  {
    this(paramContext, true, 1);
  }

  public CameraAlbumLoader(Context paramContext, EsAccount paramEsAccount, MediaRef[] paramArrayOfMediaRef)
  {
    this(paramContext, paramEsAccount);
    if (this.mExcludedUris != null)
    {
      this.mExcludedUris.clear();
      if (paramArrayOfMediaRef == null)
        this.mExcludedUris = null;
    }
    if ((paramArrayOfMediaRef != null) && (paramArrayOfMediaRef.length > 0))
    {
      if (this.mExcludedUris == null)
        this.mExcludedUris = new HashSet(paramArrayOfMediaRef.length);
      int i = paramArrayOfMediaRef.length;
      for (int j = 0; j < i; j++)
      {
        MediaRef localMediaRef = paramArrayOfMediaRef[j];
        if (localMediaRef.hasLocalUri())
          this.mExcludedUris.add(localMediaRef.getLocalUri().toString());
      }
    }
  }

  private CameraAlbumLoader(Context paramContext, boolean paramBoolean, int paramInt)
  {
    super(paramContext, null);
    this.mLoadLimit = i;
    this.mPageable = true;
    if (this.mPageable);
    while (true)
    {
      this.mLoadLimit = i;
      return;
      i = -1;
    }
  }

  protected Cursor buildMatrixCursor(Context paramContext, Cursor[] paramArrayOfCursor, Uri[] paramArrayOfUri)
  {
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(AlbumViewLoader.PhotoQuery.PROJECTION);
    this.mExcludedCount = 0;
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
            break label92;
        label92: for (long l3 = 0L; ; l3 = localCursor2.getLong(1))
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
          Uri localUri = ContentUris.withAppendedId(paramArrayOfUri[i], l2);
          if ((this.mExcludedUris == null) || (!this.mExcludedUris.contains(localUri.toString())))
          {
            String str = localCursor1.getString(2);
            byte[] arrayOfByte = MediaStoreUtils.toVideoDataBytes(paramContext, localUri);
            Object[] arrayOfObject = new Object[AlbumViewLoader.PhotoQuery.PROJECTION.length];
            arrayOfObject[0] = Long.valueOf(l2);
            arrayOfObject[1] = null;
            arrayOfObject[2] = null;
            arrayOfObject[3] = null;
            arrayOfObject[4] = null;
            arrayOfObject[5] = null;
            arrayOfObject[6] = null;
            arrayOfObject[7] = str;
            arrayOfObject[8] = Long.valueOf(0L);
            arrayOfObject[9] = localUri.toString();
            arrayOfObject[10] = null;
            arrayOfObject[11] = arrayOfByte;
            arrayOfObject[13] = Long.valueOf(l1);
            localEsMatrixCursor.addRow(arrayOfObject);
          }
          while (true)
          {
            localCursor1.moveToNext();
            break;
            this.mExcludedCount = (1 + this.mExcludedCount);
          }
        }
        finally
        {
          localCursor1.moveToNext();
        }
      }
    }
    return localEsMatrixCursor;
  }

  public final Cursor esLoadInBackground()
  {
    boolean bool = EsLog.isLoggable("IUAlbumLoader", 2);
    StopWatch localStopWatch = null;
    if (bool)
    {
      localStopWatch = new StopWatch().start();
      Log.v("IUAlbumLoader", "esLoadInBackground: BEGIN thread=" + Thread.currentThread().getName());
    }
    Cursor[] arrayOfCursor = new Cursor[sMediaStoreUri.length];
    int m;
    String str;
    int i2;
    Cursor localCursor2;
    label363: label370: 
    try
    {
      int k = this.mLoadLimit;
      this.mHasMore = false;
      m = 0;
      if (m < sMediaStoreUri.length)
      {
        setUri(sMediaStoreUri[m]);
        setProjection(PhotoQuery.PROJECTION);
        if (!this.mPageable)
          break label363;
        str = "corrected_date_taken desc LIMIT 0, " + k;
        setSortOrder(str);
        arrayOfCursor[m] = super.esLoadInBackground();
        if (arrayOfCursor[m] == null)
          break label370;
        i2 = arrayOfCursor[m].getCount();
        arrayOfCursor[m].moveToFirst();
        if ((!this.mHasMore) && (this.mPageable) && (i2 == k))
          this.mHasMore = true;
      }
      else
      {
        localCursor2 = buildMatrixCursor(getContext(), arrayOfCursor, sMediaStoreUri);
        if (EsLog.isLoggable("IUAlbumLoader", 2))
          Log.v("IUAlbumLoader", "esLoadInBackground: END totalRows=" + localCursor2.getCount() + ", msec=" + localStopWatch.getElapsedMsec() + ", thread=" + Thread.currentThread().getName());
        int n = arrayOfCursor.length;
        int i1 = 0;
        if (i1 < n)
        {
          Cursor localCursor3 = arrayOfCursor[i1];
          if (localCursor3 != null)
            localCursor3.close();
          i1++;
        }
      }
    }
    finally
    {
      int i = arrayOfCursor.length;
      for (int j = 0; j < i; j++)
      {
        Cursor localCursor1 = arrayOfCursor[j];
        if (localCursor1 != null)
          localCursor1.close();
      }
    }
  }

  public final int getCurrentPage()
  {
    int i = -1;
    if ((this.mPageable) && (this.mLoadLimit != i))
      i = this.mLoadLimit / 16;
    return i;
  }

  public final int getExcludedCount()
  {
    return this.mExcludedCount;
  }

  public final boolean hasMore()
  {
    if ((this.mPageable) && (this.mHasMore));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isDataSourceLoading()
  {
    return false;
  }

  public final void loadMore()
  {
    if (hasMore())
    {
      this.mLoadLimit = (48 + this.mLoadLimit);
      onContentChanged();
    }
  }

  public final void setLoadingListener(Pageable.LoadingListener paramLoadingListener)
  {
  }

  private static abstract interface CorrectedMediaStoreColumn
  {
    public static final String DATE_TAKEN = String.format("case when (datetaken >= %1$d and datetaken < %2$d) then datetaken * 1000 when (datetaken >= %3$d and datetaken < %4$d) then datetaken when (datetaken >= %5$d and datetaken < %6$d) then datetaken / 1000 else 0 end as %7$s", arrayOfObject);

    static
    {
      Object[] arrayOfObject = new Object[7];
      arrayOfObject[0] = Long.valueOf(157680000L);
      arrayOfObject[1] = Long.valueOf(1892160000L);
      arrayOfObject[2] = Long.valueOf(157680000000L);
      arrayOfObject[3] = Long.valueOf(1892160000000L);
      arrayOfObject[4] = Long.valueOf(157680000000000L);
      arrayOfObject[5] = Long.valueOf(1892160000000000L);
      arrayOfObject[6] = "corrected_date_taken";
    }
  }

  protected static abstract interface PhotoQuery
  {
    public static final String[] PROJECTION = arrayOfString;

    static
    {
      String[] arrayOfString = new String[3];
      arrayOfString[0] = "_id";
      arrayOfString[1] = CameraAlbumLoader.CorrectedMediaStoreColumn.DATE_TAKEN;
      arrayOfString[2] = "_display_name";
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.CameraAlbumLoader
 * JD-Core Version:    0.6.2
 */