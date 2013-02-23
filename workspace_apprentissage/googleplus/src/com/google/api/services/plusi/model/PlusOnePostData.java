package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PlusOnePostData extends GenericJson
{
  public List<CommonPerson> author;
  public String faviconUrl;
  public String id;
  public ItemScope target;
  public Long timestampUsec;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusOnePostData
 * JD-Core Version:    0.6.2
 */