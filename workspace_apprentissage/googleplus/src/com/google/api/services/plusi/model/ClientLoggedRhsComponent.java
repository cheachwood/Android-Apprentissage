package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ClientLoggedRhsComponent extends GenericJson
{
  public Integer barType;
  public ClientLoggedRhsComponentType componentType;
  public Integer index;
  public List<ClientLoggedRhsComponentItem> item;
  public List<ClientLoggedRhsComponentType> neighborInfo;
  public ClientLoggedSuggestionSummaryInfo suggestionSummaryInfo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedRhsComponent
 * JD-Core Version:    0.6.2
 */