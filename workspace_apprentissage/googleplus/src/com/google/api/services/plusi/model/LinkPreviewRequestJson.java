package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LinkPreviewRequestJson extends EsJson<LinkPreviewRequest>
{
  static final LinkPreviewRequestJson INSTANCE = new LinkPreviewRequestJson();

  private LinkPreviewRequestJson()
  {
    super(LinkPreviewRequest.class, new Object[] { "callToActionDeepLinkId", "callToActionLabel", "callToActionLabelDeprecated", "callToActionUrl", ApiaryFieldsJson.class, "commonFields", "content", "deepLinkId", ClientEmbedOptionsJson.class, "embedOptions", "enableTracing", "fallbackToUrl", "isInteractivePost", "useBlackboxPreviewData", "useSmallPreviews" });
  }

  public static LinkPreviewRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LinkPreviewRequestJson
 * JD-Core Version:    0.6.2
 */