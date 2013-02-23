package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DocumentObjectJson extends EsJson<DocumentObject>
{
  static final DocumentObjectJson INSTANCE = new DocumentObjectJson();

  private DocumentObjectJson()
  {
    super(DocumentObject.class, new Object[] { "description", "embedUrl", "faviconUrl", "name", "proxiedFaviconUrl", ThumbnailJson.class, "proxiedThumbnail", "thumbnailUrl", "url" });
  }

  public static DocumentObjectJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DocumentObjectJson
 * JD-Core Version:    0.6.2
 */