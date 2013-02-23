package com.google.android.apps.plus.fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.util.MediaStoreUtils;
import com.google.api.services.plusi.model.DataVideo;
import com.google.api.services.plusi.model.DataVideoJson;

public final class VideoDataLoader extends CursorLoader
{
  private final EsAccount mAccount;
  private final Uri mLocalUri;
  private final long mPhotoId;
  private final String mPhotoUrl;

  public VideoDataLoader(Context paramContext, EsAccount paramEsAccount, String paramString, long paramLong, Uri paramUri)
  {
    super(paramContext);
    this.mAccount = paramEsAccount;
    this.mPhotoUrl = paramString;
    this.mPhotoId = paramLong;
    this.mLocalUri = paramUri;
  }

  public final Cursor loadInBackground()
  {
    ContentResolver localContentResolver = getContext().getContentResolver();
    if (this.mPhotoId != 0L)
    {
      localObject = localContentResolver.query(EsProvider.appendAccountParameter(ContentUris.withAppendedId(EsProvider.PHOTO_BY_PHOTO_ID_URI, this.mPhotoId), this.mAccount), PhotoQuery.PROJECTION, null, null, null);
      return localObject;
    }
    Object localObject = new EsMatrixCursor(PhotoQuery.PROJECTION);
    DataVideo localDataVideo;
    if (this.mPhotoUrl != null)
    {
      localDataVideo = MediaStoreUtils.toVideoData(getContext(), Uri.parse(this.mPhotoUrl));
      label81: if (localDataVideo == null)
        break label151;
    }
    label151: for (byte[] arrayOfByte = DataVideoJson.getInstance().toByteArray(localDataVideo); ; arrayOfByte = null)
    {
      Object[] arrayOfObject = new Object[PhotoQuery.PROJECTION.length];
      arrayOfObject[0] = arrayOfByte;
      ((EsMatrixCursor)localObject).addRow(arrayOfObject);
      break;
      Uri localUri = this.mLocalUri;
      localDataVideo = null;
      if (localUri == null)
        break label81;
      localDataVideo = MediaStoreUtils.toVideoData(getContext(), this.mLocalUri);
      break label81;
    }
  }

  public static abstract interface PhotoQuery
  {
    public static final String[] PROJECTION = { "video_data" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.VideoDataLoader
 * JD-Core Version:    0.6.2
 */