package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HangoutStartContextJson extends EsJson<HangoutStartContext>
{
  static final HangoutStartContextJson INSTANCE = new HangoutStartContextJson();

  private HangoutStartContextJson()
  {
    super(HangoutStartContext.class, new Object[] { "activityId", "appData", "appId", "callback", "circleId", "contextId", "conversationId", "create", "deprecatedCallback", ExternalEntityKeyJson.class, "externalKey", "externalUserId", "flippy", "hangoutId", "hangoutType", HangoutStartContextInvitationJson.class, "invitation", "invitedOid", HangoutStartContextInviteeJson.class, "invitee", HangoutStartContextLatencyMarksJson.class, "latencyMarks", "nick", "profileId", "referringUrl", "shouldAutoInvite", "shouldMuteVideo", "source", "squareId", "topic" });
  }

  public static HangoutStartContextJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutStartContextJson
 * JD-Core Version:    0.6.2
 */