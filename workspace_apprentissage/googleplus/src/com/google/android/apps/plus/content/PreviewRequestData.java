package com.google.android.apps.plus.content;

import android.net.Uri;
import android.util.Log;
import com.google.android.apps.plus.api.CallToActionData;
import com.google.android.apps.plus.util.EsLog;
import org.json.JSONArray;
import org.json.JSONException;

public final class PreviewRequestData
{
  public final CallToActionData callToAction;
  public final Uri uri;

  public PreviewRequestData(String paramString, CallToActionData paramCallToActionData)
  {
    this.uri = Uri.parse(paramString);
    this.callToAction = paramCallToActionData;
  }

  public static PreviewRequestData fromSelectionArg(String paramString)
  {
    label46: label57: PreviewRequestData localPreviewRequestData;
    try
    {
      JSONArray localJSONArray = new JSONArray(paramString);
      int i = localJSONArray.length();
      CallToActionData localCallToActionData = null;
      String str2;
      String str3;
      String str4;
      if (i > 1)
      {
        if (localJSONArray.isNull(1))
        {
          str2 = null;
          if (!localJSONArray.isNull(2))
            break label108;
          str3 = null;
          if (!localJSONArray.isNull(3))
            break label118;
          str4 = null;
          localCallToActionData = new CallToActionData(str2, str3, str4);
        }
      }
      else
        if (!localJSONArray.isNull(0))
          break label128;
      label108: label118: String str1;
      for (Object localObject = null; ; localObject = str1)
      {
        localPreviewRequestData = new PreviewRequestData((String)localObject, localCallToActionData);
        break label162;
        str2 = localJSONArray.getString(1);
        break;
        str3 = localJSONArray.getString(2);
        break label46;
        str4 = localJSONArray.getString(3);
        break label57;
        label128: str1 = localJSONArray.getString(0);
      }
    }
    catch (JSONException localJSONException)
    {
      if (EsLog.isLoggable("PreviewRequestData", 5))
        Log.w("PreviewRequestData", "Failed to deserialize PreviewRequestData JSON.");
      localPreviewRequestData = null;
    }
    label162: return localPreviewRequestData;
  }

  public final boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject);
    while (true)
    {
      return bool;
      if (!(paramObject instanceof PreviewRequestData))
      {
        bool = false;
      }
      else
      {
        PreviewRequestData localPreviewRequestData = (PreviewRequestData)paramObject;
        if (((this.uri != localPreviewRequestData.uri) && ((this.uri == null) || (!this.uri.equals(localPreviewRequestData.uri)))) || ((this.callToAction != localPreviewRequestData.callToAction) && ((this.callToAction == null) || (!this.callToAction.equals(localPreviewRequestData.callToAction)))))
          bool = false;
      }
    }
  }

  public final int hashCode()
  {
    int i;
    int j;
    int k;
    if (this.uri == null)
    {
      i = 0;
      j = 31 * (i + 527);
      CallToActionData localCallToActionData = this.callToAction;
      k = 0;
      if (localCallToActionData != null)
        break label46;
    }
    while (true)
    {
      return j + k;
      i = this.uri.hashCode();
      break;
      label46: k = this.callToAction.hashCode();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.PreviewRequestData
 * JD-Core Version:    0.6.2
 */