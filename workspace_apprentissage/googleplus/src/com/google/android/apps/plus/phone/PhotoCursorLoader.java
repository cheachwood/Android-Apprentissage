package com.google.android.apps.plus.phone;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.api.PhotosInAlbumOperation;
import com.google.android.apps.plus.api.PhotosOfUserOperation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.fragments.HostedEventFragment.DetailsQuery;
import com.google.android.apps.plus.network.HttpOperation;

public abstract class PhotoCursorLoader extends EsCursorLoader
  implements Pageable
{
  private final EsAccount mAccount;
  private final String mAlbumId;
  private final String mAuthkey;
  private int mCircleOffset;
  private boolean mDataSourceIsLoading;
  private final String mEventId;
  private String mEventResumeToken;
  Handler mHandler = new Handler(Looper.getMainLooper());
  private boolean mHasMore;
  private final int mInitialPageCount;
  private boolean mIsLoadingMore;
  private int mLoadLimit = 16;
  private boolean mNetworkRequestMade;
  private final Loader<Cursor>.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver(this);
  private boolean mObserverRegistered;
  private final String mOwnerGaiaId;
  private boolean mPageable;
  Pageable.LoadingListener mPageableLoadingListener;
  private final boolean mPaging;
  private final String mPhotoOfUserGaiaId;
  private final String mPhotoUrl;
  private final String mStreamId;

  public PhotoCursorLoader(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean, int paramInt, String paramString7)
  {
    super(paramContext, getNotificationUri(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6));
    this.mAccount = paramEsAccount;
    this.mAuthkey = paramString7;
    this.mOwnerGaiaId = paramString1;
    this.mAlbumId = paramString2;
    this.mEventId = paramString5;
    this.mPhotoOfUserGaiaId = paramString3;
    this.mStreamId = paramString4;
    this.mPhotoUrl = paramString6;
    this.mPaging = paramBoolean;
    this.mPageable = paramBoolean;
    this.mInitialPageCount = paramInt;
    if ((this.mPageable) && (paramInt != i))
      i = paramInt * 16;
    this.mLoadLimit = i;
  }

  private void doNetworkRequest()
  {
    if (this.mNetworkRequestMade)
      return;
    this.mNetworkRequestMade = true;
    invokeLoadingListener(true);
    if (this.mEventId != null)
    {
      EsEventData.readEventFromServer(getContext(), this.mAccount, this.mEventId, null, null, null, null, false, true, null, null);
      updateEventResumeToken();
      this.mNetworkRequestMade = false;
    }
    while (true)
    {
      invokeLoadingListener(false);
      break;
      Object localObject;
      if ((this.mStreamId != null) && (!"profiles_scrapbook".equals(this.mStreamId)))
        localObject = new PhotosInAlbumOperation(getContext(), this.mAccount, this.mStreamId, this.mOwnerGaiaId, null, null, this.mAuthkey);
      while (true)
      {
        if (localObject == null)
          break label202;
        ((HttpOperation)localObject).start();
        break;
        if (this.mPhotoOfUserGaiaId != null)
        {
          localObject = new PhotosOfUserOperation(getContext(), this.mAccount, this.mPhotoOfUserGaiaId, null, null);
        }
        else
        {
          String str = this.mAlbumId;
          localObject = null;
          if (str != null)
            localObject = new PhotosInAlbumOperation(getContext(), this.mAccount, this.mAlbumId, this.mOwnerGaiaId, null, null, this.mAuthkey);
        }
      }
      label202: Log.e("PhotoCursorLoader", "No valid IDs to load photos for");
    }
  }

  private static Uri getNotificationUri(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    Uri localUri;
    if ((paramString4 != null) && (!"profiles_scrapbook".equals(paramString4)))
      if (paramString1 == null)
      {
        Log.w("PhotoCursorLoader", "Viewing stream photos w/o a valid owner GAIA ID");
        localUri = null;
      }
    while (true)
    {
      return localUri;
      localUri = Uri.withAppendedPath(EsProvider.PHOTO_BY_STREAM_ID_AND_OWNER_ID_URI.buildUpon().appendPath(paramString4).build(), paramString1);
      continue;
      if (paramString5 != null)
      {
        localUri = Uri.withAppendedPath(EsProvider.PHOTO_BY_EVENT_ID_URI, paramString5);
      }
      else if (paramString3 != null)
      {
        localUri = Uri.withAppendedPath(EsProvider.PHOTO_OF_USER_ID_URI, paramString3);
      }
      else
      {
        if (paramString2 == null)
          break;
        if (paramString1 == null)
        {
          Log.w("PhotoCursorLoader", "Viewing album photos w/o a valid owner GAIA ID");
          break;
        }
        localUri = Uri.withAppendedPath(EsProvider.PHOTO_BY_ALBUM_URI, paramString2);
      }
    }
  }

  private void invokeLoadingListener(final boolean paramBoolean)
  {
    this.mHandler.post(new Runnable()
    {
      public final void run()
      {
        PhotoCursorLoader.access$002(PhotoCursorLoader.this, paramBoolean);
        if (PhotoCursorLoader.this.mPageableLoadingListener != null)
          PhotoCursorLoader.this.mPageableLoadingListener.onDataSourceLoading(paramBoolean);
      }
    });
  }

  private void updateEventResumeToken()
  {
    Cursor localCursor = EsEventData.getEvent(getContext(), this.mAccount, this.mEventId, HostedEventFragment.DetailsQuery.PROJECTION);
    if ((localCursor != null) && (localCursor.moveToFirst()))
      this.mEventResumeToken = localCursor.getString(3);
  }

  public Cursor esLoadInBackground()
  {
    boolean bool1 = true;
    Object localObject;
    if (getUri() == null)
    {
      Log.w("PhotoCursorLoader", "load NULL URI; return empty cursor");
      localObject = new EsMatrixCursor(getProjection());
      return localObject;
    }
    label61: int j;
    label83: String str1;
    String str3;
    label118: String str2;
    label148: int k;
    label192: int m;
    label201: boolean bool2;
    label226: int n;
    label287: int i1;
    label296: boolean bool3;
    if (this.mEventId != null)
    {
      if ((this.mIsLoadingMore) && (!TextUtils.isEmpty(this.mEventResumeToken)))
        doNetworkRequest();
    }
    else
    {
      int i = this.mLoadLimit;
      if ((!this.mPageable) || (this.mLoadLimit == -1))
        break label371;
      j = bool1;
      str1 = getSortOrder();
      if (str1 == null)
      {
        if (TextUtils.equals(this.mStreamId, "posts"))
          break label391;
        if (this.mAlbumId == null)
          break label376;
        str3 = "pending_status DESC,timestamp ASC";
        setSortOrder(str3);
      }
      if (j != 0)
      {
        str2 = getSortOrder();
        StringBuilder localStringBuilder = new StringBuilder();
        if (str2 == null)
          break label399;
        setSortOrder(str2 + " LIMIT 0, " + i);
      }
      localObject = super.esLoadInBackground();
      if (localObject == null)
        break label407;
      k = ((Cursor)localObject).getCount();
      if (k != i)
        break label413;
      m = bool1;
      if ((!this.mPageable) || ((m == 0) && (TextUtils.isEmpty(this.mEventResumeToken))))
        break label419;
      bool2 = bool1;
      this.mHasMore = bool2;
      this.mIsLoadingMore = false;
      if (k == 0)
      {
        ((Cursor)localObject).close();
        localObject = null;
      }
      if (localObject == null)
      {
        this.mCircleOffset = k;
        doNetworkRequest();
        localObject = super.esLoadInBackground();
        if (localObject == null)
          break label425;
        n = ((Cursor)localObject).getCount();
        if (n != i)
          break label431;
        i1 = bool1;
        if ((n == this.mCircleOffset) && (TextUtils.isEmpty(this.mEventResumeToken)))
          break label437;
        bool3 = bool1;
        label318: this.mPageable = bool3;
        if ((!this.mPageable) || ((i1 == 0) && (TextUtils.isEmpty(this.mEventResumeToken))))
          break label443;
      }
    }
    while (true)
    {
      this.mHasMore = bool1;
      if (j == 0)
        break;
      setSortOrder(str1);
      break;
      updateEventResumeToken();
      break label61;
      label371: j = 0;
      break label83;
      label376: if (this.mEventId != null)
      {
        str3 = "timestamp DESC";
        break label118;
      }
      label391: str3 = "pending_status DESC,timestamp DESC";
      break label118;
      label399: str2 = "";
      break label148;
      label407: k = 0;
      break label192;
      label413: m = 0;
      break label201;
      label419: bool2 = false;
      break label226;
      label425: n = 0;
      break label287;
      label431: i1 = 0;
      break label296;
      label437: bool3 = false;
      break label318;
      label443: bool1 = false;
    }
  }

  public final int getCurrentPage()
  {
    int i = -1;
    if ((this.mPageable) && (this.mLoadLimit != i))
      i = this.mLoadLimit / 16;
    return i;
  }

  final Uri getLoaderUri()
  {
    Uri localUri1 = getNotificationUri(this.mOwnerGaiaId, this.mAlbumId, this.mPhotoOfUserGaiaId, this.mStreamId, this.mEventId, this.mPhotoUrl);
    if (localUri1 != null);
    for (Uri localUri2 = EsProvider.appendAccountParameter(localUri1, this.mAccount); ; localUri2 = null)
      return localUri2;
  }

  public final boolean hasMore()
  {
    if ((this.mPageable) && (this.mHasMore));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isDataSourceLoading()
  {
    return this.mDataSourceIsLoading;
  }

  public final void loadMore()
  {
    if ((this.mPageable) && (this.mHasMore))
    {
      this.mLoadLimit = (48 + this.mLoadLimit);
      this.mIsLoadingMore = true;
      onContentChanged();
    }
  }

  protected final void onAbandon()
  {
    if (this.mObserverRegistered)
    {
      getContext().getContentResolver().unregisterContentObserver(this.mObserver);
      this.mObserverRegistered = false;
    }
    super.onAbandon();
  }

  protected final void onStartLoading()
  {
    if (!this.mObserverRegistered)
    {
      getContext().getContentResolver().registerContentObserver(EsProvider.PHOTO_URI, false, this.mObserver);
      this.mObserverRegistered = true;
    }
    super.onStartLoading();
  }

  public final void setLoadingListener(Pageable.LoadingListener paramLoadingListener)
  {
    this.mPageableLoadingListener = paramLoadingListener;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PhotoCursorLoader
 * JD-Core Version:    0.6.2
 */