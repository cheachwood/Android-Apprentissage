package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetPhotoRequestJson extends EsJson<GetPhotoRequest>
{
  static final GetPhotoRequestJson INSTANCE = new GetPhotoRequestJson();

  private GetPhotoRequestJson()
  {
    super(GetPhotoRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "ownerId", "photoId", RequestsPhotoOptionsJson.class, "photoOptions" });
  }

  public static GetPhotoRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetPhotoRequestJson
 * JD-Core Version:    0.6.2
 */