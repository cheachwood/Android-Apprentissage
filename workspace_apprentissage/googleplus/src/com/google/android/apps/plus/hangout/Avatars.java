package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.util.ImageUtils;

public final class Avatars
{
  public static void renderAvatar(Context paramContext, MeetingMember paramMeetingMember, ImageView paramImageView)
  {
    VCard localVCard;
    if (paramMeetingMember == null)
    {
      localVCard = null;
      Bitmap localBitmap = null;
      if (localVCard != null)
      {
        byte[] arrayOfByte1 = localVCard.getAvatarData();
        localBitmap = null;
        if (arrayOfByte1 != null)
        {
          byte[] arrayOfByte2 = localVCard.getAvatarData();
          localBitmap = ImageUtils.decodeByteArray(arrayOfByte2, 0, arrayOfByte2.length);
        }
      }
      if (localBitmap == null)
        break label64;
      paramImageView.setImageBitmap(localBitmap);
    }
    while (true)
    {
      return;
      localVCard = paramMeetingMember.getVCard();
      break;
      label64: paramImageView.setImageDrawable(paramContext.getResources().getDrawable(R.drawable.ic_avatar));
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.Avatars
 * JD-Core Version:    0.6.2
 */