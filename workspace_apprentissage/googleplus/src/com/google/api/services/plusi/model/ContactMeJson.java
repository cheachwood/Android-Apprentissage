package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ContactMeJson extends EsJson<ContactMe>
{
  static final ContactMeJson INSTANCE = new ContactMeJson();

  private ContactMeJson()
  {
    super(ContactMe.class, new Object[] { ContactMeFieldJson.class, "chat", ContactMeFieldJson.class, "email", ContactMeFieldJson.class, "hangout", ContactMeFieldJson.class, "phone", ContactMeFieldJson.class, "share" });
  }

  public static ContactMeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ContactMeJson
 * JD-Core Version:    0.6.2
 */