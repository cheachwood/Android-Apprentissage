package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.text.TextUtils;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.PeopleSearchQueryOperation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.network.HttpOperation;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.api.services.plusi.model.DataCircleMemberId;
import com.google.api.services.plusi.model.DataCircleMemberProperties;
import com.google.api.services.plusi.model.DataCircleMemberPropertiesEntityInfo;
import com.google.api.services.plusi.model.PeopleResult;
import java.util.List;

public final class PublicProfileSearchLoader extends EsCursorLoader
{
  public static final MatrixCursor ABORTED = new MatrixCursor(new String[0]);
  private final EsAccount mAccount;
  private boolean mIncludePlusPages = true;
  private final int mMinQueryLength;
  private volatile PeopleSearchQueryOperation mOperation;
  private final String[] mProjection;
  private final String mQuery;
  private final String mToken;

  public PublicProfileSearchLoader(Context paramContext, EsAccount paramEsAccount, String[] paramArrayOfString, String paramString1, int paramInt, boolean paramBoolean1, boolean paramBoolean2, String paramString2)
  {
    super(paramContext);
    this.mAccount = paramEsAccount;
    this.mProjection = paramArrayOfString;
    this.mQuery = paramString1;
    this.mMinQueryLength = 2;
    this.mIncludePlusPages = paramBoolean1;
    if (paramBoolean2);
    for (String str = "gaia_id IS NOT NULL"; ; str = null)
    {
      setSelection(str);
      this.mToken = paramString2;
      return;
    }
  }

  private void abort()
  {
    PeopleSearchQueryOperation localPeopleSearchQueryOperation = this.mOperation;
    if (localPeopleSearchQueryOperation != null)
      localPeopleSearchQueryOperation.abort();
    this.mOperation = null;
  }

  public final boolean cancelLoad()
  {
    abort();
    return super.cancelLoad();
  }

  public final Cursor esLoadInBackground()
  {
    Object localObject1;
    if ((TextUtils.isEmpty(this.mQuery)) || (this.mQuery.length() < this.mMinQueryLength))
      localObject1 = new EsMatrixCursor(this.mProjection);
    label265: label571: label581: label601: 
    while (true)
    {
      return localObject1;
      PeopleSearchQueryOperation localPeopleSearchQueryOperation = new PeopleSearchQueryOperation(getContext(), this.mAccount, this.mQuery, this.mToken, this.mIncludePlusPages, null, null);
      this.mOperation = localPeopleSearchQueryOperation;
      try
      {
        localPeopleSearchQueryOperation.start();
        if (localPeopleSearchQueryOperation.isAborted())
        {
          localObject1 = ABORTED;
          this.mOperation = null;
          continue;
        }
        this.mOperation = null;
        if (localPeopleSearchQueryOperation.hasError())
        {
          localPeopleSearchQueryOperation.logError("PublicProfileSearch");
          localObject1 = null;
        }
      }
      finally
      {
        this.mOperation = null;
      }
      List localList = localPeopleSearchQueryOperation.getPeopleSearchResults();
      String str1 = localPeopleSearchQueryOperation.getContinuationToken();
      Resources localResources = getContext().getResources();
      Object[] arrayOfObject1 = new Object[this.mProjection.length];
      arrayOfObject1[0] = this.mToken;
      arrayOfObject1[1] = str1;
      ((EsMatrixCursor)localObject1).addRow(arrayOfObject1);
      int i;
      if (localList != null)
        i = localList.size();
      for (int j = 0; ; j++)
      {
        if (j >= i)
          break label601;
        PeopleResult localPeopleResult = (PeopleResult)localList.get(j);
        DataCircleMemberProperties localDataCircleMemberProperties = localPeopleResult.memberProperties;
        DataCircleMemberId localDataCircleMemberId = localPeopleResult.memberId;
        if ((localDataCircleMemberId != null) && (localDataCircleMemberProperties != null))
        {
          Object[] arrayOfObject2 = new Object[this.mProjection.length];
          int k = 0;
          if (k < this.mProjection.length)
          {
            String str2 = this.mProjection[k];
            if ("_id".equals(str2))
              arrayOfObject2[k] = Integer.valueOf(j);
            label483: 
            do
              while (true)
              {
                k++;
                break label265;
                i = 0;
                break;
                if ("gaia_id".equals(str2))
                {
                  arrayOfObject2[k] = localDataCircleMemberId.obfuscatedGaiaId;
                }
                else if ("person_id".equals(str2))
                {
                  arrayOfObject2[k] = ("g:" + localDataCircleMemberId.obfuscatedGaiaId);
                }
                else if ("name".equals(str2))
                {
                  arrayOfObject2[k] = localDataCircleMemberProperties.displayName;
                }
                else if ("profile_type".equals(str2))
                {
                  if ((localDataCircleMemberProperties.entityInfo != null) && (localDataCircleMemberProperties.entityInfo.type != null))
                    arrayOfObject2[k] = localDataCircleMemberProperties.entityInfo.type;
                  else
                    arrayOfObject2[k] = Integer.valueOf(1);
                }
                else
                {
                  if (!"avatar".equals(str2))
                    break label483;
                  arrayOfObject2[k] = EsAvatarData.compressAvatarUrl(localDataCircleMemberProperties.photoUrl);
                }
              }
            while (!"snippet".equals(str2));
            String str3 = localPeopleResult.snippetHtml;
            if (str3 == null)
            {
              if (localDataCircleMemberProperties.company == null)
                break label581;
              if (localDataCircleMemberProperties.occupation == null)
                break label571;
              int m = R.string.people_search_job;
              Object[] arrayOfObject3 = new Object[2];
              arrayOfObject3[0] = localDataCircleMemberProperties.occupation;
              arrayOfObject3[1] = localDataCircleMemberProperties.company;
              str3 = localResources.getString(m, arrayOfObject3);
            }
            while (true)
            {
              arrayOfObject2[k] = str3;
              break;
              str3 = localDataCircleMemberProperties.company;
              continue;
              str3 = localDataCircleMemberProperties.occupation;
            }
          }
          ((EsMatrixCursor)localObject1).addRow(arrayOfObject2);
        }
      }
    }
  }

  public final String getToken()
  {
    return this.mToken;
  }

  public final void onAbandon()
  {
    abort();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PublicProfileSearchLoader
 * JD-Core Version:    0.6.2
 */