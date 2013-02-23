package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReadOptionsJson extends EsJson<ReadOptions>
{
  static final ReadOptionsJson INSTANCE = new ReadOptionsJson();

  private ReadOptionsJson()
  {
    super(ReadOptions.class, new Object[] { ReadOptionsCommentsOptionsJson.class, "commentsOptions", ReadOptionsUpdateOptionsJson.class, "eventUpdateOptions", ReadOptionsFramesOptionsJson.class, "framesOptions", "includePhotoContributors", "includePlusEvent", ReadOptionsInviteeOptionsJson.class, "inviteeOptions", "optionsCriteria", ReadOptionsPhotosOptionsJson.class, "photosOptions", "resolvePersons", "responseFormat" });
  }

  public static ReadOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReadOptionsJson
 * JD-Core Version:    0.6.2
 */