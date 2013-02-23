package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class Profile extends GenericJson
{
  public StringField aboutMeHtml;
  public BirthdayField birthday;
  public StringField braggingRights;
  public String canonicalProfileUrl;
  public Classification classification;
  public ContactMeField contactMeChat;
  public ContactMeField contactMeEmail;
  public ContactMeField contactMeHangout;
  public ContactMeField contactMePhone;
  public ContactMeField contactMeShare;
  public Links contributorToLinks;
  public DeviceLocations deviceLocations;
  public Educations educations;
  public Employments employments;
  public BooleanField enableWallRead;
  public BooleanField enableWallWrite;
  public EntityInfo entityInfo;
  public Gender gender;
  public StringField googleAnalyticsWebPropertyId;
  public Boolean googleMeEnabled;
  public ContactInfo homeContact;
  public Boolean inAbuseiamQueue;
  public IntField incomingConnections;
  public String legacyPublicUsername;
  public Links links;
  public LocalEntityInfo localInfo;
  public LocalUserActivity localUserActivity;
  public String locationMapUrl;
  public Locations locations;
  public Links meLinks;
  public Name name;
  public NameDisplayOptions nameDisplayOptions;
  public NickName nickName;
  public String obfuscatedGaiaId;
  public StringField occupation;
  public Boolean optedIntoLocal;
  public Links otherLinks;
  public OtherNames otherNames;
  public Boolean outOfBoxDismissed;
  public Boolean photoIsSilhouette;
  public String photoUrl;
  public PlusPageInfo plusPageInfo;
  public ProfilesLink primaryLink;
  public Boolean profileBirthdayMissing;
  public ProfileCompletionStats profileCompletionStats;
  public Boolean profilePageCrawlable;
  public ProfileState profileState;
  public String profileType;
  public Boolean profileWasAgeRestricted;
  public String publicUsername;
  public RelationshipInterests relationshipInterests;
  public RelationshipStatus relationshipStatus;
  public SharingRosterData rosterData;
  public ScrapbookInfo scrapbookInfo;
  public SegmentationInfo segmentationInfo;
  public Boolean showFollowerCounts;
  public TabVisibility tabVisibility;
  public StringField tagLine;
  public String validAgeRestrictions;
  public VerifiedDomains verifiedDomains;
  public ContactInfo workContact;
  public YouTubeChannelInfo youtubeChannelInfo;
  public Links youtubeLinks;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.Profile
 * JD-Core Version:    0.6.2
 */