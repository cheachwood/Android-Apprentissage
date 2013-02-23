package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FieldRequestOptionsJson extends EsJson<FieldRequestOptions>
{
  static final FieldRequestOptionsJson INSTANCE = new FieldRequestOptionsJson();

  private FieldRequestOptionsJson()
  {
    super(FieldRequestOptions.class, new Object[] { "includeBuzzData", "includeDebugData", "includeDeprecatedFields", "includeEmbedsData", "includeLegacyMediaData", "includePhotosData", "includeSharingData", "includeSparksData", "includeUrls" });
  }

  public static FieldRequestOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FieldRequestOptionsJson
 * JD-Core Version:    0.6.2
 */