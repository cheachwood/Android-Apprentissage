package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CarouselFrameDataJson extends EsJson<CarouselFrameData>
{
  static final CarouselFrameDataJson INSTANCE = new CarouselFrameDataJson();

  private CarouselFrameDataJson()
  {
    super(CarouselFrameData.class, new Object[] { FrameJson.class, "frame" });
  }

  public static CarouselFrameDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CarouselFrameDataJson
 * JD-Core Version:    0.6.2
 */