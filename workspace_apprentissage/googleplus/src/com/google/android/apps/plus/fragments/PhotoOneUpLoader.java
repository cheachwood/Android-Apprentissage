package com.google.android.apps.plus.fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MatrixCursor.RowBuilder;
import android.database.MergeCursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import android.text.TextUtils;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.phone.EsMatrixCursor.RowBuilder;
import com.google.android.apps.plus.util.MediaStoreUtils;
import com.google.api.services.plusi.model.DataVideo;
import com.google.api.services.plusi.model.DataVideoJson;

public final class PhotoOneUpLoader extends CursorLoader
{
  private final EsAccount mAccount;
  private final boolean mDisableComments;
  private final Loader<Cursor>.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver(this);
  private boolean mObserverRegistered;
  private final String mOwnerId;
  private final long mPhotoId;
  private final String mPhotoUrl;

  public PhotoOneUpLoader(Context paramContext, EsAccount paramEsAccount, long paramLong, String paramString1, String paramString2, boolean paramBoolean)
  {
    super(paramContext);
    this.mAccount = paramEsAccount;
    this.mPhotoId = paramLong;
    this.mPhotoUrl = paramString2;
    this.mOwnerId = paramString1;
    this.mDisableComments = paramBoolean;
  }

  public final Cursor loadInBackground()
  {
    ContentResolver localContentResolver = getContext().getContentResolver();
    Object localObject1;
    Object localObject2;
    Cursor localCursor1;
    EsMatrixCursor localEsMatrixCursor;
    int i;
    label96: Cursor localCursor2;
    if (this.mPhotoId != 0L)
    {
      localObject1 = localContentResolver.query(EsProvider.appendAccountParameter(ContentUris.withAppendedId(EsProvider.PHOTO_BY_PHOTO_ID_URI, this.mPhotoId), this.mAccount), PhotoQuery.PROJECTION, null, null, null);
      boolean bool = this.mDisableComments;
      localObject2 = null;
      localCursor1 = null;
      localEsMatrixCursor = null;
      if (!bool)
      {
        if ((localObject1 == null) || (!((Cursor)localObject1).moveToFirst()))
          break label585;
        i = ((Cursor)localObject1).getInt(12);
        ((Cursor)localObject1).moveToPosition(-1);
        Uri localUri = EsProvider.appendAccountParameter(ContentUris.withAppendedId(EsProvider.PHOTO_COMMENTS_BY_PHOTO_ID_URI, this.mPhotoId), this.mAccount);
        localCursor2 = localContentResolver.query(localUri, PhotoCommentCountQuery.PROJECTION, null, null, null);
        if ((localCursor2 == null) || (!localCursor2.moveToFirst()) || (localCursor2.getInt(2) <= 0))
          break label591;
        localObject2 = localCursor2;
        label159: localCursor1 = localContentResolver.query(localUri, PhotoCommentQuery.PROJECTION, null, null, "create_time");
        if (localCursor1 == null)
          break label612;
      }
    }
    label351: label612: for (int j = localCursor1.getCount(); ; j = 0)
    {
      localEsMatrixCursor = null;
      if (i != j)
      {
        localEsMatrixCursor = new EsMatrixCursor(PhotoCommentLoadingQuery.PROJECTION);
        ((EsMatrixCursor)localEsMatrixCursor).newRow().add(Integer.valueOf(2147483645)).add(Integer.valueOf(3));
      }
      MatrixCursor localMatrixCursor = new MatrixCursor(LeftoverQuery.PROJECTION);
      localMatrixCursor.newRow().add(Integer.valueOf(2147483644)).add(Integer.valueOf(5));
      MergeCursor localMergeCursor = new MergeCursor(new Cursor[] { localObject1, localObject2, localCursor1, localEsMatrixCursor, localMatrixCursor });
      return localMergeCursor;
      String[] arrayOfString = PhotoQuery.PROJECTION;
      localObject1 = new EsMatrixCursor(arrayOfString);
      DataVideo localDataVideo = MediaStoreUtils.toVideoData(getContext(), Uri.parse(this.mPhotoUrl));
      byte[] arrayOfByte;
      String str1;
      if (localDataVideo != null)
      {
        arrayOfByte = DataVideoJson.getInstance().toByteArray(localDataVideo);
        if (!TextUtils.isEmpty(this.mOwnerId))
          break label567;
        str1 = this.mAccount.getGaiaId();
        label370: if (str1 == null)
          break label576;
      }
      for (Integer localInteger = null; ; localInteger = Integer.valueOf(1))
      {
        String str2 = EsPeopleData.getUserName(getContext(), this.mAccount, str1);
        Object[] arrayOfObject = new Object[PhotoQuery.PROJECTION.length];
        arrayOfObject[0] = Long.valueOf(0L);
        arrayOfObject[1] = Integer.valueOf(0);
        arrayOfObject[2] = Long.valueOf(0L);
        arrayOfObject[3] = str1;
        arrayOfObject[4] = str2;
        arrayOfObject[6] = Long.valueOf(0L);
        arrayOfObject[7] = null;
        arrayOfObject[8] = null;
        arrayOfObject[9] = this.mPhotoUrl;
        arrayOfObject[10] = Integer.valueOf(0);
        arrayOfObject[11] = null;
        arrayOfObject[12] = Integer.valueOf(0);
        arrayOfObject[13] = null;
        arrayOfObject[14] = arrayOfByte;
        arrayOfObject[15] = Integer.valueOf(0);
        arrayOfObject[16] = Integer.valueOf(0);
        arrayOfObject[17] = "ORIGINAL";
        arrayOfObject[18] = localInteger;
        arrayOfObject[19] = null;
        arrayOfObject[20] = null;
        ((EsMatrixCursor)localObject1).addRow(arrayOfObject);
        break;
        arrayOfByte = null;
        break label351;
        str1 = this.mOwnerId;
        break label370;
      }
      i = 0;
      break label96;
      localObject2 = null;
      if (localCursor2 == null)
        break label159;
      localCursor2.close();
      localObject2 = null;
      break label159;
    }
  }

