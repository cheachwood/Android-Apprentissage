package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ContactsJson extends EsJson<Contacts>
{
  static final ContactsJson INSTANCE = new ContactsJson();

  private ContactsJson()
  {
    super(Contacts.class, new Object[] { TaggedAddressJson.class, "address", TaggedEmailJson.class, "email", TaggedImJson.class, "instantMessage", TaggedPhoneJson.class, "phone" });
  }

  public static ContactsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ContactsJson
 * JD-Core Version:    0.6.2
 */