package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EmotishareJson extends EsJson<Emotishare>
{
  static final EmotishareJson INSTANCE = new EmotishareJson();

  private EmotishareJson()
  {
    super(Emotishare.class, new Object[] { "description", "emotion", "name", ThumbnailJson.class, "proxiedImage", "url" });
  }

  public static EmotishareJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EmotishareJson
 * JD-Core Version:    0.6.2
 */