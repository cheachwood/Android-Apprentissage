package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClickTrackingCGIJson extends EsJson<ClickTrackingCGI>
{
  static final ClickTrackingCGIJson INSTANCE = new ClickTrackingCGIJson();

  private ClickTrackingCGIJson()
  {
    super(ClickTrackingCGI.class, new Object[] { "deprecated1", "deprecated2", "doNotLogUrl", "elementIndex", "nonArchivalVeIndex", "pageStart", "resultGroupElementIndex", "resultIndex", "style", "veIndex", "veType" });
  }

  public static ClickTrackingCGIJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClickTrackingCGIJson
 * JD-Core Version:    0.6.2
 */