  protected final void onAbandon()
  {
    if (this.mObserverRegistered)
    {
      getContext().getContentResolver().unregisterContentObserver(this.mObserver);
      this.mObserverRegistered = false;
    }
  }

  protected final void onReset()
  {
    cancelLoad();
    super.onReset();
    onAbandon();
  }

  protected final void onStartLoading()
  {
    super.onStartLoading();
    if (!this.mObserverRegistered)
    {
      ContentResolver localContentResolver = getContext().getContentResolver();
      Uri localUri = ContentUris.withAppendedId(EsProvider.PHOTO_COMMENTS_BY_PHOTO_ID_URI, this.mPhotoId);
      localContentResolver.registerContentObserver(ContentUris.withAppendedId(EsProvider.PHOTO_BY_PHOTO_ID_URI, this.mPhotoId), false, this.mObserver);
      localContentResolver.registerContentObserver(localUri, false, this.mObserver);
      this.mObserverRegistered = true;
    }
  }

  protected final void onStopLoading()
  {
  }

  public static abstract interface LeftoverQuery
  {
    public static final String[] PROJECTION = { "_id", "5 AS row_type" };
  }

  public static abstract interface PhotoCommentCountQuery
  {
    public static final String[] PROJECTION = { "2147483646 AS _id", "4 AS row_type", "COUNT(*) AS _count" };
  }

  public static abstract interface PhotoCommentLoadingQuery
  {
    public static final String[] PROJECTION = { "_id", "4 AS row_type" };
  }

  public static abstract interface PhotoCommentQuery
  {
    public static final String[] PROJECTION = { "_id", "1 AS row_type", "comment_id", "author_id", "owner_name", "avatar", "create_time", "truncated", "content", "plusone_data" };
  }

  public static abstract interface PhotoQuery
  {
    public static final String[] PROJECTION = { "_id", "0 AS row_type", "photo_id", "owner_id", "owner_name", "owner_avatar_url", "album_id", "album_name", "album_stream", "url", "action_state", "timestamp", "comment_count", "pending_status", "video_data", "plusone_by_me", "plusone_count", "upload_status", "downloadable", "description", "plusone_data" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PhotoOneUpLoader
 * JD-Core Version:    0.6.2
 */