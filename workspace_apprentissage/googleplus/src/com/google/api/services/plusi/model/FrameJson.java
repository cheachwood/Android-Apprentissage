package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FrameJson extends EsJson<Frame>
{
  static final FrameJson INSTANCE = new FrameJson();

  private FrameJson()
  {
    super(Frame.class, arrayOfObject);
  }

  public static FrameJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FrameJson
 * JD-Core Version:    0.6.2
 */