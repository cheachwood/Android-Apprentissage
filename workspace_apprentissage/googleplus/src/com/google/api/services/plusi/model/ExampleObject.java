package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ExampleObject extends GenericJson
{
  public ExampleObject about;
  public List<String> additionalName;
  public List<ExampleObjectAttendee> attendee;
  public List<ExampleObject> author;
  public List<EmbedClientItem> contributor;
  public String description;
  public String forClientOnly;
  public String forOwnerOnly;
  public String imageUrl;
  public ExampleObject itemExtensionField;
  public List<ExampleObject> itemRepeatedExtensionField;
  public Place location;
  public String name;
  public String stringExtensionField;
  public List<String> stringRepeatedExtensionField;
  public EmbedClientItem thumbnail;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ExampleObject
 * JD-Core Version:    0.6.2
 */