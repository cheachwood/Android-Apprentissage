package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class EntitySquaresData extends GenericJson
{
  public List<EntitySquaresDataSquareInvite> invite;
  public List<EntitySquaresDataSquareMembershipApproved> membershipApproved;
  public List<EntitySquaresDataSquareMembershipRequest> membershipRequest;
  public List<EntitySquaresDataNewModerator> newModerator;
  public EntitySquaresDataRenderSquaresData renderSquaresData;
  public List<EntitySquaresDataSquareNameChange> squareNameChange;
  public List<EntitySquaresDataSquareSubscription> subscription;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySquaresData
 * JD-Core Version:    0.6.2
 */