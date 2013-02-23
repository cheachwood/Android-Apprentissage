package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ExampleObjectJson extends EsJson<ExampleObject>
{
  static final ExampleObjectJson INSTANCE = new ExampleObjectJson();

  private ExampleObjectJson()
  {
    super(ExampleObject.class, new Object[] { ExampleObjectJson.class, "about", "additionalName", ExampleObjectAttendeeJson.class, "attendee", ExampleObjectJson.class, "author", EmbedClientItemJson.class, "contributor", "description", "forClientOnly", "forOwnerOnly", "imageUrl", ExampleObjectJson.class, "itemExtensionField", ExampleObjectJson.class, "itemRepeatedExtensionField", PlaceJson.class, "location", "name", "stringExtensionField", "stringRepeatedExtensionField", EmbedClientItemJson.class, "thumbnail", "url" });
  }

  public static ExampleObjectJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ExampleObjectJson
 * JD-Core Version:    0.6.2
 */