import { Moment } from 'moment';

export interface ILending {
  id?: number;
  start?: Moment;
  plannedEnd?: Moment;
  end?: Moment;
  informedAboutEnd?: boolean;
  cost?: number;
  paid?: boolean;
  borrowerLogin?: string;
  borrowerId?: number;
  itemName?: string;
  itemId?: number;
}

export const defaultValue: Readonly<ILending> = {
  informedAboutEnd: false,
  paid: false
};
