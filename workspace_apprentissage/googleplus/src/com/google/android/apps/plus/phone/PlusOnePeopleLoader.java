package com.google.android.apps.plus.phone;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import com.google.android.apps.plus.api.GetPlusOnePeopleOperation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.PlusOnePeopleFragment.PeopleSetQuery;
import com.google.api.services.plusi.model.DataPerson;
import java.util.Iterator;
import java.util.List;

public final class PlusOnePeopleLoader extends EsCursorLoader
{
  private final EsAccount mAccount;
  private final String mPlusOneId;

  public PlusOnePeopleLoader(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    super(paramContext);
    this.mAccount = paramEsAccount;
    this.mPlusOneId = paramString;
  }

  public final Cursor esLoadInBackground()
  {
    Object localObject = null;
    GetPlusOnePeopleOperation localGetPlusOnePeopleOperation = new GetPlusOnePeopleOperation(getContext(), this.mAccount, null, null, this.mPlusOneId, 50);
    localGetPlusOnePeopleOperation.start();
    if (localGetPlusOnePeopleOperation.hasError())
      localGetPlusOnePeopleOperation.logError("PlusOnePeopleLoader");
    while (true)
    {
      return localObject;
      List localList = localGetPlusOnePeopleOperation.getPeople();
      localObject = null;
      if (localList != null)
      {
        MatrixCursor localMatrixCursor = new MatrixCursor(PlusOnePeopleFragment.PeopleSetQuery.PROJECTION, localList.size());
        int i = 0;
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
        {
          DataPerson localDataPerson = (DataPerson)localIterator.next();
          Object[] arrayOfObject = new Object[5];
          int j = i + 1;
          arrayOfObject[0] = Integer.valueOf(i);
          arrayOfObject[1] = ("g:" + localDataPerson.obfuscatedId);
          arrayOfObject[2] = localDataPerson.obfuscatedId;
          arrayOfObject[3] = localDataPerson.userName;
          arrayOfObject[4] = localDataPerson.photoUrl;
          localMatrixCursor.addRow(arrayOfObject);
          i = j;
        }
        localObject = localMatrixCursor;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PlusOnePeopleLoader
 * JD-Core Version:    0.6.2
 */