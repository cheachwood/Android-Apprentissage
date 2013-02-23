package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class Contacts extends GenericJson
{
  public List<TaggedAddress> address;
  public List<TaggedEmail> email;
  public List<TaggedIm> instantMessage;
  public List<TaggedPhone> phone;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.Contacts
 * JD-Core Version:    0.6.2
 */