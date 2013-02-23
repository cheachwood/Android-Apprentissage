package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.content.SquareTargetData;
import com.google.android.apps.plus.util.EsLog;
import java.util.ArrayList;

public class TextOnlyAudienceView extends AudienceView
{
  private View mAudienceHint;
  private ImageView mAudienceIcon;
  private ConstrainedTextView mAudienceNames;
  private ChevronDirection mChevronDirection = ChevronDirection.POINT_DOWN;
  private ImageView mChevronIcon;

  public TextOnlyAudienceView(Context paramContext)
  {
    this(paramContext, null);
  }

  public TextOnlyAudienceView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public TextOnlyAudienceView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, false);
  }

  private TextOnlyAudienceView(Context paramContext, AttributeSet paramAttributeSet, int paramInt, boolean paramBoolean)
  {
    super(paramContext, paramAttributeSet, paramInt, false);
  }

  protected final void addChip(int paramInt)
  {
  }

  protected final int getChipCount()
  {
    return 0;
  }

  protected final void init()
  {
    addView(inflate(R.layout.audience_view_text_only));
    this.mAudienceNames = ((ConstrainedTextView)findViewById(R.id.audience_names_container));
    this.mAudienceIcon = ((ImageView)findViewById(R.id.audience_to_icon));
    this.mChevronIcon = ((ImageView)findViewById(R.id.chevron_icon));
    this.mAudienceHint = findViewById(R.id.audience_to_text);
    update();
  }

  protected final void removeLastChip()
  {
  }

  public void setChevronDirection(ChevronDirection paramChevronDirection)
  {
    int i;
    switch (1.$SwitchMap$com$google$android$apps$plus$views$TextOnlyAudienceView$ChevronDirection[paramChevronDirection.ordinal()])
    {
    default:
      paramChevronDirection = ChevronDirection.POINT_DOWN;
      i = R.drawable.ic_down;
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      this.mChevronDirection = paramChevronDirection;
      if (this.mChevronIcon != null)
        this.mChevronIcon.setImageResource(i);
      return;
      i = R.drawable.ic_left;
      continue;
      i = R.drawable.ic_right;
      continue;
      i = R.drawable.ic_up;
    }
  }

  public void setChevronVisibility(int paramInt)
  {
    if (this.mChevronIcon != null)
      this.mChevronIcon.setVisibility(paramInt);
  }

  protected final void update()
  {
    int i = this.mChips.size();
    Resources localResources = getResources();
    String str1 = localResources.getString(R.string.compose_acl_separator);
    String str2 = localResources.getString(17039374);
    String str3 = localResources.getString(R.string.loading);
    String str4 = localResources.getString(R.string.square_unknown);
    StringBuilder localStringBuilder = new StringBuilder();
    int i5;
    int i6;
    if (i == 0)
    {
      i5 = 0;
      i6 = 8;
      this.mAudienceNames.setText(localStringBuilder.toString());
      this.mAudienceIcon.setVisibility(i6);
      this.mAudienceHint.setVisibility(i5);
      if (this.mAudienceChangedCallback != null)
        this.mAudienceChangedCallback.run();
      return;
    }
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    if (i3 < i)
    {
      AudienceData localAudienceData = (AudienceData)this.mChips.get(i3);
      int i7;
      label167: int i8;
      label184: int i10;
      label259: Object localObject;
      if (localAudienceData.getCircleCount() == 1)
      {
        i7 = 1;
        if (i7 == 0)
          break label323;
        if (localAudienceData.getCircle(0) != null)
          break label317;
        i8 = 1;
        if ((i8 != 0) && (EsLog.isLoggable("TextOnlyAudienceView", 4)))
          Log.i("TextOnlyAudienceView", "Circle in AudienceData was null: " + localAudienceData.toString());
        if ((i7 == 0) || (i8 != 0))
          break label387;
        CircleData localCircleData = localAudienceData.getCircle(0);
        i10 = localCircleData.getType();
        if (i10 != 9)
          break label329;
        j = 1;
        String str9 = localCircleData.getName();
        if (TextUtils.isEmpty(str9))
          break label380;
        localObject = str9;
      }
      while (true)
      {
        localStringBuilder.append((String)localObject);
        int i9 = i - 1;
        if (i3 < i9)
          localStringBuilder.append(str1);
        i3++;
        break;
        i7 = 0;
        break label167;
        label317: i8 = 0;
        break label184;
        label323: i8 = 0;
        break label184;
        label329: if (i10 == 8)
        {
          k = 1;
          break label259;
        }
        if (i10 == 7)
        {
          n = 1;
          break label259;
        }
        if (i10 == 5)
        {
          i1 = 1;
          break label259;
        }
        if (i10 != 101)
          break label259;
        i2 = 1;
        break label259;
        label380: localObject = str3;
        continue;
        label387: if (localAudienceData.getUserCount() == 1)
        {
          PersonData localPersonData = localAudienceData.getUser(0);
          String str7 = localPersonData.getName();
          String str8 = localPersonData.getEmail();
          if (!TextUtils.isEmpty(str7))
            localObject = str7;
          while (true)
          {
            break;
            if (!TextUtils.isEmpty(str8))
              localObject = str8;
            else
              localObject = str2;
          }
        }
        if (localAudienceData.getSquareTargetCount() != 1)
          break label542;
        SquareTargetData localSquareTargetData = localAudienceData.getSquareTarget(0);
        String str5 = localSquareTargetData.getSquareName();
        String str6 = localSquareTargetData.getSquareStreamName();
        if (TextUtils.isEmpty(str5))
          str5 = str4;
        m = 1;
        if (TextUtils.isEmpty(str6))
          localObject = str5;
        else
          localObject = localResources.getString(R.string.square_name_and_topic, new Object[] { str5, str6 });
      }
      label542: throw new IllegalArgumentException();
    }
    int i4 = R.drawable.ic_person_active;
    if (m != 0)
      i4 = R.drawable.ic_nav_communities;
    while (true)
    {
      this.mAudienceIcon.setImageResource(i4);
      i5 = 8;
      i6 = 0;
      break;
      if (i == 1)
        if (j != 0)
          i4 = R.drawable.ic_public_active;
        else if (k != 0)
          i4 = R.drawable.list_domain;
        else if (n != 0)
          i4 = R.drawable.list_extended;
        else if (i1 != 0)
          i4 = R.drawable.ic_circles_active;
        else if (i2 != 0)
          i4 = R.drawable.ic_private;
    }
  }

  protected final void updateChip(int paramInt1, int paramInt2, int paramInt3, String paramString, Object paramObject, boolean paramBoolean)
  {
  }

  public static enum ChevronDirection
  {
    static
    {
      POINT_LEFT = new ChevronDirection("POINT_LEFT", 1);
      POINT_UP = new ChevronDirection("POINT_UP", 2);
      POINT_DOWN = new ChevronDirection("POINT_DOWN", 3);
      ChevronDirection[] arrayOfChevronDirection = new ChevronDirection[4];
      arrayOfChevronDirection[0] = POINT_RIGHT;
      arrayOfChevronDirection[1] = POINT_LEFT;
      arrayOfChevronDirection[2] = POINT_UP;
      arrayOfChevronDirection[3] = POINT_DOWN;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.TextOnlyAudienceView
 * JD-Core Version:    0.6.2
 */