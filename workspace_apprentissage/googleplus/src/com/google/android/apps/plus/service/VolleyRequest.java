package com.google.android.apps.plus.service;

import android.net.Uri;
import android.util.Log;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.apps.plus.phone.EsApplication;
import java.util.Map;

public abstract class VolleyRequest extends Request<byte[]>
{
  private static final int MAX_GIF_DOWNLOAD;
  private Uri mContentUri;

  static
  {
    if (EsApplication.sMemoryClass >= 48);
    for (MAX_GIF_DOWNLOAD = 8388608; ; MAX_GIF_DOWNLOAD = 2097152)
      return;
  }

  public VolleyRequest(String paramString, Uri paramUri)
  {
    super(null, null);
    this.mContentUri = paramUri;
  }

  public abstract void deliverResponse(byte[] paramArrayOfByte);

  public final Uri getContentUri()
  {
    return this.mContentUri;
  }

  protected final Response<byte[]> parseNetworkResponse(NetworkResponse paramNetworkResponse)
  {
    int i = -1;
    String str;
    if (paramNetworkResponse.headers.containsKey("Content-Type"))
    {
      str = (String)paramNetworkResponse.headers.get("Content-Type");
      if ((str != null) && (str.equals("image/gif")))
        i = MAX_GIF_DOWNLOAD;
      if (Log.isLoggable("VolleyRequest", 3))
        Log.d("VolleyRequest", "Download: " + paramNetworkResponse.data.length + ", allowed: " + i + ", type: " + str);
      if ((i <= 0) || (paramNetworkResponse.data.length <= i))
        break label204;
    }
    label204: for (Response localResponse = Response.error(new VolleyError("Download is too large: " + paramNetworkResponse.data.length + ", allowed: " + i + ", type: " + str)); ; localResponse = Response.success(paramNetworkResponse.data, null))
    {
      return localResponse;
      if (paramNetworkResponse.headers.containsKey("content-type"))
      {
        str = (String)paramNetworkResponse.headers.get("content-type");
        break;
      }
      str = null;
      break;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.VolleyRequest
 * JD-Core Version:    0.6.2
 */