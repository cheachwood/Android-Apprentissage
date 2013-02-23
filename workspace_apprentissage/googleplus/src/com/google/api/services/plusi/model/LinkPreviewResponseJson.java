package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LinkPreviewResponseJson extends EsJson<LinkPreviewResponse>
{
  static final LinkPreviewResponseJson INSTANCE = new LinkPreviewResponseJson();

  private LinkPreviewResponseJson()
  {
    super(LinkPreviewResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "blackboxPreviewData", EmbedClientItemJson.class, "embedItem", "image", MediaLayoutJson.class, "mediaLayout", "selectedImage", "succeeded" });
  }

  public static LinkPreviewResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LinkPreviewResponseJson
 * JD-Core Version:    0.6.2
 */