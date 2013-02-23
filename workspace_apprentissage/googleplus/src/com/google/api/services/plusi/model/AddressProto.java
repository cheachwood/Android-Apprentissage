package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class AddressProto extends GenericJson
{
  public List<AddressLinesProto> addressLines;
  public List<AddressComponentProto> component;
  public List<AddressComponentProto> crossStreet;
  public Boolean isMailing;
  public Boolean isPhysical;
  public List<AddressLinesProto> koreanAddressMigration;
  public Boolean unambiguouslyDesignatesFeature;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AddressProto
 * JD-Core Version:    0.6.2
 */