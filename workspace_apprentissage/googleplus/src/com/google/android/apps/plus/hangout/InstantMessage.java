package com.google.android.apps.plus.hangout;

final class InstantMessage
{
  private final MeetingMember from;
  private final String fromMucJid;
  private final String message;

  static
  {
    if (!InstantMessage.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  InstantMessage(MeetingMember paramMeetingMember, String paramString1, String paramString2)
  {
    assert ((paramMeetingMember == null) || (paramMeetingMember.getMucJid().equals(paramString1)));
    this.from = paramMeetingMember;
    this.fromMucJid = paramString1;
    this.message = paramString2;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.InstantMessage
 * JD-Core Version:    0.6.2
 */