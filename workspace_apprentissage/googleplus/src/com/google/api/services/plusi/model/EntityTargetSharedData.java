package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class EntityTargetSharedData extends GenericJson
{
  public List<String> actorOid;
  public List<EntityTargetSharedDataPost> post;
  public String targetId;
  public String titleSanitizedHtml;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityTargetSharedData
 * JD-Core Version:    0.6.2
 */