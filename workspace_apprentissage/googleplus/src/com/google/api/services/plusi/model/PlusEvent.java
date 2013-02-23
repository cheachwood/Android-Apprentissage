package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PlusEvent extends GenericJson
{
  public String abuseStatus;
  public String authKey;
  public EmbedsPerson creator;
  public String creatorObfuscatedId;
  public EventCategory deprecated11;
  public Boolean deprecated29;
  public String deprecated6;
  public List<Invitee> deprecated9;
  public String description;
  public PlusEventDisplayContent displayContent;
  public String endDate;
  public EventTime endTime;
  public EventOptions eventOptions;
  public PlusEventFeatureFlags featureFlags;
  public List<PlusPhoto> featuredPlusPhoto;
  public HangoutInfo hangoutInfo;
  public String id;
  public List<InviteeSummary> inviteeSummary;
  public Boolean isBroadcastView;
  public Boolean isPublic;
  public Place location;
  public String name;
  public List<Invitee> photoContributor;
  public Integer photoCount;
  public String startDate;
  public EventTime startTime;
  public Theme theme;
  public ThemeSpecification themeSpecification;
  public EventThirdPartyInfo thirdPartyInfo;
  public String url;
  public Invitee viewerInfo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusEvent
 * JD-Core Version:    0.6.2
 */