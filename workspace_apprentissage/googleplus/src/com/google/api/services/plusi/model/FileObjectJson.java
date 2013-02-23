package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FileObjectJson extends EsJson<FileObject>
{
  static final FileObjectJson INSTANCE = new FileObjectJson();

  private FileObjectJson()
  {
    super(FileObject.class, new Object[] { "description", "embedUrl", "faviconUrl", "name", "playerType", "proxiedFaviconUrl", ThumbnailJson.class, "proxiedThumbnail", "thumbnailUrl", "url" });
  }

  public static FileObjectJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FileObjectJson
 * JD-Core Version:    0.6.2
 */