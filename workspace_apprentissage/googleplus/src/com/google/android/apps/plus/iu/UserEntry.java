package com.google.android.apps.plus.iu;

import com.android.gallery3d.common.Entry;
import com.android.gallery3d.common.Entry.Column;
import com.android.gallery3d.common.Entry.Table;
import com.android.gallery3d.common.EntrySchema;

@Entry.Table("users")
public final class UserEntry extends Entry
{
  public static final EntrySchema SCHEMA = new EntrySchema(UserEntry.class);

  @Entry.Column(indexed=true, value="account")
  public String account;

  public UserEntry()
  {
  }

  public UserEntry(String paramString)
  {
    this.account = paramString;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.UserEntry
 * JD-Core Version:    0.6.2
 */