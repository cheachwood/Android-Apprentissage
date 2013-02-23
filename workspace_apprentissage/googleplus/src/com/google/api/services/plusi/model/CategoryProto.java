package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class CategoryProto extends GenericJson
{
  public String categoryLabel;
  public GVerticalProto gvertical;
  public Boolean showClosedZippyEllipsis;
  public List<CategoryProtoItem> zippyClosedItem;
  public List<CategoryProtoItem> zippyOpenedItem;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CategoryProto
 * JD-Core Version:    0.6.2
 */