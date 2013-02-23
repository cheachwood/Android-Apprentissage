package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPhotosDataApiary;
import com.google.android.apps.plus.content.EsPostsData;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.api.services.plusi.model.DataPlusOne;

public final class CommentOptimisticPlusOneOperation extends PlusOneOperation
{
  private String mActivityId;
  private long mPhotoId;

  public CommentOptimisticPlusOneOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString1, long paramLong, String paramString2, boolean paramBoolean)
  {
    super(paramContext, paramEsAccount, paramIntent, paramOperationListener, "TACO_COMMENT", paramString2, paramBoolean);
    this.mActivityId = paramString1;
    this.mPhotoId = paramLong;
  }

  protected final void onFailure()
  {
    boolean bool1 = true;
    boolean bool2;
    Context localContext1;
    EsAccount localEsAccount1;
    String str1;
    String str2;
    if (this.mActivityId != null)
    {
      Context localContext2 = this.mContext;
      EsAccount localEsAccount2 = this.mAccount;
      String str3 = this.mActivityId;
      String str4 = this.mItemId;
      if (!this.mIsPlusOne)
      {
        bool2 = bool1;
        EsPostsData.plusOneComment(localContext2, localEsAccount2, str3, str4, bool2);
      }
    }
    else if (this.mPhotoId != 0L)
    {
      localContext1 = this.mContext;
      localEsAccount1 = this.mAccount;
      str1 = Long.toString(this.mPhotoId);
      str2 = this.mItemId;
      if (this.mIsPlusOne)
        break label116;
    }
    while (true)
    {
      EsPhotosDataApiary.updatePhotoCommentPlusOne(localContext1, localEsAccount1, str1, str2, bool1);
      return;
      bool2 = false;
      break;
      label116: bool1 = false;
    }
  }

  protected final void onPopulateRequest()
  {
    if (this.mActivityId != null)
      EsPostsData.plusOneComment(this.mContext, this.mAccount, this.mActivityId, this.mItemId, this.mIsPlusOne);
    if (this.mPhotoId != 0L)
      EsPhotosDataApiary.updatePhotoCommentPlusOne(this.mContext, this.mAccount, Long.toString(this.mPhotoId), this.mItemId, this.mIsPlusOne);
  }

  protected final void onSuccess(DataPlusOne paramDataPlusOne)
  {
    if ((paramDataPlusOne != null) && (this.mActivityId != null))
      EsPostsData.updateCommentPlusOneId(this.mContext, this.mAccount, this.mActivityId, this.mItemId, paramDataPlusOne.id);
    if ((paramDataPlusOne != null) && (this.mPhotoId != 0L))
      EsPhotosDataApiary.updatePhotoCommentPlusOne(this.mContext, this.mAccount, Long.toString(this.mPhotoId), this.mItemId, paramDataPlusOne, true);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.CommentOptimisticPlusOneOperation
 * JD-Core Version:    0.6.2
 */