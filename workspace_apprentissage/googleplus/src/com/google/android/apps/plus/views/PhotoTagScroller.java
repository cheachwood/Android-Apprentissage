package com.google.android.apps.plus.views;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.api.services.plusi.model.DataRectRelative;
import com.google.api.services.plusi.model.DataRectRelativeJson;
import java.util.ArrayList;

public class PhotoTagScroller extends HorizontalScrollView
  implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
  private PhotoTagAvatarView mCheckedAvatar;
  private View.OnClickListener mExternalClickListener;
  private boolean mHideTags = true;
  private Long mMyApprovedShapeId;
  private PhotoHeaderView mPhotoHeader;
  private final Rect mScrollerRect = new Rect();
  private boolean mShapeNeedsApproval;
  private ArrayList<PhotoTagAvatarView> mTags = new ArrayList();

  public PhotoTagScroller(Context paramContext)
  {
    super(paramContext);
  }

  public PhotoTagScroller(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public PhotoTagScroller(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bind(Context paramContext, EsAccount paramEsAccount, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(paramContext);
    long l1;
    Object localObject1;
    if (this.mCheckedAvatar != null)
    {
      Long localLong = (Long)this.mCheckedAvatar.getTag(R.id.tag_shape_id);
      if (localLong != null)
      {
        l1 = localLong.longValue();
        localObject1 = null;
        this.mTags.clear();
        this.mMyApprovedShapeId = null;
        this.mShapeNeedsApproval = false;
        paramViewGroup.removeAllViews();
        paramCursor.moveToPosition(-1);
      }
    }
    while (true)
    {
      label73: if (!paramCursor.moveToNext())
        break label604;
      long l2 = paramCursor.getLong(4);
      String str2 = paramCursor.getString(7);
      String str3 = paramCursor.getString(6);
      String str4 = paramCursor.getString(2);
      String str5 = paramCursor.getString(5);
      byte[] arrayOfByte = paramCursor.getBlob(0);
      boolean bool1 = TextUtils.equals(str5, "PENDING");
      boolean bool2 = TextUtils.equals(str5, "SUGGESTED");
      boolean bool3 = paramEsAccount.isMyGaiaId(str2);
      if ((!TextUtils.equals(str5, "DETECTED")) && (!TextUtils.equals(str5, "REJECTED")) && (!TextUtils.isEmpty(str3)))
      {
        DataRectRelative localDataRectRelative = (DataRectRelative)DataRectRelativeJson.getInstance().fromByteArray(arrayOfByte);
        View localView1;
        int i;
        if (bool3)
          if ((!bool1) && (!bool2))
          {
            localView1 = localLayoutInflater.inflate(R.layout.photo_tag_view, null);
            i = paramViewGroup.getChildCount();
            this.mMyApprovedShapeId = Long.valueOf(l2);
          }
        while (true)
        {
          paramViewGroup.addView(localView1, i);
          PhotoTagAvatarView localPhotoTagAvatarView = (PhotoTagAvatarView)localView1.findViewById(R.id.avatar);
          localPhotoTagAvatarView.setSubjectGaiaId(str2);
          this.mTags.add(localPhotoTagAvatarView);
          localPhotoTagAvatarView.setOnCheckedChangeListener(this);
          if (l1 == l2)
            localObject1 = localPhotoTagAvatarView;
          RectF localRectF2 = new RectF(localDataRectRelative.left.floatValue(), localDataRectRelative.top.floatValue(), localDataRectRelative.right.floatValue(), localDataRectRelative.bottom.floatValue());
          localPhotoTagAvatarView.setTag(R.id.tag_shape_rect, localRectF2);
          localPhotoTagAvatarView.setTag(R.id.tag_shape_name, str3);
          localPhotoTagAvatarView.setTag(R.id.tag_shape_id, Long.valueOf(l2));
          if (!bool3)
            break label73;
          if (!bool1)
            break label565;
          ((TextView)findViewById(R.id.name)).setText(str4);
          break label73;
          l1 = 0L;
          break;
          l1 = 0L;
          break;
          localView1 = localLayoutInflater.inflate(R.layout.photo_tag_approval_view, null);
          View localView2 = localView1.findViewById(R.id.tag_approve);
          localView2.setTag(R.id.tag_shape_id, Long.valueOf(l2));
          localView2.setTag(R.id.tag_is_suggestion, Boolean.valueOf(bool2));
          localView2.setTag(R.id.tag_gaiaid, str2);
          localView2.setOnClickListener(this);
          View localView3 = localView1.findViewById(R.id.tag_deny);
          localView3.setTag(R.id.tag_shape_id, Long.valueOf(l2));
          localView3.setTag(R.id.tag_is_suggestion, Boolean.valueOf(bool2));
          localView3.setTag(R.id.tag_gaiaid, str2);
          localView3.setOnClickListener(this);
          this.mShapeNeedsApproval = true;
          i = 0;
          continue;
          localView1 = localLayoutInflater.inflate(R.layout.photo_tag_view, null);
          i = paramViewGroup.getChildCount();
        }
        label565: if (bool2)
        {
          ((TextView)findViewById(R.id.name)).setText(str3);
          ((TextView)findViewById(R.id.second)).setText(R.string.photo_view_tag_suggestion_of_you);
        }
      }
    }
    label604: Object localObject2;
    if (paramViewGroup.getChildCount() > 0)
      if (localObject1 != null)
      {
        localObject2 = localObject1;
        RectF localRectF1 = (RectF)((PhotoTagAvatarView)localObject2).getTag(R.id.tag_shape_rect);
        String str1 = (String)((PhotoTagAvatarView)localObject2).getTag(R.id.tag_shape_name);
        this.mPhotoHeader.bindTagData(localRectF1, str1);
        ((PhotoTagAvatarView)localObject2).setChecked(true);
      }
    while (true)
    {
      invalidate();
      requestLayout();
      return;
      localObject2 = (PhotoTagAvatarView)paramViewGroup.getChildAt(0).findViewById(R.id.avatar);
      break;
      this.mPhotoHeader.bindTagData(null, null);
    }
  }

  public final Long getMyApprovedShapeId()
  {
    return this.mMyApprovedShapeId;
  }

  public final boolean hasTags()
  {
    if (this.mTags.size() > 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void hideTags(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mHideTags)
    {
      this.mHideTags = false;
      setVisibility(0);
      this.mPhotoHeader.showTagShape();
    }
  }

  public final boolean isWaitingMyApproval()
  {
    return this.mShapeNeedsApproval;
  }

  public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
  {
    PhotoTagAvatarView localPhotoTagAvatarView1;
    if ((paramCompoundButton instanceof PhotoTagAvatarView))
    {
      localPhotoTagAvatarView1 = (PhotoTagAvatarView)paramCompoundButton;
      if (!paramBoolean)
        break label88;
      if (paramCompoundButton != this.mCheckedAvatar);
    }
    else
    {
      return;
    }
    PhotoTagAvatarView localPhotoTagAvatarView2 = this.mCheckedAvatar;
    this.mCheckedAvatar = localPhotoTagAvatarView1;
    if (localPhotoTagAvatarView2 != null)
      localPhotoTagAvatarView2.setChecked(false);
    label47: RectF localRectF;
    if (this.mCheckedAvatar == null)
    {
      localRectF = null;
      label57: if (this.mCheckedAvatar != null)
        break label122;
    }
    label88: label122: for (CharSequence localCharSequence = null; ; localCharSequence = (CharSequence)this.mCheckedAvatar.getTag(R.id.tag_shape_name))
    {
      this.mPhotoHeader.bindTagData(localRectF, localCharSequence);
      this.mPhotoHeader.invalidate();
      break;
      if (paramCompoundButton != this.mCheckedAvatar)
        break label47;
      this.mCheckedAvatar = null;
      break label47;
      localRectF = (RectF)this.mCheckedAvatar.getTag(R.id.tag_shape_rect);
      break label57;
    }
  }

  public void onClick(View paramView)
  {
    if (this.mExternalClickListener != null)
      this.mExternalClickListener.onClick(paramView);
  }

  public void setExternalOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mExternalClickListener = paramOnClickListener;
  }

  public void setHeaderView(PhotoHeaderView paramPhotoHeaderView)
  {
    this.mPhotoHeader = paramPhotoHeaderView;
  }

  public static abstract interface PhotoShapeQuery
  {
    public static final String[] PROJECTION = { "bounds", "creator_id", "creator_name", "photo_id", "shape_id", "status", "subject_name", "subject_id" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoTagScroller
 * JD-Core Version:    0.6.2
 */