package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DownloadPhotoOperation extends HttpOperation
{
  private byte[] mBytes;

  public DownloadPhotoOperation(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    this(paramContext, "GET", paramString, paramEsAccount, new ByteArrayOutputStream(15000), null, null);
  }

  DownloadPhotoOperation(Context paramContext, String paramString1, String paramString2, EsAccount paramEsAccount, OutputStream paramOutputStream, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramString1, paramString2, paramEsAccount, paramOutputStream, paramIntent, paramOperationListener);
  }

  public final byte[] getBytes()
  {
    return this.mBytes;
  }

  protected final void onHttpHandleContentFromStream$6508b088(InputStream paramInputStream)
    throws IOException
  {
    onStartResultProcessing();
    ByteArrayOutputStream localByteArrayOutputStream;
    if ((getOutputStream() instanceof ByteArrayOutputStream))
      localByteArrayOutputStream = (ByteArrayOutputStream)getOutputStream();
    try
    {
      this.mBytes = localByteArrayOutputStream.toByteArray();
      return;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      Log.w("HttpTransaction", "DownloadPhotoOperation OutOfMemoryError on photo bytes: " + localByteArrayOutputStream.size(), localOutOfMemoryError);
    }
    throw new ProtocolException("Cannot handle downloaded photo");
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.DownloadPhotoOperation
 * JD-Core Version:    0.6.2
 */