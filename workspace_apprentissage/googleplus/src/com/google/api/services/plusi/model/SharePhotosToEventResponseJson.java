package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SharePhotosToEventResponseJson extends EsJson<SharePhotosToEventResponse>
{
  static final SharePhotosToEventResponseJson INSTANCE = new SharePhotosToEventResponseJson();

  private SharePhotosToEventResponseJson()
  {
    super(SharePhotosToEventResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", DataPhotoJson.class, "photo" });
  }

  public static SharePhotosToEventResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SharePhotosToEventResponseJson
 * JD-Core Version:    0.6.2
 */