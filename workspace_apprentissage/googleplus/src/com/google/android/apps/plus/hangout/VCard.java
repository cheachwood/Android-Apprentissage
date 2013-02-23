package com.google.android.apps.plus.hangout;

import java.io.Serializable;

public final class VCard
  implements Serializable
{
  private static final long serialVersionUID = 1717157811110968432L;
  private byte[] avatarData;
  private String avatarHash;
  private String cellPhoneNumber;
  private String fullName;
  private String homePhoneNumber;
  private boolean isAvatarModified;
  private String workPhoneNumber;

  public VCard(String paramString1, boolean paramBoolean, byte[] paramArrayOfByte, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.fullName = paramString1;
    this.isAvatarModified = paramBoolean;
    this.avatarData = paramArrayOfByte;
    this.avatarHash = paramString2;
    this.homePhoneNumber = paramString3;
    this.workPhoneNumber = paramString4;
    this.cellPhoneNumber = paramString5;
  }

  public final byte[] getAvatarData()
  {
    return this.avatarData;
  }

  public final String getAvatarHash()
  {
    return this.avatarHash;
  }

  public final String getCellPhoneNumber()
  {
    return this.cellPhoneNumber;
  }

  public final String getFullName()
  {
    return this.fullName;
  }

  public final String getHomePhoneNumber()
  {
    return this.homePhoneNumber;
  }

  public final boolean getIsAvatarModified()
  {
    return this.isAvatarModified;
  }

  public final String getWorkPhoneNumber()
  {
    return this.workPhoneNumber;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.VCard
 * JD-Core Version:    0.6.2
 */