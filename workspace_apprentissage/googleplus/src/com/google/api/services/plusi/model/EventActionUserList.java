package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class EventActionUserList extends GenericJson
{
  public EntityEventsDataChangedFields changedFields;
  public String eventAction;
  public List<String> inviterOid;
  public Boolean isNew;
  public List<EmbedsPerson> user;
  public List<String> userOid;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventActionUserList
 * JD-Core Version:    0.6.2
 */