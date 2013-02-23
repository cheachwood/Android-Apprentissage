package com.google.android.apps.plus.phone;

import android.content.Context;
import android.database.Cursor;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.fragments.AddedToCircleFragment.AddedToCircleQuery;
import com.google.api.services.plusi.model.DataActor;
import java.util.List;

public final class AddedToCircleLoader extends EsCursorLoader
{
  private final EsAccount mAccount;
  private final List<DataActor> mActors;
  private final Context mContext;
  private final String mNotificationId;

  public AddedToCircleLoader(Context paramContext, EsAccount paramEsAccount, String paramString, List<DataActor> paramList)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mAccount = paramEsAccount;
    this.mNotificationId = paramString;
    this.mActors = paramList;
    int i = this.mActors.size();
    String[] arrayOfString = new String[i];
    StringBuilder localStringBuilder1 = new StringBuilder();
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder1.append("gaia_id IN (");
    localStringBuilder2.append("(CASE gaia_id");
    for (int j = 0; j < i; j++)
    {
      String str = ((DataActor)this.mActors.get(j)).obfuscatedGaiaId;
      arrayOfString[j] = str;
      if (j > 0)
        localStringBuilder1.append(',');
      localStringBuilder1.append(" ?");
      localStringBuilder2.append(" WHEN '");
      localStringBuilder2.append(str);
      localStringBuilder2.append("' THEN ");
      localStringBuilder2.append(j);
    }
    localStringBuilder1.append(')');
    localStringBuilder2.append(" END)");
    setUri(EsProvider.appendAccountParameter(EsProvider.CONTACTS_URI, paramEsAccount));
    setProjection(AddedToCircleFragment.AddedToCircleQuery.PROJECTION);
    setSelection(localStringBuilder1.toString());
    setSelectionArgs(arrayOfString);
    setSortOrder(localStringBuilder2.toString());
  }

  public final Cursor esLoadInBackground()
  {
    if (!EsPeopleData.hasCircleActionData(this.mContext, this.mAccount, this.mNotificationId))
      EsPeopleData.insertCircleActionData(this.mContext, this.mAccount, this.mNotificationId, this.mActors);
    return super.esLoadInBackground();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.AddedToCircleLoader
 * JD-Core Version:    0.6.2
 */