package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SharePhotosToEventRequestJson extends EsJson<SharePhotosToEventRequest>
{
  static final SharePhotosToEventRequestJson INSTANCE = new SharePhotosToEventRequestJson();

  private SharePhotosToEventRequestJson()
  {
    super(SharePhotosToEventRequest.class, arrayOfObject);
  }

  public static SharePhotosToEventRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SharePhotosToEventRequestJson
 * JD-Core Version:    0.6.2
 */