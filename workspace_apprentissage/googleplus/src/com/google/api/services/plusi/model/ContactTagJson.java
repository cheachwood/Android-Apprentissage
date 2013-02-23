package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ContactTagJson extends EsJson<ContactTag>
{
  static final ContactTagJson INSTANCE = new ContactTagJson();

  private ContactTagJson()
  {
    super(ContactTag.class, new Object[] { "customTag", "tag" });
  }

  public static ContactTagJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ContactTagJson
 * JD-Core Version:    0.6.2
 */