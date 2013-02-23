package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class Metadata extends GenericJson
{
  public String aclModelJson;
  public Boolean editable;
  public List<String> focusGroup;
  public Boolean publicAcl;
  public String scope;
  public SharingRoster sharingRoster;
  public Boolean useDefaultAcl;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.Metadata
 * JD-Core Version:    0.6.2
 */