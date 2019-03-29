export const enum TimeFrame {
  MINUTE = 'MINUTE',
  HOUR = 'HOUR',
  DAY = 'DAY',
  MONTH = 'MONTH',
  YEAR = 'YEAR'
}

export interface IItemCost {
  id?: number;
  timeFrame?: TimeFrame;
  costPerTimeFrame?: number;
  itemName?: string;
  itemId?: number;
}

export const defaultValue: Readonly<IItemCost> = {};
