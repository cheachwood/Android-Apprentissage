package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class AddressComponentProto extends GenericJson
{
  public FeatureIdProto featureId;
  public Integer featureType;
  public List<NameProto> parsedName;
  public AddressRangeProto range;
  public String type;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AddressComponentProto
 * JD-Core Version:    0.6.2
 */