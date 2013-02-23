package com.google.android.apps.plus.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MatrixCursor.RowBuilder;
import android.database.MergeCursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import android.util.Log;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;

public final class StreamOneUpLoader extends CursorLoader
{
  private final Uri mActivityUri;
  private final Uri mCommentsUri;
  private boolean mNeedToRefreshComments;
  private final Loader<Cursor>.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver(this);
  private boolean mObserverRegistered;

  public StreamOneUpLoader(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    super(paramContext);
    this.mActivityUri = EsProvider.buildActivityViewUri(paramEsAccount, paramString);
    Uri.Builder localBuilder = EsProvider.COMMENTS_VIEW_BY_ACTIVITY_ID_URI.buildUpon();
    localBuilder.appendPath(paramString);
    EsProvider.appendAccountParameter(localBuilder, paramEsAccount);
    this.mCommentsUri = localBuilder.build();
  }

  public final Cursor loadInBackground()
  {
    Object localObject;
    try
    {
      ContentResolver localContentResolver = getContext().getContentResolver();
      localCursor1 = localContentResolver.query(this.mActivityUri, StreamOneUpAdapter.ActivityQuery.PROJECTION, null, null, null);
      if ((localCursor1 != null) && (localCursor1.getCount() != 0))
      {
        Cursor localCursor2 = localContentResolver.query(this.mCommentsUri, StreamOneUpAdapter.CommentCountQuery.PROJECTION, null, null, null);
        Cursor localCursor3;
        Cursor localCursor4;
        if ((localCursor2 != null) && (localCursor2.moveToFirst()) && (localCursor2.getInt(2) > 0))
        {
          localCursor3 = localCursor2;
          localCursor4 = localContentResolver.query(this.mCommentsUri, StreamOneUpAdapter.CommentQuery.PROJECTION, null, null, "created ASC");
          if ((!localCursor1.moveToFirst()) || (localCursor4.getCount() == localCursor1.getInt(7)))
            break label271;
        }
        label271: for (boolean bool = true; ; bool = false)
        {
          this.mNeedToRefreshComments = bool;
          MatrixCursor localMatrixCursor1 = new MatrixCursor(StreamOneUpAdapter.LoadingQuery.PROJECTION);
          localMatrixCursor1.newRow().add(Integer.valueOf(2147483644)).add(Integer.valueOf(3));
          MatrixCursor localMatrixCursor2 = new MatrixCursor(StreamOneUpAdapter.LeftoverQuery.PROJECTION);
          localMatrixCursor2.newRow().add(Integer.valueOf(2147483645)).add(Integer.valueOf(4));
          localObject = new MergeCursor(new Cursor[] { localCursor1, localCursor3, localCursor4, localMatrixCursor1, localMatrixCursor2 });
          break label295;
          if (localCursor2 != null)
            localCursor2.close();
          localCursor3 = null;
          break;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      Cursor localCursor1;
      Log.w("StreamOnUpLoader", "loadInBackground failed", localThrowable);
      localObject = null;
      break label295;
      localObject = localCursor1;
    }
    label295: return localObject;
  }

  public final boolean needToRefreshComments()
  {
    return this.mNeedToRefreshComments;
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
      localContentResolver.registerContentObserver(this.mActivityUri, false, this.mObserver);
      localContentResolver.registerContentObserver(this.mCommentsUri, false, this.mObserver);
      this.mObserverRegistered = true;
    }
  }

  protected final void onStopLoading()
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.StreamOneUpLoader
 * JD-Core Version:    0.6.2
 */