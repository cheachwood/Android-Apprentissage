package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UploadedMediaJson extends EsJson<UploadedMedia>
{
  static final UploadedMediaJson INSTANCE = new UploadedMediaJson();

  private UploadedMediaJson()
  {
    super(UploadedMedia.class, new Object[] { AuthorProtoJson.class, "author", MediaProtoJson.class, "userMedia" });
  }

  public static UploadedMediaJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UploadedMediaJson
 * JD-Core Version:    0.6.2
 */