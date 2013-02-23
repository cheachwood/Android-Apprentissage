package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UploadMediaRequestJson extends EsJson<UploadMediaRequest>
{
  static final UploadMediaRequestJson INSTANCE = new UploadMediaRequestJson();

  private UploadMediaRequestJson()
  {
    super(UploadMediaRequest.class, new Object[] { "albumId", "albumLabel", "albumTitle", "autoResize", "clientAssignedId", ApiaryFieldsJson.class, "commonFields", "description", "displayName", "enableTracing", "eventId", LocalDataJson.class, "localData", "offset", "ownerId", ScottyMediaJson.class, "scottyMedia", "setProfilePhoto" });
  }

  public static UploadMediaRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UploadMediaRequestJson
 * JD-Core Version:    0.6.2
 */