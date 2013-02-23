package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class DownloadImageOperationNoCache extends HttpOperation
{
  private Bitmap mBitmap;

  public DownloadImageOperationNoCache(Context paramContext, EsAccount paramEsAccount, String paramString, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, "GET", paramString, paramEsAccount, new ByteArrayOutputStream(15000), null, null);
  }

  public final Bitmap getBitmap()
  {
    return this.mBitmap;
  }

  protected final void onHttpHandleContentFromStream$6508b088(InputStream paramInputStream)
    throws IOException
  {
    onStartResultProcessing();
    ByteArrayOutputStream localByteArrayOutputStream = (ByteArrayOutputStream)getOutputStream();
    try
    {
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      this.mBitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
      return;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      Log.w("HttpTransaction", "DownloadImageOperation OutOfMemoryError on image bytes: " + localByteArrayOutputStream.size(), localOutOfMemoryError);
    }
    throw new ProtocolException("Cannot handle downloaded image");
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.DownloadImageOperationNoCache
 * JD-Core Version:    0.6.2
 */