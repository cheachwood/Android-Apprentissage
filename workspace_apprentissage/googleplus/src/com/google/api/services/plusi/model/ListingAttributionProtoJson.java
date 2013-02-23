package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ListingAttributionProtoJson extends EsJson<ListingAttributionProto>
{
  static final ListingAttributionProtoJson INSTANCE = new ListingAttributionProtoJson();

  private ListingAttributionProtoJson()
  {
    super(ListingAttributionProto.class, new Object[] { LinkFragmentJson.class, "attribution", "attributionPosition", "attributionStyle" });
  }

  public static ListingAttributionProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ListingAttributionProtoJson
 * JD-Core Version:    0.6.2
 */