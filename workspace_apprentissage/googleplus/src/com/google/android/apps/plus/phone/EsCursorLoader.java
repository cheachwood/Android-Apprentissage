package com.google.android.apps.plus.phone;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import android.util.Log;

public class EsCursorLoader extends CursorLoader
{
  private boolean mLoaderException;
  private final Uri mNotificationUri;
  private final Loader<Cursor>.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver(this);
  private boolean mObserverRegistered;

  public EsCursorLoader(Context paramContext)
  {
    this(paramContext, null);
  }

  public EsCursorLoader(Context paramContext, Uri paramUri)
  {
    super(paramContext);
    this.mNotificationUri = paramUri;
  }

  public EsCursorLoader(Context paramContext, Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    this(paramContext, paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2, null);
  }

  public EsCursorLoader(Context paramContext, Uri paramUri1, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2, Uri paramUri2)
  {
    super(paramContext, paramUri1, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2);
    this.mNotificationUri = paramUri2;
  }

  public final void deliverResult(Cursor paramCursor)
  {
    int i;
    if ((paramCursor != null) && (paramCursor.isClosed()))
    {
      i = 1;
      if ((this.mLoaderException) || (i != 0))
        break label37;
      super.deliverResult(paramCursor);
    }
    while (true)
    {
      return;
      i = 0;
      break;
      label37: if (i != 0)
        Log.w("EsCursorLoader", "Cursor was delivered closed.  This should never happen");
    }
  }

  public Cursor esLoadInBackground()
  {
    return super.loadInBackground();
  }

  public final Cursor loadInBackground()
  {
    try
    {
      Cursor localCursor2 = esLoadInBackground();
      localCursor1 = localCursor2;
      return localCursor1;
    }
    catch (Throwable localThrowable)
    {
      while (true)
      {
        Log.w("EsCursorLoader", "loadInBackground failed", localThrowable);
        this.mLoaderException = true;
        Cursor localCursor1 = null;
      }
    }
  }

  protected void onAbandon()
  {
    if (this.mObserverRegistered)
    {
      getContext().getContentResolver().unregisterContentObserver(this.mObserver);
      this.mObserverRegistered = false;
    }
  }

  protected void onReset()
  {
    cancelLoad();
    super.onReset();
    onAbandon();
  }

  protected void onStartLoading()
  {
    super.onStartLoading();
    if ((!this.mObserverRegistered) && (this.mNotificationUri != null))
    {
      getContext().getContentResolver().registerContentObserver(this.mNotificationUri, false, this.mObserver);
      this.mObserverRegistered = true;
    }
  }

  protected final void onStopLoading()
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EsCursorLoader
 * JD-Core Version:    0.6.2
 */