package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UserJson extends EsJson<User>
{
  static final UserJson INSTANCE = new UserJson();

  private UserJson()
  {
    super(User.class, new Object[] { BirthdayFieldJson.class, "birthday", StringFieldJson.class, "braggingRights", DeviceLocationsJson.class, "deviceLocations", EducationsJson.class, "educations", EmploymentsJson.class, "employments", "enableLocal", GenderJson.class, "gender", LocalUserActivityJson.class, "localUserActivity", LocationsJson.class, "locations", NameJson.class, "name", NameDisplayOptionsJson.class, "nameDisplayOptions", StringFieldJson.class, "occupation", OtherNamesJson.class, "otherNames", "profilePageCrawlable", "publicUsername", RelationshipInterestsJson.class, "relationshipInterests", RelationshipStatusJson.class, "relationshipStatus" });
  }

  public static UserJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UserJson
 * JD-Core Version:    0.6.2
 */