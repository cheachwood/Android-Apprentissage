package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class MoreDetailsProto extends GenericJson
{
  public List<MoreDetailsProtoProviderDetails> providerDetail;
  public List<DetailProto> summaryDetail;
  public StoryTitle title;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MoreDetailsProto
 * JD-Core Version:    0.6.2
 */