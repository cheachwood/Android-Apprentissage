package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RenderContextJson extends EsJson<RenderContext>
{
  static final RenderContextJson INSTANCE = new RenderContextJson();

  private RenderContextJson()
  {
    super(RenderContext.class, new Object[] { "deprecatedIsViewerLoggedIn", "disableExplanation", "disableHeader", "disableReshare", "disableVisibilityLink", "isSummaryFormat", "location", "moderationViewType", "renderKey", "showMuted", "showSectionHeaders", "showStreamModerationControls", "showUnreadIndicator", "streamId", "viewerIsModerator" });
  }

  public static RenderContextJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RenderContextJson
 * JD-Core Version:    0.6.2
 */