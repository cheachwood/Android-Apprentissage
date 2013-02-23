package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AppInviteJson extends EsJson<AppInvite>
{
  static final AppInviteJson INSTANCE = new AppInviteJson();

  private AppInviteJson()
  {
    super(AppInvite.class, new Object[] { EmbedClientItemJson.class, "about", DeepLinkJson.class, "callToAction", "deleted9", "description", "imageUrl", "isPreview", "name", "proxiedFaviconUrl", ThumbnailJson.class, "proxiedImage", "text", "url" });
  }

  public static AppInviteJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AppInviteJson
 * JD-Core Version:    0.6.2
 */