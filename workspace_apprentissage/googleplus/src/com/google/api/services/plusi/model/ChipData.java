package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ChipData extends GenericJson
{
  public Boolean active;
  public Integer displayNumber;
  public String displayText;
  public String iconCss;
  public String iconUrl;
  public String language;
  public String payload;
  public List<ChipData> subChip;
  public String type;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ChipData
 * JD-Core Version:    0.6.2
 */