package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class HangoutStartContext extends GenericJson
{
  public String activityId;
  public String appData;
  public String appId;
  public String callback;
  public String circleId;
  public String contextId;
  public String conversationId;
  public Boolean create;
  public Boolean deprecatedCallback;
  public ExternalEntityKey externalKey;
  public String externalUserId;
  public Boolean flippy;
  public String hangoutId;
  public String hangoutType;
  public HangoutStartContextInvitation invitation;
  public String invitedOid;
  public List<HangoutStartContextInvitee> invitee;
  public HangoutStartContextLatencyMarks latencyMarks;
  public String nick;
  public List<String> profileId;
  public String referringUrl;
  public Boolean shouldAutoInvite;
  public Boolean shouldMuteVideo;
  public String source;
  public String squareId;
  public String topic;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutStartContext
 * JD-Core Version:    0.6.2
 */