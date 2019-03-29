import { IItemCost } from 'app/shared/model/item-cost.model';

export interface IItem {
  id?: number;
  name?: string;
  locationDetail?: string;
  description?: string;
  costs?: IItemCost[];
  ownerLogin?: string;
  ownerId?: number;
  locationName?: string;
  locationId?: number;
}

export const defaultValue: Readonly<IItem> = {};
