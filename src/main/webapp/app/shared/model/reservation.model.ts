import { Moment } from 'moment';

export interface IReservation {
  id?: number;
  start?: Moment;
  end?: Moment;
  borrowerLogin?: string;
  borrowerId?: number;
  itemName?: string;
  itemId?: number;
}

export const defaultValue: Readonly<IReservation> = {};
