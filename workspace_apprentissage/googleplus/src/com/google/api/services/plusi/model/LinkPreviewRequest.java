package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class LinkPreviewRequest extends GenericJson
{
  public String callToActionDeepLinkId;
  public String callToActionLabel;
  public String callToActionLabelDeprecated;
  public String callToActionUrl;
  public ApiaryFields commonFields;
  public String content;
  public String deepLinkId;
  public ClientEmbedOptions embedOptions;
  public Boolean enableTracing;
  public Boolean fallbackToUrl;
  public Boolean isInteractivePost;
  public Boolean useBlackboxPreviewData;
  public Boolean useSmallPreviews;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LinkPreviewRequest
 * JD-Core Version:    0.6.2
 */