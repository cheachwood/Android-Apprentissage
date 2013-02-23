package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class EventFrame extends GenericJson
{
  public Long firstTimeMillis;
  public List<Invitee> invitee;
  public Long lastTimeMillis;
  public List<EmbedsPerson> person;
  public Integer totalPersons;
  public String verbType;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventFrame
 * JD-Core Version:    0.6.2
 */