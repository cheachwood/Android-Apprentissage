package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ProfileCompletionStatsFieldJson extends EsJson<ProfileCompletionStatsField>
{
  static final ProfileCompletionStatsFieldJson INSTANCE = new ProfileCompletionStatsFieldJson();

  private ProfileCompletionStatsFieldJson()
  {
    super(ProfileCompletionStatsField.class, new Object[] { MetadataJson.class, "metadata", "percentComplete", "type" });
  }

  public static ProfileCompletionStatsFieldJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProfileCompletionStatsFieldJson
 * JD-Core Version:    0.6.2
 */